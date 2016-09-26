package br.com.diogojayme.databasemanager.database.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import br.com.diogojayme.databasemanager.database.DatabaseHelper;
import diogo.tablegenerator.processor.ColumnMetadata;
import diogo.tablegenerator.processor.TableGenerator;

/**
 * Created by diogojayme on 9/22/16.
 */

public class InsertHelper {
    private SQLiteDatabase database;
    private Object object;
    private String table;

    public InsertHelper(Context context){
        this.database = new DatabaseHelper(context).getReadableDatabase();
    }

    public InsertHelper into(Class clazz){
        table = TableGenerator.getTableName(clazz);
        return this;
    }

    public InsertHelper value(Object object){
        this.object = object;
        return this;
    }

    public boolean executeQuery(){
        database.beginTransaction();
        long id = database.insertWithOnConflict(table, null, objectToValues(object), SQLiteDatabase.CONFLICT_IGNORE);
        database.setTransactionSuccessful();
        database.endTransaction();
        return id > 0;
    }

    private ContentValues objectToValues(Object object){
        HashMap<String, ColumnMetadata> dataMap = TableGenerator.getObjectValues(object);
        ContentValues contentValues = new ContentValues();

        if(dataMap == null)
            return null;

        Iterator it = dataMap.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            ColumnMetadata columnData = (ColumnMetadata) pair.getValue();

            if(columnData.getValue().getClass() == String.class){
                contentValues.put(columnData.getColumnName(), (String) columnData.getValue());
            }

            if(columnData.getValue().getClass() == Long.class){
                contentValues.put(columnData.getColumnName(), (Long) columnData.getValue());
            }

            if(columnData.getValue().getClass() == Boolean.class){
                contentValues.put(columnData.getColumnName(), (Boolean) columnData.getValue());
            }

            if(columnData.getValue().getClass() == Integer.class){
                contentValues.put(columnData.getColumnName(), (Integer) columnData.getValue());
            }

            it.remove(); // avoids a ConcurrentModificationException
        }

        return contentValues;
    }

}
