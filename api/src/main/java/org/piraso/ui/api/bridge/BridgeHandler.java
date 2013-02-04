package org.piraso.ui.api.bridge;

import org.apache.commons.lang.StringUtils;
import org.mortbay.jetty.handler.AbstractHandler;
import org.piraso.server.spring.web.PirasoServlet;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class BridgeHandler extends AbstractHandler {

    private ApplicationContext context;

    public BridgeHandler() {
        context = new ClassPathXmlApplicationContext("classpath:spring/piraso.xml");
    }

    @Override
    public void handle(String s, HttpServletRequest request, HttpServletResponse response, int i) throws IOException, ServletException {
        PirasoServlet servlet = context.getBean(PirasoServlet.class);

        if(StringUtils.equals("/piraso/logging", request.getRequestURI())) {
            response.setStatus(HttpServletResponse.SC_OK);
            servlet.handleRequest(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<body>");
            out.println("<h1>Bad Request</h1>");
            out.println("<p>");
            out.println("URI: " + request.getRequestURI());
            out.println("</p>");
            out.println("</body>");
            out.println("</html>");
            out.flush();
            out.close();
        }
    }
}
