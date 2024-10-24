package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
public class StudentRepositoryImpl implements StudentRepository {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public int save(Connection connection, Student student) {
        //todo#2 학생등록
        int result = 0;

        String query = String.format(
                "insert into jdbc_students(id, name, gender, age) values (?, ?, ?, ?);");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, student.getId());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getGender().name());
            pstmt.setInt(4, student.getAge());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public Optional<Student> findById(Connection connection, String id) {
        //todo#3 학생조회

        String query = "select * from jdbc_students where id = ?";
        Student student = null;
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, id);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getString("id");
                String name = resultSet.getString("name");
                Student.GENDER gender = Student.GENDER.valueOf(resultSet.getString("gender"));
                int age = resultSet.getInt("age");
                LocalDateTime createdAt = LocalDateTime.parse(resultSet.getString("created_at"), formatter);

                student = new Student(id, name, gender, age, createdAt);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(student);
    }

    @Override
    public int update(Connection connection, Student student) {
        //todo#4 학생수정
        String query = String.format("update jdbc_students set name=?, gender=? , age=? where id=?;");

        int result = 0;
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getGender().name());
            pstmt.setInt(3, student.getAge());
            pstmt.setString(4, student.getId());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public int deleteById(Connection connection, String id) {
        //todo#5 학생삭제
        String query = String.format("delete from jdbc_students where id=?;");

        int result = 0;
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, id);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

}