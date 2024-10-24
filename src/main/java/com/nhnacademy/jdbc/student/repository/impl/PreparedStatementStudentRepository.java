package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import com.nhnacademy.jdbc.util.DbUtils;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class PreparedStatementStudentRepository implements StudentRepository {
    private Connection conn;
    //private PreparedStatement pstmt;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public PreparedStatementStudentRepository() throws SQLException {
        conn = DbUtils.getConnection();
    }
    @Override
    public int save(Student student) {
        //todo#1 insert student
        String query = String.format(
                "insert into jdbc_students(id, name, gender, age) values (?, ?, ?, ?);");

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, student.getId());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getGender().name());
            pstmt.setInt(4, student.getAge());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 1;
    }

    @Override
    public Optional<Student> findById(String id){
        //todo#2 student 조회
        String query = String.format("select * from jdbc_students where id = ?;");

        Student student = null;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
            ResultSet resultSet = pstmt.executeQuery();

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
        String query = String.format("update jdbc_students set name=?, gender=? , age=? where id=?;");

        try (PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getGender().name());
            pstmt.setInt(3, student.getAge());
            pstmt.setString(4, student.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 1;
    }

    @Override
    public int deleteById(String id){
        //todo#4 student 삭제
        String query = String.format("delete from jdbc_students where id=?;");

        try (PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 1;
    }
}