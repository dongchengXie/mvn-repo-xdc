package com.example.xdc.testimporteclipse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;


public class IndicatorFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private View mView;
    private ViewPager mViewPager;
    private CirclePageIndicator mCirclePageIndicator;
    private ArrayList<TestListFragment> mFragments;
    private FragmentPagerAdapter mFragmentPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null != mView) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (null != parent) {
                parent.removeView(mView);
            }
        } else {
            mView = inflater.inflate(R.layout.fragment_indicator, null);
            mViewPager = (ViewPager) mView.findViewById(R.id.indicator_viewpager);
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
                public android.support.v4.app.Fragment getItem(int arg0) {
                    return mFragments.get(arg0);
                }
            };
            mViewPager.setAdapter(mFragmentPagerAdapter);
            mViewPager.setOnPageChangeListener(this);
            mCirclePageIndicator = (CirclePageIndicator) mView.findViewById(R.id.indicator_indicator);
            mCirclePageIndicator.setViewPager(mViewPager);
        }
        return mView;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
