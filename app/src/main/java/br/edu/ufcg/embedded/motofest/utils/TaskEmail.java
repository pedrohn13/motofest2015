package br.edu.ufcg.embedded.motofest.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import br.edu.ufcg.embedded.motofest.activity.fragments.FeedBackFragment;
import br.edu.ufcg.embedded.motofest.model.Email;



public class TaskEmail extends AsyncTask<Void, Void, Boolean> {
    private final FeedBackFragment fragment;
    private final Email email;
    private static final String LOG_TAG = "log_tag";

    public TaskEmail(Context context, Email email, FeedBackFragment fragment) {
        Context context1 = context;
        this.fragment = fragment;
        this.email = email;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        Log.i(LOG_TAG, "Servico iniciado pelo TaskMessage");
    }

    protected Boolean doInBackground(Void... params) {
        if (!email.equals(null)) {
            Log.i(LOG_TAG, "Servico vai enviar a avaliacao");
            String feedback = "";
            String response = HttpConnectUtil.sendAvaliation(email);
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
            Log.i(LOG_TAG, "Servico concluido pelo TaskMessage");
        } else {
            Log.i(LOG_TAG, "Servico nao concluido pelo TaskMessage");
        }
        try {
            fragment.atualizaDialogo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
