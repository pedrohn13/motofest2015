package br.edu.ufcg.embedded.motofest.utils;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import br.edu.ufcg.embedded.motofest.R;

public class PartnersAdapter extends BaseExpandableListAdapter {






    private final Context mContext;
    private final List<String> mListDataHeader; // header titles
    private final HashMap<String, List<PartnersListItem>> mListDataChild;

    public PartnersAdapter(Context mContext, List<String> mListDataHeader, HashMap<String, List<PartnersListItem>> mListDataChild) {
        this.mContext = mContext;
        this.mListDataHeader = mListDataHeader;
        this.mListDataChild = mListDataChild;
    }

    @Override
    public int getGroupCount() {
        return this.mListDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.mListDataChild.get(this.mListDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mListDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int child) {
        return this.mListDataChild.get(this.mListDataHeader.get(groupPosition)).get(child);
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

        String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.parceiros_list_item, parent, false);
        }
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.parceiros_label);
        lblListHeader.setText(headerTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final PartnersListItem child = (PartnersListItem) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.parceiros_down_list_item, parent, false);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.parceiros_down_image);
        TextView ajudaDownLabel = (TextView) convertView.findViewById(R.id.parceiros_down_label);
        TextView parceiroSubTitle = (TextView) convertView.findViewById(R.id.parceiros_sub_down_label);
        imageView.setImageResource(child.getImage());
        ajudaDownLabel.setText(child.getLabel());
        parceiroSubTitle.setText(child.getSubtitle());
        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
