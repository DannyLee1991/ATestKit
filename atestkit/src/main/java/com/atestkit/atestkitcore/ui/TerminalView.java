package com.atestkit.atestkitcore.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by lijianan on 16/11/14.
 */

public class TerminalView extends TextView {
    private static final int MSG_LOG_REFRESH = 1;
    private OnRefreshCallback onRefreshCallback;
    private String cmd;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_LOG_REFRESH) {
                String log = msg.obj.toString();
                setText(log);
                if (onRefreshCallback != null) {
                    onRefreshCallback.onRefresh(log);
                }
            }
        }
    };

    public TerminalView(Context context) {
        super(context);
    }

    public TerminalView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TerminalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnRefreshCallback(OnRefreshCallback onRefreshCallback) {
        this.onRefreshCallback = onRefreshCallback;
    }

    public void execute(final String cmd) {
        this.cmd = cmd;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Process process = Runtime.getRuntime().exec(cmd);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    StringBuilder log = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        Message msg = Message.obtain();
                        log.append(line);
                        msg.obj = log;
                        msg.what = MSG_LOG_REFRESH;
                        handler.sendMessage(msg);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public interface OnRefreshCallback {
        void onRefresh(String log);
    }
}
