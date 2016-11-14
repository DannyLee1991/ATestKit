package com.atestkit.atestkitcore.ui.tabview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.atestkit.atestkitcore.R;
import com.atestkit.atestkitcore.test.log.LogTest;
import com.atestkit.atestkitcore.ui.TerminalView;


/**
 * Created by lijianan on 16/11/9.
 */

public class LogTabLayout extends TabLayout {

    TerminalView tvLog;
    TextView scrollToBottomView;
    TextView scrollToTopView;
    TerminalView.OnRefreshCallback onLogRefreshCallback;

    public LogTabLayout(Context context) {
        super(context);
    }

    public LogTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LogTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    private void initView() {
        tvLog = (TerminalView) findViewById(R.id.tv_log);
        scrollToBottomView = (TextView) findViewById(R.id.tv_scroll_to_bottom);
        scrollToTopView = (TextView) findViewById(R.id.tv_scroll_to_top);

        scrollToBottomView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        scrollToTopView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tvLog.setOnRefreshCallback(new TerminalView.OnRefreshCallback() {
            @Override
            public void onRefresh(String log) {
                if (TextUtils.isEmpty(log)) {
                    scrollToBottomView.setVisibility(GONE);
                    scrollToTopView.setVisibility(GONE);
                } else {
                    scrollToTopView.setVisibility(VISIBLE);
                    scrollToBottomView.setVisibility(VISIBLE);
                }
            }
        });
    }

    public void setData(LogTest test) {
        tvLog.execute(test.getCmd());
    }

}
