package br.com.diogojayme.databasemanager.database;

import br.com.diogojayme.databasemanager.table.Course;
import br.com.diogojayme.databasemanager.table.University;
import br.com.diogojayme.databasemanager.table.User;
import diogo.tablegenerator.processor.TableGenerator;

/**
 * Created by diogojayme on 9/22/16.
 */
 class SchemeConfiguration {

    //TODO Criar um código que gera essa configuração com base nos models

    static final int VERSION = 4;
    static final String NAME = "DatabaseManager.db";

    private static String DROP_TABLE= "DROP TABLE IF EXISTS ";

    static final String[] CREATE_SQL = new String[]{
            TableGenerator.from(University.class),
            TableGenerator.from(Course.class),
            TableGenerator.from(User.class)
    };

    static final String[] DROP_SQL = new String[]{
            DROP_TABLE + TableGenerator.getTableName(University.class),
            DROP_TABLE + TableGenerator.getTableName(Course.class),
            DROP_TABLE + TableGenerator.getTableName(User.class),
    };
}
