package br.edu.ufcg.embedded.motofest.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.edu.ufcg.embedded.motofest.R;
import br.edu.ufcg.embedded.motofest.activity.MainActivity;
import br.edu.ufcg.embedded.motofest.activity.SplashScreenActivity;
import br.edu.ufcg.embedded.motofest.model.Message;



public class TimelineAdapter extends BaseAdapter {
    private Context context;
    private List<Message> lista;
    private ImageLoader imageLoader;
    private TextView txtDate;

    private static final int MILISECONDS = 1000;
    private static final int ONE_MINUTE = 60;
    private static final int ONE_DAY = 24;
    private static final int TWO_DAY = 48;

    private Message message;

    private SharedPreferences sharedPreferences;

    public TimelineAdapter(Context ctx, List<Message> mensagens, ImageLoader imageLoader, MainActivity activity) {
        this.context = ctx;
        this.lista = mensagens;
        this.imageLoader = imageLoader;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        message = lista.get(position);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.message_single, null);
        }

        sharedPreferences = context.getSharedPreferences(SplashScreenActivity.PREFERENCE_NAME, context.MODE_PRIVATE);
        String idPlus = sharedPreferences.getString(SplashScreenActivity.USER_ID, "0");

        TextView txtNameUser = (TextView) view.findViewById(R.id.name_user);
        TextView txtContent = (TextView) view.findViewById(R.id.content);
        txtDate = (TextView) view.findViewById(R.id.date);
        ImageView imgUser = (ImageView) view.findViewById(R.id.img_user);
        ImageView icDone = (ImageView) view.findViewById(R.id.ic_done);

        if (message.getDateSend().equals(context.getString(R.string.sending_message)) && message.getIdUser().equals(idPlus)) {
            icDone.setVisibility(ImageView.GONE);
            txtDate.setText(context.getString(R.string.sending_message));
        } else if (message.getDateSend().equals(context.getString(R.string.message_error)) && message.getIdUser().equals(idPlus)) {
            icDone.setVisibility(ImageView.GONE);
            txtDate.setText(context.getString(R.string.message_error));
        } else if (message.getIdUser().equals(idPlus)) {
            icDone.setVisibility(ImageView.VISIBLE);
            txtDate.setText(getDiffDates(message.getDateSend()));
        } else {
            icDone.setVisibility(ImageView.GONE);
            txtDate.setText(getDiffDates(message.getDateSend()));
        }

        txtNameUser.setText(message.getNameUser());
        txtContent.setText(geraLinkTexto(message.getContent()));
        txtContent.setMovementMethod(LinkMovementMethod.getInstance());

        loadImageUser(message.getUrlUser(), imgUser);

        view.setId(message.getId());

        return view;
    }

    public String geraLinkTexto(String texto){
        String novo_texto = "";
        String link = "";
        boolean ehlink = false;
        for(int i = 0; i < texto.length(); i++){
            if (ehlink) {
                link += texto.charAt(i);
                if (i+1 < texto.length()){
                    if (texto.charAt(i+1) == ' ' || texto.charAt(i+1) == ',') {
                        ehlink = false;
                        if (link.charAt(0) == 'h' && link.charAt(1) == 't') {
                            novo_texto  += "<a href=\"" + link + "\" target=\"_blank\">" + link + "</a>";
                            link = "";
                        } else {
                            novo_texto  += "<a href=\"http://" + link + "\" target=\"_blank\">" + link + "</a>";
                            link = "";
                        }

                    }
                } else {
                    ehlink = false;
                    if (link.charAt(0) == 'h' && link.charAt(1) == 't') {
                        novo_texto  += "<a href=\"" + link + "\" target=\"_blank\">" + link + "</a>";
                        link = "";
                    } else {
                        novo_texto  += "<a href=\"http://" + link + "\" target=\"_blank\">" + link + "</a>";
                        link = "";
                    }
                }

            } else if ((texto.charAt(i) == 'h' && texto.charAt(i+1) == 't'
                    && texto.charAt(i+2) == 't' && texto.charAt(i+3) == 'p'
                    && texto.charAt(i+4) == ':' && texto.charAt(i+5) == '/'
                    && texto.charAt(i+6) == '/' && texto.charAt(i+7) != ' ') ||
                    (texto.charAt(i) == 'h' && texto.charAt(i+1) == 't'
                            && texto.charAt(i+2) == 't' && texto.charAt(i+3) == 'p' && texto.charAt(i+4) == 's'
                            && texto.charAt(i+5) == ':' && texto.charAt(i+6) == '/'
                            && texto.charAt(i+6) == '/' && texto.charAt(i+8) != ' ') || (texto.charAt(i) == 'w' && texto.charAt(i+1) == 'w'
                    && texto.charAt(i+2) == 'w' && texto.charAt(i+3) == '.')) {
                ehlink = true;
                link +=  texto.charAt(i);
            } else {
                novo_texto += texto.charAt(i);
            }
        }

        return novo_texto;
    }

    private String getDiffDates(String dataBanco) {
        SimpleDateFormat formatoBanco = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatoAplicacao = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoHoras = new SimpleDateFormat("HH:mm");
        Date dateB = null;
        String dataAplicacao;
        try {
            dateB = formatoBanco.parse(dataBanco); //catch exception
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long dateBd = dateB.getTime();

        Calendar dataInstante = Calendar.getInstance();
        long dateNow = dataInstante.getTimeInMillis();

        long diffDates = dateNow - dateBd;
        diffDates = diffDates / MILISECONDS;

        if (diffDates <= ONE_MINUTE) {
            dataAplicacao = context.getString(R.string.seconds_ago);
        } else if (diffDates > ONE_MINUTE && diffDates < ONE_MINUTE * ONE_MINUTE) {
            dataAplicacao = context.getString(R.string.a) + " " + String.valueOf((int) diffDates / ONE_MINUTE) + " " + context.getString(R.string.minute);
            if ((int) diffDates / ONE_MINUTE != 1) {
                dataAplicacao += "s";
            }
        } else if (diffDates >= ONE_MINUTE * ONE_MINUTE && diffDates < ONE_MINUTE * ONE_MINUTE * ONE_DAY) {
            dataAplicacao = context.getString(R.string.a) + " "  + String.valueOf((int) diffDates / (ONE_MINUTE * ONE_MINUTE)) + " " + context.getString(R.string.hour);
            if ((int) diffDates / (ONE_MINUTE * ONE_MINUTE) != 1) {
                dataAplicacao += "s";
            }
        } else if (diffDates >= ONE_MINUTE * ONE_MINUTE * ONE_DAY && diffDates < ONE_MINUTE * ONE_MINUTE * TWO_DAY) {
            dataAplicacao = context.getString(R.string.yesterday_at) + " " + formatoHoras.format(dateB);
        } else {
            dataAplicacao = formatoAplicacao.format(dateB) + " " + context.getString(R.string.at) + " " + formatoHoras.format(dateB);
        }
        return dataAplicacao;
    }

    public void loadImageUser(String imageUri, ImageView imageView) {
        imageLoader.displayImage(imageUri, imageView, null, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
            }
            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            }
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            }
            @Override
            public void onLoadingCancelled(String imageUri, View view) {
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
            }
        });
    }
}
