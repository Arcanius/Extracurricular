package com.example.extracurricular.web.listener;

import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public final class ApplicationContextListener implements ServletContextListener {
    private static final Logger log = Logger.getLogger(ApplicationContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Application started at: " + sce.getServletContext().getServerInfo());
    }
}
