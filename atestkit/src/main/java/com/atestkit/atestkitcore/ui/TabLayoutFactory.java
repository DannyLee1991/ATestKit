package com.atestkit.atestkitcore.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.atestkit.atestkitcore.R;
import com.atestkit.atestkitcore.test.ITest;
import com.atestkit.atestkitcore.test.activity.ActivityTest;
import com.atestkit.atestkitcore.test.common.CommonTest;
import com.atestkit.atestkitcore.test.event.EventTest;
import com.atestkit.atestkitcore.test.log.LogTest;
import com.atestkit.atestkitcore.ui.tabview.ActivityTabLayout;
import com.atestkit.atestkitcore.ui.tabview.CommonTabLayout;
import com.atestkit.atestkitcore.ui.tabview.EventTabLayout;
import com.atestkit.atestkitcore.ui.tabview.LogTabLayout;
import com.atestkit.atestkitcore.ui.tabview.TabLayout;

/**
 * Created by lijianan on 16/11/4.
 */

public class TabLayoutFactory {

    public static TabLayout create(Context context, final ITest test, ViewGroup parent) {
        if (test instanceof ActivityTest) {
            return genActivityTestView(context, (ActivityTest) test, parent);
        } else if (test instanceof EventTest) {
            return genEventTestView(context, (EventTest) test, parent);
        } else if (test instanceof CommonTest) {
            return genCommonTestView(context, (CommonTest) test, parent);
        } else if (test instanceof LogTest) {
            return genLogTestView(context, (LogTest) test, parent);
        }
        return null;
    }

    @NonNull
    private static ActivityTabLayout genActivityTestView(final Context context, final ActivityTest test, ViewGroup parent) {
        ActivityTabLayout activityTab = (ActivityTabLayout) LayoutInflater.from(context).inflate(R.layout.tabview_activity_test, parent, false);
        activityTab.setData(test);
        return activityTab;
    }

    private static TabLayout genEventTestView(Context context, EventTest test, ViewGroup parent) {
        EventTabLayout eventTab = (EventTabLayout) LayoutInflater.from(context).inflate(R.layout.tabview_event_test, parent, false);
        eventTab.notifyData();
        return eventTab;
    }

    @NonNull
    private static CommonTabLayout genCommonTestView(Context context, CommonTest test, ViewGroup parent) {
        CommonTabLayout commonTab = (CommonTabLayout) LayoutInflater.from(context).inflate(R.layout.tabview_common_test, parent, false);
        commonTab.setData(test);
        return commonTab;
    }

    @NonNull
    private static LogTabLayout genLogTestView(Context context, LogTest test, final ViewGroup parent) {
        final LogTabLayout logTabLayout = (LogTabLayout) LayoutInflater.from(context).inflate(R.layout.tabview_log_test, parent, false);
        logTabLayout.setData(test);
        return logTabLayout;
    }

}
