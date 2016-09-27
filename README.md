# SqliteModelMapper

##Initialize in your Application
```Java
        Configuration.setDatabase(Database.SQLITE);

        SchemeConfiguration.initialize(new String[]{
                TableGenerator.from(University.class),
                TableGenerator.from(Course.class),
                TableGenerator.from(User.class)
        }, new String[]{
                "DROP TABLE IF EXISTS " + TableGenerator.getTableName(University.class),
                "DROP TABLE IF EXISTS "+ TableGenerator.getTableName(Course.class),
                "DROP TABLE IF EXISTS "+ TableGenerator.getTableName(User.class),
        }, "sample.db", 1);
```
##Usage

```Java
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
            new SelectHelper<User>(context)
                    .query(allUsers)
                    .from(User.class)
                    .withRelation(universityAndCourse)
                    .executeQueryAsync()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError(throwable -> {
                        //error
                    })
                    .subscribe(userList -> {
                        //success
                    });
        } catch (SQLException e) {
            e.printStackTrace();
        }
```
