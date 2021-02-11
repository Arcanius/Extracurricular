package com.example.extracurricular.web.listener;

import javax.servlet.annotation.WebListener;
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
        se.getSession().setMaxInactiveInterval(-1);
    }
}
