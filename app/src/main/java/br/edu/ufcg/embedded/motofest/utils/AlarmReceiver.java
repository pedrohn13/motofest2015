package br.edu.ufcg.embedded.motofest.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.edu.ufcg.embedded.motofest.R;
import br.edu.ufcg.embedded.motofest.activity.MainActivity;
import br.edu.ufcg.embedded.motofest.controller.Controller;
import br.edu.ufcg.embedded.motofest.specific.MotoFestData;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String APP_NOTIFY_PREFS = "AppSharedPrefsNotify";
    private static final String TAG = "Alarme Receiver";
    private static final String NOTIFY = "notify";
    private Context mContext;
    private Controller controller;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(MainActivity.ACTION_ALARM)) {
            this.mContext = context;
            controller = Controller.getInstance(new MotoFestData());
            SharedPreferences settings = mContext.getSharedPreferences(APP_NOTIFY_PREFS, 0);
            boolean isPrefNotify = settings.getBoolean(NOTIFY, true);
            if (isPrefNotify) {
                pegaNotificacao();
            }

            Log.d(TAG, "AlarmReceiver recebeu o alarme");
        }
    }

    private void pegaNotificacao() {
        Date data = new Date();
        data.setTime(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String date = formatter.format(data);
        try {
            int idString = controller.getNotificacaoDia(date);
            String notificacao = mContext.getString(idString);
            launchNotification(notificacao);
        } catch (Exception e) {
            Log.d(TAG, "Error in Alarm: " + e.getMessage());
        }

    }

    private void launchNotification(String mensagemNotificacao) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mContext)
                        .setSmallIcon(R.drawable.laucher)
                        .setContentTitle(mContext.getString(R.string.app_name))
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(mensagemNotificacao))
                        .setContentText(mensagemNotificacao);
        Intent resultIntent = new Intent(mContext, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        int mId = 1;
        mNotificationManager.notify(mId, mBuilder.build());
    }
}
