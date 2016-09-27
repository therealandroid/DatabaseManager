package br.com.diogojayme.databasemanager.sample.services;

import android.content.Context;

import java.util.List;

import rx.Observable;

/**
 * Created by diogojayme on 9/23/16.
 */

public interface UserService<T> {

    Observable<List<T>> getFullUsers(Context context);

    Observable<List<T>> getUserById(Context context, Long id);

}
