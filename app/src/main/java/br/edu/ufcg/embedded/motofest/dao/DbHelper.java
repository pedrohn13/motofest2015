package br.edu.ufcg.embedded.motofest.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DbHelper extends SQLiteOpenHelper {

    public static final String ID = "_id";

    public static final String MESSAGE = "MENSAGENS";
    public static final String ID_USER = "id_user";
    public static final String NAME_USER = "name_user";
    public static final String URL_USER = "url_user";
    public static final String CONTENT = "content";
    public static final String DATE_SEND = "date_send";
    public static final String VISUALIZED = "visualized";

    public static final String[] COLUNAS_MESSAGE = new String []{
            ID, ID_USER, NAME_USER, URL_USER, CONTENT, DATE_SEND, VISUALIZED
    };

    public static final String USER = "USUARIOS";
    public static final String ID_PLUS = "id_plus";
    public static final String REGISTRATION_ID = "registration_id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String URL_PHOTO = "url_photo";

    public static final String[] COLUNAS_USER = new String []{
            ID, ID_PLUS, REGISTRATION_ID, NAME, EMAIL, URL_PHOTO
    };

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "APP_BD";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        createTables(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase dataBase, int oldVersion, int newVersion) {
       // createTables(dataBase);
    }

    private void createTables(SQLiteDatabase dataBase) {
        Log.i(DB_NAME, "Criando Tabelas do Banco de Dados");
        dataBase.execSQL("CREATE TABLE " + MESSAGE + "(_id integer primary key autoincrement, "
                + ID_USER + " text, " + NAME_USER + " text, " + URL_USER + " text," + CONTENT
                + " text," +  DATE_SEND + " text," + VISUALIZED + " text);");
        dataBase.execSQL("CREATE TABLE " + USER + "(_id integer primary key autoincrement, "
                + ID_PLUS + " text, " + REGISTRATION_ID + " text, " + NAME + " text, " + EMAIL
                + " text, " + URL_PHOTO + " text);");
    }
}
