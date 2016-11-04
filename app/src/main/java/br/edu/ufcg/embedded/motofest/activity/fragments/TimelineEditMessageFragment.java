package br.edu.ufcg.embedded.motofest.activity.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.common.SignInButton;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import br.edu.ufcg.embedded.motofest.R;
import br.edu.ufcg.embedded.motofest.activity.MainActivity;
import br.edu.ufcg.embedded.motofest.activity.SplashScreenActivity;
import br.edu.ufcg.embedded.motofest.dao.DataSource;
import br.edu.ufcg.embedded.motofest.model.Message;
import br.edu.ufcg.embedded.motofest.utils.TaskMessage;
import br.edu.ufcg.embedded.motofest.utils.TaskMessageEdit;
import br.edu.ufcg.embedded.motofest.utils.TimelineAdapter;
import de.greenrobot.event.EventBus;


public class TimelineEditMessageFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "TIMELINE_MESSAGE_FRAGMENT";
    private static final int MIL = 1000;

    private ListView listview;
    private ImageButton btSendMessage;
    private LinearLayout layoutTextMessage;
    private LinearLayout layoutLogin;
    private EditText boxMessage;
    private List<Message> listaMensagens;
    private TaskMessageEdit taskMessageEdit;
    private DataSource dataSource;
    private TimelineAdapter timelineAdapter;
    private SharedPreferences sharedPreferences;
    private boolean boxAberto = false;
    private SignInButton btSignInDefault;
    private ImageLoader mImageLoader;
    private boolean inputStatus = false;

    private ProgressDialog progressDialog;

    private boolean catchHeight = false;
    private int heightKeyboard;

    private boolean fragmentEmoji = false;

    private View rootView;

    private MainActivity activity;

    private Message messageEdit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_timeline, container, false);
        rootView = view;
        boxMessage = (EditText) view.findViewById(R.id.layoutBoxMessage_et);
        btSendMessage = (ImageButton) view.findViewById(R.id.bt_send);
        boxMessage.setHint(getString(R.string.hint_message));
        boxMessage.requestFocus();

        MainActivity activityMain = (MainActivity) getActivity();
        messageEdit = activityMain.getMessageEditFragment();

        try {
            boxMessage.setText(activityMain.getMessageEditFragment().getContent());
        } catch (Exception e){
            e.printStackTrace();
        }

        btSendMessage.setOnClickListener(this);
        dataSource = new DataSource(getActivity().getApplicationContext());

        activity = (MainActivity) getActivity();

        sharedPreferences = getActivity().getSharedPreferences(SplashScreenActivity.PREFERENCE_NAME, getActivity().MODE_PRIVATE);

        btSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = boxMessage.getText().toString();

                boolean existEmoticon = existEmoticonMessage(content);

                if (!content.trim().equals("") && !existEmoticon) {
                    if (messageEdit != null) {
                        taskMessageEdit = new TaskMessageEdit(getActivity().getApplicationContext(), content, messageEdit, dataSource, activity);
                        taskMessageEdit.execute();
                        MainActivity activity = (MainActivity) getActivity();
                        activity.showProgressDialog(getString(R.string.editing_publication));

                    }
//                    if (!idPlus.equals("0")) {
//                        Message message = new Message(0, idPlus, name, urlPhoto, content, getActivity().getString(R.string.sending_message), 1);
//                        boxMessage.setText("");
//                        int idMessage = (int) dataSource.saveMessage(message);
//                        if (idMessage > 0) {
//                            message.setId(idMessage);
//                            EventBus.getDefault().post(message);
//                        }
//
//                        if (message != null) {
//                            taskMessage = new TaskMessage(getActivity().getApplicationContext(), message, null, dataSource, activity);
//                            taskMessage.execute();
//                        }
//                    }

                    activity.closeFragmentMessage();
                } else {
                    if (existEmoticon) {
                        activity.showToastMessageEmoticon();
                    } else {
                        activity.showToastMessageBlank();
                    }
                }


            }
        });


        return view;
    }

    private boolean existEmoticonMessage(String message) {
        for (int i = 0; i < message.length(); i++) {
            int codePointAt = Character.codePointAt(message, i);

            if (codePointAt > MIL) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        Log.i("Motofest", "Test");
    }
}

