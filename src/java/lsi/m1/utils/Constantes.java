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

    public static final String URL = "jdbc:derby://localhost:1527/JEEPRJ";
    public static final String USER_BDD = "jee";
    public static final String MDP_BDD = "jee";
    public static final String REQ_TOUS_UTILISATEURS = "SELECT * from UTILISATEUR";
    public static final String REQ_TOUS_EMPLOYES = "SELECT * from EMPLOYES";
    public static final String ERR_CONNEXION_KO = "Infos de connexion non valides. Réessayez svp.";
    public static final String FRM_LOGIN = "loginForm";
    public static final String FRM_MDP = "mdpForm";
    public static final String JSP_LOGIN = "WEB-INF/login.jsp";
    public static final String JSP_LISTE_EMP = "WEB-INF/listeEmployes.jsp";
    public static final String JSP_DETAILS_EMP = "WEB-INF/memberDetails.jsp";

}
