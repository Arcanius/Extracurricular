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

public final class UsersCommand extends Command {
    private static final Logger log = Logger.getLogger(UsersCommand.class);

    private final UserDao userDao = UserDaoImpl.getInstance();

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("id") == null) {
            	req.setAttribute("users", userDao.getAll());
                req.getRequestDispatcher("jsp/users.jsp").forward(req, resp);
            } else {
            	try {
            		int id = Integer.parseInt(req.getParameter("id"));
            		User user = userDao.getById(id);
            		if (user != null) {
            			req.setAttribute("user", userDao.getById(id));
            			req.getRequestDispatcher("jsp/user.jsp").forward(req, resp);
            		} else {
            			resp.sendRedirect("/users");
            		}
            	} catch (NumberFormatException e) {
            		log.error(e.getMessage());
            		resp.sendRedirect("/users");
            	}
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            req.setAttribute("error", "error.db");
            req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
        }
    }
    
    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	try {
            if (req.getParameter("id") == null) {
            	resp.sendRedirect("/users");
            } else {
            	try {
            		int id = Integer.parseInt(req.getParameter("id"));
            		User user = userDao.getById(id);
            		if (user != null) {
            			if (req.getParameter("role") != null) {
            				user.setRole(User.Role.valueOf(req.getParameter("role")));
            			}
            			if (req.getParameter("banned") != null) {
            				user.setBanned(Boolean.valueOf(req.getParameter("banned")));
            			}
            			userDao.update(user);
            		}
            		resp.sendRedirect("/users");
            	} catch (IllegalArgumentException e) {
            		log.error(e.getMessage());
            		resp.sendRedirect("/users");
            	}
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            req.setAttribute("error", "error.db");
            req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
        }
    }
}
