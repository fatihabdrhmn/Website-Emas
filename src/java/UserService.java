
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


@Stateless
public class UserService {

    @PersistenceContext(unitName = "yourPersistenceUnit")
    private EntityManager em;

    // Method to save or update a user in the database
    public void saveUser(User user) {
        if (user.getId() == null) {
            em.persist(user);  // For new users
        } else {
            em.merge(user);  // For existing users (update)
        }
    }
}

