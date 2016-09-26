package br.com.diogojayme.databasemanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, SchemeConfiguration.NAME, null, SchemeConfiguration.VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        for (String sql: SchemeConfiguration.CREATE_SQL) {
            db.execSQL(sql);
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (String sql: SchemeConfiguration.DROP_SQL){
            db.execSQL(sql);
        }
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}