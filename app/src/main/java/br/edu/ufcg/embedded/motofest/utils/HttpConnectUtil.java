package br.edu.ufcg.embedded.motofest.utils;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;

import br.edu.ufcg.embedded.motofest.model.Email;
import br.edu.ufcg.embedded.motofest.model.Message;
import br.edu.ufcg.embedded.motofest.model.User;


public final class HttpConnectUtil {

    private HttpConnectUtil() {

    }
    private static final String CODIFICACAO = "UTF-8";
    public static String sendUserToServer(User user) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://216.59.16.163/motofest-app/index.php/aplicativo/saveuser");
        String answer = "";

        try {
            ArrayList<NameValuePair> valores = new ArrayList<>();
            valores.add(new BasicNameValuePair("id_plus", user.getIdPlus()));
            valores.add(new BasicNameValuePair("registration-id", user.getRegistrationId()));
            valores.add(new BasicNameValuePair("name", user.getName()));
            valores.add(new BasicNameValuePair("email", user.getEmail()));
            valores.add(new BasicNameValuePair("url_photo", user.getUrlPhoto()));

            httpPost.setEntity(new UrlEncodedFormEntity(valores, CODIFICACAO));
            HttpResponse resposta = httpClient.execute(httpPost);
            answer = EntityUtils.toString(resposta.getEntity());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return answer;
    }

    public static String sendMessageToServer(Message message) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://216.59.16.163/motofest-app/index.php/aplicativo/sendmessage");
        String answer = "";

        try {
            ArrayList<NameValuePair> valores = new ArrayList<>();
            valores.add(new BasicNameValuePair("id_user", String.valueOf(message.getIdUser())));
            valores.add(new BasicNameValuePair("name_user", message.getNameUser()));
            valores.add(new BasicNameValuePair("url_photo_user", message.getUrlUser()));
            valores.add(new BasicNameValuePair("content", message.getContent()));

            httpPost.setEntity(new UrlEncodedFormEntity(valores, CODIFICACAO));
            HttpResponse resposta = httpClient.execute(httpPost);
            answer = EntityUtils.toString(resposta.getEntity());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return answer;
    }

    public static String sendMessageEditToServer(Message message, String content) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://216.59.16.163/motofest-app/index.php/aplicativo/sendmessageedit");
        String answer = "";

        try {
            ArrayList<NameValuePair> valores = new ArrayList<>();
            valores.add(new BasicNameValuePair("id_user", String.valueOf(message.getIdUser())));
            valores.add(new BasicNameValuePair("name_user", message.getNameUser()));
            valores.add(new BasicNameValuePair("url_photo_user", message.getUrlUser()));
            valores.add(new BasicNameValuePair("content", message.getContent()));
            valores.add(new BasicNameValuePair("new_content", content));
            valores.add(new BasicNameValuePair("date_send", message.getDateSend()));

            httpPost.setEntity(new UrlEncodedFormEntity(valores, CODIFICACAO));
            HttpResponse resposta = httpClient.execute(httpPost);
            answer = EntityUtils.toString(resposta.getEntity());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return answer;
    }

    public static String deleteMessageServer(Message message) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://216.59.16.163/motofest-app/index.php/aplicativo/deletemessage");
        String answer = "";

        try {
            ArrayList<NameValuePair> valores = new ArrayList<>();
            valores.add(new BasicNameValuePair("id_user", String.valueOf(message.getIdUser())));
            valores.add(new BasicNameValuePair("name_user", message.getNameUser()));
            valores.add(new BasicNameValuePair("url_photo_user", message.getUrlUser()));
            valores.add(new BasicNameValuePair("content", message.getContent()));
            valores.add(new BasicNameValuePair("date_send", message.getDateSend()));

            httpPost.setEntity(new UrlEncodedFormEntity(valores, CODIFICACAO));
            HttpResponse resposta = httpClient.execute(httpPost);
            answer = EntityUtils.toString(resposta.getEntity());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return answer;
    }

    public static String sendMessageEdittoAllUsers(String idMessage) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://216.59.16.163/motofest-app/index.php/aplicativo/sendmessageeditallusers");
        String answer = "";

        try {

            ArrayList<NameValuePair> valores = new ArrayList<>();
            valores.add(new BasicNameValuePair("id_message", idMessage));

            httpPost.setEntity(new UrlEncodedFormEntity(valores, CODIFICACAO));
            HttpResponse resposta = httpClient.execute(httpPost);
            answer = EntityUtils.toString(resposta.getEntity());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return answer;
    }

    public static String deleteMessageAllUsers(String idMessage) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://216.59.16.163/motofest-app/index.php/aplicativo/sendmessageeditallusers");
        String answer = "";

        try {

            ArrayList<NameValuePair> valores = new ArrayList<>();
            valores.add(new BasicNameValuePair("id_message", idMessage));

            httpPost.setEntity(new UrlEncodedFormEntity(valores, CODIFICACAO));
            HttpResponse resposta = httpClient.execute(httpPost);
            answer = EntityUtils.toString(resposta.getEntity());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return answer;
    }

    public static String sendMessagetoAllUsers(String idMessage) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://216.59.16.163/motofest-app/index.php/aplicativo/sendmessageallusers");
        String answer = "";

        try {

            ArrayList<NameValuePair> valores = new ArrayList<>();
            valores.add(new BasicNameValuePair("id_message", idMessage));

            httpPost.setEntity(new UrlEncodedFormEntity(valores, CODIFICACAO));
            HttpResponse resposta = httpClient.execute(httpPost);
            answer = EntityUtils.toString(resposta.getEntity());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return answer;
    }

    public static String sendAvaliation(Email email) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://216.59.16.163/motofest-app/index.php/aplicativo/sendemailcontact");
        String answer = "";

        try {
            ArrayList<NameValuePair> valores = new ArrayList<>();
            valores.add(new BasicNameValuePair("name_user", email.getName()));
            valores.add(new BasicNameValuePair("email_user", email.getEmail()));
            valores.add(new BasicNameValuePair("message_avaliation", email.getAvaliation()));

            httpPost.setEntity(new UrlEncodedFormEntity(valores, CODIFICACAO));
            HttpResponse resposta = httpClient.execute(httpPost);
            answer = EntityUtils.toString(resposta.getEntity());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return answer;
    }
}