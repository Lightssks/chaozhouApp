package com.example.chaozhou.module.share.x;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.example.chaozhou.module.share.x.log.LogManager;

import java.lang.ref.WeakReference;


public class PhotoViewAttacher implements IPhotoView, OnTouchListener, OnGestureListener, OnGlobalLayoutListener {
    private static final String LOG_TAG = "PhotoViewAttacher";
    @SuppressLint("WrongConstant")
    private static final boolean DEBUG = Log.isLoggable("PhotoViewAttacher", 3);
    static final Interpolator sInterpolator = new AccelerateDecelerateInterpolator();
    int ZOOM_DURATION = 200;
    static final int EDGE_NONE = -1;
    static final int EDGE_LEFT = 0;
    static final int EDGE_RIGHT = 1;
    static final int EDGE_BOTH = 2;
    private float mMinScale = 1.0F;
    private float mMidScale = 1.75F;
    private float mMaxScale = 3.0F;
    private boolean mAllowParentInterceptOnEdge = true;
    private WeakReference<ImageView> mImageView;
    private GestureDetector mGestureDetector;
    private com.example.chaozhou.module.share.x.GestureDetector mScaleDragDetector;
    private final Matrix mBaseMatrix = new Matrix();
    private final Matrix mDrawMatrix = new Matrix();
    private final Matrix mSuppMatrix = new Matrix();
    private final RectF mDisplayRect = new RectF();
    private final float[] mMatrixValues = new float[9];
    private PhotoViewAttacher.OnMatrixChangedListener mMatrixChangeListener;
    private PhotoViewAttacher.OnPhotoTapListener mPhotoTapListener;
    private PhotoViewAttacher.OnViewTapListener mViewTapListener;
    private OnLongClickListener mLongClickListener;
    private int mIvTop;
    private int mIvRight;
    private int mIvBottom;
    private int mIvLeft;
    private PhotoViewAttacher.FlingRunnable mCurrentFlingRunnable;
    private int mScrollEdge = 2;
    private boolean mRotationDetectionEnabled = false;
    private boolean mZoomEnabled;
    private ScaleType mScaleType;
    private float mLastRotation;

    private static void checkZoomLevels(float minZoom, float midZoom, float maxZoom) {
        if (minZoom >= midZoom) {
            throw new IllegalArgumentException("MinZoom has to be less than MidZoom");
        } else if (midZoom >= maxZoom) {
            throw new IllegalArgumentException("MidZoom has to be less than MaxZoom");
        }
    }

    private static boolean hasDrawable(ImageView imageView) {
        return imageView != null && imageView.getDrawable() != null;
    }

    private static boolean isSupportedScaleType(ScaleType scaleType) {
        if (scaleType == null) {
            return false;
        } else {
            switch(scaleType.ordinal()) {
                case 8:
                    throw new IllegalArgumentException(scaleType.name() + " is not supported in PhotoView");
                default:
                    return true;
            }
        }
    }

    private static void setImageViewScaleTypeMatrix(ImageView imageView) {
        if (imageView != null && !(imageView instanceof IPhotoView) && !ScaleType.MATRIX.equals(imageView.getScaleType())) {
            imageView.setScaleType(ScaleType.MATRIX);
        }

    }

    public PhotoViewAttacher(ImageView imageView) {
        this.mScaleType = ScaleType.FIT_CENTER;
        this.mLastRotation = 0.0F;
        this.mImageView = new WeakReference(imageView);
        imageView.setDrawingCacheEnabled(true);
        imageView.setOnTouchListener(this);
        ViewTreeObserver observer = imageView.getViewTreeObserver();
        if (observer != null) {
            observer.addOnGlobalLayoutListener(this);
        }

        setImageViewScaleTypeMatrix(imageView);
        if (!imageView.isInEditMode()) {
            this.mScaleDragDetector = VersionedGestureDetector.newInstance(imageView.getContext(), this);
            this.mGestureDetector = new GestureDetector(imageView.getContext(), new SimpleOnGestureListener() {
                public void onLongPress(MotionEvent e) {
                    if (PhotoViewAttacher.this.mLongClickListener != null) {
                        PhotoViewAttacher.this.mLongClickListener.onLongClick(PhotoViewAttacher.this.getImageView());
                    }

                }
            });
            this.mGestureDetector.setOnDoubleTapListener(new DefaultOnDoubleTapListener(this));
            this.setZoomable(true);
        }
    }

    public void setOnDoubleTapListener(OnDoubleTapListener newOnDoubleTapListener) {
        if (newOnDoubleTapListener != null) {
            this.mGestureDetector.setOnDoubleTapListener(newOnDoubleTapListener);
        } else {
            this.mGestureDetector.setOnDoubleTapListener(new DefaultOnDoubleTapListener(this));
        }

    }

    public boolean canZoom() {
        return this.mZoomEnabled;
    }

    public void cleanup() {
        if (this.mImageView != null) {
            ImageView imageView = (ImageView)this.mImageView.get();
            if (imageView != null) {
                ViewTreeObserver observer = imageView.getViewTreeObserver();
                if (observer != null && observer.isAlive()) {
                    observer.removeGlobalOnLayoutListener(this);
                }

                imageView.setOnTouchListener((OnTouchListener)null);
                this.cancelFling();
            }

            if (this.mGestureDetector != null) {
                this.mGestureDetector.setOnDoubleTapListener((OnDoubleTapListener)null);
            }

            this.mMatrixChangeListener = null;
            this.mPhotoTapListener = null;
            this.mViewTapListener = null;
            this.mImageView = null;
        }
    }

    public RectF getDisplayRect() {
        this.checkMatrixBounds();
        return this.getDisplayRect(this.getDrawMatrix());
    }

    public boolean setDisplayMatrix(Matrix finalMatrix) {
        if (finalMatrix == null) {
            throw new IllegalArgumentException("Matrix cannot be null");
        } else {
            ImageView imageView = this.getImageView();
            if (imageView == null) {
                return false;
            } else if (imageView.getDrawable() == null) {
                return false;
            } else {
                this.mSuppMatrix.set(finalMatrix);
                this.setImageViewMatrix(this.getDrawMatrix());
                this.checkMatrixBounds();
                return true;
            }
        }
    }

    public void setPhotoViewRotation(float degrees) {
        degrees %= 360.0F;
        this.mSuppMatrix.postRotate(this.mLastRotation - degrees);
        this.mLastRotation = degrees;
        this.checkAndDisplayMatrix();
    }

    public ImageView getImageView() {
        ImageView imageView = null;
        if (this.mImageView != null) {
            imageView = (ImageView)this.mImageView.get();
        }

        if (imageView == null) {
            this.cleanup();
            Log.i("PhotoViewAttacher", "ImageView no longer exists. You should not use this PhotoViewAttacher any more.");
        }

        return imageView;
    }

    /** @deprecated */
    @Deprecated
    public float getMinScale() {
        return this.getMinimumScale();
    }

    public float getMinimumScale() {
        return this.mMinScale;
    }

    /** @deprecated */
    @Deprecated
    public float getMidScale() {
        return this.getMediumScale();
    }

    public float getMediumScale() {
        return this.mMidScale;
    }

    /** @deprecated */
    @Deprecated
    public float getMaxScale() {
        return this.getMaximumScale();
    }

    public float getMaximumScale() {
        return this.mMaxScale;
    }

    public float getScale() {
        return (float) Math.sqrt((float)Math.pow((double)this.getValue(this.mSuppMatrix, 0), 2.0D) + (float)Math.pow((double)this.getValue(this.mSuppMatrix, 3), 2.0D));
    }

    public ScaleType getScaleType() {
        return this.mScaleType;
    }

    public void onDrag(float dx, float dy) {
        if (!this.mScaleDragDetector.isScaling()) {
            if (DEBUG) {
                LogManager.getLogger().d("PhotoViewAttacher", String.format("onDrag: dx: %.2f. dy: %.2f", dx, dy));
            }

            ImageView imageView = this.getImageView();
            this.mSuppMatrix.postTranslate(dx, dy);
            this.checkAndDisplayMatrix();
            ViewParent parent = imageView.getParent();
            if (this.mAllowParentInterceptOnEdge) {
                if ((this.mScrollEdge == 2 || this.mScrollEdge == 0 && dx >= 1.0F || this.mScrollEdge == 1 && dx <= -1.0F) && parent != null) {
                    parent.requestDisallowInterceptTouchEvent(false);
                }
            } else if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }

        }
    }

    public void onFling(float startX, float startY, float velocityX, float velocityY) {
        if (DEBUG) {
            LogManager.getLogger().d("PhotoViewAttacher", "onFling. sX: " + startX + " sY: " + startY + " Vx: " + velocityX + " Vy: " + velocityY);
        }

        ImageView imageView = this.getImageView();
        this.mCurrentFlingRunnable = new PhotoViewAttacher.FlingRunnable(imageView.getContext());
        this.mCurrentFlingRunnable.fling(this.getImageViewWidth(imageView), this.getImageViewHeight(imageView), (int)velocityX, (int)velocityY);
        imageView.post(this.mCurrentFlingRunnable);
    }

    public void onGlobalLayout() {
        ImageView imageView = this.getImageView();
        if (imageView != null) {
            if (this.mZoomEnabled) {
                int top = imageView.getTop();
                int right = imageView.getRight();
                int bottom = imageView.getBottom();
                int left = imageView.getLeft();
                if (top != this.mIvTop || bottom != this.mIvBottom || left != this.mIvLeft || right != this.mIvRight) {
                    this.updateBaseMatrix(imageView.getDrawable());
                    this.mIvTop = top;
                    this.mIvRight = right;
                    this.mIvBottom = bottom;
                    this.mIvLeft = left;
                }
            } else {
                this.updateBaseMatrix(imageView.getDrawable());
            }
        }

    }

    public void onScale(float scaleFactor, float focusX, float focusY) {
        if (DEBUG) {
            LogManager.getLogger().d("PhotoViewAttacher", String.format("onScale: scale: %.2f. fX: %.2f. fY: %.2f", scaleFactor, focusX, focusY));
        }

        if (this.getScale() < this.mMaxScale || scaleFactor < 1.0F) {
            this.mSuppMatrix.postScale(scaleFactor, scaleFactor, focusX, focusY);
            this.checkAndDisplayMatrix();
        }

    }

    public boolean onTouch(View v, MotionEvent ev) {
        boolean handled = false;
        if (this.mZoomEnabled && hasDrawable((ImageView)v)) {
            ViewParent parent = v.getParent();
            switch(ev.getAction()) {
                case 0:
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    } else {
                        Log.i("PhotoViewAttacher", "onTouch getParent() returned null");
                    }

                    this.cancelFling();
                    break;
                case 1:
                case 3:
                    if (this.getScale() < this.mMinScale) {
                        RectF rect = this.getDisplayRect();
                        if (rect != null) {
                            v.post(new PhotoViewAttacher.AnimatedZoomRunnable(this.getScale(), this.mMinScale, rect.centerX(), rect.centerY()));
                            handled = true;
                        }
                    }
                case 2:
            }

            if (this.mScaleDragDetector != null && this.mScaleDragDetector.onTouchEvent(ev)) {
                handled = true;
            }

            if (this.mGestureDetector != null && this.mGestureDetector.onTouchEvent(ev)) {
                handled = true;
            }
        }

        return handled;
    }

    public void setAllowParentInterceptOnEdge(boolean allow) {
        this.mAllowParentInterceptOnEdge = allow;
    }

    /** @deprecated */
    @Deprecated
    public void setMinScale(float minScale) {
        this.setMinimumScale(minScale);
    }

    public void setMinimumScale(float minimumScale) {
        checkZoomLevels(minimumScale, this.mMidScale, this.mMaxScale);
        this.mMinScale = minimumScale;
    }

    /** @deprecated */
    @Deprecated
    public void setMidScale(float midScale) {
        this.setMediumScale(midScale);
    }

    public void setMediumScale(float mediumScale) {
        checkZoomLevels(this.mMinScale, mediumScale, this.mMaxScale);
        this.mMidScale = mediumScale;
    }

    /** @deprecated */
    @Deprecated
    public void setMaxScale(float maxScale) {
        this.setMaximumScale(maxScale);
    }

    public void setMaximumScale(float maximumScale) {
        checkZoomLevels(this.mMinScale, this.mMidScale, maximumScale);
        this.mMaxScale = maximumScale;
    }

    public void setOnLongClickListener(OnLongClickListener listener) {
        this.mLongClickListener = listener;
    }

    public void setOnMatrixChangeListener(PhotoViewAttacher.OnMatrixChangedListener listener) {
        this.mMatrixChangeListener = listener;
    }

    public void setOnPhotoTapListener(PhotoViewAttacher.OnPhotoTapListener listener) {
        this.mPhotoTapListener = listener;
    }

    public PhotoViewAttacher.OnPhotoTapListener getOnPhotoTapListener() {
        return this.mPhotoTapListener;
    }

    public void setOnViewTapListener(PhotoViewAttacher.OnViewTapListener listener) {
        this.mViewTapListener = listener;
    }

    public PhotoViewAttacher.OnViewTapListener getOnViewTapListener() {
        return this.mViewTapListener;
    }

    public void setScale(float scale) {
        this.setScale(scale, false);
    }

    public void setScale(float scale, boolean animate) {
        ImageView imageView = this.getImageView();
        if (imageView != null) {
            this.setScale(scale, (float)(imageView.getRight() / 2), (float)(imageView.getBottom() / 2), animate);
        }

    }

    public void setScale(float scale, float focalX, float focalY, boolean animate) {
        ImageView imageView = this.getImageView();
        if (imageView != null) {
            if (scale < this.mMinScale || scale > this.mMaxScale) {
                LogManager.getLogger().i("PhotoViewAttacher", "Scale must be within the range of minScale and maxScale");
                return;
            }

            if (animate) {
                imageView.post(new PhotoViewAttacher.AnimatedZoomRunnable(this.getScale(), scale, focalX, focalY));
            } else {
                this.mSuppMatrix.setScale(scale, scale, focalX, focalY);
                this.checkAndDisplayMatrix();
            }
        }

    }

    public void setScaleType(ScaleType scaleType) {
        if (isSupportedScaleType(scaleType) && scaleType != this.mScaleType) {
            this.mScaleType = scaleType;
            this.update();
        }

    }

    public void setZoomable(boolean zoomable) {
        this.mZoomEnabled = zoomable;
        this.update();
    }

    public void update() {
        ImageView imageView = this.getImageView();
        if (imageView != null) {
            if (this.mZoomEnabled) {
                setImageViewScaleTypeMatrix(imageView);
                this.updateBaseMatrix(imageView.getDrawable());
            } else {
                this.resetMatrix();
            }
        }

    }

    public Matrix getDisplayMatrix() {
        return new Matrix(this.getDrawMatrix());
    }

    public Matrix getDrawMatrix() {
        this.mDrawMatrix.set(this.mBaseMatrix);
        this.mDrawMatrix.postConcat(this.mSuppMatrix);
        return this.mDrawMatrix;
    }

    private void cancelFling() {
        if (this.mCurrentFlingRunnable != null) {
            this.mCurrentFlingRunnable.cancelFling();
            this.mCurrentFlingRunnable = null;
        }

    }

    private void checkAndDisplayMatrix() {
        if (this.checkMatrixBounds()) {
            this.setImageViewMatrix(this.getDrawMatrix());
        }

    }

    private void checkImageViewScaleType() {
        ImageView imageView = this.getImageView();
        if (imageView != null && !(imageView instanceof IPhotoView) && !ScaleType.MATRIX.equals(imageView.getScaleType())) {
            throw new IllegalStateException("The ImageView's ScaleType has been changed since attaching a PhotoViewAttacher");
        }
    }

    private boolean checkMatrixBounds() {
        ImageView imageView = this.getImageView();
        if (imageView == null) {
            return false;
        } else {
            RectF rect = this.getDisplayRect(this.getDrawMatrix());
            if (rect == null) {
                return false;
            } else {
                float height = rect.height();
                float width = rect.width();
                float deltaX = 0.0F;
                float deltaY = 0.0F;
                int viewHeight = this.getImageViewHeight(imageView);
                if (height <= (float)viewHeight) {
                    switch(this.mScaleType.hashCode()) {
                        case 5:
                            deltaY = (float)viewHeight - height - rect.top;
                            break;
                        case 6:
                            deltaY = -rect.top;
                            break;
                        default:
                            deltaY = ((float)viewHeight - height) / 2.0F - rect.top;
                    }
                } else if (rect.top > 0.0F) {
                    deltaY = -rect.top;
                } else if (rect.bottom < (float)viewHeight) {
                    deltaY = (float)viewHeight - rect.bottom;
                }

                int viewWidth = this.getImageViewWidth(imageView);
                if (width <= (float)viewWidth) {
                    switch(this.mScaleType.ordinal()) {
                        case 5:
                            deltaX = (float)viewWidth - width - rect.left;
                            break;
                        case 6:
                            deltaX = -rect.left;
                            break;
                        default:
                            deltaX = ((float)viewWidth - width) / 2.0F - rect.left;
                    }

                    this.mScrollEdge = 2;
                } else if (rect.left > 0.0F) {
                    this.mScrollEdge = 0;
                    deltaX = -rect.left;
                } else if (rect.right < (float)viewWidth) {
                    deltaX = (float)viewWidth - rect.right;
                    this.mScrollEdge = 1;
                } else {
                    this.mScrollEdge = -1;
                }

                this.mSuppMatrix.postTranslate(deltaX, deltaY);
                return true;
            }
        }
    }

    private RectF getDisplayRect(Matrix matrix) {
        ImageView imageView = this.getImageView();
        if (imageView != null) {
            Drawable d = imageView.getDrawable();
            if (d != null) {
                this.mDisplayRect.set(0.0F, 0.0F, (float)d.getIntrinsicWidth(), (float)d.getIntrinsicHeight());
                matrix.mapRect(this.mDisplayRect);
                return this.mDisplayRect;
            }
        }

        return null;
    }

    public Bitmap getVisibleRectangleBitmap() {
        ImageView imageView = this.getImageView();
        return imageView == null ? null : imageView.getDrawingCache();
    }

    public void setZoomTransitionDuration(int milliseconds) {
        if (milliseconds < 0) {
            milliseconds = 200;
        }

        this.ZOOM_DURATION = milliseconds;
    }

    private float getValue(Matrix matrix, int whichValue) {
        matrix.getValues(this.mMatrixValues);
        return this.mMatrixValues[whichValue];
    }

    private void resetMatrix() {
        this.mSuppMatrix.reset();
        this.setImageViewMatrix(this.getDrawMatrix());
        this.checkMatrixBounds();
    }

    private void setImageViewMatrix(Matrix matrix) {
        ImageView imageView = this.getImageView();
        if (imageView != null) {
            this.checkImageViewScaleType();
            imageView.setImageMatrix(matrix);
            if (this.mMatrixChangeListener != null) {
                RectF displayRect = this.getDisplayRect(matrix);
                if (displayRect != null) {
                    this.mMatrixChangeListener.onMatrixChanged(displayRect);
                }
            }
        }

    }

    private void updateBaseMatrix(Drawable d) {
        ImageView imageView = this.getImageView();
        if (imageView != null && d != null) {
            float viewWidth = (float)this.getImageViewWidth(imageView);
            float viewHeight = (float)this.getImageViewHeight(imageView);
            int drawableWidth = d.getIntrinsicWidth();
            int drawableHeight = d.getIntrinsicHeight();
            this.mBaseMatrix.reset();
            float widthScale = viewWidth / (float)drawableWidth;
            float heightScale = viewHeight / (float)drawableHeight;
            if (this.mScaleType == ScaleType.CENTER) {
                this.mBaseMatrix.postTranslate((viewWidth - (float)drawableWidth) / 2.0F, (viewHeight - (float)drawableHeight) / 2.0F);
            } else {
                float scale;
                if (this.mScaleType == ScaleType.CENTER_CROP) {
                    scale = Math.max(widthScale, heightScale);
                    this.mBaseMatrix.postScale(scale, scale);
                    this.mBaseMatrix.postTranslate((viewWidth - (float)drawableWidth * scale) / 2.0F, (viewHeight - (float)drawableHeight * scale) / 2.0F);
                } else if (this.mScaleType == ScaleType.CENTER_INSIDE) {
                    scale = Math.min(1.0F, Math.min(widthScale, heightScale));
                    this.mBaseMatrix.postScale(scale, scale);
                    this.mBaseMatrix.postTranslate((viewWidth - (float)drawableWidth * scale) / 2.0F, (viewHeight - (float)drawableHeight * scale) / 2.0F);
                } else {
                    RectF mTempSrc = new RectF(0.0F, 0.0F, (float)drawableWidth, (float)drawableHeight);
                    RectF mTempDst = new RectF(0.0F, 0.0F, viewWidth, viewHeight);
                    switch(this.mScaleType.ordinal()) {
                        case 4:
                            this.mBaseMatrix.setRectToRect(mTempSrc, mTempDst, ScaleToFit.CENTER);
                            break;
                        case 5:
                            this.mBaseMatrix.setRectToRect(mTempSrc, mTempDst, ScaleToFit.END);
                            break;
                        case 6:
                            this.mBaseMatrix.setRectToRect(mTempSrc, mTempDst, ScaleToFit.START);
                            break;
                        case 7:
                            this.mBaseMatrix.setRectToRect(mTempSrc, mTempDst, ScaleToFit.FILL);
                    }
                }
            }

            this.resetMatrix();
        }
    }

    private int getImageViewWidth(ImageView imageView) {
        return imageView == null ? 0 : imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
    }

    private int getImageViewHeight(ImageView imageView) {
        return imageView == null ? 0 : imageView.getHeight() - imageView.getPaddingTop() - imageView.getPaddingBottom();
    }

    private class AnimatedZoomRunnable implements Runnable {
        private final float mFocalX;
        private final float mFocalY;
        private final long mStartTime;
        private final float mZoomStart;
        private final float mZoomEnd;

        public AnimatedZoomRunnable(float currentZoom, float targetZoom, float focalX, float focalY) {
            this.mFocalX = focalX;
            this.mFocalY = focalY;
            this.mStartTime = System.currentTimeMillis();
            this.mZoomStart = currentZoom;
            this.mZoomEnd = targetZoom;
        }

        public void run() {
            ImageView imageView = PhotoViewAttacher.this.getImageView();
            if (imageView != null) {
                float t = this.interpolate();
                float scale = this.mZoomStart + t * (this.mZoomEnd - this.mZoomStart);
                float deltaScale = scale / PhotoViewAttacher.this.getScale();
                PhotoViewAttacher.this.mSuppMatrix.postScale(deltaScale, deltaScale, this.mFocalX, this.mFocalY);
                PhotoViewAttacher.this.checkAndDisplayMatrix();
                if (t < 1.0F) {
                    Compat.postOnAnimation(imageView, this);
                }

            }
        }

        private float interpolate() {
            float t = 1.0F * (float)(System.currentTimeMillis() - this.mStartTime) / (float)PhotoViewAttacher.this.ZOOM_DURATION;
            t = Math.min(1.0F, t);
            t = PhotoViewAttacher.sInterpolator.getInterpolation(t);
            return t;
        }
    }

    private class FlingRunnable implements Runnable {
        private final ScrollerProxy mScroller;
        private int mCurrentX;
        private int mCurrentY;

        public FlingRunnable(Context context) {
            this.mScroller = ScrollerProxy.getScroller(context);
        }

        public void cancelFling() {
            if (PhotoViewAttacher.DEBUG) {
                LogManager.getLogger().d("PhotoViewAttacher", "Cancel Fling");
            }

            this.mScroller.forceFinished(true);
        }

        public void fling(int viewWidth, int viewHeight, int velocityX, int velocityY) {
            RectF rect = PhotoViewAttacher.this.getDisplayRect();
            if (rect != null) {
                int startX = Math.round(-rect.left);
                int minX;
                int maxX;
                if ((float)viewWidth < rect.width()) {
                    minX = 0;
                    maxX = Math.round(rect.width() - (float)viewWidth);
                } else {
                    maxX = startX;
                    minX = startX;
                }

                int startY = Math.round(-rect.top);
                int minY;
                int maxY;
                if ((float)viewHeight < rect.height()) {
                    minY = 0;
                    maxY = Math.round(rect.height() - (float)viewHeight);
                } else {
                    maxY = startY;
                    minY = startY;
                }

                this.mCurrentX = startX;
                this.mCurrentY = startY;
                if (PhotoViewAttacher.DEBUG) {
                    LogManager.getLogger().d("PhotoViewAttacher", "fling. StartX:" + startX + " StartY:" + startY + " MaxX:" + maxX + " MaxY:" + maxY);
                }

                if (startX != maxX || startY != maxY) {
                    this.mScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY, 0, 0);
                }

            }
        }

        public void run() {
            if (!this.mScroller.isFinished()) {
                ImageView imageView = PhotoViewAttacher.this.getImageView();
                if (imageView != null && this.mScroller.computeScrollOffset()) {
                    int newX = this.mScroller.getCurrX();
                    int newY = this.mScroller.getCurrY();
                    if (PhotoViewAttacher.DEBUG) {
                        LogManager.getLogger().d("PhotoViewAttacher", "fling run(). CurrentX:" + this.mCurrentX + " CurrentY:" + this.mCurrentY + " NewX:" + newX + " NewY:" + newY);
                    }

                    PhotoViewAttacher.this.mSuppMatrix.postTranslate((float)(this.mCurrentX - newX), (float)(this.mCurrentY - newY));
                    PhotoViewAttacher.this.setImageViewMatrix(PhotoViewAttacher.this.getDrawMatrix());
                    this.mCurrentX = newX;
                    this.mCurrentY = newY;
                    Compat.postOnAnimation(imageView, this);
                }

            }
        }
    }

    public interface OnMatrixChangedListener {
        void onMatrixChanged(RectF var1);
    }

    public interface OnPhotoTapListener {
        void onPhotoTap(View var1, float var2, float var3);
    }

    public interface OnViewTapListener {
        void onViewTap(View var1, float var2, float var3);
    }
}
