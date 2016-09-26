package querybuilder.executors.mapper;

import android.database.Cursor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import querybuilder.helpers.StringHelper;


/**
 * Created by augustoccesar on 8/9/16.
 */
public class ResultSetExtractor {
    private Cursor cursor;

    public ResultSetExtractor(Cursor cursor) {
        this.cursor = cursor;
    }

    public <T> T extractClass(String prefix, Class<T> clazz) throws SQLException {
        Constructor constructor = null;
        for (Constructor ctr : clazz.getDeclaredConstructors()) {
            constructor = ctr;
            if (constructor.getGenericParameterTypes().length == 0)
                break;
        }

        try {
            assert constructor != null;
            constructor.setAccessible(true);
            Object response =  constructor.newInstance();

            T responseObject = clazz.cast(response);

            for (Column column : EntityReader.getColumns(prefix, clazz)) {
                Field field = response.getClass().getDeclaredField(column.getName());
                String queryColumn = column.getPrefix() + "_" + StringHelper.camelToSnakeCase(column.getName()); // TODO generic type of alias

                if (checkIfColumnExists(queryColumn, cursor)) {
                    boolean accessible = field.isAccessible();
                    field.setAccessible(true);
                    field.set(response, getResultSetByType(column, cursor));
                    field.setAccessible(accessible);
                }
            }

            return responseObject;

        } catch (InstantiationException i){
            i.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T> T extractClasses(QueryRelation queryRelation, Class<T> clazz) throws SQLException {
        Object base = extractClass(queryRelation.getParentClass().getPrefix(), queryRelation.getParentClass().getClazz());
        T baseClazz = clazz.cast(base);

        for (String key : queryRelation.getIncludes().keySet()) {
            ClassTable classTable = queryRelation.getIncludes().get(key);
            Object includeObj =  extractClass(classTable.getPrefix(), classTable.getClazz());

            try {
                Field field = base.getClass().getDeclaredField(key);
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                field.set(base, includeObj);
                field.setAccessible(accessible);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return baseClazz;
    }

    private Object getResultSetByType(Column column, Cursor rs) throws SQLException {
        Class type = column.getType();
        String columnString = column.getPrefix() + "_" + StringHelper.camelToSnakeCase(column.getName()); // TODO Transform to generic alias

        if (type == Long.class) {
            return rs.getLong(rs.getColumnIndex(columnString));
        } else if (type == Integer.class) {
            return rs.getInt(rs.getColumnIndex(columnString));
        } else if (type == String.class) {
            return rs.getString(rs.getColumnIndex(columnString));
        } else {
            System.err.println("Invalid type.");
        }

        return null;
    }

    boolean checkIfColumnExists(String column, Cursor cursor) {
        return cursor.getColumnIndex(column) != -1;//TODO testar mais possibilidades de retornos
    }
}
