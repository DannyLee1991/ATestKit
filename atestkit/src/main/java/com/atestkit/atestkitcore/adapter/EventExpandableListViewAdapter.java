package com.atestkit.atestkitcore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


import com.atestkit.atestkitcore.R;
import com.atestkit.atestkitcore.test.event.EventInfo;
import com.atestkit.atestkitcore.test.event.EventTest;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by lijianan on 16/11/14.
 */

public class EventExpandableListViewAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<EventTest> tests;

    public EventExpandableListViewAdapter(Context context, List<EventTest> tests) {
        this.context = context;
        this.tests = tests;
    }

    @Override
    public int getGroupCount() {
        return tests.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return tests.get(groupPosition).getInfos().size();
    }

    @Override
    public EventTest getGroup(int groupPosition) {
        return tests.get(groupPosition);
    }

    @Override
    public EventInfo getChild(int groupPosition, int childPosition) {
        return tests.get(groupPosition).getInfos().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_group_event_view, null);
            holder = new GroupHolder();
            holder.tvClassName = (TextView) convertView.findViewById(R.id.tv_class_name);
            holder.tvIsOpen = (TextView) convertView.findViewById(R.id.tv_is_open);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.tvClassName.setText(getGroup(groupPosition).getTargetClassName());
        if (isExpanded) {
            holder.tvIsOpen.setText(" - ");
        } else {
            holder.tvIsOpen.setText(" + ");
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_child_event_view, null);
            holder = new ChildHolder();
            holder.tvDescription = (TextView) convertView.findViewById(R.id.tv_event_info_des);
            holder.btAction = (TextView) convertView.findViewById(R.id.bt_action);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }

        holder.tvDescription.setText(getChild(groupPosition, childPosition).getDescription());
        holder.btAction.setText(getChild(groupPosition, childPosition).getName());
        holder.btAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getChild(groupPosition, childPosition).invoke();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private class GroupHolder {
        TextView tvIsOpen;
        TextView tvClassName;
    }

    private class ChildHolder {
        TextView tvDescription;
        TextView btAction;
    }
}
