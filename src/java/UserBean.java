/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import java.security.SecureRandom;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import javax.faces.context.FacesContext;
import java.util.Date;
import java.util.UUID;



@ManagedBean(name = "userBean")
@ViewScoped
public class UserBean {

    private User user = new User();
    public String address;
    private String email;
    private String password;
    private String errorMessage;
    private String successMessage;
    
    
    // Getter and Setter for address
    public User getUser() {
        return user; // Assuming User has a getAddress method
    }

    public void setUser(User user) {
        this.user = user; // Assuming User has a setAddress method
    }

    //woi p//
    
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    
    public String login() {
        Session session = null;
        errorMessage = "";
        successMessage = "";

        try {
            session = HibernateUtil.getSessionFactory().openSession(); // Open session
            String hashedPassword = hashPassword(password);
            String hql = "FROM User WHERE email = :email AND password = :password";
            Query query = session.createQuery(hql);
            query.setParameter("email", email);
            query.setParameter("password", hashedPassword);
            User authenticatedUser = (User) query.uniqueResult();
            if (authenticatedUser != null) {
                // User authenticated successfully
                createSession(authenticatedUser, session); // Create a new session record
                return "User"; // Change this to your success page
            } else {
                // Authentication failed
                errorMessage = "Invalid email or password";
                return null; // Stay on the same page
            }
        } catch (Exception e) {
            e.printStackTrace();
            //errorMessage = "Login failed: " + e.getMessage();
            return null; // Stay on the same page
        } finally {
            if (session != null) {
                session.close(); // Manually close the session
            }
        }
    }
    
    private void createSession(User user, Session session) {
        // Create a new UserSession
        
        
        UserSession userSession = new UserSession();
        userSession.setUserId(user.getId()); // Assuming User has getId method
        userSession.setSessionId(UUID.randomUUID().toString()); // Generate a unique session ID
        userSession.setCreatedAt(new Date());

        // Save the session to the database
        Transaction transaction = session.beginTransaction();
        session.save(userSession);
        transaction.commit();

        // Set a cookie with the session ID
        setSessionCookie(userSession.getSessionId());
    }

    private void setSessionCookie(String sessionId) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        
        Cookie cookie = new Cookie("session_id", sessionId);
        cookie.setMaxAge(60 * 60); // 1 hour
        cookie.setPath("/"); // Set path for the cookie
        response.addCookie(cookie);
    }

    public String register() {
        // Save the user data to the database
        errorMessage = "";
        successMessage = "";
        Transaction transaction = null;
        Session session = null;
        SecureRandom secureRandom = new SecureRandom();
        
        try {
            session = HibernateUtil.getSessionFactory().openSession(); // Open session
            transaction = session.beginTransaction(); // Start transaction
            String hashedPassword = hashPassword(user.getPassword());
            user.setPassword(hashedPassword);
            long number = 10000000000000000L + (long)(secureRandom.nextDouble() * 9000000000000000L);
            user.setRekening(String.valueOf(number));
            user.setRole("USER");
            session.save(user); // Save the user object to the database
            transaction.commit(); // Commit transaction
            
            // Reset the user object for the next use
            user = new User(); // Clear the form after successful registration
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback on error
            }
            e.printStackTrace(); // Log the error
            errorMessage = "Registration failed!";
            return null; // Stay on the same page
        } finally {
            if (session != null) {
                session.close(); // Manually close the session
            }
        }
        successMessage = "Succesfully create account, please login";
        return null; // Navigate to a success page after registration
    }
     public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
