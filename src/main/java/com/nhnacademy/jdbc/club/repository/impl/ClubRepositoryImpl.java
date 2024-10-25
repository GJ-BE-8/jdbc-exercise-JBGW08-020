package com.nhnacademy.jdbc.club.repository.impl;

import com.nhnacademy.jdbc.club.domain.Club;
import com.nhnacademy.jdbc.club.repository.ClubRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ClubRepositoryImpl implements ClubRepository {

    private static final DateTimeFormatter fomatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Override
    public Optional<Club> findByClubId(Connection connection, String clubId) {
        //todo#3 club 조회
        String query = "select * from jdbc_club where club_id = ?;";
        Club club = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, clubId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String clubName = rs.getString("club_name");
                LocalDateTime clubCreatedAt = LocalDateTime.parse(rs.getString("club_created_at"), fomatter);

                club = new Club(clubId, clubName, clubCreatedAt);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(club);
    }

    @Override
    public int save(Connection connection, Club club) {
        //todo#4 club 생성, executeUpdate() 결과를 반환
        String query = "insert into jdbc_club(club_id, club_name) values (?, ?);";
        int result = 0;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, club.getClubId());
            pstmt.setString(2, club.getClubName());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public int update(Connection connection, Club club) {
        //todo#5 club 수정, clubName을 수정합니다. executeUpdate()결과를 반환
        String query = "update jdbc_club set club_name = ? where club_id = ?;";
        int result = 0;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, club.getClubName());
            pstmt.setString(2, club.getClubId());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public int deleteByClubId(Connection connection, String clubId) {
        //todo#6 club 삭제, executeUpdate()결과 반환
        String query = "delete from jdbc_club where club_id = ?";
        int result = 0;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, clubId);;

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public int countByClubId(Connection connection, String clubId) {
        //todo#7 clubId에 해당하는 club의 count를 반환
        String query = "select count(*) from jdbc_club where club_id = ?";
        int result = 0;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, clubId);

            ResultSet rs = pstmt.executeQuery();
            rs.next();

            result = rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
