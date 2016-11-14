package com.atestkit.atestkitcore.ui.tabview;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;

import com.atestkit.atestkitcore.R;
import com.atestkit.atestkitcore.test.activity.ActivityTest;

/**
 * Created by lijianan on 16/11/9.
 */

public class ActivityTabLayout extends TabLayout {

    private TextView tvName;
    private TextView tvFields;

    private ActivityTest mTest;

    public ActivityTabLayout(Context context) {
        super(context);
    }

    public ActivityTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ActivityTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onRefresh() {
        refreshData();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initViews();
    }

    private void initViews() {
        tvName = (TextView) findViewById(R.id.tv_activity_name);
        tvFields = (TextView) findViewById(R.id.tv_fields);
    }

    public void setData(ActivityTest test) {
        mTest = test;

        tvName.setText(String.format("activity name:%s", test.getActivityName()));
    }

    private void refreshData() {
        if (mTest == null) {
            return;
        }

        tvFields.setText(Html.fromHtml(mTest.getActivityFieldInfosHtmlStr()));
    }


}
