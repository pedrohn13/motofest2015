package br.edu.ufcg.embedded.motofest.activity.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Locale;

import br.edu.ufcg.embedded.motofest.R;
import br.edu.ufcg.embedded.motofest.activity.MainActivity;

public class MapDialogFragment extends DialogFragment {

    private MapsFragment mapsFragment;
    private ArrayList<String> markersTypes;
    private ArrayList<CheckBox> checkboxes;
    private LinearLayout linearLayout;
    private ArrayList<String> markersChecked;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        markersChecked = new ArrayList<>();
        checkboxes = new ArrayList<>();
        markersTypes = ((MainActivity) getActivity()).getController().getTypeMarkers(Locale.getDefault());
        createCheckboxes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dialog_maps, container, false);

        Window window = getDialog().getWindow();
        window.setTitle(getString(R.string.pinpoint));
        window.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

        linearLayout = (LinearLayout) rootView.findViewById(R.id.checkBoxesLayout);

        Button btOk = (Button) rootView.findViewById(R.id.bt_ok);
        Button btCancel = (Button) rootView.findViewById(R.id.bt_cancel);

        btOk.setOnClickListener(onBtClick);
        btCancel.setOnClickListener(onBtClick);
        initializateCheckboxes();

        return rootView;
    }


    private void createCheckboxes() {
        for (String type: markersTypes) {
            CheckBox checkbox = new CheckBox(getActivity().getApplicationContext());
            checkbox.setChecked(false);
            checkbox.setText(type);
            checkbox.setTextColor(getResources().getColor(R.color.preto));
            checkbox.setButtonDrawable(R.drawable.checkbox_selector);
            checkboxes.add(checkbox);
        }
    }

    private void initializateCheckboxes() {
        for (CheckBox checkbox: checkboxes) {
            linearLayout.addView(checkbox);
        }
    }



    private Button.OnClickListener onBtClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bt_ok:
                    for (CheckBox c: checkboxes) {
                        if (c.isChecked()) {
                            markersChecked.add(c.getText().toString());
                        }

                    }
                    mapsFragment.setMarkers(markersChecked);
                    markersChecked.clear();
                    mapsFragment.hideMarkerDescription();
                    getDialog().dismiss();
                    break;
                case R.id.bt_cancel:
                    getDialog().dismiss();
                    break;
                default:
                    break;
            }

        }
    };

    private void setChecked(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            for (CheckBox c: checkboxes) {
                c.setChecked(!mapsFragment.isEmpty(c.getText().toString()));
            }
        } else {
            for (CheckBox c: checkboxes) {
                c.setChecked(savedInstanceState.getBoolean(c.getText().toString()));
            }

        }

    }

    public MapsFragment getMapsFragment() {
        return mapsFragment;
    }

    public void setMapsFragment(MapsFragment mapsFragment) {
        this.mapsFragment = mapsFragment;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        setChecked(savedInstanceState);
    }
    @Override
    @NonNull
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        for (CheckBox c: checkboxes) {
           outState.putBoolean(c.getText().toString(), !mapsFragment.isEmpty(c.getText().toString()));
       }
    }
}
