package com.example.admission;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    public Context context;
    public List<Deptdata> Parent;
    public HashMap<String, List<String>> Child;

    public CustomExpandableListAdapter(Context context, List<Deptdata> Parent,
                                       HashMap<String, List<String>> Child) {
        this.context = context;
        this.Parent = Parent;
        this.Child = Child;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {

        return this.Child.get(this.Parent.get(listPosition).getDepartment()).get(expandedListPosition);
    }

    @Override
    public int getChildrenCount(int listPosition) {

        return this.Child.get(this.Parent.get(listPosition).getDepartment())
                .size();


    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }


    @Override
    public Object getGroup(int listPosition) {

        return this.Parent.get(listPosition).getDepartment();

    }

    @Override
    public int getGroupCount() {

        return this.Parent.size();
    }

    @Override
    public long getGroupId(int listPosition) {

        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.ListTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}


