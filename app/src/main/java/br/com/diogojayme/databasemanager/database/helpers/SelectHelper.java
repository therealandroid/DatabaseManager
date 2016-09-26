package br.com.diogojayme.databasemanager.database.helpers;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.HandlerThread;

import java.util.ArrayList;
import java.util.List;

import br.com.diogojayme.databasemanager.database.Callback;
import br.com.diogojayme.databasemanager.database.DatabaseHelper;
import querybuilder.builders.SelectBuilder;
import querybuilder.executors.mapper.QueryRelation;
import querybuilder.executors.mapper.ResultSetExtractor;

/**
 * Created by diogojayme on 9/14/16.
 *
 */
public class SelectHelper<T> {
    private SQLiteDatabase database;
    private SelectBuilder selectBuilder;
    private QueryRelation queryRelation;
    private Handler mainHandler;
    private Class<T> from;

    public SelectHelper(Context context){
        this.database = new DatabaseHelper(context).getReadableDatabase();
        this.mainHandler = new Handler(context.getMainLooper());
    }

    public SelectHelper query(SelectBuilder builder){
        this.selectBuilder = builder;
        return this;
    }

    @SuppressWarnings("unchecked")
    public SelectHelper from(Class clazz){
        this.from = clazz;
        return this;
    }

    public SelectHelper withRelation(QueryRelation queryRelation){
        this.queryRelation = queryRelation;
        return this;
    }

    @SuppressWarnings("unchecked")
    public void executeQueryAsync(final Callback callback) {
        HandlerThread handlerThread = new HandlerThread("Background Thread");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        handler.post(() -> {
            try {
                Cursor cursor = database.rawQuery(selectBuilder.build(), null);
                List<T> list = new ArrayList<>();

                while(cursor.moveToNext()) {
                    ResultSetExtractor resultSet = new ResultSetExtractor(cursor);
                    T myObject = resultSet.extractClasses(queryRelation, from);
                    list.add(myObject);
                }

                onSuccess(callback, list);
                closeCursor(cursor);
            }catch (Exception e){
                e.printStackTrace();
                onError(callback);
            }
        });
    }

    private void closeCursor(Cursor cursor){
        if(cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    @SuppressWarnings("unchecked")
    private void onSuccess(final Callback callback,final List<T> objects){
        if(database != null && database.isOpen()){
            database.close();
        }

        if(mainHandler  != null) {
            mainHandler.post(() -> callback.onQueryResults(objects));
        }
    }

    @SuppressWarnings("unchecked")
    private void onError(final Callback callback){
        if(database != null && database.isOpen()){
            database.close();
        }

        if(mainHandler  != null) {
            mainHandler.post(callback::onEmptyResults);
        }
    }
}
