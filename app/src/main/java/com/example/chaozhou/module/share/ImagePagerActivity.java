package com.example.chaozhou.module.share;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.example.chaozhou.R;
import com.example.chaozhou.utils.HackyViewPager;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

public class ImagePagerActivity extends FragmentActivity {

    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";
    private HackyViewPager mPager;
    private int pagerPosition;
    private TextView indicator;

    public ImagePagerActivity() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_image_pager);
        this.pagerPosition = this.getIntent().getIntExtra("image_index", 0);
        ArrayList<String> urls = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);

        Logger.d("text2");
        this.mPager = (HackyViewPager)this.findViewById(R.id.pager);
        ImagePagerActivity.ImagePagerAdapter mAdapter = new ImagePagerActivity.ImagePagerAdapter(this.getSupportFragmentManager(), urls);
        this.mPager.setAdapter(mAdapter);
        this.indicator = (TextView)this.findViewById(R.id.indicator);
        CharSequence text = this.getString(R.string.viewpager_indicator, new Object[]{1, this.mPager.getAdapter().getCount()});
        this.indicator.setText(text);
        this.mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int arg0) {
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageSelected(int arg0) {
                CharSequence text = ImagePagerActivity.this.getString(R.string.viewpager_indicator, new Object[]{arg0 + 1, ImagePagerActivity.this.mPager.getAdapter().getCount()});
                ImagePagerActivity.this.indicator.setText(text);
            }
        });
        if (savedInstanceState != null) {
            this.pagerPosition = savedInstanceState.getInt("STATE_POSITION");
        }

        this.mPager.setCurrentItem(this.pagerPosition);
    }

    @SuppressLint("MissingSuperCall")
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("STATE_POSITION", this.mPager.getCurrentItem());
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {
        public ArrayList<String> fileList;

        public ImagePagerAdapter(FragmentManager fm, ArrayList<String> fileList) {
            super(fm);
            this.fileList = fileList;
        }

        public int getCount() {
            return this.fileList == null ? 0 : this.fileList.size();
        }

        public Fragment getItem(int position) {
            String url = this.fileList.get(position);
            Logger.d("text3"+ url);
            return ImageDetailFragment.newInstance(url );
        }
    }
}
