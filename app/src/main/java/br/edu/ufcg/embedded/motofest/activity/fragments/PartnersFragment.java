package br.edu.ufcg.embedded.motofest.activity.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import br.edu.ufcg.embedded.motofest.R;
import br.edu.ufcg.embedded.motofest.utils.PartnersAdapter;
import br.edu.ufcg.embedded.motofest.utils.PartnersListItem;

public class PartnersFragment extends Fragment {
    private List<String> listDataHeader;
    private HashMap<String, List<PartnersListItem>> listDataChild;
    private PartnersAdapter adapter;
    private PartnersListItem item;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partners, container, false);

        prepareListData();
        ExpandableListView listView = (ExpandableListView) view.findViewById(R.id.list_partners);
        adapter = new PartnersAdapter(getActivity(), listDataHeader, listDataChild);
        listView.setAdapter(adapter);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view1,
                                        int groupPosition, int childPosition, long itemId) {
                item = (PartnersListItem) adapter.getChild(groupPosition, childPosition);
                showPartner(item);
                return false;
            }
        });

        for (int i = listView.getCount() - 1; i >= 0; i--) {
            listView.setSelection(i);
            listView.expandGroup(i);
        }
        disableCollapseListView(listView);

        return view;

    }

    private void showPartner(PartnersListItem item) {
        PartnersListItem pItem = item;
        ExpandePartnerFragment expand = ExpandePartnerFragment.newInstance(pItem);
        expand.show(getFragmentManager(), getString(R.string.partners));
    }

    private void disableCollapseListView(ExpandableListView listView) {
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long itemId) {
                // Doing nothing
                return true;
            }
        });
    }
    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        String motoclube1 = getString(R.string.Motoclube);
        List<PartnersListItem> list = new ArrayList<>();

        PartnersListItem motoclube1ListItem = new PartnersListItem(getString(R.string.Motoclube1), getString(R.string.Motoclube1_sub));
        motoclube1ListItem.setImage(R.drawable.ic_motoclubes_0000_motoclub_01);
        motoclube1ListItem.setImagelarge(R.drawable.motoclubes_maiores_0000_1);
        PartnersListItem motoclube2ListItem = new PartnersListItem(getString(R.string.Motoclube2), getString(R.string.Motoclube2_sub));
        motoclube2ListItem.setImage(R.drawable.ic_motoclubes_0018_motoclub_19);
        motoclube2ListItem.setImagelarge(R.drawable.motoclubes_maiores_0018_19);
        PartnersListItem motoclube3ListItem = new PartnersListItem(getString(R.string.Motoclube3), getString(R.string.Motoclube3_sub));
        motoclube3ListItem.setImage(R.drawable.ic_motoclubes_0004_motoclub_05);
        motoclube3ListItem.setImagelarge(R.drawable.motoclubes_maiores_0004_5);
        PartnersListItem motoclube4ListItem = new PartnersListItem(getString(R.string.Motoclube4), getString(R.string.Motoclube4_sub));
        motoclube4ListItem.setImage(R.drawable.ic_motoclubes_0005_motoclub_06);
        motoclube4ListItem.setImagelarge(R.drawable.motoclubes_maiores_0005_6);
        PartnersListItem motoclube5ListItem = new PartnersListItem(getString(R.string.Motoclube5), getString(R.string.Motoclube5_sub));
        motoclube5ListItem.setImage(R.drawable.ic_motoclubes_0009_motoclub_10);
        motoclube5ListItem.setImagelarge(R.drawable.motoclubes_maiores_0009_10);
        PartnersListItem motoclube6ListItem = new PartnersListItem(getString(R.string.Motoclube6), getString(R.string.Motoclube6_sub));
        motoclube6ListItem.setImage(R.drawable.ic_motoclubes_0003_motoclub_04);
        motoclube6ListItem.setImagelarge(R.drawable.motoclubes_maiores_0003_4);
        PartnersListItem motoclube7ListItem = new PartnersListItem(getString(R.string.Motoclube7), getString(R.string.Motoclube7_sub));
        motoclube7ListItem.setImage(R.drawable.ic_motoclubes_0006_motoclub_07);
        motoclube7ListItem.setImagelarge(R.drawable.motoclubes_maiores_0006_7);
        PartnersListItem motoclube8ListItem = new PartnersListItem(getString(R.string.Motoclube8), getString(R.string.Motoclube8_sub));
        motoclube8ListItem.setImage(R.drawable.ic_motoclubes_0007_motoclub_08);
        motoclube8ListItem.setImagelarge(R.drawable.motoclubes_maiores_0007_8);
        PartnersListItem motoclube9ListItem = new PartnersListItem(getString(R.string.Motoclube9), getString(R.string.Motoclube9_sub));
        motoclube9ListItem.setImage(R.drawable.ic_motoclubes_0011_motoclub_12);
        motoclube9ListItem.setImagelarge(R.drawable.motoclubes_maiores_0011_12);
        PartnersListItem motoclube11ListItem = new PartnersListItem(getString(R.string.Motoclube11), getString(R.string.Motoclube11_sub));
        motoclube11ListItem.setImage(R.drawable.ic_motoclubes_0020_motoclub_21);
        motoclube11ListItem.setImagelarge(R.drawable.motoclubes_maiores_0020_21);
        PartnersListItem motoclube12ListItem = new PartnersListItem(getString(R.string.Motoclube12), getString(R.string.Motoclube12_sub));
        motoclube12ListItem.setImage(R.drawable.ic_motoclubes_0013_motoclub_14);
        motoclube12ListItem.setImagelarge(R.drawable.motoclubes_maiores_0013_14);
        PartnersListItem motoclube13ListItem = new PartnersListItem(getString(R.string.Motoclube13), getString(R.string.Motoclube13_sub));
        motoclube13ListItem.setImage(R.drawable.ic_motoclubes_0008_motoclub_09);
        motoclube13ListItem.setImagelarge(R.drawable.motoclubes_maiores_0008_9);
        PartnersListItem motoclube14ListItem = new PartnersListItem(getString(R.string.Motoclube14), getString(R.string.Motoclube14_sub));
        motoclube14ListItem.setImage(R.drawable.ic_motoclubes_0016_motoclub_17);
        motoclube14ListItem.setImagelarge(R.drawable.motoclubes_maiores_0016_17);
        PartnersListItem motoclube15ListItem = new PartnersListItem(getString(R.string.Motoclube15), getString(R.string.Motoclube15_sub));
        motoclube15ListItem.setImage(R.drawable.ic_motoclubes_0015_motoclub_16);
        motoclube15ListItem.setImagelarge(R.drawable.motoclubes_maiores_0015_16);
        PartnersListItem motoclube17ListItem = new PartnersListItem(getString(R.string.Motoclube17), getString(R.string.Motoclube17_sub));
        motoclube17ListItem.setImage(R.drawable.ic_motoclubes_0017_motoclub_18);
        motoclube17ListItem.setImagelarge(R.drawable.motoclubes_maiores_0017_18);
        PartnersListItem motoclube18ListItem = new PartnersListItem(getString(R.string.Motoclube18), getString(R.string.Motoclube18_sub));
        motoclube18ListItem.setImage(R.drawable.ic_motoclubes_0019_motoclub_20);
        motoclube18ListItem.setImagelarge(R.drawable.motoclubes_maiores_0019_20);
        PartnersListItem motoclube19ListItem = new PartnersListItem(getString(R.string.Motoclube19), getString(R.string.Motoclube19_sub));
        motoclube19ListItem.setImage(R.drawable.ic_motoclubes_0012_motoclub_13);
        motoclube19ListItem.setImagelarge(R.drawable.motoclubes_maiores_0012_13);
        PartnersListItem motoclube20ListItem = new PartnersListItem(getString(R.string.Motoclube20), getString(R.string.Motoclube20_sub));
        motoclube20ListItem.setImage(R.drawable.ic_motoclubes_0001_motoclub_02);
        motoclube20ListItem.setImagelarge(R.drawable.motoclubes_maiores_0001_2);
        PartnersListItem motoclube21ListItem = new PartnersListItem(getString(R.string.Motoclube21), getString(R.string.Motoclube21_sub));
        motoclube21ListItem.setImage(R.drawable.ic_motoclubes_0014_motoclub_15);
        motoclube21ListItem.setImagelarge(R.drawable.motoclubes_maiores_0014_15);
        PartnersListItem motoclube22ListItem = new PartnersListItem(getString(R.string.Motoclube22), getString(R.string.Motoclube22_sub));
        motoclube22ListItem.setImage(R.drawable.ic_motoclubes_0010_motoclub_11);
        motoclube22ListItem.setImagelarge(R.drawable.motoclubes_maiores_0010_11);
        PartnersListItem motoclube23ListItem = new PartnersListItem(getString(R.string.Motoclube23), getString(R.string.Motoclube23_sub));
        motoclube23ListItem.setImage(R.drawable.ic_motoclubes_0022_motoclub_23);
        motoclube23ListItem.setImagelarge(R.drawable.motoclubes_maiores_0022_23);
        PartnersListItem motoclube24ListItem = new PartnersListItem(getString(R.string.Motoclube24), getString(R.string.Motoclube24_sub));
        motoclube24ListItem.setImage(R.drawable.ic_motoclubes_0002_motoclub_03);
        motoclube24ListItem.setImagelarge(R.drawable.motoclubes_maiores_0002_3);
        PartnersListItem motoclube25ListItem = new PartnersListItem(getString(R.string.Motoclube25), getString(R.string.Motoclube25_sub));
        motoclube25ListItem.setImage(R.drawable.ic_motoclubes_0024_motoclub_25);
        motoclube25ListItem.setImagelarge(R.drawable.motoclubes25);

        list.add(motoclube1ListItem);
        list.add(motoclube2ListItem);
        list.add(motoclube3ListItem);
        list.add(motoclube4ListItem);
        list.add(motoclube5ListItem);
        list.add(motoclube6ListItem);
        list.add(motoclube7ListItem);
        list.add(motoclube8ListItem);
        list.add(motoclube9ListItem);
        list.add(motoclube11ListItem);
        list.add(motoclube12ListItem);
        list.add(motoclube13ListItem);
        list.add(motoclube14ListItem);
        list.add(motoclube15ListItem);
        list.add(motoclube17ListItem);
        list.add(motoclube18ListItem);
        list.add(motoclube19ListItem);
        list.add(motoclube20ListItem);
        list.add(motoclube21ListItem);
        list.add(motoclube22ListItem);
        list.add(motoclube23ListItem);
        list.add(motoclube24ListItem);
        list.add(motoclube25ListItem);

        listDataHeader.add(motoclube1);
        listDataChild.put(motoclube1, list);

    }

    public static class ExpandePartnerFragment extends DialogFragment {
        private static PartnersListItem item;

        static ExpandePartnerFragment newInstance(PartnersListItem listitem) {
            ExpandePartnerFragment expandfragment = new ExpandePartnerFragment();
            item = listitem;
            return expandfragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.partner_view, container, false);

            Window window = getDialog().getWindow();
            window.requestFeature(Window.FEATURE_NO_TITLE);
            window.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

            ImageView logo = (ImageView) view.findViewById(R.id.mc_logo);
            logo.setImageResource(item.getImagelarge());

            TextView name = (TextView) view.findViewById(R.id.partnerD_name);
            name.setText(item.getLabel());
            TextView sub = (TextView) view.findViewById(R.id.partnerD_sub);
            sub.setText(item.getSubtitle());

            ImageButton btX = (ImageButton) view.findViewById(R.id.x_bt);
            btX.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    onDestroyView();
                }
            });

            return view;
        }


    }

}
