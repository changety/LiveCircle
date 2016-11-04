package org.live.circle.activity;

import android.support.annotation.ArrayRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import org.live.circle.R;
import org.live.circle.component.PagerSlidingTabStrip;

public abstract class PagerSlidingTabStripBaseActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, PagerSlidingTabStrip.OnTabSelectedListener {

    protected String[] tabTitles;
    protected ViewPager mViewPager;
    protected PagerSlidingTabStrip mTabLayout;
    protected PagerAdapter mPagerAdapter;
    public final static int DEFAULT_SELECT_PAGE_INDEX = 0;
    public final static String SELECT_PAGE_INDEX = "SELECT_PAGE_INDEX";

    public int getPagerIndex() {
        return pagerIndex;
    }

    public void setPagerIndex(int pagerIndex) {
        this.pagerIndex = pagerIndex;
    }

    protected int pagerIndex = DEFAULT_SELECT_PAGE_INDEX;

    public void initBasicTabAndViewPagerComponent(int padding) {
        if (tabTitles == null || mViewPager == null || mTabLayout == null || mPagerAdapter == null) {
            throw new IllegalArgumentException("can not initBasicTabAndViewPagerComponent before set basic compnent");
        }
        mViewPager.setOffscreenPageLimit(tabTitles.length);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setTabPaddingLeftRight(padding);
        mTabLayout.setViewPager(mViewPager);
        mTabLayout.setOnPageChangeListener(this);
        mTabLayout.setOnTabSelectedListener(this);
    }

    public void initBasicTabAndViewPagerComponent() {
        initBasicTabAndViewPagerComponent(0);
    }

    public void findAndInitCommonBasicTabAndViewPagerComponent(@ArrayRes int tabTitles, PagerAdapter pagerAdapter, int layoutResId) {
//        setContentView(layoutResId);
        setTabTitles(getResources().getStringArray(tabTitles));
        setViewPager((ViewPager) findViewById(R.id.common_view_pager));
        setTabLayout((PagerSlidingTabStrip) findViewById(R.id.common_tab_layout));
        setPagerAdapter(pagerAdapter);
        initBasicTabAndViewPagerComponent();
    }

    public void onPageScrollStateChanged(int state) {
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    public void onPageSelected(int position) {
        if (mViewPager != null) {
            setCurrentPage(position);
        }
    }

    public int getCurrentPagerPosition() {
        return mViewPager != null ? mViewPager.getCurrentItem() : 0;

    }

    public void setCurrentPage(int page) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(page);
        }
    }

    public void setCurrentPage(int page, boolean smoothScroll) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(page, smoothScroll);
        }
    }

    public String[] getTabTitles() {
        return tabTitles;
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    public void setTabTitles(String[] tabTitles) {
        this.tabTitles = tabTitles;
    }

    public void setViewPager(ViewPager mViewPager) {
        this.mViewPager = mViewPager;
    }

    public void setPagerAdapter(PagerAdapter mPagerAdapter) {
        this.mPagerAdapter = mPagerAdapter;
    }

    public Fragment getFragmentByPostion(int position) {
        return getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.common_view_pager + ":" + position);
    }

    public void loadFragmentByPosition(int position) {
        Fragment fragment = getFragmentByPostion(position);
//		if (fragment != null && !fragment.isInited()) {
//			fragment.load(null);
//		}
    }

    public void setTabLayout(PagerSlidingTabStrip tabLayout) {
        this.mTabLayout = tabLayout;
    }

    public void setTabText(int position, CharSequence title) {
        if (mTabLayout != null) {
            mTabLayout.setTabText(position, title);
        }
    }

    public CharSequence getTabText(int position) {
        return mTabLayout == null ? "" : mTabLayout.getTabText(position);
    }

    public View getTargetTabviewByIndex(int index) {
        if (mTabLayout == null) {
            return null;
        }
        try {
            ViewGroup rootView = (ViewGroup) mTabLayout.getChildAt(0);
            return rootView.getChildAt(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onTabReselected(View tabView, int position) {
    }

    @Override
    public void onTabSelected(View tabView, int position) {
    }
}


