package com.example.chaozhou.module.share;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.chaozhou.R;
import com.example.chaozhou.module.share.x.PhotoViewAttacher;
import com.example.chaozhou.module.share.x.PhotoViewAttacher.OnPhotoTapListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class ImageDetailFragment extends Fragment {
    private String mImageUrl;
    private ImageView mImageView;
    private ProgressBar progressBar;
    private PhotoViewAttacher mAttacher;

    public ImageDetailFragment() {
    }

    public static ImageDetailFragment newInstance(String imageUrl) {
        ImageDetailFragment f = new ImageDetailFragment();
        Bundle args = new Bundle();
        args.putString("url", imageUrl);
        f.setArguments(args);
        return f;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mImageUrl = this.getArguments() != null ? this.getArguments().getString("url") : null;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.image_detail_fragment, container, false);
        this.mImageView = (ImageView)v.findViewById(R.id.image);
        this.mAttacher = new PhotoViewAttacher(this.mImageView);
        this.mAttacher.setOnPhotoTapListener(new OnPhotoTapListener() {
            public void onPhotoTap(View arg0, float arg1, float arg2) {
                ImageDetailFragment.this.getActivity().finish();
            }
        });
        this.progressBar = (ProgressBar)v.findViewById(R.id.loading);
        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageLoader.getInstance().displayImage(this.mImageUrl, this.mImageView, new SimpleImageLoadingListener() {
            @SuppressLint("WrongConstant")
            public void onLoadingStarted(String imageUri, View view) {
                ImageDetailFragment.this.progressBar.setVisibility(0);
            }

            @SuppressLint("WrongConstant")
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                String message = null;
                switch(failReason.getType().ordinal()) {
                    case 1:
                        message = "下载错误";
                        break;
                    case 2:
                        message = "图片无法显示";
                        break;
                    case 3:
                        message = "网络有问题，无法下载";
                        break;
                    case 4:
                        message = "图片太大无法显示";
                        break;
                    case 5:
                        message = "未知的错误";
                }

                Toast.makeText(ImageDetailFragment.this.getActivity(), message, 0).show();
                ImageDetailFragment.this.progressBar.setVisibility(8);
            }

            @SuppressLint("WrongConstant")
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                ImageDetailFragment.this.progressBar.setVisibility(8);
                ImageDetailFragment.this.mAttacher.update();
            }
        });
    }
}
