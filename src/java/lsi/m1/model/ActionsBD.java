package lsi.m1.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import static lsi.m1.utils.Constantes.*;

/**
 *
 * @author JAA
 */
public class ActionsBD {

    Connection conn;
    Statement stmt;
    ResultSet rs;
    Utilisateur user;
    ArrayList<Utilisateur> listeUsers;
    ArrayList<EmployeBean> listeEmployes;


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
            rs = getResultSet(REQ_TOUS);

            while (rs.next()) {
                Utilisateur userBean = new Utilisateur();
                userBean.setLogin(rs.getString("LOGIN"));
                userBean.setMdp(rs.getString("PASSWORD"));

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
                EmployeBean emplBean = new EmployeBean();
                emplBean.setNom(rs.getString("NOM"));
                emplBean.setPrenom(rs.getString("PRENOM"));                
                emplBean.setTelDom(rs.getString("TELDOM"));
                emplBean.setTelPort(rs.getString("TELPORT"));
                emplBean.setTelPro(rs.getString("TELPRO"));
                emplBean.setAdresse(rs.getString("ADRESSE"));
                emplBean.setCodePostal(rs.getString("CODEPOSTAL"));
                emplBean.setVille(rs.getString("VILLE"));
                emplBean.setEmail(rs.getString("EMAIL"));
                

                listeEmployes.add(emplBean);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listeEmployes;
    }

    public boolean verifInfosConnexion(Utilisateur userInput) {
        boolean test = false;

        listeUsers = getUtilisateurs();

        for (Utilisateur userBase : listeUsers) {
            if (userBase.getLogin().equals(userInput.getLogin())
                    && userBase.getMdp().equals(userInput.getMdp())) {
                test = true;
            }
        }

        return test;
    }

}
