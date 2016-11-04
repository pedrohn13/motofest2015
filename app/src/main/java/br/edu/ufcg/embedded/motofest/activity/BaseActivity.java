package br.edu.ufcg.embedded.motofest.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import br.edu.ufcg.embedded.motofest.R;

/**
 * Created by Pedro
 */
public class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String themeColour;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

    }

    public void setBar(String title) {
        Toolbar toolbarFromContext = (Toolbar) findViewById(R.id.toolbar);
        if (toolbarFromContext != null) {
            toolbar = toolbarFromContext;
        }
        toolbar.setTitle(title);

        changeToolbar();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }

    public int getHeightToolbar() {
        return toolbar.getHeight();
    }

    public void changeToolbar() {
        sharedPreferences = getSharedPreferences(SettingsActivity.PREFERENCE_NAME, MODE_PRIVATE);
        themeColour = sharedPreferences.getString(SettingsActivity.THEME_APP, getString(R.string.black));

        if (themeColour.equals(getString(R.string.red))) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.vermelho_claro));
        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.cinza));
        }
    }

}
