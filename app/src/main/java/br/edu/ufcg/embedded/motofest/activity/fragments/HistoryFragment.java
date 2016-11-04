package br.edu.ufcg.embedded.motofest.activity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import br.edu.ufcg.embedded.motofest.R;


public class HistoryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historia, container, false);
        String data = getString(R.string.paragrafo);
        String comeco = getString(R.string.comeco);
        String titulo = getString(R.string.agora);
        String data2 = getString(R.string.paragrafo4);
        String data3 = getString(R.string.frase);




        String message = "<font color='white', font size ='3'>"
                + "<H3>%s</H3>"
                + "<p align='justify'>    %s</p>"
                + "</font>"
                + "<text-align:justify>";

        String message2 = "<font color='white', font size ='3'>"
                + "<H3>%s</H3>"
                + "<p align='justify'>    %s</p>"
                + "<H3>''%s''</H3>"
                + "</font>"
                + "<text-align:justify>";

        WebView webView = (WebView) view.findViewById(R.id.webView1);
        WebView webView2 = (WebView) view.findViewById(R.id.webView2);
        webView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        webView.loadData(String.format(message, comeco, data), "text/html;charset=UTF-8", null);
        webView2.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        webView2.loadData(String.format(message2, titulo, data2, data3), "text/html;charset=UTF-8", null);


        return view;

    }






}