package br.edu.ufcg.embedded.motofest.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import br.edu.ufcg.embedded.motofest.R;
import br.edu.ufcg.embedded.motofest.activity.MainActivity;
import br.edu.ufcg.embedded.motofest.dao.DataSource;
import br.edu.ufcg.embedded.motofest.model.Message;
import br.edu.ufcg.embedded.motofest.model.User;



public class TaskMessage extends AsyncTask<Void, Void, Boolean> {
    private final Message message;
    private final User user;
    private final DataSource dataSource;
    private final MainActivity activity;
    private boolean atualiza;
    private Context context;

    public TaskMessage(Context context, Message message, User user, DataSource dataSource, MainActivity activity) {
        this.context = context;
        this.message = message;
        this.user = user;
        this.dataSource = dataSource;
        this.activity = activity;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected Boolean doInBackground(Void... params) {
        atualiza =  false;
        if (message != null && user == null) {

            String feedback = "", idMessage = "0", dateSend = "";
            String response = HttpConnectUtil.sendMessageToServer(message);
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(response);
                feedback = jsonObject.getString("feedback");
                idMessage = jsonObject.getString("id_message");
                dateSend = jsonObject.getString("date_send");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (feedback.equals("true")) {
                message.setDateSend(dateSend);
                if (dataSource != null) {
                    dataSource.updateMessage(message);
                    atualiza = true;
                }
                String responseAll = HttpConnectUtil.sendMessagetoAllUsers(idMessage);
                return true;
            }

        } else if (message == null && user != null) {
            String feedback = "";
            String response = HttpConnectUtil.sendUserToServer(user);
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(response);
                feedback = jsonObject.getString("feedback");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (feedback.equals("true")) {
                return true;
            }
        }
        return false;
    }

    protected void onPostExecute(Boolean result) {
        if (result) {
            if (atualiza) {
                try {
                    activity.getFragmentTimeline().atualizaLista();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                message.setDateSend(context.getString(R.string.message_error));
                if (dataSource != null) {
                    dataSource.updateMessage(message);
                    atualiza = true;
                }

                if (atualiza) {
                    try {
                        activity.getFragmentTimeline().atualizaLista();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}
