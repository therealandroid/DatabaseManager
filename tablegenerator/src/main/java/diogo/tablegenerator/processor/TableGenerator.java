package diogo.tablegenerator.processor;


import android.util.Log;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import diogo.tablegenerator.annotations.Column;
import diogo.tablegenerator.annotations.ForeignKey;
import diogo.tablegenerator.annotations.PrimaryKey;
import diogo.tablegenerator.annotations.Table;
import querybuilder.builders.TableBuilder;
import querybuilder.configurations.Configuration;
import querybuilder.configurations.Database;
import querybuilder.query.creation.CreateColumn;


/**
 * Created by diogojayme on 8/4/16.
 *
 * http://stackoverflow.com/questions/4453159/how-to-get-annotations-of-a-member-variable
 */
public class TableGenerator {

    public static String from(Class<?> clazz){

        String tableName = getTableName(clazz);
        TableBuilder builder = new TableBuilder();
        builder.withName(tableName);

        Map<?, ?> map = getClassColumns(clazz);

        if(map == null)
            throw new NullPointerException("Cannot generate Sql from a class that have no Columns annotations");

        Iterator it = map.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            ColumnMetadata columnMetadata = (ColumnMetadata) pair.getValue();

            if(columnMetadata.isPrimaryKey()){
                builder.columns(
                        new CreateColumn()
                                .withName(columnMetadata.getColumnName())
                                .ofType(TypeUtil.getTypeIdentifier(columnMetadata.getFieldType()))
                                .primaryKey(true)
                );
            }else if(columnMetadata.isForeignKey()){
                builder.foreignKeys(
                        new querybuilder.query.creation.ForeignKey().column(columnMetadata.getColumnName())
                                .references(columnMetadata.getReferencesTable(), columnMetadata.getForeignKeyId())
                );

                if(Configuration.getDatabase() == Database.SQLITE){
                    builder.columns(
                            new CreateColumn()
                                    .withName(columnMetadata.getColumnName())
                                    .ofType(TypeUtil.getTypeIdentifier(columnMetadata.getFieldType()))
                    );
                }
            }else{
                builder.columns(
                        new CreateColumn()
                                .withName(columnMetadata.getColumnName())
                                .ofType(TypeUtil.getTypeIdentifier(columnMetadata.getFieldType()))
                );
            }

            it.remove();
        }

        String SQL = builder.build();
        Log.d("Sql", "generating sql " + SQL);
        return SQL;
    }

    public static String getTableName(Class<?> clazz) {
        if(clazz.isAnnotationPresent(Table.class)){
            Table table = clazz.getAnnotation(Table.class);
            return table.name();
        }else{
            return clazz.getSimpleName();
        }
    }

    public static HashMap<String, ColumnMetadata> getClassColumns(Class<?> clazz) {
        try {
            Field[] fields = clazz.getDeclaredFields();
            HashMap<String, ColumnMetadata> columns = new HashMap<>();

             for (Field field: fields) {
                if(field != null){
                    Log.d("Sql", "parsing attribute = " + field.getName());

                    ColumnMetadata columnMetadata = new ColumnMetadata();
                    field.setAccessible(true);

                    if (field.isAnnotationPresent(PrimaryKey.class)) {
                        PrimaryKey column = field.getAnnotation(PrimaryKey.class);
                        Class<?> fieldType = field.getType();
                        String columnName = column.name();
                        columnMetadata.setColumnName(columnName);
                        columnMetadata.setFieldType(fieldType);
                        columnMetadata.setPrimaryKey(true);
                        columnMetadata.setSize(column.size());

                        columns.put(columnName, columnMetadata);
                    }else if(field.isAnnotationPresent(ForeignKey.class)){
                        ForeignKey annotation = field.getAnnotation(ForeignKey.class);
                        String columnName = annotation.name();
                        Class<?> references = annotation.references();
                        Class<?> fieldType = field.getType();
                        columnMetadata.setForeignKeyId(annotation.foreignKey());
                        columnMetadata.setReferencesTable(getTableName(references));
                        columnMetadata.setFieldType(fieldType);
                        columnMetadata.setForeignKey(true);
                        columnMetadata.setColumnName(columnName);

                        columns.put(columnName, columnMetadata);
                    }else if(field.isAnnotationPresent(Column.class)){
                        Column column = field.getAnnotation(Column.class);
                        Class<?> fieldType = field.getType();
                        String columnName = column.name();
                        columnMetadata.setColumnName(columnName);
                        columnMetadata.setFieldType(fieldType);
                        columnMetadata.setSize(column.size());

                        columns.put(columnName, columnMetadata);
                    }

                    field.setAccessible(false);
                }
            }

            return columns;
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static HashMap<String, ColumnMetadata> getObjectValues(Object object) {
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            HashMap<String, ColumnMetadata> columns = new HashMap<>();

            for (Field field: fields) {
                if(field != null){
                    Log.d("Sql", "parsing attribute = " + field.getName());

                    ColumnMetadata columnMetadata = new ColumnMetadata();
                    field.setAccessible(true);

                    if (field.isAnnotationPresent(PrimaryKey.class)) {
                        PrimaryKey column = field.getAnnotation(PrimaryKey.class);
                        Class<?> fieldType = field.getType();
                        String columnName = column.name();
                        columnMetadata.setColumnName(columnName);
                        columnMetadata.setFieldType(fieldType);
                        columnMetadata.setPrimaryKey(true);
                        columnMetadata.setSize(column.size());
                        columnMetadata.setValue(field.get(object));

                        columns.put(columnName, columnMetadata);
                    }else if(field.isAnnotationPresent(ForeignKey.class)){
                        ForeignKey annotation = field.getAnnotation(ForeignKey.class);
                        String columnName = annotation.name();
                        Class<?> references = annotation.references();
                        Class<?> fieldType = field.getType();
                        columnMetadata.setForeignKeyId(annotation.foreignKey());
                        columnMetadata.setReferencesTable(getTableName(references));
                        columnMetadata.setFieldType(fieldType);
                        columnMetadata.setForeignKey(true);
                        columnMetadata.setColumnName(columnName);
                        columnMetadata.setValue(field.get(object));

                        columns.put(columnName, columnMetadata);
                    }else if(field.isAnnotationPresent(Column.class)){
                        Column column = field.getAnnotation(Column.class);
                        Class<?> fieldType = field.getType();
                        String columnName = column.name();
                        columnMetadata.setColumnName(columnName);
                        columnMetadata.setFieldType(fieldType);
                        columnMetadata.setSize(column.size());
                        columnMetadata.setValue(field.get(object));

                        columns.put(columnName, columnMetadata);
                    }

                    field.setAccessible(false);
                }
            }

            return columns;
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}

