package com.nhnacademy.jdbc.club.repository.impl;

import com.nhnacademy.jdbc.club.domain.ClubStudent;
import com.nhnacademy.jdbc.club.repository.ClubRegistrationRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
public class ClubRegistrationRepositoryImpl implements ClubRegistrationRepository {

    @Override
    public int save(Connection connection, String studentId, String clubId) {
        //todo#11 - 핵생 -> 클럽 등록, executeUpdate() 결과를 반환
        String query = "insert into jdbc_club_registrations(student_id, club_id) values (? ,?);";
        int result;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, studentId);
            pstmt.setString(2, clubId);

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public int deleteByStudentIdAndClubId(Connection connection, String studentId, String clubId) {
        //todo#12 - 핵생 -> 클럽 탈퇴, executeUpdate() 결과를 반환
        String query = "delete from jdbc_club_registrations where student_id = ? and club_id = ?;";
        int result = 0;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, studentId);
            pstmt.setString(2, clubId);

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public List<ClubStudent> findClubStudentsByStudentId(Connection connection, String studentId) {
        //todo#13 - 핵생 -> 클럽 등록, executeUpdate() 결과를 반환
        List<ClubStudent> clubStudents = new ArrayList<>();
        String query =
                "select s.id as student_id, s.name as student_name, c.club_id, c.club_name " +
                "from jdbc_students s " +
                "join jdbc_club_registrations cr on s.id = cr.student_id " +
                "join jdbc_club c on cr.club_id = c.club_id " +
                "where student_id = ?; ";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, studentId);

            ResultSet rs = pstmt.executeQuery();

            String sid;
            String sname;
            String cid;
            String cname;

            while(rs.next()) {
                sid = rs.getString("student_id");
                sname = rs.getString("student_name");
                cid = rs.getString("club_id");
                cname = rs.getString("club_name");

                clubStudents.add(new ClubStudent(sid, sname, cid, cname));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clubStudents;
    }

    @Override
    public List<ClubStudent> findClubStudents(Connection connection) {
        //todo#21 - join
        List<ClubStudent> clubStudents = new ArrayList<>();
        String query =
                "select s.id as student_id, s.name as student_name, c.club_id, c.club_name " +
                        "from jdbc_students s " +
                        "inner join jdbc_club_registrations cr on s.id = cr.student_id "  +
                        "inner join jdbc_club c on cr.club_id = c.club_id " +
                        "order by s.id asc, c.club_id asc ";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();

            String sid;
            String sname;
            String cid;
            String cname;

            while(rs.next()) {
                sid = rs.getString("student_id");
                sname = rs.getString("student_name");
                cid = rs.getString("club_id");
                cname = rs.getString("club_name");

                clubStudents.add(new ClubStudent(sid, sname, cid, cname));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clubStudents;
    }

    @Override
    public List<ClubStudent> findClubStudents_left_join(Connection connection) {
        //todo#22 - left join
        List<ClubStudent> clubStudents = new ArrayList<>();
        String query =
                "select s.id as student_id, s.name as student_name, c.club_id, c.club_name " +
                        "from jdbc_students s " +
                        "left join jdbc_club_registrations cr on s.id = cr.student_id " +
                        "left join jdbc_club c on cr.club_id = c.club_id; ";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();

            String sid;
            String sname;
            String cid;
            String cname;

            while(rs.next()) {
                sid = rs.getString("student_id");
                sname = rs.getString("student_name");
                cid = rs.getString("club_id");
                cname = rs.getString("club_name");

                clubStudents.add(new ClubStudent(sid, sname, cid, cname));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clubStudents;
    }

    @Override
    public List<ClubStudent> findClubStudents_right_join(Connection connection) {
        //todo#23 - right join
        List<ClubStudent> clubStudents = new ArrayList<>();
        String query =
                "select s.id as student_id, s.name as student_name, c.club_id, c.club_name " +
                        "from jdbc_students s " +
                        "right join jdbc_club_registrations cr on s.id = cr.student_id " +
                        "right join jdbc_club c on cr.club_id = c.club_id; ";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();

            String sid;
            String sname;
            String cid;
            String cname;

            while(rs.next()) {
                sid = rs.getString("student_id");
                sname = rs.getString("student_name");
                cid = rs.getString("club_id");
                cname = rs.getString("club_name");

                clubStudents.add(new ClubStudent(sid, sname, cid, cname));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clubStudents;
    }

    @Override
    public List<ClubStudent> findClubStudents_full_join(Connection connection) {
        //todo#24 - full join = left join union right join
        List<ClubStudent> clubStudents = new ArrayList<>();
        String query = "select s.id as student_id, s.name as student_name, c.club_id, c.club_name " +
                "from jdbc_students s " +
                "left join jdbc_club_registrations cr on s.id = cr.student_id " +
                "left join jdbc_club c on cr.club_id = c.club_id " +
                "union " +
                "select s.id as student_id, s.name as student_name, c.club_id, c.club_name " +
                "from jdbc_students s " +
                "right join jdbc_club_registrations cr on s.id = cr.student_id " +
                "right join jdbc_club c on cr.club_id = c.club_id; ";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();

            String sid;
            String sname;
            String cid;
            String cname;

            while(rs.next()) {
                sid = rs.getString("student_id");
                sname = rs.getString("student_name");
                cid = rs.getString("club_id");
                cname = rs.getString("club_name");

                clubStudents.add(new ClubStudent(sid, sname, cid, cname));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clubStudents;
    }

    @Override
    public List<ClubStudent> findClubStudents_left_excluding_join(Connection connection) {
        //todo#25 - left excluding join
        List<ClubStudent> clubStudents = new ArrayList<>();
        String query =
                "select s.id as student_id, s.name as student_name, c.club_id, c.club_name " +
                        "from jdbc_students s " +
                        "left join jdbc_club_registrations cr on s.id = cr.student_id " +
                        "left join jdbc_club c on cr.club_id = c.club_id " +
                        "where c.club_id is null; ";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();

            String sid;
            String sname;
            String cid;
            String cname;

            while(rs.next()) {
                sid = rs.getString("student_id");
                sname = rs.getString("student_name");
                cid = rs.getString("club_id");
                cname = rs.getString("club_name");

                clubStudents.add(new ClubStudent(sid, sname, cid, cname));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clubStudents;
    }

    @Override
    public List<ClubStudent> findClubStudents_right_excluding_join(Connection connection) {
        //todo#26 - right excluding join
        List<ClubStudent> clubStudents = new ArrayList<>();
        String query =
                "select s.id as student_id, s.name as student_name, c.club_id, c.club_name " +
                        "from jdbc_students s " +
                        "right join jdbc_club_registrations cr on s.id = cr.student_id " +
                        "right join jdbc_club c on cr.club_id = c.club_id " +
                        "where student_id is null; ";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();

            String sid;
            String sname;
            String cid;
            String cname;

            while(rs.next()) {
                sid = rs.getString("student_id");
                sname = rs.getString("student_name");
                cid = rs.getString("club_id");
                cname = rs.getString("club_name");

                clubStudents.add(new ClubStudent(sid, sname, cid, cname));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clubStudents;
    }

    @Override
    public List<ClubStudent> findClubStudents_outher_excluding_join(Connection connection) {
        //todo#27 - outher_excluding_join = left excluding join union right excluding join
        List<ClubStudent> clubStudents = new ArrayList<>();
        String query =
                "select s.id as student_id, s.name as student_name, c.club_id, c.club_name " +
                "from jdbc_students s " +
                "left join jdbc_club_registrations cr on s.id = cr.student_id " +
                "left join jdbc_club c on cr.club_id = c.club_id " +
                "where c.club_id is null " +
                "union " +
                "select s.id as student_id, s.name as student_name, c.club_id, c.club_name " +
                "from jdbc_students s " +
                "right join jdbc_club_registrations cr on s.id = cr.student_id " +
                "right join jdbc_club c on cr.club_id = c.club_id " +
                "where s.id is null; ";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();

            String sid;
            String sname;
            String cid;
            String cname;

            while(rs.next()) {
                sid = rs.getString("student_id");
                sname = rs.getString("student_name");
                cid = rs.getString("club_id");
                cname = rs.getString("club_name");

                clubStudents.add(new ClubStudent(sid, sname, cid, cname));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clubStudents;
    }

}