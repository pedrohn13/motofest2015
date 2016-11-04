package br.edu.ufcg.embedded.motofest.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import br.edu.ufcg.embedded.motofest.R;
import br.edu.ufcg.embedded.motofest.activity.MainActivity;



public class DiaEventoListAdapter extends BaseExpandableListAdapter {

    private final Context mContext;
    private final List<String> mListDataHeader;
    private final HashMap<String, List<AtracoesListItem>> mListDataChild;

    public DiaEventoListAdapter(Context context, List<String> listDataHeader,
                                HashMap<String, List<AtracoesListItem>> listChildData) {
        this.mContext = context;
        this.mListDataHeader = listDataHeader;
        this.mListDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.mListDataChild.get(this.mListDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final AtracoesListItem child = (AtracoesListItem) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.atracao_list_item, parent, false);
        }

        TextView atracaoLabel = (TextView) convertView
                .findViewById(R.id.atracao_label);
        atracaoLabel.setText(child.getLabel());
        TextView scheduleLabel = (TextView) convertView.findViewById(R.id.schedule_label);
        scheduleLabel.setText(child.getSchedule());

        final ImageView videoButton = (ImageView) convertView.findViewById(R.id.video_link);
        videoButton.setVisibility(View.GONE);
        if (child.isBand()) {
            videoButton.setVisibility(View.VISIBLE);
            videoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String label = child.getLabel();
                    switch (label) {
                        case "New Band":
                            label = "New Band Paraíba";
                            break;
                        case "Banda Warcursed":
                            label = "WarcursedOfficial";
                            break;
                        case "Banda God's Way":
                            label = "Banda God's Way Recife";
                            break;
                        default:
                            break;
                    }
                    label = label.replaceAll(" ", "+");
                    if (!isNetworkAvailable()) {
                        displayPromptForEnablingInternet();
                    } else {
                        ((MainActivity) mContext).showVideoDialog("https://www.youtube.com/results?search_query=" + label);
                    }


                }
            });
        }


        return convertView;
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void displayPromptForEnablingInternet() {
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(mContext);
        final String actionWifiSettings = Settings.ACTION_WIFI_SETTINGS;
        final String actionWirelessSettings = Settings.ACTION_WIRELESS_SETTINGS;
        final String message = mContext.getString(R.string.enable_network);

        builder.setMessage(message)
                .setPositiveButton(mContext.getString(R.string.bt_wifi),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int idButton) {
                                mContext.startActivity(new Intent(actionWifiSettings));
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton(mContext.getString(R.string.bt_mobile_network),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int idButton) {
                                mContext.startActivity(new Intent(actionWirelessSettings));
                                dialog.dismiss();
                            }
                        })
                .setNeutralButton(mContext.getString(R.string.bt_cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int idButton) {
                                dialog.cancel();
                            }
                        });
        builder.create().show();
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
    public int getGroupCount() {
        return this.mListDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.evento_list_item, parent, false);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.date_label);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
