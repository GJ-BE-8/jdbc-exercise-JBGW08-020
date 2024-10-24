package com.nhnacademy.jdbc.bank.repository.impl;

import com.nhnacademy.jdbc.bank.domain.Account;
import com.nhnacademy.jdbc.bank.repository.AccountRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AccountRepositoryImpl implements AccountRepository {

    public Optional<Account> findByAccountNumber(Connection connection, long accountNumber){
        //todo#1 계좌-조회
        String query = "select * from jdbc_account where account_number = ?;";

        Account account = null;
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                long balance = rs.getLong("balance");

                account = new Account(accountNumber, name, balance);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(account);
    }

    @Override
    public int save(Connection connection, Account account) {
        //todo#2 계좌-등록, executeUpdate() 결과를 반환 합니다.
        String query = "insert into jdbc_account(account_number, name, balance) values (? ,?, ?);";
        int result = 0;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1, account.getAccountNumber());
            pstmt.setString(2, account.getName());
            pstmt.setLong(3, account.getBalance());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int countByAccountNumber(Connection connection, long accountNumber){
        int count=0;
        //todo#3 select count(*)를 이용해서 계좌의 개수를 count해서 반환
        String query = "select count(*) from jdbc_account where account_number = ?;";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return count;
    }

    @Override
    public int deposit(Connection connection, long accountNumber, long amount){
        //todo#4 입금, executeUpdate() 결과를 반환 합니다.
        String query = "update jdbc_account set balance = balance + ? where account_number = ?;";
        int result = 0;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, amount);
            pstmt.setLong(2, accountNumber);

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public int withdraw(Connection connection, long accountNumber, long amount){
        //todo#5 출금, executeUpdate() 결과를 반환 합니다.
        String query = "update jdbc_account set balance = balance - ? where account_number = ?;";
        int result = 0;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, amount);
            pstmt.setLong(2, accountNumber);

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;

    }

    @Override
    public int deleteByAccountNumber(Connection connection, long accountNumber) {
        //todo#6 계좌 삭제, executeUpdate() 결과를 반환 합니다.
        String query = "delete from jdbc_account where account_number = ?;";
        int result = 0;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, accountNumber);

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
