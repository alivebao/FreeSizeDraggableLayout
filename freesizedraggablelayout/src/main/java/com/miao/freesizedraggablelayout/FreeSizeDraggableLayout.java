package com.miao.freesizedraggablelayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by miaoyunze on 2016/4/9.
 */
public class FreeSizeDraggableLayout extends ViewGroup {
    final String TAG = "FreeSizeDraggableLayout";
    final int INVALID_POSITION = -1;
    private int mUnitWidthNum = 4;
    private int mUnitHeightNum = 4;
    private int mUnitWidth;
    private int mUnitHeight;
    private List<DetailView> listViews;

    WindowManager.LayoutParams mWindowLayoutParams;
    private int mClickX;
    private int mClickY;
    private int mPoint2ItemLeft;
    private int mPoint2ItemTop;
    private int mOffset2Left;
    private int mOffset2Top;
    private int mStatusHeight;
    private int mViewPadding = 5;
    private ImageView mDragImageView;
    private WindowManager mWindowManager;
    private int mPressedItem = INVALID_POSITION;
    private long mResponseTime = 300;
    private Handler mHandler = new Handler();
    private boolean mPress = false;
    private Vibrator mVibrator;
    private Runnable mPressHandler = new Runnable() {
        @Override
        public void run() {
            Log.e(TAG, "in runnable");
            mPress = true;
            mVibrator.vibrate(50);
            createPressImageView(listViews.get(mPressedItem).getView(), mClickX, mClickY);
            View viewPress = getChildAt(mPressedItem);
            viewPress.setVisibility(View.INVISIBLE);
        }
    };

    public FreeSizeDraggableLayout(Context context) {
        this(context, null);
    }

    public FreeSizeDraggableLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FreeSizeDraggableLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mStatusHeight = getStatusHeight(getContext());
    }

    private void createPressImageView(View v, int x, int y) {
        mWindowLayoutParams = new WindowManager.LayoutParams();
        mWindowLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;

        mWindowLayoutParams.x = x - mPoint2ItemLeft + mOffset2Left;
        mWindowLayoutParams.y = y - mPoint2ItemTop + mOffset2Top - mStatusHeight;
        mWindowLayoutParams.alpha = 0.5f;
        mWindowLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

        mDragImageView = new ImageView(getContext());
        v.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());
        mDragImageView.setImageBitmap(bitmap);
        v.destroyDrawingCache();
        mWindowManager.addView(mDragImageView, mWindowLayoutParams);
    }

    public void setList(List<DetailView> list) {
        listViews = list;
        removeAllViews();
        for (DetailView v : listViews) {
            addView(v.getView());
        }
    }

    public void setUnitWidthNum(int i) {
        mUnitWidthNum = i;
    }

    public void setUnitHeightNum(int i) {
        mUnitHeightNum = i;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPressedItem = getClickedItem(new Point((int) ev.getX(), (int) ev.getY()));
                if (mPressedItem == INVALID_POSITION)
                    return super.dispatchTouchEvent(ev);
                mHandler.postDelayed(mPressHandler, mResponseTime);

                mClickX = (int) ev.getX();
                mClickY = (int) ev.getY();
                mPoint2ItemTop = mClickY - listViews.get(mPressedItem).getView().getTop();
                mPoint2ItemLeft = mClickX - listViews.get(mPressedItem).getView().getLeft();

                mOffset2Top = (int) (ev.getRawY() - mClickY);
                mOffset2Left = (int) (ev.getRawX() - mClickX);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mPress) {
                    mWindowLayoutParams.x = (int) ev.getX() - mPoint2ItemLeft + mOffset2Left;
                    mWindowLayoutParams.y = (int) ev.getY() - mPoint2ItemTop + mOffset2Top - mStatusHeight;
                    mWindowManager.updateViewLayout(mDragImageView, mWindowLayoutParams);

                    int iPrepareChangePosition = getClickedItem(new Point((int) ev.getX(), (int) ev.getY()));
                    if (iPrepareChangePosition != INVALID_POSITION &&
                            listViews.get(iPrepareChangePosition).bSameSize(listViews.get(mPressedItem))) {
                        View viewPrepareChange = listViews.get(iPrepareChangePosition).getView();
                        View viewDraged = listViews.get(mPressedItem).getView();
                        changeViewsPosition(viewPrepareChange, viewDraged);
                        changePositionInList(iPrepareChangePosition, mPressedItem);
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                mHandler.removeCallbacks(mPressHandler);
                if (mPress) {
                    mWindowManager.removeView(mDragImageView);
                    View viewPress = listViews.get(mPressedItem).getView();
                    viewPress.setVisibility(View.VISIBLE);
                    viewPress.invalidate();
                    mPress = false;
                    return true;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void changePositionInList(int i, int j) {
        Point p = listViews.get(i).getPoint();
        listViews.get(i).setPoint(listViews.get(j).getPoint());
        listViews.get(j).setPoint(p);
    }

    private void changeViewsPosition(View v1, View v2) {
        int x = v1.getLeft();
        int y = v1.getTop();
        int z = v1.getRight();
        int w = v1.getBottom();

        v1.setLeft(v2.getLeft());
        v1.setTop(v2.getTop());
        v1.setRight(v2.getRight());
        v1.setBottom(v2.getBottom());

        v2.setLeft(x);
        v2.setTop(y);
        v2.setRight(z);
        v2.setBottom(w);

    }

    private int getClickedItem(Point p) {
        int i = INVALID_POSITION;
        for (DetailView view : listViews) {
            View v = view.getView();
            Rect rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
            if (rect.contains(p.x, p.y)) {
                i = listViews.indexOf(view);
                break;
            }
        }
        return i;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mUnitWidth = MeasureSpec.getSize(widthMeasureSpec) / mUnitWidthNum;
        mUnitHeight = MeasureSpec.getSize(heightMeasureSpec) / mUnitHeightNum;
    }

    /**
     * set the padding of subviews in layout, default is 5
     * @param i
     */
    public void setsubViewPadding(int i) {
        mViewPadding = i;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int iChildCount = getChildCount();
        for (int i = 0; i < iChildCount; ++i) {
            DetailView dvView = listViews.get(i);
            View vChild = getChildAt(i);
            int iL = dvView.getPoint().x * mUnitWidth;
            int iT = dvView.getPoint().y * mUnitHeight;
            int iR = iL + dvView.getWidthNum() * mUnitWidth;
            int iB = iT + dvView.getHeightNum() * mUnitHeight;
            vChild.layout(iL + mViewPadding, iT + mViewPadding, iR - mViewPadding, iB - mViewPadding);
        }
    }

    private static int getStatusHeight(Context context) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = context.getResources().getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }
}
