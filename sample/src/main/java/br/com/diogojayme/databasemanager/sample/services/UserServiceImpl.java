package br.com.diogojayme.databasemanager.sample.services;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import br.com.diogojayme.databasemanager.sample.Course;
import br.com.diogojayme.databasemanager.sample.University;
import br.com.diogojayme.databasemanager.sample.User;
import br.com.diogojayme.sqlitemodelmapper.database.helpers.SelectHelper;
import querybuilder.builders.SelectBuilder;
import querybuilder.executors.mapper.ClassTable;
import querybuilder.executors.mapper.QueryRelation;
import querybuilder.query.Comparisons;
import querybuilder.query.Condition;
import querybuilder.query.Join;
import rx.Observable;

/**
 * Created by diogojayme on 9/23/16.
 */

public class UserServiceImpl implements UserService<User> {

    @Override
    public Observable<List<User>> getFullUsers(Context context) {
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

        try {
            return new SelectHelper<User>(context)
                    .query(allUsers)
                    .from(User.class)
                    .withRelation(universityAndCourse)
                    .executeQueryAsync();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Observable<List<User>> getUserById(Context context, Long id) {
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
                .where(new Condition("u.id", Comparisons.EQUALS, id))
                .joins(
                        new Join(Join.Type.INNER_JOIN).table("university un").on("u.university_id = un.id"),
                        new Join(Join.Type.INNER_JOIN).table("course co").on("u.course_id = co.id")
                );

        try {
           return new SelectHelper<User>(context)
                    .query(allUsers)
                    .from(User.class)
                    .withRelation(universityAndCourse)
                    .executeQueryAsync();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


}
