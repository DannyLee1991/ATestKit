package com.atestkit.atestkitcore.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TabHost;

import com.atestkit.atestkitcore.R;
import com.atestkit.atestkitcore.test.ITest;
import com.atestkit.atestkitcore.test.activity.ActivityTest;
import com.atestkit.atestkitcore.test.common.CommonTest;
import com.atestkit.atestkitcore.test.event.EventTest;
import com.atestkit.atestkitcore.test.log.LogTest;
import com.atestkit.atestkitcore.ui.tabview.TabLayout;

import java.util.List;

/**
 * Created by lijianan on 16/11/4.
 */

public class PopWinContainer extends LinearLayout {

    public static final int REFRESH_SLEEP_TIME = 300;

    private ScrollView activityTabView;
    private ScrollView eventTabView;
    private ScrollView commonTabView;
    private ScrollView logTabView;

    private boolean mNeedRefresh = true;

    public PopWinContainer(Context context) {
        super(context);
    }

    public PopWinContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PopWinContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        initTabView();
    }

    private void initTabView() {
        TabHost th = (TabHost) findViewById(R.id.tabhost);
        th.setup();            //初始化TabHost容器

        //在TabHost创建标签，然后设置：标题／图标／标签页布局
        th.addTab(th.newTabSpec("tab1").setIndicator("Activity", null).setContent(R.id.activity_tab));
        th.addTab(th.newTabSpec("tab2").setIndicator("Event", null).setContent(R.id.event_tab));
        th.addTab(th.newTabSpec("tab3").setIndicator("Common", null).setContent(R.id.common_tab));
        th.addTab(th.newTabSpec("tab4").setIndicator("Log", null).setContent(R.id.log_tab));

        activityTabView = (ScrollView) findViewById(R.id.activity_tab);
        eventTabView = (ScrollView) findViewById(R.id.event_tab);
        commonTabView = (ScrollView) findViewById(R.id.common_tab);
        logTabView = getLogTabScrollView();

        logTabView.post(new Runnable() {
            @Override
            public void run() {
                logTabView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    public ScrollView getLogTabScrollView() {
        return (ScrollView) findViewById(R.id.log_tab);
    }

    public void showOrHide() {
        if (isHide()) {
            setVisibility(VISIBLE);
        } else {
            setVisibility(GONE);
        }
    }

    public void refresh(List<ITest> tests) {
        // TODO: 16/11/13 每次刷新时 可以只刷新指定的页面，没必要全部刷新
        activityTabView.removeAllViews();
        eventTabView.removeAllViews();
        commonTabView.removeAllViews();
        logTabView.removeAllViews();

        for (ITest test : tests) {
            revreshTabView(test);
        }
    }

    private void revreshTabView(ITest test) {
        ScrollView tabView = null;
        if (test instanceof ActivityTest) {
            tabView = activityTabView;
        } else if (test instanceof EventTest) {
            tabView = eventTabView;
        } else if (test instanceof CommonTest) {
            tabView = commonTabView;
        } else if (test instanceof LogTest) {
            tabView = logTabView;
        } else {
            return;
        }

        tabView.removeAllViews();

        TabLayout view = TabLayoutFactory.create(getContext(), test, this);
        tabView.addView(view, getLayoutParams());

    }

    public boolean isHide() {
        return getVisibility() == View.INVISIBLE || getVisibility() == View.GONE;
    }

    public boolean isShow() {
        return getVisibility() == View.VISIBLE;
    }

    public void startRefresh() {

        new AsyncTask<Integer, Integer, String>() {
            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                refreshTabView(activityTabView);
                refreshTabView(eventTabView);
                refreshTabView(commonTabView);
                refreshTabView(logTabView);
            }

            @Override
            protected String doInBackground(Integer... params) {
                while (mNeedRefresh) {
                    try {
                        publishProgress(params);
                        Thread.sleep(REFRESH_SLEEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        }.execute();
    }

    private void refreshTabView(ViewGroup view) {
        if (view != null && view.getChildCount() > 0) {
            if (view.getChildAt(0) instanceof TabLayout) {
                final TabLayout tabLayout = ((TabLayout) view.getChildAt(0));
                tabLayout.setOnFinishInflateCallback(new TabLayout.OnFinishInflateCallback() {
                    @Override
                    public void onFinish() {
                        tabLayout.onRefresh();
                    }
                });
            }
        }
    }
}
