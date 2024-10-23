package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@Slf4j
public class StatementStudentRepository implements StudentRepository {
    private Connection connection;
    private Statement statement;

    public StatementStudentRepository() throws SQLException {
        connection = DbUtils.getConnection();
        statement = connection.createStatement();
    }
    @Override
    public int save(Student student) {
        //todo#1 insert student
        String query = String.format(
                "insert into jdbc_students(id, name, gender, age) values (%s, %s, %s, %s);",
                student.getId(), student.getName(), student.getGender(), student.getAge());
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return Integer.parseInt(student.getId());
    }

    @Override
    public Optional<Student> findById(String id){
        //todo#2 student 조회
        String query = "select * from jdbc_students;";

        try {
            statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public int update(Student student){
        //todo#3 student 수정, name <- 수정합니다.
        String id = student.getId();
        String updatedName = student.getName();
        String query = String.format("update table jdbc_students set name='%s' where id='%s';", updatedName, id);

        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Integer.parseInt(student.getId());
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

        return Integer.parseInt(id);
    }

}
