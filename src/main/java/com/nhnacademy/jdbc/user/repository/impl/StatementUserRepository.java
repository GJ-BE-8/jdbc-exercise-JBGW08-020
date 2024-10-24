package com.nhnacademy.jdbc.user.repository.impl;

import com.nhnacademy.jdbc.user.domain.User;
import com.nhnacademy.jdbc.user.repository.UserRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@Slf4j
public class StatementUserRepository implements UserRepository {
    private final Connection conn;
    private Statement statement;

    public StatementUserRepository() {
        conn = DbUtils.getConnection();
        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
        //todo#1 아이디, 비밀번호가 일치하는 User 조회
        String query = String.format("select * from jdbc_users where user_id = '%s' and user_password = '%s';", userId, userPassword);

        User user = null;
        try {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                userId = resultSet.getString("user_id");
                String userName = resultSet.getString("user_name");
                userPassword = resultSet.getString("user_password");

                user = new User(userId, userName, userPassword);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findById(String userId) {
        //#todo#2-아이디로 User 조회
        String query = String.format("select * from jdbc_users where user_id = '%s';", userId);

        User user = null;
        try {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                userId = resultSet.getString("user_id");
                String userName = resultSet.getString("user_name");
                String userPassword = resultSet.getString("user_password");

                user = new User(userId, userName, userPassword);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(user);
    }

    @Override
    public int save(User user) {
        //todo#3- User 저장
        String query = String.format("insert into jdbc_users values ('%s', '%s', '%s');",
                user.getUserId(), user.getUserName(), user.getUserPassword());

        try {
            statement.executeUpdate(query);
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updateUserPasswordByUserId(String userId, String userPassword) {
        //todo#4-User 비밀번호 변경
        String query = String.format("update jdbc_users set user_password = '%s' where user_id = '%s';",
                userPassword, userId);

        try {
            statement.executeUpdate(query);
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteByUserId(String userId) {
        //todo#5 - User 삭제
        String query = String.format("delete from jdbc_users where user_id = '%s';", userId);

        try {
            statement.executeUpdate(query);
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
