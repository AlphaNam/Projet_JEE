/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsi.m1.utils;

/**
 *
 * @author nitsu
 */
public class Constantes {

    public static final String URL = "jdbc:derby://localhost:1527/jee_v2";
    public static final String USER_BDD = "jee";
    public static final String MDP_BDD = "jee";
    
    public static final String REQ_TOUS_UTILISATEURS = "SELECT * from UTILISATEUR";
    public static final String REQ_TOUS_EMPLOYES = "SELECT * from EMPLOYES";
    public static final String REQ_DELETE_EMPLOYE = "DELETE from EMPLOYES WHERE id = ?";
    public static final String REQ_ADD_EMPLOYE = "INSERT INTO EMPLOYES (nom,prenom,teldom,telport," +
                                                 "telpro,adresse,codepostal,ville,email)" +
                                                 " VALUES (?,?,?,?,?,?,?,?,?)";
    public static final String REQ_MODIFY_EMPLOYE = "UPDATE EMPLOYES SET nom = ?,prenom = ?,teldom = ?,telport = ?," +
                                                 "telpro = ?,adresse = ?,codepostal = ?,ville = ?,email = ? WHERE id = ?";
    
    public static final String ERR_SELECTION_DELETE_KO = "Veuillez sélectionner l'employé(e) à supprimer";
    public static final String ERR_SELECTION_DETAILS_KO = "Veuillez sélectionner l'employé(e) à sélectionner";
    public static final String ERR_INCOMPLETE_KO = "Vous devez renseigner les deux champs";
    public static final String ERR_INCORRECT_KO = "Echec de la connexion! Verifiez votre login et/ou mot de passe et essayez à nouveau";
    public static final String ERR_ADD_KO = "Echec de l'ajout";
    
    public static final String MSG_SUPPRESSION_OK = "Suppression réussie";
    public static final String MSG_VIDE_EMPLOYE = "Nous devons recruter !";
    
    
    public static final String FRM_LOGIN = "loginForm";
    public static final String FRM_MDP = "mdpForm";
    
    public static final String JSP_LOGIN = "WEB-INF/login.jsp";
    public static final String JSP_LISTE_EMP = "WEB-INF/listeEmployes.jsp";
    public static final String JSP_DETAILS_EMP = "WEB-INF/memberDetails.jsp";

}
