package br.edu.ufcg.embedded.motofest.activity.fragments;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

import br.edu.ufcg.embedded.motofest.R;
import br.edu.ufcg.embedded.motofest.activity.MainActivity;
import br.edu.ufcg.embedded.motofest.activity.SplashScreenActivity;
import br.edu.ufcg.embedded.motofest.dao.DataSource;
import br.edu.ufcg.embedded.motofest.model.Message;
import br.edu.ufcg.embedded.motofest.utils.TaskMessage;
import br.edu.ufcg.embedded.motofest.utils.TaskMessageDelete;
import br.edu.ufcg.embedded.motofest.utils.TaskMessageEdit;
import br.edu.ufcg.embedded.motofest.utils.TimelineAdapter;
import de.greenrobot.event.EventBus;


public class TimelineFragment extends Fragment implements OnClickListener {
    private static final int CINQUENTA = 50;
    private static final int CINCO = 5;
    private ListView listview;
    private Button boxMessage;
    private List<Message> listaMensagens;
    private TaskMessage taskMessage;
    private DataSource dataSource;
    private TimelineAdapter timelineAdapter;
    private static final int MIL = 1000;
    private static final int DIA = 1024;
    private SharedPreferences sharedPreferences;
    private String idUserPlus;
    private TaskMessageDelete taskMessageDelete;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        listview = (ListView) view.findViewById(R.id.messages_list);
        LinearLayout layoutTextMessage = (LinearLayout) view.findViewById(R.id.layoutBoxMessage);
        LinearLayout layoutLogin = (LinearLayout) view.findViewById(R.id.layoutLogin);
        boxMessage = (Button) view.findViewById(R.id.boxMessage);
        SignInButton btSignInDefault = (SignInButton) view.findViewById(R.id.btSignInDefault);
        EventBus.getDefault().removeAllStickyEvents();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        btSignInDefault.setOnClickListener(this);

        sharedPreferences = getActivity().getSharedPreferences(SplashScreenActivity.PREFERENCE_NAME, getActivity().MODE_PRIVATE);
        idUserPlus = sharedPreferences.getString(SplashScreenActivity.USER_ID, "0");

        boxMessage.setText(getString(R.string.hint_message));
        dataSource = new DataSource(getActivity().getApplicationContext());

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long itemId) {

                try {
                    Message msg = (Message) timelineAdapter.getItem(position);
                    if (msg.getDateSend().equals(getString(R.string.message_error))) {
                        msg.setDateSend(getString(R.string.sending_message));
                        dataSource.updateMessage(msg);
                        timelineAdapter.notifyDataSetChanged();
                        taskMessage = new TaskMessage(getActivity().getApplicationContext(), msg, null, dataSource, (MainActivity) getActivity());
                        taskMessage.execute();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final CharSequence colors[] = new CharSequence[]{getActivity().getString(R.string.edit)};

                final Message message = (Message) timelineAdapter.getItem(position);

                if(message.getIdUser().equals(idUserPlus)){
                    final MainActivity activity = (MainActivity) getActivity();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    //builder.setTitle(getActivity().getString(R.string.what_to_do));
                    builder.setItems(colors, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    activity.setMessageEditFragment(message);
                                    activity.openFragmentMessageEdit();
                                    break;
//                                case 1:
//                                    taskMessageDelete = new TaskMessageDelete(message, dataSource, (MainActivity) getActivity());
//                                    taskMessageDelete.execute();
//
//                                    activity.showProgressDialog(getString(R.string.delete_publication));
//                                    break;
                            }
                        }
                    });
                    builder.show();
                }

                return false;
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SplashScreenActivity.PREFERENCE_NAME, getActivity().MODE_PRIVATE);
        String idUser = sharedPreferences.getString(SplashScreenActivity.USER_ID, "0");

        if (!idUser.equals("0")) {
            //btSendMessage(view);
            loadList();
            layoutLogin.setVisibility(LinearLayout.GONE);
        } else {
            layoutTextMessage.setVisibility(LinearLayout.GONE);
            layoutLogin.setVisibility(LinearLayout.VISIBLE);
            listview.setVisibility(ListView.GONE);
            boxMessage.setVisibility(Button.GONE);
        }

        return view;
    }

    public void onEvent(final Message pushMessage) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (pushMessage != null) {
                    listaMensagens.add(0, pushMessage);
                    pushMessage.setVisualized(1);
                    dataSource.updateMessage(pushMessage);
                    timelineAdapter.notifyDataSetChanged();
                } else {
                    loadList();
                    timelineAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void onEvent(final Boolean atualiza) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    loadList();
                    timelineAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadList();
    }

    private void loadList() {
        DisplayImageOptions mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_motofest)
                .showImageOnFail(R.drawable.ic_motofest)
                .showImageOnLoading(R.drawable.ic_motofest)
                .displayer(new RoundedBitmapDisplayer(MIL))
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext())
                .defaultDisplayImageOptions(mDisplayImageOptions)
                .memoryCacheSize(CINQUENTA * DIA * DIA)
                .diskCacheSize(CINQUENTA * DIA * DIA)
                .threadPoolSize(CINCO)
                .writeDebugLogs()
                .build();
        ImageLoader mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(conf);

        listaMensagens = dataSource.getMessages();
        boolean retorno = dataSource.setAllVisualized();
        timelineAdapter = new TimelineAdapter(getActivity().getApplicationContext(), listaMensagens, mImageLoader, (MainActivity) getActivity());
        listview.setAdapter(timelineAdapter);


        boxMessage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                activity.openFragmentMessage();
            }
        });
    }

    @Override
    public void onClick(View view) {
        //chamarTelaPrincipal();
        try {
            MainActivity activity = (MainActivity) getActivity();
            activity.setLogin(true);
            activity.callLoginGooglePlus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void atualizaLista() {
        //listaMensagens = dataSource.getMessages();
        //boolean retorno = dataSource.setAllVisualized();
        //timelineAdapter = new TimelineAdapter(getActivity().getApplicationContext(), listaMensagens, mImageLoader);
        //listview.setAdapter(timelineAdapter);
        timelineAdapter.notifyDataSetChanged();
    }
}
