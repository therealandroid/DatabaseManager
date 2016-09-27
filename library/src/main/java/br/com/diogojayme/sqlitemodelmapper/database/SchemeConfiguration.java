package br.com.diogojayme.sqlitemodelmapper.database;

/**
 * Created by diogojayme on 9/22/16.
 */
public class SchemeConfiguration {

   public static int VERSION ;
   public static String NAME;
   public static String[] DROP_SQL;
   public static String[] CREATE_SQL;

   private SchemeConfiguration(String[] createTableSql, String[] dropTableSql, String databaseName, int version){
      CREATE_SQL = createTableSql;
      DROP_SQL = dropTableSql;
      NAME = databaseName;
      VERSION = version;
   }

   public static void initialize(String[] createTableSql, String[] dropTableSql, String databaseName, int version){
      new SchemeConfiguration(createTableSql, dropTableSql, databaseName, version);
   }

}
