package com.atestkit.atestkitcore.ui.tabview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by lijianan on 16/11/13.
 */

public abstract class TabLayout extends LinearLayout {

    private boolean isInflated = false;

    private OnFinishInflateCallback onFinishInflateCallback;

    public TabLayout(Context context) {
        super(context);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        isInflated = true;
        if (onFinishInflateCallback != null) {
            onFinishInflateCallback.onFinish();
        }
    }

    /**
     * every {@link com.hjwordgamestest.testtool.ui.PopWinContainer#REFRESH_SLEEP_TIME} ms, call this method
     * just for refresh every TabLayout to reload data.
     */
    public abstract void onRefresh();

    public void setOnFinishInflateCallback(OnFinishInflateCallback onFinishInflateCallback) {
        this.onFinishInflateCallback = onFinishInflateCallback;
        if (isInflated) {
            if (onFinishInflateCallback != null) {
                onFinishInflateCallback.onFinish();
            }
        }
    }

    public interface OnFinishInflateCallback {
        void onFinish();
    }

}
