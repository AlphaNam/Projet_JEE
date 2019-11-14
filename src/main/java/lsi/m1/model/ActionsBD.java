package lsi.m1.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static lsi.m1.utils.Constantes.*;

/**
 *
 * @author JAA
 */
public class ActionsBD {

    Connection conn;
    Statement stmt;
    PreparedStatement preparedStmt;
    ResultSet rs;
    Utilisateur user;
    ArrayList<Utilisateur> listeUsers;
    ArrayList<Employe> listeEmployes;


    public ActionsBD() {
        try {
            conn = DriverManager.getConnection(URL, USER_BDD, MDP_BDD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public Statement getStatement() {
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return stmt;
    }
    
        public PreparedStatement getPreparedStatement(String req) {
        try {
            preparedStmt = conn.prepareStatement(req);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return preparedStmt;
    }

    public ResultSet getResultSet(String req) {
        try {
            stmt = getStatement();

            rs = stmt.executeQuery(req);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }

    public ArrayList getUtilisateurs() {
        listeUsers = new ArrayList<>();
        try {
            rs = getResultSet(REQ_TOUS_UTILISATEURS);

            while (rs.next()) {
                Utilisateur userBean = new Utilisateur();
                userBean.setLogin(rs.getString("LOGIN"));
                userBean.setPassword(rs.getString("PASSWORD"));

                listeUsers.add(userBean);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listeUsers;
    }
    
    
    public ArrayList getEmployes() {
        listeEmployes = new ArrayList<>();
        try {
            rs = getResultSet(REQ_TOUS_EMPLOYES);

            while (rs.next()) {
                Employe emplBean = new Employe();
                emplBean.setId(rs.getInt("ID"));
                emplBean.setNom(rs.getString("NOM"));
                emplBean.setPrenom(rs.getString("PRENOM"));                
                emplBean.setTeldom(rs.getString("TELDOM"));
                emplBean.setTelport(rs.getString("TELPORT"));
                emplBean.setTelpro(rs.getString("TELPRO"));
                emplBean.setAdresse(rs.getString("ADRESSE"));
                emplBean.setCodepostal(rs.getString("CODEPOSTAL"));
                emplBean.setVille(rs.getString("VILLE"));
                emplBean.setEmail(rs.getString("EMAIL"));     
                listeEmployes.add(emplBean);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listeEmployes;
    }
    
    public Employe getSingleEmploye(int id) {
        listeEmployes = getEmployes();
        for(Employe emp : listeEmployes){
            if (emp.getId() == id){
                return emp;
            }
        }
        return null;        
    }
    
    public void deleteEmploye(int id) {
        try {
            preparedStmt = getPreparedStatement(REQ_DELETE_EMPLOYE);
            preparedStmt.setInt(1, id);          
            preparedStmt.executeUpdate();
            listeEmployes = getEmployes();
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public void addEmploye(String nom,String prenom,String teldom,String telport,String telpro,String adresse,String codepostal,String ville,String email) {
        try {
            preparedStmt = getPreparedStatement(REQ_ADD_EMPLOYE);
            preparedStmt.setString(1, nom);
            preparedStmt.setString(2, prenom);
            preparedStmt.setString(3, teldom);
            preparedStmt.setString(4, telport);
            preparedStmt.setString(5, telpro);
            preparedStmt.setString(6, adresse);
            preparedStmt.setString(7, codepostal);
            preparedStmt.setString(8, ville);
            preparedStmt.setString(9, email);            
            preparedStmt.executeUpdate();
            listeEmployes = getEmployes();
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public void modifyEmploye(int id, String nom,String prenom,String teldom,String telport,String telpro,String adresse,String codepostal,String ville,String email) {
        try {
            preparedStmt = getPreparedStatement(REQ_MODIFY_EMPLOYE);
            preparedStmt.setString(1, nom);
            preparedStmt.setString(2, prenom);
            preparedStmt.setString(3, teldom);
            preparedStmt.setString(4, telport);
            preparedStmt.setString(5, telpro);
            preparedStmt.setString(6, adresse);
            preparedStmt.setString(7, codepostal);
            preparedStmt.setString(8, ville);
            preparedStmt.setString(9, email);
            preparedStmt.setInt(10, id);            
            preparedStmt.executeUpdate();
            listeEmployes = getEmployes();
        } catch (SQLException ex) {
            Logger.getLogger(ActionsBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean verifInfosConnexion(Utilisateur userInput) {
        boolean test = false;

        listeUsers = getUtilisateurs();

        for (Utilisateur userBase : listeUsers) {
            if (userBase.getLogin().equals(userInput.getLogin())
                    && userBase.getPassword().equals(userInput.getPassword())) {
                test = true;
            }
        }

        return test;
    }

}
