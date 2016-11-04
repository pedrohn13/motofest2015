package br.edu.ufcg.embedded.motofest.activity;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.edu.ufcg.embedded.motofest.R;
import br.edu.ufcg.embedded.motofest.activity.fragments.AboutFragment;
import br.edu.ufcg.embedded.motofest.activity.fragments.FeedBackFragment;
import br.edu.ufcg.embedded.motofest.activity.fragments.HelpFragment;
import br.edu.ufcg.embedded.motofest.activity.fragments.MapDialogFragment;
import br.edu.ufcg.embedded.motofest.activity.fragments.MapsFragment;
import br.edu.ufcg.embedded.motofest.activity.fragments.PartnersFragment;
import br.edu.ufcg.embedded.motofest.activity.fragments.ProgramacaoFragment;
import br.edu.ufcg.embedded.motofest.activity.fragments.TabsFragment;
import br.edu.ufcg.embedded.motofest.activity.fragments.TimelineEditMessageFragment;
import br.edu.ufcg.embedded.motofest.activity.fragments.TimelineFragment;
import br.edu.ufcg.embedded.motofest.activity.fragments.TimelineMessageFragment;
import br.edu.ufcg.embedded.motofest.activity.fragments.VideoDialogFragment;
import br.edu.ufcg.embedded.motofest.controller.Controller;
import br.edu.ufcg.embedded.motofest.model.Message;
import br.edu.ufcg.embedded.motofest.services.MyGcmListenerService;
import br.edu.ufcg.embedded.motofest.services.RegistrationIntentService;
import br.edu.ufcg.embedded.motofest.specific.MotoFestData;
import br.edu.ufcg.embedded.motofest.utils.ItemMenu;
import br.edu.ufcg.embedded.motofest.utils.MenuAdapter;


public class MainActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private static final String TIMEL_TAG = "TIMEL_TAG";
    private static final String TIMEL_MESSAGE_TAG = "TIMEL_MESSAGE_TAG";
    private static final String TIMEL_EDIT_MESSAGE_TAG = "TIMEL__EDIT_MESSAGE_TAG";
    public static final String MAP_TAG = "MAP_TAG";
    private static final String PAR_TAG = "PAR_TAG";
    private static final String VIDEO_TAG = "VIDEO_TAG";
    public static final String TABS_TAG = "TABS_TAG";
    public static final String ABOUT_TAG = "ABOUT_TAG";
    public static final String HELP_TAG = "HELP_TAG";
    public static final String FEEDBACK_TAG = "FEEDBACK_TAG";
    private static final int NOVE_MIL = 9000;

    private MapDialogFragment dialogFragment;
    private Menu menu;
    private String email, name;

    private String[] mItensTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private TypedArray mItensImageIcons;

    private Fragment currentFragment;
    private TimelineFragment timelineFragment;
    private MapsFragment mapsFragment;
    private PartnersFragment partnersFragment;
    private TimelineMessageFragment timelineMessageFragment;
    private TimelineEditMessageFragment timelineEditMessageFragment;
    private FeedBackFragment feedBackFragment;
    private HelpFragment helpFragment;
    private AboutFragment aboutFragment;

    private String senderApiKey = "AIzaSyC4TcsJe9e-5XxySzw9SJ-TrhxfD9MFgT8";
    public static final String SENDER_ID = "851795169770";

    public static final int HOME = 1;
    private static final int CAMERA = 2;
    private static final int TIMELINE = 3;
    private static final int MAPA = 4;
    private static final int PARTNERS = 5;
    private static final int CONTACT = 6;
    private static final int HELP = 7;
    private static final int ABOUT = 8;

    private Controller controller;

    private int lastFragment = 0;

    private GoogleApiClient googleApiClient;

    private Context mContext;
    public static final String ACTION_ALARM = "br.edu.ufcg.embedded.motofest.utils.AlarmReceiver";
    private static final String TAG = "MainActivity - Motofest";
    private static final String PREFERENCE_NAME = "NotificationService";
    private static final String NOTIFICATION_INIT = "NotificationInit";

    private FragmentManager fragmentManager;
    private Fragment tabsFragment;
    private ActionBarDrawerToggle mDrawerToggle;

    private boolean messageIntent;
    private boolean drawerOpened;
    private boolean notSetHeader;

    private String themeColour;
    private SharedPreferences sharedPreferences;
    private ProgressDialog progressDialog;

    private static final int SIGN_IN_CODE = 56465;
    private ConnectionResult connectionResult;

    private boolean isConsentScreenOpened;
    private boolean isSignInButtonClicked, btLogin, btLogout, jaTentouLogar;

    private Message messageEditFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (String.valueOf(Locale.getDefault()).equals("pt_BR")) {
            mItensTitles = getResources().getStringArray(R.array.itens_menu);
        } else {
            mItensTitles = getResources().getStringArray(R.array.itens_menuen);
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        changeThemeIcons();
        notSetHeader = true;

        mContext = getApplicationContext();
        setUpFragments();
        //initializeTabHost();

        fragmentManager = getSupportFragmentManager();

        controller = Controller.getInstance(new MotoFestData());
        setBar(controller.getNomeEvento());
        controller.setLanguage(Locale.getDefault());

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        setUpLogoutPlus();


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                try {
                    imm.hideSoftInputFromWindow(findViewById(R.id.layoutBoxMessage_et).getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (currentFragment != mapsFragment) {
                    invalidateOptionsMenu();
                }
                drawerOpened = true;
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                if (currentFragment != mapsFragment) {
                    invalidateOptionsMenu();
                }
                drawerOpened = false;
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);


//        inicializeNotification();

        if (checkPlayServices()) {
            Intent intentRegistration = new Intent(this, RegistrationIntentService.class);
            startService(intentRegistration);
        }

        checkIntentMessage();

    }

    public Menu getMenu() {
        return menu;
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(Fragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    public Fragment getTabsFragment() {
        return tabsFragment;
    }

    public void changeThemeIcons() {
        sharedPreferences = getSharedPreferences(SettingsActivity.PREFERENCE_NAME, MODE_PRIVATE);
        themeColour = sharedPreferences.getString(SettingsActivity.THEME_APP, getString(R.string.black));

        if (themeColour.equals(getString(R.string.red))) {
            mItensImageIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons_red);
        } else {
            mItensImageIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons_black);
        }

        if (!notSetHeader) {
            View header = getLayoutInflater().inflate(R.layout.menu_header, null);
            mDrawerList.addHeaderView(header);
        }

        mDrawerList.setAdapter(new MenuAdapter(getApplicationContext(), geraItensMenu()));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    private void setUpLogoutPlus() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    public void setMessageEditFragment(Message msg){
        messageEditFragment = msg;
    }

    public Message getMessageEditFragment(){
        return messageEditFragment;
    }

    private void checkIntentMessage() {
        try {
            Intent intent = getIntent();
            if (intent.getStringExtra(MyGcmListenerService.EXTRAS_NOTIFICATION).equals("1")) {
                messageIntent = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeFragmentMessage() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.timeline));
        if (fragmentManager.findFragmentByTag(TIMEL_TAG) == null) {
            fragmentTransaction.hide(currentFragment);
            fragmentTransaction.add(R.id.fragment_container, timelineFragment, TIMEL_TAG);
            fragmentTransaction.show(timelineFragment).commit();
        } else if (!fragmentManager.findFragmentByTag(TIMEL_TAG).isVisible()) {
            fragmentTransaction.hide(currentFragment).show(timelineFragment).commit();
        }
        currentFragment = timelineFragment;

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(findViewById(R.id.layoutBoxMessage_et).getWindowToken(), 0);
    }

    public void reloadTimeline(){
        btLogin = true;
        goToFragment(TIMELINE);
    }

    public void openFragmentMessage() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        getSupportActionBar().setTitle(R.string.new_publication);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        if (fragmentManager.findFragmentByTag(TIMEL_MESSAGE_TAG) == null) {
            fragmentTransaction.hide(currentFragment);
            fragmentTransaction.add(R.id.fragment_container, timelineMessageFragment, TIMEL_MESSAGE_TAG);
            fragmentTransaction.show(timelineMessageFragment).commit();
        } else if (!fragmentManager.findFragmentByTag(TIMEL_MESSAGE_TAG).isVisible()) {
            fragmentTransaction.hide(currentFragment).show(timelineMessageFragment).commit();
        }

        currentFragment = timelineMessageFragment;

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

    }

    public void openFragmentMessageEdit() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        getSupportActionBar().setTitle(R.string.edit_publication);
        timelineEditMessageFragment = new TimelineEditMessageFragment();
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        if (fragmentManager.findFragmentByTag(TIMEL_EDIT_MESSAGE_TAG) == null) {
            fragmentTransaction.hide(currentFragment);
            fragmentTransaction.add(R.id.fragment_container, timelineEditMessageFragment, TIMEL_EDIT_MESSAGE_TAG);
            fragmentTransaction.show(timelineEditMessageFragment).commit();
            currentFragment = timelineEditMessageFragment;
        } else if (!fragmentManager.findFragmentByTag(TIMEL_EDIT_MESSAGE_TAG).isVisible()) {
            fragmentTransaction.hide(currentFragment);
            fragmentTransaction.replace(R.id.fragment_container, timelineEditMessageFragment, TIMEL_EDIT_MESSAGE_TAG);
            fragmentTransaction.show(timelineEditMessageFragment).commit();
            currentFragment = timelineEditMessageFragment;
        }

        currentFragment = timelineEditMessageFragment;

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

    }

    public void closeFragmentMessageEdit() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.timeline));
        if (fragmentManager.findFragmentByTag(TIMEL_TAG) == null) {
            fragmentTransaction.hide(currentFragment);
            fragmentTransaction.add(R.id.fragment_container, timelineFragment, TIMEL_TAG);
            fragmentTransaction.show(timelineFragment).commit();
        } else if (!fragmentManager.findFragmentByTag(TIMEL_TAG).isVisible()) {
            fragmentTransaction.hide(currentFragment).show(timelineFragment).commit();
        }
        currentFragment = timelineFragment;

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(findViewById(R.id.layoutBoxMessage_et).getWindowToken(), 0);
    }

    public void showToastMessageBlank() {
        Toast.makeText(this, getString(R.string.message_blank), Toast.LENGTH_SHORT).show();
    }

    public void showToastMessageEmoticon() {
        Toast.makeText(this, getString(R.string.message_emoticon), Toast.LENGTH_SHORT).show();
    }

    public TimelineFragment getFragmentTimeline() {
        return timelineFragment;
    }
    public TimelineEditMessageFragment getFragmentEditMessageTimeline() {
        return timelineEditMessageFragment;
    }

    public void setLogin(boolean login) {
        this.btLogin = login;
        this.jaTentouLogar = false;
    }

    @Override
    public void onConnected(Bundle bundle) {
        isSignInButtonClicked = false;
        getDataProfile();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragmentManager.findFragmentByTag(TIMEL_TAG) != null) {
            fragmentTransaction.detach(timelineFragment);
            fragmentTransaction.attach(timelineFragment);
        }
        if (fragmentManager.findFragmentByTag(FEEDBACK_TAG) != null) {
            fragmentTransaction.detach(feedBackFragment);
            fragmentTransaction.attach(feedBackFragment);
        }

        fragmentTransaction.commit();
    }

    @Override
    public void onConnectionSuspended(int conection) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), MainActivity.this, 0).show();
            return;
        }

        if (!isConsentScreenOpened) {
            connectionResult = result;
        }

        if (btLogin && !jaTentouLogar) {
            resolveSignIn();
            jaTentouLogar = true;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SIGN_IN_CODE) {
            isConsentScreenOpened = false;

            if (resultCode != RESULT_OK) {
                isSignInButtonClicked = false;
            }

            if (!googleApiClient.isConnecting()) {
                googleApiClient.connect();
            }
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long itemId) {
            if (position > 0) {
                goToFragment(position);
            }
        }
    }


    private List<ItemMenu> geraItensMenu() {
        List<ItemMenu> result = new ArrayList<>();
        for (int i = 0; i < mItensTitles.length; i++) {
            result.add(new ItemMenu(mItensTitles[i], mItensImageIcons.getResourceId(i, -1)));
        }
        return result;
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(MainActivity.this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, MainActivity.this, NOVE_MIL);
            } else {
                Toast.makeText(MainActivity.this, getString(R.string.not_support), Toast.LENGTH_SHORT).show();
            }


        } else {
            return true;
        }
        return false;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void setUpFragments(){
        tabsFragment = new TabsFragment();
        mapsFragment = new MapsFragment();
        timelineFragment = new TimelineFragment();
        partnersFragment = new PartnersFragment();
        timelineMessageFragment = new TimelineMessageFragment();
        timelineEditMessageFragment = new TimelineEditMessageFragment();
        helpFragment = new HelpFragment();
        aboutFragment = new AboutFragment();
        feedBackFragment = new FeedBackFragment();

        currentFragment = tabsFragment;

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, tabsFragment, TABS_TAG);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dialogFragment = new MapDialogFragment();

        changeToolbar();
        changeThemeIcons();
        try {
            TabsFragment tab = (TabsFragment) tabsFragment;
            tab.setUpWidgets();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Boolean isActivityRunning(Class activityClass) {
        ActivityManager activityManager = (ActivityManager) getBaseContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (activityClass.getCanonicalName().equalsIgnoreCase(task.baseActivity.getClassName())) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (googleApiClient != null) {
            googleApiClient.connect();
        }

        if (messageIntent) {
            goToFragment(TIMELINE);
        } else if (verifyFragment(tabsFragment.getTag())) {
            goToFragment(HOME);
        } else if (verifyFragment(mapsFragment.getTag())) {
            goToFragment(MAPA);
        } else if (verifyFragment(timelineFragment.getTag())) {
            goToFragment(TIMELINE);
        } else if (verifyFragment(partnersFragment.getTag())) {
            goToFragment(PARTNERS);
        } else {
            goToFragment(HOME);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            imm.hideSoftInputFromWindow(findViewById(R.id.layoutBoxMessage_et).getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @NonNull
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        getSupportFragmentManager().beginTransaction().hide(currentFragment).commit();
        setUpFragments();
    }

    private boolean verifyFragment(String tag) {
        fragmentManager = getSupportFragmentManager();
        return fragmentManager.findFragmentByTag(tag) != null && fragmentManager.findFragmentByTag(tag).isVisible();
    }

    public void goToFragment(int viewId) {
        Intent intent;
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        hideIcons();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (viewId == 0) {
            viewId = lastFragment;
        }

        mDrawerList.setItemChecked(viewId, true);
        switch (viewId) {
            case HOME:
                getSupportActionBar().setTitle(getString(R.string.app_full_name));
                if (fragmentManager.findFragmentByTag(TABS_TAG) == null) {
                    fragmentTransaction.hide(currentFragment);
                    fragmentTransaction.add(R.id.fragment_container, tabsFragment, TABS_TAG);
                    fragmentTransaction.show(tabsFragment).commit();
                } else if (!fragmentManager.findFragmentByTag(TABS_TAG).isVisible()) {
                    fragmentTransaction.hide(currentFragment).show(tabsFragment).commit();
                    //programacaoFragment.loadList();
                }

                currentFragment = tabsFragment;
                lastFragment = HOME;
                break;
            case MAPA:
                menu.findItem(R.id.action_location).setVisible(true);
                getSupportActionBar().setTitle(getString(R.string.title_map));
                if (fragmentManager.findFragmentByTag(MAP_TAG) == null) {
                    fragmentTransaction.hide(currentFragment);
                    fragmentTransaction.add(R.id.fragment_container, mapsFragment, MAP_TAG);
                    fragmentTransaction.show(mapsFragment).commit();
                } else if (!fragmentManager.findFragmentByTag(MAP_TAG).isVisible()) {
                    fragmentTransaction.hide(currentFragment).show(mapsFragment).commit();
                }
                currentFragment = mapsFragment;
                dialogFragment.setMapsFragment((MapsFragment) currentFragment);
                lastFragment = MAPA;
                break;
            case CAMERA:

                getSupportActionBar().setTitle(getString(R.string.title_activity_camera));
                Intent mIntent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(mIntent);
                lastFragment = CAMERA;
                break;

            case TIMELINE:
                getSupportActionBar().setTitle(getString(R.string.timeline));
                if (fragmentManager.findFragmentByTag(TIMEL_TAG) == null) {
                    fragmentTransaction.hide(currentFragment);
                    fragmentTransaction.add(R.id.fragment_container, timelineFragment, TIMEL_TAG);
                    fragmentTransaction.show(timelineFragment).commit();
                } else if (fragmentManager.findFragmentByTag(TIMEL_TAG) != null && (btLogin || btLogout)) {
                    fragmentTransaction.hide(currentFragment);
                    timelineFragment = new TimelineFragment();
                    fragmentTransaction.replace(R.id.fragment_container, timelineFragment, TIMEL_TAG);
                    fragmentTransaction.show(timelineFragment).commit();
                    btLogin = false;
                    btLogout = false;
                } else if (!fragmentManager.findFragmentByTag(TIMEL_TAG).isVisible()) {
                    fragmentTransaction.hide(currentFragment).show(timelineFragment).commit();
                }
                currentFragment = timelineFragment;
                lastFragment = TIMELINE;
                break;

            case PARTNERS:
                getSupportActionBar().setTitle(getString(R.string.partners));
                if (fragmentManager.findFragmentByTag(PAR_TAG) == null) {
                    fragmentTransaction.hide(currentFragment);
                    fragmentTransaction.add(R.id.fragment_container, partnersFragment, PAR_TAG);
                    fragmentTransaction.show(partnersFragment).commit();
                } else if (!fragmentManager.findFragmentByTag(PAR_TAG).isVisible()) {
                    fragmentTransaction.hide(currentFragment).show(partnersFragment).commit();
                }
                currentFragment = partnersFragment;
                lastFragment = PARTNERS;
                break;
            case CONTACT:
                getSupportActionBar().setTitle(getString(R.string.contact_us));
                if (fragmentManager.findFragmentByTag(FEEDBACK_TAG) == null) {
                    fragmentTransaction.hide(currentFragment);
                    fragmentTransaction.add(R.id.fragment_container, feedBackFragment, FEEDBACK_TAG);
                    fragmentTransaction.show(feedBackFragment).commit();
                } else if (fragmentManager.findFragmentByTag(FEEDBACK_TAG) != null && (btLogin || btLogout)) {
                    fragmentTransaction.hide(currentFragment);
                    feedBackFragment = new FeedBackFragment();
                    fragmentTransaction.replace(R.id.fragment_container, feedBackFragment, FEEDBACK_TAG);
                    fragmentTransaction.show(feedBackFragment).commit();
                    btLogin = false;
                    btLogout = false;
                } else if (!fragmentManager.findFragmentByTag(FEEDBACK_TAG).isVisible()) {
                    fragmentTransaction.hide(currentFragment).show(feedBackFragment).commit();
                }
                currentFragment = feedBackFragment;
                lastFragment = CONTACT;

                break;
            case HELP:
                getSupportActionBar().setTitle(getString(R.string.help));
                if (fragmentManager.findFragmentByTag(HELP_TAG) == null) {
                    fragmentTransaction.hide(currentFragment);
                    fragmentTransaction.add(R.id.fragment_container, helpFragment, HELP_TAG);
                    fragmentTransaction.show(helpFragment).commit();
                } else if (!fragmentManager.findFragmentByTag(HELP_TAG).isVisible()) {
                    fragmentTransaction.hide(currentFragment).show(helpFragment).commit();
                }
                currentFragment = helpFragment;
                lastFragment = HELP;
                break;
            case ABOUT:
                getSupportActionBar().setTitle(getString(R.string.about));
                if (fragmentManager.findFragmentByTag(ABOUT_TAG) == null) {
                    fragmentTransaction.hide(currentFragment);
                    fragmentTransaction.add(R.id.fragment_container, aboutFragment, ABOUT_TAG);
                    fragmentTransaction.show(aboutFragment).commit();
                } else if (!fragmentManager.findFragmentByTag(ABOUT_TAG).isVisible()) {
                    fragmentTransaction.hide(currentFragment).show(aboutFragment).commit();
                }
                currentFragment = aboutFragment;
                lastFragment = ABOUT;
                break;
            default:
                break;

        }
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    private void hideIcons() {
        if (menu != null) {
            MenuItem location = menu.findItem(R.id.action_location);
            if (currentFragment == mapsFragment) {
                location.setVisible(true);
            } else {
                location.setVisible(false);
            }
        }
    }

    public void callLoginGooglePlus() {
        btLogin = true;
        resolveSignIn();
        jaTentouLogar = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menuAux) {
        sharedPreferences = getSharedPreferences(SplashScreenActivity.PREFERENCE_NAME, MODE_PRIVATE);

        name = sharedPreferences.getString(SplashScreenActivity.USER_NOME, "");
        email = sharedPreferences.getString(SplashScreenActivity.USER_EMAIL, "");
        if (email.equals("") || name.equals("")) {
            getMenuInflater().inflate(R.menu.menu_main2, menuAux);
            this.menu = menuAux;
            return true;
        } else {
            getMenuInflater().inflate(R.menu.menu_main, menuAux);
            this.menu = menuAux;
            return true;
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (itemId) {
            case R.id.action_location:
                if (!dialogFragment.isVisible()) {
                    dialogFragment.setMapsFragment(mapsFragment);
                    dialogFragment.show(getFragmentManager(), "Pinpoint Menu");
                } else {
                    dialogFragment.dismiss();
                }
                return true;
            case android.R.id.home:
                goToFragment(HOME);
                return true;
            case R.id.action_share:
                startActivity(Intent.createChooser(new Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app_text)
                        + " https://play.google.com/store/apps/details?id=" + getString(R.string.app_id)), "Share via"));
                return true;
            case R.id.action_rate:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getString(R.string.app_id))));
                return true;

            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;

            case R.id.action_logout:
                if (googleApiClient.isConnected()) {
                    Plus.AccountApi.clearDefaultAccount(googleApiClient);
                    googleApiClient.disconnect();
                    googleApiClient.connect();
                }

                sharedPreferences = getSharedPreferences(SplashScreenActivity.PREFERENCE_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(SplashScreenActivity.USER_ID, "0");
                editor.putString(SplashScreenActivity.USER_EMAIL, "");
                editor.putString(SplashScreenActivity.USER_NOME, "");
                editor.apply();

                Toast.makeText(this, getString(R.string.logout_success), Toast.LENGTH_SHORT).show();
                btLogout = true;
                btLogin = false;
                goToFragment(0);

                invalidateOptionsMenu();

                return true;


            case R.id.action_login:

                btLogout = false;
                btLogin = true;
                resolveSignIn();
                jaTentouLogar = true;

//                sharedPreferences = getSharedPreferences(SplashScreenActivity.PREFERENCE_NAME, MODE_PRIVATE);
//                editor = sharedPreferences.edit();
//                editor.putString(SplashScreenActivity.USER_EMAIL, "");
//                editor.putString(SplashScreenActivity.USER_NOME, "");
//                editor.putBoolean(SplashScreenActivity.USER_STATUS, false);
//                editor.apply();
//                SharedPreferences preferencesEditor = getSharedPreferences(SettingsActivity.PREFERENCE_NAME, MODE_PRIVATE);
//                SharedPreferences.Editor prefEditor = preferencesEditor.edit();
//
//                prefEditor.putBoolean(SettingsActivity.SPLASH_LAUNCHER, true);
//                prefEditor.apply();
//                chamaSplashScreen();
//                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getDataProfile() {
        Person pessoa = Plus.PeopleApi.getCurrentPerson(googleApiClient);

        if (pessoa != null) {
            sharedPreferences = getSharedPreferences(SplashScreenActivity.PREFERENCE_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            String pessoaId = pessoa.getId();
            String name = pessoa.getDisplayName();
            String email = Plus.AccountApi.getAccountName(googleApiClient);
            String urlImage = pessoa.getImage().getUrl();

            editor.putString(SplashScreenActivity.USER_ID, pessoaId);
            editor.putString(SplashScreenActivity.USER_NOME, name);
            editor.putString(SplashScreenActivity.USER_EMAIL, email);
            editor.putString(SplashScreenActivity.USER_URL_PHOTO, urlImage);
            editor.putBoolean(SplashScreenActivity.USER_STATUS, true);

            editor.apply();
            Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
            goToFragment(0);
            invalidateOptionsMenu();
            btLogin = false;
        } else {
            Toast.makeText(this, getString(R.string.login_error), Toast.LENGTH_SHORT).show();
            btLogin = false;
        }
    }

    private void resolveSignIn() {
        if (connectionResult != null && connectionResult.hasResolution()) {
            try {
                isConsentScreenOpened = true;
                connectionResult.startResolutionForResult(MainActivity.this, SIGN_IN_CODE);
            } catch (IntentSender.SendIntentException e) {
                isConsentScreenOpened = false;
                googleApiClient.connect();
            }
        }
    }


    public Controller getController() {
        if (controller == null) {
            controller = Controller.getInstance(new MotoFestData());
        }
        return controller;
    }

    public void inicializeNotification() {
        sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        boolean notificado = sharedPreferences.getBoolean(NOTIFICATION_INIT, false);
        boolean errorParseException = false;

        if (!notificado) {
            List<String> listaNotification = getController().getAllNotification();
            // for(int i=0;i<listaNotification.size();i++){
            for (int i = listaNotification.size() - 1; i >= 0; i--) {
                // for(int i=0;i<1;i++){
                Date nowDate = new Date();
                nowDate.setTime(System.currentTimeMillis());

                Date notificationDate;
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                try {
                    notificationDate = formatter.parse(listaNotification.get(i) + " 23:59");
                    if (!notificationDate.before(nowDate)) {
                        createNotification(listaNotification.get(i), i);
                    }


                } catch (ParseException e) {
                    Log.d(TAG, "Error ao formatar a data: " + e.getMessage());
                    errorParseException = true;
                }
            }

            if (!errorParseException) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(NOTIFICATION_INIT, true);
                editor.apply();
            }
        }

    }

    private void createNotification(String pDate, int flag) {
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(ACTION_ALARM);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(mContext, 0, intent, flag);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = null;

        try {
            //  date = formatter.parse(pDate);
            date = formatter.parse(pDate + " 12:00");
            // date = formatter.parse("17/08/2015 14:41");
        } catch (ParseException e) {
            Log.d(TAG, "Error ao formatar a data: " + e.getMessage());
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        Log.i("MotoFest", "Criada notificação para: " + calendar.getTime().toString());
    }

    public void showVideoDialog(String url) {
        VideoDialogFragment videoDialogFragment = new VideoDialogFragment();
        videoDialogFragment.setUrl(url);

        getSupportActionBar().setTitle(getString(R.string.app_full_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_back);
        mDrawerToggle.setDrawerIndicatorEnabled(false);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(currentFragment);
        fragmentTransaction.add(R.id.fragment_container, videoDialogFragment, VIDEO_TAG);
        fragmentTransaction.show(videoDialogFragment).commit();
        currentFragment = videoDialogFragment;
    }

    public void showProgressDialog(String mensagem){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.wait));
        progressDialog.setMessage(mensagem);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void cancelProgressDialog(boolean ok){
        try{
            progressDialog.dismiss();
        } catch (Exception e){
            e.printStackTrace();
        }

        if(ok){
            showAlertDialog("Sucesso","Publicação alterada com sucesso.");
        } else {
            showAlertDialog("Erro","Verifique a sua conexão com a internet e tente novamente.");
        }
    }

    public void showAlertDialog(String title, String message){

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
//        alertDialog.setTitle(getString(R.string.wait));
//        alertDialog.setMessage(message);
//        alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
//            }
//        });
//        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (drawerOpened) {
            mDrawerLayout.closeDrawer(mDrawerList);
        } else if (currentFragment == mapsFragment) {
            MapsFragment mapsFragmentAux = (MapsFragment) getSupportFragmentManager().findFragmentByTag(MAP_TAG);
            if (mapsFragmentAux.isMarkerDescriptionVisible()) {
                mapsFragmentAux.hideMarkerDescription();
            } else {
                goToFragment(HOME);
                menu.findItem(R.id.action_location).setVisible(false);
            }
        } else if (currentFragment == timelineMessageFragment) {
            closeFragmentMessage();
        } else if (currentFragment == timelineEditMessageFragment) {
            closeFragmentMessageEdit();
        } else if (currentFragment != tabsFragment) {
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            goToFragment(HOME);
            //        getSupportActionBar().setTitle(getString(R.string.app_full_name));
            //        fragmentManager = getSupportFragmentManager();
            //        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //       if (fragmentManager.findFragmentByTag(TABS_TAG) == null) {
            //           fragmentTransaction.hide(currentFragment);
            //          fragmentTransaction.add(R.id.fragment_container, tabsFragment, TABS_TAG);
            //           fragmentTransaction.show(currentFragment).commit();
            //      } else if (!fragmentManager.findFragmentByTag(TABS_TAG).isVisible()) {
            //          fragmentTransaction.hide(currentFragment).show(tabsFragment).commit();
            //      }
            //     currentFragment = tabsFragment;
        } else {
            super.onBackPressed();
        }
    }

    public boolean isLogged() {
        return googleApiClient.isConnected();
    }
}