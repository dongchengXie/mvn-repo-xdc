package com.example.xdc.testimporteclipse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.xdc.mylibrary.ColorTrackTextView;

import java.util.ArrayList;
import java.util.List;

public class CustomFragment extends Fragment implements OnPageChangeListener {

    public final static String TAG = "com.example.newtest.CustomFragment";

    private View mView;
    private ColorTrackTextView mColorTrackTextViewLeft;
    private ColorTrackTextView mColorTrackTextViewMiddle;
    private ColorTrackTextView mColorTrackTextViewRight;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mFragmentPagerAdapter;

    private List<TestListFragment> mFragments;
    private List<ColorTrackTextView> tvs;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != mView) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (null != parent) {
                parent.removeView(mView);
            }
        } else {
            mView = inflater.inflate(R.layout.fragment_custom, null);
            initializeViews();

        }
        return mView;
    }

    private void initializeViews() {
        mColorTrackTextViewLeft = (ColorTrackTextView) mView.findViewById(R.id.color_track_textview_left);
        mColorTrackTextViewMiddle = (ColorTrackTextView) mView.findViewById(R.id.color_track_textview_middle);
        mColorTrackTextViewRight = (ColorTrackTextView) mView.findViewById(R.id.color_track_textview_right);
        tvs = new ArrayList<ColorTrackTextView>();
        tvs.add(mColorTrackTextViewLeft);
        tvs.add(mColorTrackTextViewMiddle);
        tvs.add(mColorTrackTextViewRight);
        mViewPager = (ViewPager) mView.findViewById(R.id.viewpager);
        mFragments = new ArrayList<TestListFragment>();
        mFragments.add(new TestListFragment());
        mFragments.add(new TestListFragment());
        mFragments.add(new TestListFragment());
        mFragmentPagerAdapter = new FragmentPagerAdapter(getFragmentManager()) {

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mFragments.get(arg0);
            }
        };
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mViewPager.setOnPageChangeListener(this);
//		mViewPager.setCurrentItem(1);
    }

    @Override
    public void onPageScrollStateChanged(int position) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (0 == positionOffset) {
            ColorTrackTextView left = tvs.get(position);
            left.setDirection(ColorTrackTextView.DIRECTION_LEFT);
            left.setProgress(positionOffset);
        } else if (0 < positionOffset) {
            ColorTrackTextView left = tvs.get(position);
            ColorTrackTextView right = tvs.get(position + 1);
            left.setDirection(ColorTrackTextView.DIRECTION_LEFT);
            right.setDirection(ColorTrackTextView.DIRECTION_RIGHT);
            left.setProgress(positionOffset);
            right.setProgress(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int state) {
        // TODO Auto-generated method stub

    }

}
