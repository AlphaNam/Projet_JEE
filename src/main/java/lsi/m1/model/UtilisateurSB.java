/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsi.m1.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import static lsi.m1.utils.Constantes.REQ_TOUS_UTILISATEURS;

/**
 *
 * @author agilan.colbert
 */
@Stateless
public class UtilisateurSB {

 @PersistenceContext
 EntityManager em;

     public List getUtilisateurs() {
        Query q = em.createQuery("SELECT u FROM Utilisateur u");
        return q.getResultList();
    }
     
    public boolean verifInfosConnexion(Utilisateur userInput) {
        boolean test = false;
        
        List<Utilisateur> listeUsers = getUtilisateurs();

        for (Utilisateur userBase : listeUsers) {
            if (userBase.getLogin().equals(userInput.getLogin())
                    && userBase.getPassword().equals(userInput.getPassword())) {
                test = true;
            }
        }

        return test;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
