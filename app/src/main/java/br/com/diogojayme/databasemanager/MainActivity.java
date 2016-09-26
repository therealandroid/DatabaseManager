package br.com.diogojayme.databasemanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import br.com.diogojayme.databasemanager.database.Callback;
import br.com.diogojayme.databasemanager.database.helpers.InsertHelper;
import br.com.diogojayme.databasemanager.table.Course;
import br.com.diogojayme.databasemanager.table.University;
import br.com.diogojayme.databasemanager.table.User;
import br.com.diogojayme.databasemanager.table.services.UserServiceImpl;


/**
 * Created by diogojayme on 9/13/16.
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testInsertUser();
    }

    private boolean testInsertUser(){
        User user = new User();
        user.setId(3);
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
    }

    private void testInsertCourse(){
        Course course = new Course();
        course.setId(2L);
        course.setName("Engenharia de software");
    }

    private void testGetUsers(){
        new UserServiceImpl().getFullUsers(this, new Callback<List<User>>() {
            @Override
            public void onQueryResults(List<User> users) {

            }

            @Override
            public void onEmptyResults() {

            }
        });

    }
}
