package br.edu.ufcg.embedded.motofest.utils;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import br.edu.ufcg.embedded.motofest.activity.MainActivity;
import br.edu.ufcg.embedded.motofest.dao.DataSource;
import br.edu.ufcg.embedded.motofest.model.Message;


public class TaskMessageDelete extends AsyncTask<Void, Void, Boolean> {
    private final Message message;
    private final DataSource dataSource;
    private final MainActivity activity;
    private boolean atualiza;

    public TaskMessageDelete(Message message, DataSource dataSource, MainActivity activity) {
        this.message = message;
        this.dataSource = dataSource;
        this.activity = activity;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected Boolean doInBackground(Void... params) {
        atualiza =  false;
        if (message != null) {
            String feedback = "", idMessage = "0", dateSend = "";
            String response = HttpConnectUtil.deleteMessageServer(message);
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(response);
                feedback = jsonObject.getString("feedback");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (feedback.equals("true")) {
                if (dataSource != null) {
                    dataSource.deleteMessage(message);
                    atualiza = true;
                }
                String responseAll = HttpConnectUtil.deleteMessageAllUsers(idMessage);
                return true;
            }
        }
        return false;
    }

    protected void onPostExecute(Boolean result) {
        try {
            activity.cancelProgressDialog(result);
            activity.getFragmentTimeline().atualizaLista();
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