package com.example.extracurricular.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class WrongCommand extends Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	req.setAttribute("error", "error.not_found");
        req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
    }
}
