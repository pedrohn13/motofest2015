package br.edu.ufcg.embedded.motofest.activity.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import br.edu.ufcg.embedded.motofest.R;


public class VideoDialogFragment extends Fragment {

    private static final int CEM = 100;
    private String url;
    private WebView webview;
    private ProgressBar progress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dialog_video, container, false);

        Window window = getActivity().getWindow();
//        window.requestFeature(Window.FEATURE_PROGRESS);
        window.setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
        window.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);


        webview = (WebView) rootView.findViewById(R.id.webView);
        if (url != null && webview.getUrl() != url) {
            loadPage();

        }

        progress = (ProgressBar) rootView.findViewById(R.id.progressBar);
        progress.setProgress(0);
        progress.setVisibility(View.VISIBLE);

        return rootView;
    }

    private void loadPage() {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        webview.getSettings().setSupportMultipleWindows(false);
        webview.getSettings().setSupportZoom(false);
        webview.setVerticalScrollBarEnabled(false);
        webview.setHorizontalScrollBarEnabled(false);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setAppCacheEnabled(true);
        webview.getSettings().setUseWideViewPort(false);
        webview.setWebChromeClient(new WebChromeClient() {


            public void onProgressChanged(WebView view, int prog) {
                //Make the bar disappear after URL is loaded, and changes string to Loading...
                //MyDialog.setTitle("Loading...");
                progress.setProgress(prog * CEM); //Make the bar disappear after URL is loaded

                // Return the app name after finish loading
                if (prog == CEM) {
                    progress.setVisibility(View.GONE);
            }
            }
        });
        webview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                displayPromptForEnablingInternet();
                onDestroyView();
            }
        });

        // these settings speed up page load into the webview
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.loadUrl(url);
    }

    public void setValue(int progress) {
        this.progress.setProgress(progress);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void displayPromptForEnablingInternet() {
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        final String actionWifiSettings = Settings.ACTION_WIFI_SETTINGS;
        final String actionWirelessSettings = Settings.ACTION_WIRELESS_SETTINGS;
        final String message = getString(R.string.enable_network);

        builder.setMessage(message)
                .setPositiveButton(getString(R.string.bt_wifi),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int idButton) {
                                getActivity().startActivity(new Intent(actionWifiSettings));
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton(getString(R.string.bt_mobile_network),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int idButton) {
                                getActivity().startActivity(new Intent(actionWirelessSettings));
                                dialog.dismiss();
                            }
                        })
                .setNeutralButton(getString(R.string.bt_cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int idButton) {
                                dialog.cancel();
                            }
                        });
        builder.create().show();
    }

    public void setUrl(String url) {
        this.url = url;
        if (webview != null  && webview.getUrl().equals(url)) {
            loadPage();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        webview.onPause();
    }
}
