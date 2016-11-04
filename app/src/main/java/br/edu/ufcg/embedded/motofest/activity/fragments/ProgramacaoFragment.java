package br.edu.ufcg.embedded.motofest.activity.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.edu.ufcg.embedded.motofest.R;
import br.edu.ufcg.embedded.motofest.activity.MainActivity;
import br.edu.ufcg.embedded.motofest.utils.AtracoesListItem;
import br.edu.ufcg.embedded.motofest.utils.DiaEventoListAdapter;


public class ProgramacaoFragment extends Fragment {

    private static final int DIA_UM = 1;
    private static final int DIA_DOIS = 2;
    private static final int DIA_TRES = 3;
    private static final int DIA_QUATRO = 4;
    private static final int DIA_CINCO = 5;
    private static final int DIA_SEIS = 6;
    private static final int DIA_SETE = 7;
    private static final int HORA_8 = 8;
    private static final int MES_9 = 9;
    private static final int NUM_22 = 22;
    private static final int MIL = 1000;
    private static final int DIVISOR = 86400;
    private static final int HORA = 3600;
    private static final int DIVISOR_2 = 60;
    private static final String TAG = "PROGRAMACAO_FRAGMENT";

    private ExpandableListView listview;
    private List<String> listDataHeader;
    private HashMap<String, List<AtracoesListItem>> listDataChild;

    private LinearLayout layoutCount;


    private TextView mDaysLabel, mHoursLabel, mMinutesLabel, mSecondsLabel;

   private final Time conferenceTime = new Time(Time.getCurrentTimezone());

    private int year;

    private int mDisplayDays;
    private int mDisplayHours;
    private int mDisplayMinutes;
    private int mDisplaySeconds;
    private TextView mCountText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_programacao, container, false);
        listview = (ExpandableListView) view.findViewById(R.id.eventos_list);
        layoutCount = (LinearLayout) view.findViewById(R.id.layoutCount);
        loadList();
        configureCount(view);

        disableCollapseListView(listview);

        return view;
    }



    private void loadList() {
        prepareListData();
        listview.setAdapter(new DiaEventoListAdapter(getActivity(), listDataHeader, listDataChild));
        int todayPosition = getTodayPosition();
        if (todayPosition >= 0) {
            listview.setSelection(todayPosition);
            listview.expandGroup(todayPosition);
        }

        for (int i = listview.getCount() - 1; i >= 0; i--) {
            listview.setSelection(i);
            listview.expandGroup(i);
        }

    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        HashMap<String, List<AtracoesListItem>> aux =
                ((MainActivity) getActivity()).getController().getMapaEventosAtracoes();

        listDataHeader = asSortedList(aux.keySet());
        for (String string : listDataHeader) {
            listDataChild.put(string, aux.get(string));
        }


    }



    private int getTodayPosition() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        return listDataHeader.indexOf(formatData(simpleDateFormat.format(today)));
    }

    private String formatData(String dateLabel) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date day = new Date();
        try {
            day = format.parse(dateLabel);
        } catch (ParseException e) {
            Log.d(TAG,
                    "Error formatting day.");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String sHoje = format.format(new Date());
        String sDateLabel = format.format(day);
        String weekDay;
        if (sHoje.equals(sDateLabel)) {
            weekDay = getString(R.string.hoje);
        } else {
            weekDay = getWeekDay(dayOfWeek);
        }

        return String.format("%s, %s", weekDay, dateLabel);

    }

    private String getWeekDay(int weekDay) {
        switch (weekDay) {
            case DIA_UM:
                return getString(R.string.sun);
            case DIA_DOIS:
                return getString(R.string.mon);
            case DIA_TRES:
                return getString(R.string.tue);
            case DIA_QUATRO:
                return getString(R.string.wed);
            case DIA_CINCO:
                return getString(R.string.thu);
            case DIA_SEIS:
                return getString(R.string.fri);
            case DIA_SETE:
                return getString(R.string.sat);
            default:
        }
        return "";
    }

    private static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> tCollection) {
        List<T> list = new ArrayList<>(tCollection);
        java.util.Collections.sort(list);
        return list;
    }

    private void disableCollapseListView(ExpandableListView listView) {
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long itemid) {
                // Doing nothing
                return true;
            }
        });
    }

    private void configureCount(View view) {
        this.conferenceTime.setToNow();
        this.year = conferenceTime.year;

        //this.mDaysLabel = (TextView) view.findViewById(R.id.textLabelDays);
        //this.mHoursLabel = (TextView) view.findViewById(R.id.textLabelHours);
        //this.mMinutesLabel = (TextView) view.findViewById(R.id.textLabelMinutes);
        //this.mSecondsLabel = (TextView) view.findViewById(R.id.textLabelSeconds);

        this.mCountText = (TextView) view.findViewById(R.id.countText);

        configureEventDate();
    }

    private void configureEventDate() {
        int hour = HORA_8;
        int minute = 00;
        int second = 00;
        int monthDay = NUM_22;
        int month = MES_9;
        conferenceTime.set(second, minute, hour, monthDay, month, year);
        conferenceTime.normalize(true);
        long confMillis = conferenceTime.toMillis(true);

        Time nowTime = new Time(Time.getCurrentTimezone());
        nowTime.setToNow();
        nowTime.normalize(true);
        long nowMillis = nowTime.toMillis(true);

        long milliDiff = confMillis - nowMillis;

        new CountDownTimer(milliDiff, MIL) {

            @Override
            public void onTick(long millisUntilFinished) {
                try {
                    ProgramacaoFragment.this.mDisplayDays = (int) ((millisUntilFinished / MIL) / DIVISOR);
                    ProgramacaoFragment.this.mDisplayHours = (int) (((millisUntilFinished / MIL) - (ProgramacaoFragment.this.mDisplayDays * DIVISOR)) / HORA);
                    ProgramacaoFragment.this.mDisplayMinutes = (int) (((millisUntilFinished / MIL) - ((ProgramacaoFragment.this.mDisplayDays * DIVISOR)
                            + (ProgramacaoFragment.this.mDisplayHours * HORA))) / DIVISOR_2);
                    ProgramacaoFragment.this.mDisplaySeconds = (int) ((millisUntilFinished / MIL) % DIVISOR_2);

                    String mDaysLabel = String.valueOf(ProgramacaoFragment.this.mDisplayDays) + getString(R.string.days) + "  ";
                    String mHoursLabel = String.valueOf(ProgramacaoFragment.this.mDisplayHours) + getString(R.string.hours) + "  ";
                    String mMinutesLabel = String.valueOf(ProgramacaoFragment.this.mDisplayMinutes) + getString(R.string.minutes) + "  ";
                    String mSecondsLabel = String.valueOf(ProgramacaoFragment.this.mDisplaySeconds) + getString(R.string.seconds);

                    ProgramacaoFragment.this.mCountText.setText(mDaysLabel + mHoursLabel + mMinutesLabel + mSecondsLabel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                layoutCount.setVisibility(LinearLayout.GONE);
            }
        } .start();
    }

}
