package com.example.extracurricular.web.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Implementation of {@link HttpSessionListener}.
 *
 * @author Yurii Khmil
 */
@WebListener
public final class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
    	HttpSession session = se.getSession();
        session.setMaxInactiveInterval(-1);
        session.setAttribute("orderBy", "none");
    }
}
