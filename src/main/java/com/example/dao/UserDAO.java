package com.example.dao;

import com.example.util.DBUtil;
import java.sql.*;

public class UserDAO {

    public boolean register(String login, String password) {

        String sql = "INSERT INTO users (login, password) VALUES (?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);
            ps.setString(2, password);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            return false;
        }
    }

    public boolean login(String login, String password) {

        String sql = "SELECT * FROM users WHERE login=? AND password=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}