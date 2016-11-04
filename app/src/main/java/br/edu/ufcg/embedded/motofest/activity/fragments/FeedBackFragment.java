package br.edu.ufcg.embedded.motofest.activity.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;


import br.edu.ufcg.embedded.motofest.R;
import br.edu.ufcg.embedded.motofest.activity.MainActivity;
import br.edu.ufcg.embedded.motofest.activity.SplashScreenActivity;
import br.edu.ufcg.embedded.motofest.model.Email;
import br.edu.ufcg.embedded.motofest.utils.TaskEmail;


public class FeedBackFragment extends Fragment {
    private EditText avaliation;
    private ProgressDialog dialogProgress;
    private String email, name;
    private LinearLayout layoutEvaluate, layoutLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
                SplashScreenActivity.PREFERENCE_NAME, getActivity().MODE_PRIVATE);
        name = sharedPreferences.getString(SplashScreenActivity.USER_NOME, "");
        email = sharedPreferences.getString(SplashScreenActivity.USER_EMAIL, "");

        avaliation = (EditText) view.findViewById(R.id.avaliation_text);


        SignInButton btSignInDefault = (SignInButton) view.findViewById(R.id.btSignInDefault);
        btSignInDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
                        SplashScreenActivity.PREFERENCE_NAME, getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(SplashScreenActivity.USER_ID, "");
                editor.putBoolean(SplashScreenActivity.USER_STATUS, false);

                editor.apply();
                startActivity(new Intent(getActivity(), SplashScreenActivity.class));
                getActivity().finish();
            */
                try {
                    MainActivity activity = (MainActivity) getActivity();
                    activity.setLogin(true);
                    activity.callLoginGooglePlus();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        if (email.equals("")) {
            layoutEvaluate = (LinearLayout) view.findViewById(R.id.layoutEvaluate);
            layoutLogin = (LinearLayout) view.findViewById(R.id.layoutLogin);
            layoutEvaluate.setVisibility(View.INVISIBLE);
            layoutLogin.setVisibility(View.VISIBLE);
        }

        ((Button) view.findViewById(R.id.avaliation_bt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = avaliation.getText().toString();

                if (text.equals("")) {
                    Context contexto = getActivity();
                    String texto = getString(R.string.texto2);
                    int duracao = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(contexto, texto, duracao);
                    toast.show();
                } else {
                    dialogProgress = ProgressDialog.show(getActivity(), getString(R.string.sending_avaliation_title), getString(R.string.sending_avaliation), true);
                    dialogProgress.setCancelable(true);

                    Email email1 = new Email(name, email, text);
                    TaskEmail taskEmail = new TaskEmail(getActivity(), email1, FeedBackFragment.this);
                    taskEmail.execute();

                }
            }
        });

        return view;
    }

    public void atualizaDialogo() {
        name = "";
        email = "";
        avaliation.setText("");
        dialogProgress.dismiss();
        Context contexto = getActivity();
        String texto = getString(R.string.avaliation_send);
        int duracao = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(contexto, texto, duracao);
        toast.show();
    }

}
