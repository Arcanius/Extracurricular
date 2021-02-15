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

public final class RegistrationCommand extends Command {
    private static final Logger log = Logger.getLogger(RegistrationCommand.class);

    private final UserDao userDao = UserDaoImpl.getInstance();

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("jsp/registration.jsp").forward(req, resp);
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        user.setNameEn(req.getParameter("name_en"));
        user.setNameUk(req.getParameter("name_uk"));
        user.setRole(User.Role.STUDENT);
        user.setBanned(false);
        try {
            Map<String, String> errors = userDao.validateForRegistration(user, req.getParameter("confirmPassword"));
            if (errors.isEmpty()) {
                userDao.save(user);
                resp.sendRedirect(req.getContextPath() + "/login");
            } else {
                errors.forEach(req::setAttribute);
                req.getRequestDispatcher("jsp/registration.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            req.setAttribute("error", "error.db");
            req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
        }
    }
}
