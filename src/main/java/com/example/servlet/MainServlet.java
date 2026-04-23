package com.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/files")
public class MainServlet extends HttpServlet {
    // можно ограничить корень

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("login");
            return;
        }

        String user = (String) session.getAttribute("user");
        String ROOT = "C:/Users/Student/filemanager/" + user;

        String path = req.getParameter("path");

        if (path == null || path.isEmpty()) {
            path = ROOT;
        }

        File file = new File(path);

        // если это файл — скачать
        if (file.isFile()) {

            resp.setContentType(Files.probeContentType(file.toPath()));
            resp.setHeader("Content-Disposition",
                    "attachment; filename=\"" + file.getName() + "\"");

            FileInputStream fis = new FileInputStream(file);
            OutputStream os = resp.getOutputStream();

            fis.transferTo(os);

            fis.close();
            os.close();
            return;
        }

        // список файлов
        File[] files = file.listFiles();

        req.setAttribute("files", files);
        req.setAttribute("currentPath", file.getAbsolutePath());

        // родительская директория
        if (file.getParentFile() != null) {
            req.setAttribute("parentPath", file.getParentFile().getAbsolutePath());
        }

        String date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
                .format(new Date());

        req.setAttribute("time", date);

        req.getRequestDispatcher("/mypage.jsp").forward(req, resp);
    }
}
