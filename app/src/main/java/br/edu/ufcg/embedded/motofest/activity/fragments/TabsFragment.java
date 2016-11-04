package br.edu.ufcg.embedded.motofest.activity.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabWidget;
import android.widget.TextView;

import br.edu.ufcg.embedded.motofest.R;
import br.edu.ufcg.embedded.motofest.activity.SettingsActivity;

public class TabsFragment extends Fragment {
    private FragmentTabHost mTabHost;
    private String themeColour;
    private SharedPreferences sharedPreferences;
    private View rootView;
    private static final float NUMERO = 10f;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tabs, container,
                false);

        initView();

        return rootView;
    }

    private void initView() {
        mTabHost = (FragmentTabHost) rootView
                .findViewById(android.R.id.tabhost);

        mTabHost.setup(getActivity(), getChildFragmentManager(),
                android.R.id.tabcontent);



        mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator(getString(R.string.the_event), null),
                HistoryFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator(getString(R.string.programacao), null),
                ProgramacaoFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab3").setIndicator(getString(R.string.apoio), null),
                PatrocinadoresFragment.class, null);
        mTabHost.setCurrentTab(1);

        setUpWidgets();
    }

    public void setUpWidgets() {
        TabWidget tabWidget = mTabHost.getTabWidget();

        int childCount = tabWidget.getChildCount();

        for (int i = 0; i < childCount; ++i) {
            View child = tabWidget.getChildTabViewAt(i);
            TextView texto = (TextView) child.findViewById(android.R.id.title);
            texto.setTextSize(TypedValue.COMPLEX_UNIT_DIP, NUMERO);

            sharedPreferences = getActivity().getSharedPreferences(SettingsActivity.PREFERENCE_NAME, getActivity().MODE_PRIVATE);
            themeColour = sharedPreferences.getString(SettingsActivity.THEME_APP, getString(R.string.black));

            if (themeColour.equals(getString(R.string.red))) {
                child.setBackgroundResource(R.drawable.tab_indicator_ab_red);
            } else {
                child.setBackgroundResource(R.drawable.tab_indicator_ab);
            }

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabHost = null;
    }

}