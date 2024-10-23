package com.nhnacademy.jdbc.student.domain;

import com.nhnacademy.jdbc.util.DbUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class Student {

    public enum GENDER{
        M,F
    }
    private final String id;
    private final String name;
    private final GENDER gender;
    private final Integer age;
    private final LocalDateTime createdAt;

    //todo#0 필요한 method가 있다면 추가합니다.
    public Student(String id, String name, GENDER gender, Integer age) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.createdAt = LocalDateTime.now();
    }

    public Student(String id, String name, GENDER gender, Integer age, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.createdAt = createdAt;
    }
    public void createStudentTable() throws SQLException {
        Connection connection = DbUtils.getConnection();
        Statement statement = connection.createStatement();

        String query = "CREATE TABLE jdbc_students (" +
                "  id varchar(50) NOT NULL COMMENT '학생-아이디'," +
                "  name varchar(50) NOT NULL COMMENT '학생-이름'," +
                "  gender varchar(1) NOT NULL COMMENT '성별 (M | F)'," +
                "  age int NOT NULL," +
                "  created_at datetime DEFAULT CURRENT_TIMESTAMP," +
                "  PRIMARY KEY (id)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='학생';";

        statement.executeUpdate(query);
    }
}