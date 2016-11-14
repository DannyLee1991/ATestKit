package com.atestkit.atestkitcore;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.PopupMenu;

import com.atestkit.atestkitcore.service.FWService;
import com.atestkit.atestkitcore.test.ITest;
import com.atestkit.atestkitcore.test.TestPool;
import com.atestkit.atestkitcore.ui.PopWinContainer;

import java.util.List;


/**
 * 弹窗辅助类
 *
 * @ClassName WindowUtils
 */
public class UIKit {
    //定义浮动窗口布局
    static View mRootView;
    static PopWinContainer mPopView;
    static LayoutParams wmParams;
    //创建浮动窗口设置布局参数的对象
    static WindowManager mWindowManager;

    private static boolean isShown = false;

    private static Service service;

    public static void show(final Application app, Service s) {
        if (isShown) {
            return;
        }
        isShown = true;
        service = s;

        mRootView = genFloatWindow(LayoutInflater.from(app));
        mPopView.startRefresh();

        //添加mFloatLayout
        mWindowManager.addView(mRootView, wmParams);
    }

    /**
     * 整体布局生成
     *
     * @param inflater
     * @return
     */
    private static View genFloatWindow(final LayoutInflater inflater) {
        final View rootView;
        final View viewCe;
        //获取浮动窗口视图所在布局
        rootView = inflater.inflate(R.layout.float_layout, null);
        mPopView = (PopWinContainer) rootView.findViewById(R.id.popup_window);

        //浮动窗口按钮
        viewCe = rootView.findViewById(R.id.v_ce);

        //设置监听浮动窗口的触摸移动
        viewCe.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                updateViewPostion(v, event, rootView);
                return false;
            }
        });

        viewCe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopView.showOrHide();
            }
        });

        viewCe.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPopupMenu(inflater.getContext(), viewCe);
                return false;
            }
        });

        return rootView;
    }

    private static void showPopupMenu(final Context context, View viewCe) {
        PopupMenu popup = new PopupMenu(context, viewCe);
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int i = item.getItemId();
                if (i == R.id.memu_close) {
                    context.stopService(new Intent(context, FWService.class));
                } else if (i == R.id.memu_more) {
                    TestActivity.start(service);
                }
                return true;
            }
        });
        popup.show();
    }

    private static void updateViewPostion(View v, MotionEvent event, View rootView) {
        wmParams.x = (int) event.getRawX() - v.getMeasuredWidth() / 3 * 2;
        wmParams.y = (int) event.getRawY() - v.getMeasuredHeight();
        mWindowManager.updateViewLayout(rootView, wmParams);
    }

    public static void init(Application app) {
        wmParams = new LayoutParams();
        //获取的是WindowManagerImpl.CompatModeWrapper
        mWindowManager = (WindowManager) app.getSystemService(app.WINDOW_SERVICE);
        //设置window type
        wmParams.type = LayoutParams.TYPE_PHONE;
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;
        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
        wmParams.x = 0;
        wmParams.y = 0;

        //设置悬浮窗口长宽数据
        wmParams.width = LayoutParams.WRAP_CONTENT;
        wmParams.height = LayoutParams.WRAP_CONTENT;

        TestKit.setUpdateCallback(new TestPool.IUpdateCallback() {
            @Override
            public void onUpdate(List<ITest> tests) {
                if (mPopView != null) {
                    mPopView.refresh(tests);
                }
            }
        });
    }

    public static void hide() {
        if (isShown && null != mRootView) {
            mWindowManager.removeView(mRootView);
            isShown = false;
        }
    }

}