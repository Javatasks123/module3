package com.example.servlet;

import com.example.dao.UserDAO;
import com.example.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        User user = userDAO.findByLoginAndPassword(login, password);

        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", login);
            resp.sendRedirect("files");
        } else {
            resp.getWriter().write("Неверный логин или пароль");
        }
    }
}