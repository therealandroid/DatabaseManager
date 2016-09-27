package br.com.diogojayme.sqlitemodelmapper.database.helpers;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.diogojayme.sqlitemodelmapper.database.DatabaseHelper;
import querybuilder.builders.SelectBuilder;
import querybuilder.executors.mapper.QueryRelation;
import querybuilder.executors.mapper.ResultSetExtractor;
import rx.Observable;

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

    public SelectHelper<T> query(SelectBuilder builder){
        this.selectBuilder = builder;
        return this;
    }

    @SuppressWarnings("unchecked")
    public SelectHelper<T> from(Class clazz){
        this.from = clazz;
        return this;
    }

    public SelectHelper<T> withRelation(QueryRelation queryRelation){
        this.queryRelation = queryRelation;
        return this;
    }

    public Observable<List<T>> executeQueryAsync() throws SQLException {
        Observable<List<T>> observable;
        Cursor cursor = database.rawQuery(selectBuilder.build(), null);
        List<T> list = new ArrayList<>();
        while(cursor.moveToNext()) {
            ResultSetExtractor resultSet = new ResultSetExtractor(cursor);
            T myObject = resultSet.extractClasses(queryRelation, from);
            list.add(myObject);
        }

        observable = Observable.just(list);
        closeDatabase(database);
        closeCursor(cursor);
        return observable;
    }

    private void closeDatabase(SQLiteDatabase database){
        if(database != null && !database.isOpen()){
            database.close();
        }
    }

    private void closeCursor(Cursor cursor){
        if(cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

}
