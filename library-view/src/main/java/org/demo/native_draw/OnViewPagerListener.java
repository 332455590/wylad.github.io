package org.demo.native_draw;

public interface OnViewPagerListener {

    void onInitComplete();

    void onPageRelease(boolean isNext, int position);

    void onPageSelected(int position, boolean isBottom);

}
