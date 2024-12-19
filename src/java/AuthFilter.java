/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Cookie[] cookies = httpRequest.getCookies();
        boolean loggedIn = false;

        // Check for the userId cookie
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("session_id".equals(cookie.getName())) {
                    loggedIn = true; // User is logged in if cookie exists
                    break;
                }
            }
        }

        String loginURI = httpRequest.getContextPath() + "/SignIn.xhtml";

        // Redirect to login page if user is not logged in
        if (!loggedIn) {
            httpResponse.sendRedirect(loginURI); // Redirect to login page
        } else {
            chain.doFilter(request, response); // User is logged in
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}

