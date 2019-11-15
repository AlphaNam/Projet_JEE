/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsi.m1.model;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 *
 * @author agilan.colbert
 */
@Stateless
public class EmployeSB {

    @PersistenceContext
    EntityManager em;
    
    public List getEmployes() {
        Query q = em.createQuery("SELECT e FROM Employe e");
        return q.getResultList();
    }
        
    public Employe getSingleEmploye(int id) {
//        Query q = em.createNativeQuery("SELECT * FROM Employes WHERE id = ?")
//                    .setParameter(1, id);
//        return (Employe) q.getResultList();
        return em.find(Employe.class, id);
    }
    
    @Transactional
    public void deleteEmploye(int id) {
        Query q = em.createNativeQuery("DELETE from EMPLOYES WHERE id = ?")
                    .setParameter(1, id);
        q.executeUpdate();
    }
    

    
    public void addEmploye(String nom,String prenom,String teldom,String telport,String telpro,
            String adresse,String codepostal,String ville,String email) {
        Query q = em.createNativeQuery("INSERT INTO EMPLOYES (nom,prenom,teldom,telport," +
                                                 "telpro,adresse,codepostal,ville,email)" +
                                                 " VALUES (?,?,?,?,?,?,?,?,?)")
                    .setParameter(1, nom)
                    .setParameter(2,prenom)
                    .setParameter(3,teldom)
                    .setParameter(4,telport)
                    .setParameter(5,telpro)
                    .setParameter(6,adresse)
                    .setParameter(7,codepostal)
                    .setParameter(8,ville)
                    .setParameter(9,email); 
        q.executeUpdate();
    }
    
    public void modifyEmploye(int id, String nom,String prenom,String teldom,String telport,
            String telpro,String adresse,String codepostal,String ville,String email) {
        Query q = em.createNativeQuery("UPDATE EMPLOYES SET nom = ?,prenom = ?,teldom = ?,telport = ?," +
                                                 "telpro = ?,adresse = ?,codepostal = ?,ville = ?,email = ? WHERE id = ?")
                    .setParameter(1, nom)
                    .setParameter(2,prenom)
                    .setParameter(3,teldom)
                    .setParameter(4,telport)
                    .setParameter(5,telpro)
                    .setParameter(6,adresse)
                    .setParameter(7,codepostal)
                    .setParameter(8,ville)
                    .setParameter(9,email)
                    .setParameter(10,id);
        q.executeUpdate();
    }
    
}
