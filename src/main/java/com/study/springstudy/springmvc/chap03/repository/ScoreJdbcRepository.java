package com.study.springstudy.springmvc.chap03.repository;

import com.study.springstudy.springmvc.chap03.entity.Score;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreJdbcRepository implements ScoreRepository{

    private String url = "jdbc:mariadb://localhost:3306/spring5";
    private String username = "root";
    private String password = "mariadb";

    public ScoreJdbcRepository() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean save(Score score) {

        try (Connection conn = connect()){

            String sql = "insert into tbl_score" +
                    "(stu_name, kor, eng, math, total, average, grade) " +
                    "values(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, score.getStuName());
            pstmt.setInt(2, score.getKor());
            pstmt.setInt(3, score.getEng());
            pstmt.setInt(4, score.getMath());
            pstmt.setInt(5, score.getTotal());
            pstmt.setDouble(6, score.getAverage());
            pstmt.setString(7, score.getGrade().toString());

            int result = pstmt.executeUpdate();
            if(result == 1) return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Score> findAll() {
        List<Score> scoreList = new ArrayList<>();
        try (Connection conn = connect()) {
            String sql = "select * from tbl_score";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(); // 결과를 담은 표를 반환
            while (rs.next()) {
                Score s = new Score(rs);
                scoreList.add(s);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return scoreList;
    }

    @Override
    public Score findOne(long stuNum) {
        Score score = null;
        try (Connection conn = connect()) {
            String sql = "select * from tbl_score where stu_num = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, stuNum);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                score = new Score(rs);
            }

            return score;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public int[] findRankByStuNum(long stuNum) {

        try (Connection conn = connect()) {

            String sql = "SELECT A.stu_num, A.rank, A.cnt" +
                    " FROM (SELECT *, " +
                    "           RANK() OVER (ORDER BY average DESC) AS rank, " +
                    "           COUNT(*) OVER() AS cnt" +
                    "       FROM tbl_score) A " +
                    "WHERE A.stu_num = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, stuNum);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new int[] {
                        rs.getInt("rank"),
                        rs.getInt("cnt")
                };
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
