package com.miao.freesizedraggablelayout;

import android.graphics.Point;
import android.view.View;

/**
 * Created by miaoyunze on 2016/4/9.
 */
public class DetailView {
    private Point mBeginPoint;
    private int mWidthNum;
    private int mHeightNum;
    private View mView;

    public DetailView() {
        this(null, 0, 0, null);
    }

    public DetailView(Point p, int width, int height, View v) {
        setPoint(p);
        setWidhtNum(width);
        setHeightNum(height);
        setView(v);
    }

    public boolean bSameSize(DetailView v) {
        return (mWidthNum == v.getWidthNum() && mHeightNum == v.getHeightNum());
    }

    public void setPoint(Point p) {
        mBeginPoint = p;
    }

    public Point getPoint() {
        return mBeginPoint;
    }

    public void setWidhtNum(int i) {
        mWidthNum = i;
    }

    public int getWidthNum() {
        return mWidthNum;
    }

    public void setHeightNum(int i) {
        mHeightNum = i;
    }

    public int getHeightNum() {
        return mHeightNum;
    }

    public void setView(View v) {
        mView = v;
    }

    public View getView() {
        return mView;
    }
}
