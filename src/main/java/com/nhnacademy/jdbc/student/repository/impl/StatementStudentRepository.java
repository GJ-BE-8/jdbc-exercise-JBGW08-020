package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@Slf4j
public class StatementStudentRepository implements StudentRepository {
    private Connection connection;
    private Statement statement;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public StatementStudentRepository() throws SQLException {
        connection = DbUtils.getConnection();
        statement = connection.createStatement();
    }
    @Override
    public int save(Student student) {
        //todo#1 insert student
        String query = String.format(
                "insert into jdbc_students(id, name, gender, age) values ('%s', '%s', '%s', %d);",
                student.getId(), student.getName(), student.getGender(), student.getAge());

        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return 1;
    }

    @Override
    public Optional<Student> findById(String id){
        //todo#2 student 조회
        String query = String.format("select * from jdbc_students where id = '%s';", id);

        Student student = null;
        try {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                id = resultSet.getString("id");
                String name = resultSet.getString("name");
                Student.GENDER gender = Student.GENDER.valueOf(resultSet.getString("gender"));
                int age = resultSet.getInt("age");
                LocalDateTime createAt = LocalDateTime.parse(resultSet.getString("created_at"), formatter);

                student = new Student(id, name, gender, age, createAt);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(student);
    }

    @Override
    public int update(Student student){
        //todo#3 student 수정, name <- 수정합니다.

        String query = String.format("update jdbc_students set name='%s', gender='%s' , age=%d where id='%s';",
                student.getName(), student.getGender(), student.getAge(), student.getId());

        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 1;
    }

    @Override
    public int deleteById(String id){
        //todo#4 student 삭제
        String query = String.format("delete from jdbc_students where id='%s';", id);

        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 1;
    }

}