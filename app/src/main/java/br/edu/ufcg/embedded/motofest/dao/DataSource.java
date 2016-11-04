package br.edu.ufcg.embedded.motofest.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ufcg.embedded.motofest.model.Message;
import br.edu.ufcg.embedded.motofest.model.User;


public class DataSource {

    private SQLiteDatabase database;
    private final DbHelper dbHelper;

    public DataSource(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public long saveUser(User user) {
        ContentValues valores = new ContentValues();
        valores.put(dbHelper.ID_PLUS, user.getIdPlus());
        valores.put(dbHelper.REGISTRATION_ID, user.getRegistrationId());
        valores.put(dbHelper.NAME, user.getName());
        valores.put(dbHelper.EMAIL, user.getEmail());
        valores.put(dbHelper.URL_PHOTO, user.getUrlPhoto());

        return getDatabase().insert(dbHelper.MESSAGE, null, valores);
    }

    private User createUser(Cursor cursor) {
        User model = new User(cursor.getInt(cursor.getColumnIndex(dbHelper.ID)),
                cursor.getString(cursor.getColumnIndex(dbHelper.ID_PLUS)),
                cursor.getString(cursor.getColumnIndex(dbHelper.REGISTRATION_ID)),
                cursor.getString(cursor.getColumnIndex(dbHelper.NAME)),
                cursor.getString(cursor.getColumnIndex(dbHelper.EMAIL)),
                cursor.getString(cursor.getColumnIndex(dbHelper.URL_PHOTO))
        );
        return model;
    }

    public long saveMessage(Message message) {
        ContentValues valores = new ContentValues();
        valores.put(dbHelper.ID_USER, message.getIdUser());
        valores.put(dbHelper.NAME_USER, message.getNameUser());
        valores.put(dbHelper.URL_USER, message.getUrlUser());
        valores.put(dbHelper.CONTENT, message.getContent());
        valores.put(dbHelper.DATE_SEND, message.getDateSend());
        valores.put(dbHelper.VISUALIZED, message.getVisualized());

        return getDatabase().insert(dbHelper.MESSAGE, null, valores);
    }

    public long updateMessage(Message message) {
        ContentValues valores = new ContentValues();
        valores.put(dbHelper.ID_USER, message.getIdUser());
        valores.put(dbHelper.NAME_USER, message.getNameUser());
        valores.put(dbHelper.URL_USER, message.getUrlUser());
        valores.put(dbHelper.CONTENT, message.getContent());
        valores.put(dbHelper.DATE_SEND, message.getDateSend());
        valores.put(dbHelper.VISUALIZED, message.getVisualized());

        return getDatabase().update(dbHelper.MESSAGE, valores, dbHelper.ID + " = '" + String.valueOf(message.getId()) + "'", null);
    }

    public int deleteMessage(Message message) {
        return getDatabase().delete(dbHelper.MESSAGE, dbHelper.ID  + " = '" + message.getId() + "'", null);
    }

    private Message createMessage(Cursor cursor) {
        Message model = new Message(cursor.getInt(cursor.getColumnIndex(dbHelper.ID)),
                cursor.getString(cursor.getColumnIndex(dbHelper.ID_USER)),
                cursor.getString(cursor.getColumnIndex(dbHelper.NAME_USER)),
                cursor.getString(cursor.getColumnIndex(dbHelper.URL_USER)),
                cursor.getString(cursor.getColumnIndex(dbHelper.CONTENT)),
                cursor.getString(cursor.getColumnIndex(dbHelper.DATE_SEND)),
                cursor.getInt(cursor.getColumnIndex(dbHelper.VISUALIZED))
        );
        return model;
    }

    public User getUser() {
        Cursor cursor = getDatabase().query(dbHelper.USER,
                dbHelper.COLUNAS_USER, null, null, null, null, null);

        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            User usuario = createUser(cursor);
            return usuario;
        }

        cursor.close();
        return null;

    }

    public Message getMessage(String id_user, String name_user, String url_photo_user, String date_send){
        Cursor cursor = getDatabase().query(dbHelper.MESSAGE,
                dbHelper.COLUNAS_MESSAGE, dbHelper.ID_USER + " = '" + id_user + "' AND "
                        + dbHelper.NAME_USER + " = '" + name_user + "' AND "
                        + dbHelper.URL_USER + " = '" + url_photo_user + "' AND "
                        + dbHelper.DATE_SEND + " = '" + date_send + "'" ,
                null, null, null, null);

        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            Message message = createMessage(cursor);
            return message;
        }

        cursor.close();
        return null;
    }

    public List<Message> getMessages() {
        Cursor cursor = getDatabase().query(dbHelper.MESSAGE,
                dbHelper.COLUNAS_MESSAGE, null, null, null, null, dbHelper.DATE_SEND + " DESC");

        List<Message> mensagens = new ArrayList<>();
        while (cursor.moveToNext()) {
            Message model = createMessage(cursor);
            mensagens.add(model);
        }
        cursor.close();
        return mensagens;
    }

    public List<Message> getMessagesNotVisualized() {
        Cursor cursor = getDatabase().query(dbHelper.MESSAGE,
                dbHelper.COLUNAS_MESSAGE, dbHelper.VISUALIZED + " = '0' ", null, null, null, dbHelper.VISUALIZED + " DESC");

        List<Message> mensagens = new ArrayList<>();
        while (cursor.moveToNext()) {
            Message model = createMessage(cursor);
            mensagens.add(model);
        }
        cursor.close();
        return mensagens;
    }

    public boolean setAllVisualized() {
        Cursor cursor = getDatabase().query(dbHelper.MESSAGE,
                dbHelper.COLUNAS_MESSAGE, null, null, null, null, dbHelper.DATE_SEND + " DESC");

        while (cursor.moveToNext()) {
            Message msg = createMessage(cursor);
            msg.setVisualized(1);
            updateMessage(msg);
        }
        cursor.close();
        return false;
    }

    private SQLiteDatabase getDatabase() {
        if (this.database == null) {
            this.database = this.dbHelper.getWritableDatabase();
        }

        return this.database;
    }
}