/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import javax.ejb.EJB;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

@ManagedBean(name = "profileBean")
@SessionScoped
public class ProfileBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private User user; // User object to hold the logged-in user
    private String changedPassword;
    private boolean editMode = false;
    private double currentGold = 1;
    @EJB
    private UserService userService; 
    
    @PostConstruct
    public void init() {
        // Retrieve user from the session when the bean is initialized
        user = retrieveUserFromSession();
    }
    
    public double getCurrentGold(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        double sumJual = getSumJualTransaction(session);
        double sumBeli = getSumBeliTransaction(session);
        return sumJual - sumBeli;
    }
    
   public double getSumJualTransaction(Session session) {
    String hql = "SELECT SUM(j.amount) FROM JualTransaction j";
    Query query = session.createQuery(hql);
    Double sumJual = (Double) query.uniqueResult();
    return (sumJual != null) ? sumJual : 0.0;
}

public double getSumBeliTransaction(Session session) {
    String hql = "SELECT SUM(b.amount) FROM BeliTransaction b";
    Query query = session.createQuery(hql);
    Double sumBeli = (Double) query.uniqueResult();
    return (sumBeli != null) ? sumBeli : 0.0;
}


    // Method to retrieve user from session
    public User retrieveUserFromSession() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        
        // Use the SessionUtils class to get the user based on session_id
        return SessionUtils.getUserFromSessionId(request);
    }

    // Getter for the User
    public User getUser() {
        return user;
    }

    // Setter for the User
    public void setUser(User user) {
        this.user = user;
    }
    // Getter for the User
    public boolean getEditMode() {
        return editMode;
    }

    // Setter for the User
    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
    // Getter for the User
    public String getChangedPassword() {
        return changedPassword;
    }

    // Setter for the User
    public void setChangedPassword(String changedPassword) {
        this.changedPassword = changedPassword;
    }
    
    // Setter for the User
    public String toggleEditMode() {
        this.editMode = !this.editMode;
        return null;
    }
    
    public String saveProfile() {
        Session session = null;
        try {
            String hql = "UPDATE User u SET u.name = :name, u.email = :email, u.nik = :nik, " +
                     "u.address = :address, u.phone = :phone, u.username = :username, " +
                     "u.dob = :dob WHERE u.id = :userId";
            if (changedPassword != null && !changedPassword.isEmpty()) {
                // Logic to update the user's password if needed
                // userService.updatePassword(user, changedPassword);
                hql = "UPDATE User u SET u.name = :name, u.email = :email, u.nik = :nik, " +
                     "u.address = :address, u.phone = :phone, u.username = :username, " +
                     "u.dob = :dob, u.password = :password WHERE u.id = :userId";
            }
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery(hql);
            query.setParameter("name", user.getName());
            query.setParameter("email", user.getEmail());
            query.setParameter("nik", user.getNik());
            query.setParameter("address", user.getAddress());
            query.setParameter("phone", user.getPhone());
            query.setParameter("username", user.getUsername());
            query.setParameter("dob", user.getDob());
            if (changedPassword != null && !changedPassword.isEmpty()) {
             query.setParameter("password", user.getPassword());   
            }
            query.setParameter("userId", user.getId());
            int result = query.executeUpdate();
            this.editMode = false;  // Reset edit mode
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Profile saved successfully."));
            return "successPage"; // Navigate to a success page or another action
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Could not save user."));
            e.printStackTrace(); // Log the exception for debugging
        }
        return null; // Remain on the same page in case of an error
    }
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
}
