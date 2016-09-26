package br.com.diogojayme.databasemanager.table.services;

import android.content.Context;

import java.util.List;

import br.com.diogojayme.databasemanager.database.Callback;
import br.com.diogojayme.databasemanager.table.User;

/**
 * Created by diogojayme on 9/23/16.
 */

public interface UserService {

    void getFullUsers(Context context, Callback<List<User>> callback);

    void getUserById(Context context, Callback<List<User>> callback, Long id);

}
