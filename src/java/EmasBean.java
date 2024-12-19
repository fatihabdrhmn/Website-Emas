/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.security.SecureRandom;
import java.util.HashSet;
import java.util.List;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;


@ManagedBean(name = "emasBean")
@SessionScoped
public class EmasBean {
    private List<BankAccount> beliBankAccount;
    private float jualAmount;
    private float beliAmount;
    private User user;
    private Session session = null;
    private BankAccount selectedBankAccount;
    private String jualSender;
    private String jualReceiver;
    private String beliSender;
    private String successMessage;

    @PostConstruct
    public void init() {
        // Retrieve user from the session when the bean is initialized
        user = retrieveUserFromSession();

        // Ensure that the session is properly opened and closed
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM BankAccount";
            Query query = session.createQuery(hql);
            beliBankAccount = (List<BankAccount>) query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Always close the session to avoid memory leaks
            if (session != null) {
                session.close();
            }
        }
    }
    public String beli() {
        Transaction transaction = null;
        Session session = null;
        SecureRandom secureRandom = new SecureRandom();
        
        try {
            session = HibernateUtil.getSessionFactory().openSession(); // Open session
            transaction = session.beginTransaction(); // Start transaction
            BeliTransaction beli = new BeliTransaction();
            beli.setReceiver(beliSender);
            beli.setAmount(beliAmount);
            beli.setCreatedAt(new Date());
            beli.setUserId(user.getId());
            session.save(beli); // Save the user object to the database
            transaction.commit(); // Commit transaction
           
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback on error
            }
            e.printStackTrace(); // Log the error
            return "User"; // Stay on the same page
        } finally {
            if (session != null) {
                session.close(); // Manually close the session
            }
        }
        return "User"; // Navigate to a success page after registration
    }
    public String jual() {
        Transaction transaction = null;
        Session session = null;
        SecureRandom secureRandom = new SecureRandom();
        
        try {
            session = HibernateUtil.getSessionFactory().openSession(); // Open session
            transaction = session.beginTransaction(); // Start transaction
            JualTransaction jual = new JualTransaction();
            jual.setSender(jualSender);
            jual.setReceiver(jualReceiver);
            jual.setAmount(jualAmount);
            jual.setUserId(user.getId());
            jual.setCreatedAt(new Date());
            session.save(jual); // Save the user object to the database
            transaction.commit(); // Commit transaction
           
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback on error
            }
            e.printStackTrace(); // Log the error
            return "User"; // Stay on the same page
        } finally {
            if (session != null) {
                session.close(); // Manually close the session
            }
        }
        return "User"; // Navigate to a success page after registration
    }

    // Method to retrieve user from session
    public User retrieveUserFromSession() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        return SessionUtils.getUserFromSessionId(request);
    }

    // Getter
    public List<BankAccount> getBeliBankAccount() {
        return beliBankAccount;
    }

    // Setter
    public void setBeliBankAccount(List<BankAccount> beliBankAccount) {
        this.beliBankAccount = beliBankAccount;
    }
    // Getter
    public BankAccount getSelectedBankAccount() {
        return selectedBankAccount;
    }

    // Setter
    public void setSelectedBankAccount(BankAccount selectedBankAccount) {
        this.selectedBankAccount = selectedBankAccount;
    }
    // Getter
    public float getJualAmount() {
        return jualAmount;
    }

    // Setter
    public void setJualAmount(float jualAmount) {
        this.jualAmount = jualAmount;
    }
    // Getter
    public float getBeliAmount() {
        return beliAmount;
    }

    // Setter
    public void setBeliAmount(float beliAmount) {
        this.beliAmount = beliAmount;
    }
    // Getter
    public User getUser() {
        return user;
    }

    // Setter
    public void setUser(User user) {
        this.user = user;
    }
    
    public String prepareConfirmJual() {
        // Here you can set additional values or perform logic
        return "ConfirmJual"; // This should match the navigation to your ConfirmJual.xhtml
    }
    public String prepareConfirmBeli() {
        // Here you can set additional values or perform logic
        return "ConfirmBeli"; // This should match the navigation to your ConfirmJual.xhtml
    }
    // Getter and Setter for jualSender
    public String getJualSender() {
        return jualSender;
    }

    public void setJualSender(String jualSender) {
        this.jualSender = jualSender;
    }

    // Getter and Setter for jualReceiver
    public String getJualReceiver() {
        return jualReceiver;
    }

    public void setJualReceiver(String jualReceiver) {
        this.jualReceiver = jualReceiver;
    }

    // Getter and Setter for beliSender
    public String getBeliSender() {
        return beliSender;
    }

    public void setBeliSender(String beliSender) {
        this.beliSender = beliSender;
    }
    // Getter
    public String getSuccessMessage() {
        return successMessage;
    }

    // Setter
    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
}

