/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import java.util.Optional;

public class SessionUtils {

    // Retrieve the session ID from cookies
    public static Optional<String> getSessionIdFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("session_id".equals(cookie.getName())) {
                    return Optional.of(cookie.getValue());
                }
            }
        }
        return Optional.empty();
    }

    // Fetch the User object using session_id
    public static User getUserFromSessionId(HttpServletRequest request) {
        Optional<String> sessionIdOpt = getSessionIdFromCookies(request);
        
        if (sessionIdOpt.isPresent()) {
            String sessionId = sessionIdOpt.get();
            Session session = null;
            Transaction transaction = null;
            
            try {
                // Open Hibernate session
                session = HibernateUtil.getSessionFactory().openSession();
                transaction = session.beginTransaction();

                // Query to find user based on session_id
                String hql = "FROM UserSession WHERE sessionId = :session_id";
                Query query = session.createQuery(hql);
                query.setParameter("session_id", sessionId);
                
                UserSession sessionRecord = (UserSession) query.uniqueResult();
                
                // If session record found, retrieve the associated user
                if (sessionRecord != null) {
                    Long userId = sessionRecord.getUserId();
                    
                    // Fetch the User based on userId
                    String userHql = "FROM User WHERE id = :user_id";
                    Query userQuery = session.createQuery(userHql);
                    userQuery.setParameter("user_id", userId);
                    User user = (User)userQuery.uniqueResult();

                    transaction.commit();
                    return user; // Return the user object
                }
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        }
        return null; // Return null if no session_id or user found
    }
}

