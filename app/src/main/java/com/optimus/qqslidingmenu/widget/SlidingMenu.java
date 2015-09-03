package com.optimus.qqslidingmenu.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;
import com.optimus.qqslidingmenu.R;
import com.optimus.qqslidingmenu.utils.ScreenUtils;

/**
 * Created by RenTianzhu on 2015/9/3.
 */
public class SlidingMenu  extends HorizontalScrollView{
    private int mScreenWidth;
    private int mMenuRightPadding;//dp
    private int mMenuWidth;
    private int mHalfMenuWidth;
    private boolean once;
    private boolean isOpen;
    private ViewGroup menu;
    private ViewGroup content;

    public SlidingMenu(Context context) {
        this(context,null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScreenWidth = ScreenUtils.getScreenWidth(context);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingMenu,defStyleAttr,0);
        int n = a.getIndexCount();
        for(int i = 0;i < n;i++){
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.SlidingMenu_rightPadding:
                    mMenuRightPadding = a.getDimensionPixelSize(attr, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50f,getResources().getDisplayMetrics()));
                    break;
            }
        }
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(!once){
            LinearLayout wrapper = (LinearLayout)getChildAt(0);
             menu = (ViewGroup) wrapper.getChildAt(0);
            content = (ViewGroup) wrapper.getChildAt(1);

            mMenuWidth = mScreenWidth - mMenuRightPadding;
            mHalfMenuWidth = mMenuWidth/2;
            menu.getLayoutParams().width = mMenuWidth;
            content.getLayoutParams().width = mScreenWidth;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed){
            this.scrollTo(mMenuWidth,0);
            once = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if(scrollX > mHalfMenuWidth){
                    this.smoothScrollTo(mMenuWidth,0);
                    isOpen = false;
                }else{
                    this.smoothScrollTo(0,0);
                    isOpen = true;
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {//l即为getScrollX()
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = l*1.0f/mMenuWidth;
        float leftScale = 1 - 0.3f*scale;
        float leftAlpha = 1 - 0.8f*scale;
        float leftTranslationX = mMenuWidth*scale*0.7f;
        float rightScale = 0.8f  + scale*0.2f;

        ViewHelper.setScaleX(menu,leftScale);
        ViewHelper.setScaleY(menu, leftScale);
        ViewHelper.setAlpha(menu, leftAlpha);
        ViewHelper.setTranslationX(menu,leftTranslationX);

        ViewHelper.setScaleX(content, rightScale);
        ViewHelper.setScaleY(content, rightScale);
        ViewHelper.setPivotX(content, 0);
        ViewHelper.setPivotY(content, content.getHeight() / 2);
    }

    public void openMenu(){
        if(isOpen) return;
        this.smoothScrollTo(0,0);
        isOpen = true;
    }

    public void closeMenu(){
        if(!isOpen) return;
        this.smoothScrollTo(mMenuWidth,0);
        isOpen = false;
    }

    public void toggle(){
        if(isOpen){
            closeMenu();
        }else{
            openMenu();
        }
    }

}
