package br.edu.ufcg.embedded.motofest.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.android.gms.gcm.GcmListenerService;

import java.util.List;

import br.edu.ufcg.embedded.motofest.R;
import br.edu.ufcg.embedded.motofest.activity.MainActivity;
import br.edu.ufcg.embedded.motofest.activity.SettingsActivity;
import br.edu.ufcg.embedded.motofest.dao.DataSource;
import br.edu.ufcg.embedded.motofest.model.Message;
import de.greenrobot.event.EventBus;


public class MyGcmListenerService extends GcmListenerService {
    public static final String LOG = "LOG";
    private static final int ID_NOTIFICATION_MOTOFEST = 999;
    public static final String EXTRAS_NOTIFICATION = "EXTRAS_NOTIFICATION";

    private static final int TIME_VIBRATE = 500;

    private boolean timelineStatus, timelineSilenceStatus;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        //super.onMessageReceived(from, data);
        String tipoMensagem = "";

        DataSource dataSource = new DataSource(this);

        SharedPreferences sharedPreferences = getSharedPreferences(SettingsActivity.PREFERENCE_NAME, MODE_PRIVATE);
        timelineStatus = sharedPreferences.getBoolean(SettingsActivity.TIMELINE_NOTIFICATION, true);
        timelineSilenceStatus = sharedPreferences.getBoolean(SettingsActivity.TIMELINE_SILENCE, true);

        try {
            tipoMensagem = data.getString("type_message");
        } catch (Exception e) {
          e.printStackTrace();
        }

        if (tipoMensagem.equals("TimelineMessage")) {
            Message message = new Message(0,
                    data.getString("id_user"),
                    data.getString("name_user"),
                    data.getString("url_photo_user"),
                    data.getString("content"),
                    data.getString("date_send"),
                    0);

            int idMsg = (int) dataSource.saveMessage(message);

            if (idMsg != 0) {
                message.setId(idMsg);
                EventBus.getDefault().post(message);

                List<Message> mensagensNaoVizualizadas = dataSource.getMessagesNotVisualized();

                if (timelineStatus) {
                    if (mensagensNaoVizualizadas.size() > 1) {
                        geraNotificationList(mensagensNaoVizualizadas);
                    } else {
                        geraNotification(message);
                    }
                }
            }

        } else if (tipoMensagem.equals("TimelineEditMessage")) {
            Message message = dataSource.getMessage(data.getString("id_user"),
                    data.getString("name_user"),
                    data.getString("url_photo_user"),
                    data.getString("date_send"));
            try {
                if (!message.equals(null)) {
                    message.setContent(data.getString("content"));
                    dataSource.updateMessage(message);
                    EventBus.getDefault().post(true);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private void geraNotificationList(List<Message> listaMsg) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon_notification)
                .setContentTitle("Motofest - Timeline")
                .setContentText(String.valueOf(listaMsg.size()) + " " + getString(R.string.mensagem_notificacao));


        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.putExtra(EXTRAS_NOTIFICATION, "1");
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        inboxStyle.setBigContentTitle(String.valueOf(listaMsg.size()) + " " + getString(R.string.mensagem_notificacao));

        for (int i = 0; i < listaMsg.size(); i++) {
            Message msg = listaMsg.get(i);
            inboxStyle.addLine(msg.getNameUser() + ": " + msg.getContent());
        }
        int color = getResources().getColor(R.color.vermelho_claro);
        mBuilder.setColor(color);
        mBuilder.setNumber(listaMsg.size());
        mBuilder.setStyle(inboxStyle);
        mBuilder.setAutoCancel(true);
        //Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (!timelineSilenceStatus) {
            mBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            mBuilder.setDefaults(Notification.DEFAULT_ALL);
            mBuilder.setVibrate(new long[]{TIME_VIBRATE, TIME_VIBRATE});
        }

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(ID_NOTIFICATION_MOTOFEST, mBuilder.build());


    }

    private void geraNotification(Message message) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.icon_notification)
                        .setContentTitle("Motofest - Timeline")
                        .setContentText(message.getNameUser() + ": " + message.getContent());


        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.putExtra(EXTRAS_NOTIFICATION, "1");
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setAutoCancel(true);
        if (!timelineSilenceStatus) {
            mBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            mBuilder.setDefaults(Notification.DEFAULT_ALL);
            mBuilder.setVibrate(new long[]{TIME_VIBRATE, TIME_VIBRATE});
        }
        int color = getResources().getColor(R.color.vermelho_claro);
        mBuilder.setColor(color);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(ID_NOTIFICATION_MOTOFEST, mBuilder.build());

    }
}
