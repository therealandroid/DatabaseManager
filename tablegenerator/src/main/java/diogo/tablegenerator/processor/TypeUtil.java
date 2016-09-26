package diogo.tablegenerator.processor;



/**
 * Created by diogojayme on 8/5/16.
 */
public class TypeUtil {

    public static ColumnType getTypeIdentifier(Class clazz){
        if(clazz == String.class){
            return ColumnType.VARCHAR;
        }else if(clazz == Integer.class){
            return ColumnType.INTEGER;
        }else if(clazz == Long.class){
            return ColumnType.INTEGER;
        }else if(clazz == Boolean.class){
            return ColumnType.INTEGER;
        }else{
            return ColumnType.VARCHAR;
        }
    }

}
