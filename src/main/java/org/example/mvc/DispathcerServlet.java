package org.example.mvc;

import org.example.mvc.controller.Controller;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/") // anything can be grabbed by webservlet
public class DispathcerServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(DispathcerServlet.class);
    private RequestMappingHandlerMapping rmhm;
    @Override
    public void init() throws ServletException {
        rmhm = new RequestMappingHandlerMapping();
        rmhm.init();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("[DispatcherServlet] service started.");
        try {
            Controller handler = rmhm.findHandler(request.getRequestURI());
            String viewName = handler.handleRequest(request, response);
            log.info("view name: ", viewName);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewName);
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            log.error("exception occurred: [{}]", e.getMessage(), e);
        }
    }
}
