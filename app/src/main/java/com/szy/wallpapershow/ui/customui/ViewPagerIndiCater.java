package com.szy.wallpapershow.ui.customui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by szjlc on 16/4/29.
 */
public class ViewPagerIndiCater extends HorizontalScrollView {

    private LinearLayout tabsContainer;

    public ViewPagerIndiCater(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPagerIndiCater(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ViewPagerIndiCater(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        tabsContainer = new LinearLayout(context);
        tabsContainer.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        addView(tabsContainer);
    }

    public void addItemView(){



    }

}
