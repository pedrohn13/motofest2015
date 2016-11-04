package br.edu.ufcg.embedded.motofest.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;


import br.edu.ufcg.embedded.motofest.R;

public class SettingsActivity extends BaseActivity {

    private SwitchCompat switchTimeline, switchTimelineSilenciar, switchLauncher, switchLauncherSom, switchTheme;

    public static final String PREFERENCE_NAME = "SETTINGS_PREFERENCES";

    public static final String TIMELINE_NOTIFICATION = "TIMELINE_NOTIFICATION";
    public static final String THEME_APP = "THEME_APP";
    public static final String TIMELINE_SILENCE = "TIMELINE_SILENCE";
    public static final String SPLASH_LAUNCHER = "SPLASH_LAUNCHER";
    public static final String SPLASH_SOUND = "SPLASH_SOUND";


    private SharedPreferences preferencesEditor;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor prefEditor;



    private boolean timelineStatus, timelineSilenceStatus, launcherStatus, launcherSoundStatus;
    private String themeStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchTimeline = (SwitchCompat) findViewById(R.id.switchTimeline);
        switchTimelineSilenciar = (SwitchCompat) findViewById(R.id.switchTimelineSilenciosa);
        switchLauncher = (SwitchCompat) findViewById(R.id.switchSplashLauncher);
        switchLauncherSom = (SwitchCompat) findViewById(R.id.switchSplashSound);
        switchTheme = (SwitchCompat) findViewById(R.id.switchTheme);
        sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);

        setUpPreferences();
        setUpSwitchs();

        setBar(getString(R.string.action_settings));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_back);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int itemId = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (itemId == R.id.action_settings) {
            return true;
        } else if (itemId == android.R.id.home) {
            finish();
            return true;
        } else if (itemId == R.id.action_share) {
            startActivity(Intent.createChooser(new Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app_text)
                    + " https://play.google.com/store/apps/details?id=" + getString(R.string.app_id)), "Share via"));
            return true;
        } else if (itemId == R.id.action_rate) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getString(R.string.app_id))));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setUpPreferences() {

        timelineStatus = sharedPreferences.getBoolean(TIMELINE_NOTIFICATION, true);
        timelineSilenceStatus = sharedPreferences.getBoolean(TIMELINE_SILENCE, false);
        launcherStatus = sharedPreferences.getBoolean(SPLASH_LAUNCHER, true);
        launcherSoundStatus = sharedPreferences.getBoolean(SPLASH_SOUND, true);
        themeStatus = sharedPreferences.getString(THEME_APP, getString(R.string.black));

        if (timelineStatus) {
            switchTimeline.setChecked(true);
            switchTimelineSilenciar.setEnabled(true);
        } else {
            switchTimeline.setChecked(false);
            switchTimelineSilenciar.setEnabled(false);
        }

        if (timelineSilenceStatus) {
            switchTimelineSilenciar.setChecked(true);
        } else {
            switchTimelineSilenciar.setChecked(false);
        }

        if (launcherStatus) {
            switchLauncher.setChecked(true);
            switchLauncherSom.setEnabled(true);
        } else {
            switchLauncher.setChecked(false);
            switchLauncherSom.setEnabled(false);
        }

        if (launcherSoundStatus) {
            switchLauncherSom.setChecked(true);
        } else {
            switchLauncherSom.setChecked(false);
        }

        if (themeStatus.equals(getString(R.string.red))) {
            switchTheme.setChecked(true);
        } else {
            switchTheme.setChecked(false);
        }

        preferencesEditor = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        prefEditor = preferencesEditor.edit();
    }

    public void setUpSwitchs() {
        switchTimeline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (timelineStatus) {
                    timelineStatus = false;
                    switchTimelineSilenciar.setEnabled(false);
                } else {
                    timelineStatus = true;
                    switchTimelineSilenciar.setEnabled(true);
                }

                prefEditor.putBoolean(TIMELINE_NOTIFICATION, timelineStatus);
                prefEditor.apply();

            }
        });

        switchTimelineSilenciar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (timelineSilenceStatus) {
                    timelineSilenceStatus = false;
                } else {
                    timelineSilenceStatus = true;
                }

                prefEditor.putBoolean(TIMELINE_SILENCE, timelineSilenceStatus);
                prefEditor.apply();
            }
        });

        switchLauncher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (launcherStatus) {
                    launcherStatus = false;
                    switchLauncherSom.setEnabled(false);
                } else {
                    launcherStatus = true;
                    switchLauncherSom.setEnabled(true);
                }

                prefEditor.putBoolean(SPLASH_LAUNCHER, launcherStatus);
                prefEditor.apply();
            }
        });

        switchLauncherSom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (launcherSoundStatus) {
                    launcherSoundStatus = false;
                } else {
                    launcherSoundStatus = true;
                }

                prefEditor.putBoolean(SPLASH_SOUND, launcherSoundStatus);
                prefEditor.apply();
            }
        });

        switchTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (themeStatus.equals(getString(R.string.red))) {
                    themeStatus = getString(R.string.black);
                } else {
                    themeStatus = getString(R.string.red);
                }

                prefEditor.putString(THEME_APP, themeStatus);
                prefEditor.apply();

                changeToolbar();
            }
        });
    }
}
