package com.nhnacademy.jdbc.user.repository.impl;

import com.nhnacademy.jdbc.user.domain.User;
import com.nhnacademy.jdbc.user.repository.UserRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
public class PreparedStatementUserRepository implements UserRepository {

    private final Connection conn;

    public PreparedStatementUserRepository() {
        conn = DbUtils.getConnection();
    }

    @Override
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
        //todo#11 -PreparedStatement- 아이디 , 비밀번호가 일치하는 회원조회
        String query = "select * from jdbc_users where user_id = ? and user_password = ?;";

        User user = null;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userId);
            pstmt.setString(2, userPassword);
            ResultSet resultSet = pstmt.executeQuery();

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
        //todo#12-PreparedStatement-회원조회
        String query = "select * from jdbc_users where user_id = ?;";

        User user = null;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, userId);
            ResultSet resultSet = pstmt.executeQuery();

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
        //todo#13-PreparedStatement-회원저장
        String query = "insert into jdbc_users values (?, ?, ?);";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getUserName());
            pstmt.setString(3, user.getUserPassword());

            pstmt.executeUpdate();

            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updateUserPasswordByUserId(String userId, String userPassword) {
        //todo#14-PreparedStatement-회원정보 수정
        String query = "update jdbc_users set user_password = ? where user_id = ?;";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userPassword);
            pstmt.setString(2, userId);

            pstmt.executeUpdate();

            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteByUserId(String userId) {
        //todo#15-PreparedStatement-회원삭제
        String query = "delete from jdbc_users where user_id = ?;";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userId);
            pstmt.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
