package br.com.diogojayme.databasemanager.table.services;

import android.content.Context;

import java.util.List;

import br.com.diogojayme.databasemanager.database.Callback;
import br.com.diogojayme.databasemanager.database.helpers.SelectHelper;
import br.com.diogojayme.databasemanager.table.Course;
import br.com.diogojayme.databasemanager.table.University;
import br.com.diogojayme.databasemanager.table.User;
import querybuilder.builders.SelectBuilder;
import querybuilder.executors.mapper.ClassTable;
import querybuilder.executors.mapper.QueryRelation;
import querybuilder.query.Comparisons;
import querybuilder.query.Condition;
import querybuilder.query.Join;

/**
 * Created by diogojayme on 9/23/16.
 */

public class UserServiceImpl implements UserService {

    @Override
    public void getFullUsers(Context context, Callback<List<User>> callback) {
        QueryRelation universityAndCourse = new QueryRelation(new ClassTable("u", User.class))
                .include("university", new ClassTable("un", University.class))
                .include("course", new ClassTable("co", Course.class));


        SelectBuilder allUsers = new SelectBuilder()
                .select(
                        new String[]{
                                "u.id", "u.first_name", "u.last_name", "u.course_id", "u.university_id",
                                "un.id", "un.name", "un.initials",
                                "co.id", "co.name"
                        })
                .from("user u")
                .joins(
                        new Join(Join.Type.INNER_JOIN).table("university un").on("u.university_id = un.id"),
                        new Join(Join.Type.INNER_JOIN).table("course co").on("u.course_id = co.id")
                );

        new SelectHelper(context)
                .query(allUsers)
                .from(User.class)
                .withRelation(universityAndCourse)
                .executeQueryAsync(new Callback<List<User>>() {
                    @Override
                    public void onQueryResults(List<User> users) {
                        callback.onQueryResults(users);
                    }

                    @Override
                    public void onEmptyResults() {
                        callback.onEmptyResults();
                    }
                });
    }

    @Override
    public void getUserById(Context context, Callback<List<User>> callback, Long id) {
        QueryRelation universityAndCourse = new QueryRelation(new ClassTable("u", User.class))
                .include("university", new ClassTable("un", University.class))
                .include("course", new ClassTable("co", Course.class));


        SelectBuilder allUsers = new SelectBuilder()
                .select(
                new String[]{
                        "u.id", "u.first_name", "u.last_name", "u.course_id", "u.university_id",
                        "un.id", "un.name", "un.initials",
                        "co.id", "co.name"
                })
                .from("user u")
                .where(new Condition("id", Comparisons.EQUALS, id))
                .joins(
                        new Join(Join.Type.INNER_JOIN).table("university un").on("u.university_id = un.id"),
                        new Join(Join.Type.INNER_JOIN).table("course co").on("u.course_id = co.id")
                );

        new SelectHelper(context)
                .query(allUsers)
                .from(User.class)
                .withRelation(universityAndCourse)
                .executeQueryAsync(new Callback<List<User>>() {
                    @Override
                    public void onQueryResults(List<User> users) {
                        callback.onQueryResults(users);
                    }

                    @Override
                    public void onEmptyResults() {
                        callback.onEmptyResults();
                    }
                });
    }
}
