package com.lsjwzh.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.lsjwzh.widget.instacontainer.R;
import com.lsjwzh.widget.powerfulscrollview.NestRecyclerViewHelper;
import com.lsjwzh.widget.powerfulscrollview.PowerfulScrollView;

/**
 * 向上滑动时,首先滚动
 */
public class ZepetoContainer extends PowerfulScrollView {
  private static final String TAG = ZepetoContainer.class.getSimpleName();
  protected ObjectAnimator mScrollAnimation;
  protected int headerMinHeight;

  public ZepetoContainer(Context context) {
    this(context, null);
  }

  public ZepetoContainer(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ZepetoContainer(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    final TypedArray a = context.obtainStyledAttributes(attrs,
        R.styleable.ZepetoContainer, defStyleAttr, 0);
    headerMinHeight = a.getDimensionPixelSize(R.styleable.ZepetoContainer_headerMinHeight, 0);
    a.recycle();
  }


  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    for (NestRecyclerViewHelper helper : mNestRecyclerViewHelpers) {
      ViewGroup.LayoutParams lp = helper.mNestedRecyclerView.getLayoutParams();
      lp.height = getMeasuredHeight() - headerMinHeight;
    }
  }

  public int getHeaderMinHeight() {
    return headerMinHeight;
  }

  public void setHeaderMinHeight(int headerMinHeight) {
    this.headerMinHeight = headerMinHeight;
  }

  @Override
  public boolean startNestedScroll(int axes, int type) {
    // stop first
    if (mScrollAnimation != null) {
      mScrollAnimation.cancel();
      mScrollAnimation = null;
    }
    return super.startNestedScroll(axes, type);
  }

  @Override
  public void stopNestedScroll(int type) {
    super.stopNestedScroll(type);
    Log.d(TAG, "stopNestedScroll:");
    int maxScrollY = getScrollRange();
    int scrollY = getScrollY();
    if (mScrollAnimation != null) {
      mScrollAnimation.cancel();
      mScrollAnimation = null;
    }
    if (scrollY < maxScrollY / 2) {
      mScrollAnimation = ObjectAnimator.ofInt(this, "scrollY", 0)
          .setDuration(200);
      mScrollAnimation.start();

    } else if (scrollY != maxScrollY) {
      mScrollAnimation = ObjectAnimator.ofInt(this, "scrollY", maxScrollY)
          .setDuration(200);
      mScrollAnimation.start();
    }
    Log.d(TAG, "stopNestedScroll getScrollY: " + scrollY);
    Log.d(TAG, "stopNestedScroll maxScrollY: " + maxScrollY);
  }

  protected View findHeaderView() {
    return ((ViewGroup) getChildAt(0)).getChildAt(0);
  }

}
