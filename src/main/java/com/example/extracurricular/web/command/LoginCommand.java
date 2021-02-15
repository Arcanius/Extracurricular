package com.example.extracurricular.web.command;

import com.example.extracurricular.db.dao.UserDao;
import com.example.extracurricular.db.dao.UserDaoImpl;
import com.example.extracurricular.db.model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public final class LoginCommand extends Command {
    private static final Logger log = Logger.getLogger(LoginCommand.class);

    private final UserDao userDao = UserDaoImpl.getInstance();

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("logout") != null && req.getSession().getAttribute("user") != null) {
            log.info("User " + req.getSession().getAttribute("user") + " logged out");
            req.getSession().removeAttribute("user");
            resp.sendRedirect(req.getContextPath() + "/login");
        } else if (req.getSession().getAttribute("user") != null) {
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.getRequestDispatcher("jsp/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        try {
            Map<String, String> errors = userDao.validateForLogin(user);
            if (errors.isEmpty()) {
                req.getSession().setAttribute("user", user);
                log.info("User " + user + " logged in");
                resp.sendRedirect(req.getContextPath() + "/");
            } else {
                errors.forEach(req::setAttribute);
                req.getRequestDispatcher("jsp/login.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            req.setAttribute("error", "error.db");
            req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
        }
    }
}
