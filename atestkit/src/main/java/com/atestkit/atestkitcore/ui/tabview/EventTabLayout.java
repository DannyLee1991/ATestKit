package com.atestkit.atestkitcore.ui.tabview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

import com.atestkit.atestkitcore.R;
import com.atestkit.atestkitcore.TestKit;
import com.atestkit.atestkitcore.adapter.EventExpandableListViewAdapter;
import com.atestkit.atestkitcore.test.event.EventTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijianan on 16/11/13.
 */

public class EventTabLayout extends TabLayout {
    private ExpandableListView eplistView;
    private EventExpandableListViewAdapter adapter;

    private List<EventTest> tests = new ArrayList<>();

    public EventTabLayout(Context context) {
        super(context);
    }

    public EventTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EventTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        eplistView = (ExpandableListView) findViewById(R.id.eplv);
        adapter = new EventExpandableListViewAdapter(getContext(), tests);
        eplistView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {

    }

    public void notifyData() {
        TestKit.getAllEventTest(tests);
        adapter.notifyDataSetChanged();

        for (int i = 0; i < adapter.getGroupCount(); i++) {
            eplistView.expandGroup(i);
        }
    }
}
