package br.com.diogojayme.databasemanager.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import br.com.diogojayme.databasemanager.sample.services.UserServiceImpl;
import br.com.diogojayme.sqlitemodelmapper.database.helpers.InsertHelper;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by diogojayme on 9/13/16.
 */
public class MainActivity extends AppCompatActivity {
    Subscription subscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testInsertCourse();
        testInsertUniversity();
        testInsertUser();
        testGetUserById();
    }

    private boolean testInsertUser(){
        User user = new User();
        user.setId(5);
        user.setFirstName("Diogo");
        user.setLastName("Jayme");
        user.setCourseId(2);
        user.setUniversityId(1);

        boolean inserted = new InsertHelper(this)
                .into(User.class)
                .value(user)
                .executeQuery();

        System.out.println(inserted ? "new object added to database" : "Cannot insert this object in the database");
        return inserted;
    }

    private void testInsertUniversity(){
        University university = new University();
        university.setId(1L);
        university.setName("Universidade Federal de Goiás");
        university.setInitials("UFG");

        boolean inserted = new InsertHelper(this)
                .into(University.class)
                .value(university)
                .executeQuery();

        System.out.println(inserted ? "new object added to database" : "Cannot insert this object in the database");

    }

    private void testInsertCourse(){
        Course course = new Course();
        course.setId(2L);
        course.setName("Engenharia de software");

        boolean inserted = new InsertHelper(this)
                .into(Course.class)
                .value(course)
                .executeQuery();

        System.out.println(inserted ? "new object added to database" : "Cannot insert this object in the database");
    }

    private void testGetUserById(){
        subscription = new UserServiceImpl()
                .getUserById(this, 5L)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                    //error
                })
                .subscribe(userList -> {
                    //success
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }
}
