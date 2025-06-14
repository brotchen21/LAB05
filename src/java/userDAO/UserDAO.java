/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package userDAO;

import dao.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author ADMIN
 */
public class UserDAO implements IUserDao {

    private static final String LOGIN = "SELECT UsersID FROM [Users] WHERE username = ? AND password = ?";
    private static final String LOGIN1 = "SELECT id, username, role FROM [Users] WHERE username = ? AND password = ?";
    private static final String INSERT_USER = "INSERT INTO Users (username, email, country, role, status, password) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM Users WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM Users";
    private static final String UPDATE_USER = "UPDATE Users SET username = ?, email = ?, country = ?, role = ?, status = ?, password = ? WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM Users WHERE id = ?";

    public static User checkLogin(String username, String password) throws SQLException {
        User us = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try (Connection con = DBConnection.getConnection()) {
            if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
                throw new SQLException("Tên đăng nhập hoặc mật khẩu không hợp lệ!");
            }
            ptm = con.prepareStatement(LOGIN1);
            ptm.setString(1, username);
            ptm.setString(2, password);
            rs = ptm.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("username");
                String role = rs.getString("role");
                us = new User(id, name, "", role);
            }
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
        }
        return us;
    }

    @Override
    public void insertUser(User user) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_USER)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getCountry());
            ps.setString(4, user.getRole());
            ps.setBoolean(5, user.isStatus());
            ps.setString(6, user.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User selectUser(int id) {
        User user = null;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_USER_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String username = rs.getString("username");
                String email = rs.getString("email");
                String country = rs.getString("country");
                String role = rs.getString("role");
                boolean status = rs.getBoolean("status");
                String password = rs.getString("password");
                user = new User(id, username, email, country, role, status, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> selectAllUsers() {
        List<User> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL_USERS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String email = rs.getString("email");
                String country = rs.getString("country");
                String role = rs.getString("role");
                boolean status = rs.getBoolean("status");
                String password = rs.getString("password");
                list.add(new User(id, username, email, country, role, status, password));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public boolean deleteUser(int id) {
        boolean deleted = false;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_USER)) {
            ps.setInt(1, id);
            deleted = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return deleted;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        boolean updated;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_USER)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getCountry());
            ps.setString(4, user.getRole());
            ps.setBoolean(5, user.isStatus());
            ps.setString(6, user.getPassword());
            ps.setInt(7, user.getId());
            updated = ps.executeUpdate() > 0;
        }
        return updated;
    }

    public static void main(String[] args) {
        try {
            User u = checkLogin("Chi Pheo", "abc@123");
            System.out.println(u);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

