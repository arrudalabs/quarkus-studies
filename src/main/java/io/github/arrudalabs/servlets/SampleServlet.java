package io.github.arrudalabs.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.Socket;

@WebServlet(urlPatterns = {"/servlet/*"})
public class SampleServlet extends HttpServlet {
    public static final String X_FORWARDED_PROTO = "X-Forwarded-Proto";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();


        if (path.equals("/make-external-call")) {
            // Fake making an external call without involving the UI
            // e.g. OAuth Authentication Flow will have a few of these, resulting in 
            // receiving the token eventually
            resp.sendRedirect("/servlet/callback");
        } else if (path.equals("/callback")) {
            // Redirect back to a path controlled by the Angular client
            String redirectPath = "/clientCallback";

            boolean proxy = Boolean.getBoolean("ui.proxy");
            if (proxy && checkProxyIsRunning()) {
                redirectPath = "http://localhost:4200" + redirectPath;
                return;
            }

            if (req.getHeader(X_FORWARDED_PROTO) != null) {
                if (req.getHeader(X_FORWARDED_PROTO).indexOf("https") != 0) {
                    String pathInfo = (req.getPathInfo() != null) ? req.getPathInfo() : "";
                    resp.sendRedirect("https://" + req.getServerName() + redirectPath);
                    return;
                }
            }

            resp.sendRedirect(redirectPath);
        } else {
            resp.sendError(404);
        }
    }


    private boolean checkProxyIsRunning() {
        try (Socket s = new Socket("localhost", 4200)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
