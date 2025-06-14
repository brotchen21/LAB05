/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import model.User;
import userDAO.IUserDao;
import userDAO.UserDAO;

/**
 *
 * @author LOQ
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    private IUserDao userDao;

    public LoginController() {
        this.userDao = new UserDAO();
    }
    
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hiển thị trang login
        String rememberedUser = "";
        String rememberedPass = "";
        
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if("username".equals(cookie.getName())) {
                    rememberedUser = java.net.URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8.toString());
                }
                if("password".equals(cookie.getName())) {
                    rememberedPass = java.net.URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8.toString());
                }
            }
        }
        request.setAttribute("rememberedUser", rememberedUser);
        request.setAttribute("rememberedPass", rememberedPass);
        
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String rememberMe = request.getParameter("rememberMe");
    
    try {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Tên đăng nhập và mật khẩu không được để trống");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        
        User user = UserDAO.checkLogin(username, password);
        if(user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            
            if("on".endsWith(rememberMe)) {
                String encodedUsername = URLEncoder.encode(username, StandardCharsets.UTF_8.toString());
                String encodedPassword = URLEncoder.encode(password, StandardCharsets.UTF_8.toString());
                
                Cookie userCookie = new Cookie("username", encodedUsername);
                userCookie.setMaxAge(7 * 24 * 60 * 60);
                userCookie.setPath(request.getContextPath());
                response.addCookie(userCookie);
                
                Cookie passCookie = new Cookie("password", encodedPassword);
                passCookie.setMaxAge(7 * 24 * 60 * 60);
                passCookie.setPath(request.getContextPath());
                response.addCookie(passCookie);
                
                
            } else {
                Cookie[] cookies = request.getCookies();
                
                if(cookies != null) {
                    for(Cookie cookie : cookies) {
                        if("username".equals(cookie.getName()) || "password".equals(cookie.getName())) {
                            cookie.setMaxAge(0);
                            cookie.setPath(request.getContextPath());
                            response.addCookie(cookie);
                        }
                    }
                }
            }
            response.sendRedirect(request.getContextPath() + "/users");
        }else {
            request.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    } catch(SQLException e) {
        request.setAttribute("error", "Lỗi đăng nhập" + e.getMessage());
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
