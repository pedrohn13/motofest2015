package br.edu.ufcg.embedded.motofest.utils;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import br.edu.ufcg.embedded.motofest.R;
import br.edu.ufcg.embedded.motofest.activity.MainActivity;
import br.edu.ufcg.embedded.motofest.dao.DataSource;
import br.edu.ufcg.embedded.motofest.model.Message;
import br.edu.ufcg.embedded.motofest.model.User;


public class TaskMessageEdit extends AsyncTask<Void, Void, Boolean> {
    private final Message message;
    private final DataSource dataSource;
    private final MainActivity activity;
    private boolean atualiza;
    private Context context;
    private String content;

    public TaskMessageEdit(Context context, String content, Message message, DataSource dataSource, MainActivity activity) {
        this.context = context;
        this.message = message;
        this.dataSource = dataSource;
        this.activity = activity;
        this.content = content;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected Boolean doInBackground(Void... params) {
        atualiza =  false;
        if (message != null) {
            String feedback = "", idMessage = "0", dateSend = "";
            String response = HttpConnectUtil.sendMessageEditToServer(message, content);
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(response);
                feedback = jsonObject.getString("feedback");
                idMessage = jsonObject.getString("id_message");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (feedback.equals("true")) {
                if (dataSource != null) {
                    message.setContent(content);
                    dataSource.updateMessage(message);
                    atualiza = true;
                }
                String responseAll = HttpConnectUtil.sendMessageEdittoAllUsers(idMessage);
                return true;
            }

        }
        return false;
    }

    protected void onPostExecute(Boolean result) {
        try {
            activity.reloadTimeline();
            activity.cancelProgressDialog(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result) {
            if (atualiza) {
                try {
                   activity.getFragmentTimeline().atualizaLista();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }



}