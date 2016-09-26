package br.com.diogojayme.databasemanager.database;

public interface Callback<T>{
    void onQueryResults(T t);
    void onEmptyResults();
}