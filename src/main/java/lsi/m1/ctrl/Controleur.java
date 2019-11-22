
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsi.m1.ctrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import lsi.m1.model.Employe;
import lsi.m1.model.Utilisateur;
import static lsi.m1.utils.Constantes.*;

/**
 *
 * @author nitsu
 */
@WebServlet(name="Controleur", urlPatterns = "/Controleur")
public class Controleur extends HttpServlet {
    
//    @EJB
//    private UtilisateurSB utilisateurSB;
//    @EJB
//    private EmployeSB employeSB;
    Utilisateur userInput;
    HttpSession session;
    
    private Client client;
    private WebTarget employesTarget;
    private WebTarget utilisateursTarget;
    
    public Controleur(){
        this.client = ClientBuilder.newClient();
        this.employesTarget = client.target("http://localhost:8080/Projet_JEE_V2/webresources/lsi.m1.model.employe");
        this.utilisateursTarget = client.target("http://localhost:8080/Projet_JEE_V2/webresources/lsi.m1.model.utilisateur");
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        session = request.getSession();
        
        if (session.getAttribute("loggedInUser")!= null){            
            if (getAllEmployesRequest().isEmpty())
                request.setAttribute("emptyErrKey", MSG_VIDE_EMPLOYE);
            switch(request.getParameter("action")){
                case "deconnect" :
                    disconnect(request, response);
                    break;
                case "delete"  :  
                    delete(request, response);
                    break;
                case "details" :
                    details(request, response);
                    break;
                case "add" :
                    request.getRequestDispatcher(JSP_DETAILS_EMP).forward(request, response);                  
                    break;         
                case "valider":
                    valider(request,response);
                    break;
                case "modify" :                    
                    modifier(request,response);
                    break;
                case "seeList" :
                    request.setAttribute("listeEmplKey", getAllEmployesRequest());
                    request.getRequestDispatcher(JSP_LISTE_EMP).forward(request, response);
                    break;
            }
        }
        else if(request.getParameter("loginForm")==null){            
            request.getRequestDispatcher(JSP_LOGIN).forward(request,response);
        }
        else{
            checkCredentialsAndLogin(request,response);

        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void disconnect(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        while(session.getAttributeNames().hasMoreElements()){
            session.removeAttribute(session.getAttributeNames().nextElement());
        }
        request.getRequestDispatcher(JSP_LOGIN).forward(request,response);
    }
    
    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        if (getAllEmployesRequest().isEmpty())
            request.setAttribute("emptyErrKey", MSG_VIDE_EMPLOYE);
        else if(request.getParameter("sel")!=null){
            deleteEmploye(new Integer(request.getParameter("sel")));
            request.setAttribute("OkKey", MSG_SUPPRESSION_OK);
        }
        else
            request.setAttribute("errKey", ERR_SELECTION_DELETE_KO);
        showUpdatedEmployeeDetailsPage(request,response);
    }
    
    private void details(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        if(request.getParameter("sel")!=null){                        
            request.setAttribute("listeEmplKey", getAllEmployesRequest());
            session.setAttribute("selectionnedIdEmplForDetails",new Integer(request.getParameter("sel")));
            request.setAttribute("singleUser", getSingleEmploye(new Integer(request.getParameter("sel"))));
            request.getRequestDispatcher(JSP_DETAILS_EMP).forward(request, response);
        }
        else{
            request.setAttribute("errKey", ERR_SELECTION_DETAILS_KO);
            showUpdatedEmployeeDetailsPage(request,response);
        }   
    }
    
    private void valider(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        if (request.getParameter("nom").isEmpty() || request.getParameter("prenom").isEmpty()){
            request.setAttribute("errKey", ERR_ADD_KO);
            request.getRequestDispatcher(JSP_DETAILS_EMP).forward(request, response);
        }                        
        addEmploye(request.getParameter("nom"),request.getParameter("prenom"),
                  request.getParameter("tel_dom"),request.getParameter("tel_mob"),
                  request.getParameter("tel_pro"),request.getParameter("adresse") ,
                  request.getParameter("cod$ePostal") ,request.getParameter("ville") ,
                  request.getParameter("email"));
        request.removeAttribute("emptyErrKey");
        showUpdatedEmployeeDetailsPage(request,response);
    }
    
    private void modifier(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        if (request.getParameter("nom").isEmpty() || request.getParameter("prenom").isEmpty()){
            request.setAttribute("errKey", ERR_ADD_KO);
            request.getRequestDispatcher(JSP_DETAILS_EMP).forward(request, response);
            return;
        }    
        modifyEmploye((int) session.getAttribute("selectionnedIdEmplForDetails"),request.getParameter("nom"),request.getParameter("prenom"),
                  request.getParameter("tel_dom"),request.getParameter("tel_mob"),
                  request.getParameter("tel_pro"),request.getParameter("adresse") ,
                  request.getParameter("codePostal") ,request.getParameter("ville") ,
                  request.getParameter("email"));
        request.setAttribute("singleUser", getSingleEmploye((int) session.getAttribute("selectionnedIdEmplForDetails")));
        request.getRequestDispatcher(JSP_DETAILS_EMP).forward(request, response);
    }

    private void checkCredentialsAndLogin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        userInput = new Utilisateur();
        userInput.setLogin(request.getParameter(FRM_LOGIN));
        userInput.setPassword(request.getParameter(FRM_MDP));

        request.setAttribute("userBean", userInput);

        if (userInput.getLogin().isEmpty() || userInput.getPassword().isEmpty()){
            request.setAttribute("errKey", ERR_INCOMPLETE_KO);
            request.getRequestDispatcher(JSP_LOGIN).forward(request, response);
        }

        else if (verifInfosConnexion(userInput)) {
           if(userInput.getLogin().equals("admin"))
               session.setAttribute("isAdmin", true);
            session.setAttribute("isEmploye", true);
            request.getSession().setAttribute("loggedInUser", userInput);
            request.setAttribute("listeEmplKey", getAllEmployesRequest());                
            if (getAllEmployesRequest().isEmpty())
                request.setAttribute("emptyErrKey", MSG_VIDE_EMPLOYE);
            showUpdatedEmployeeDetailsPage(request,response);
        } else {
            request.setAttribute("errKey", ERR_INCORRECT_KO);
            request.getRequestDispatcher(JSP_LOGIN).forward(request, response);
        }
    }
    
    private void showUpdatedEmployeeDetailsPage(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        request.setAttribute("listeEmplKey", getAllEmployesRequest());
        request.getRequestDispatcher(JSP_LISTE_EMP).forward(request, response);        
    }
    
    private List<Employe> getAllEmployesRequest(){
        List<Employe> listEmployes = employesTarget.request().get(new GenericType<List<Employe>>() {});
	return listEmployes;
    }
    
     private Employe getSingleEmploye(int id){
        return (Employe) employesTarget.path(Integer.toString(id)).request().get().getEntity();
    }
    
    private List<Utilisateur> getUtilisateurs(){
        List<Utilisateur> listUtilisateurs = new ArrayList<>();
        Invocation.Builder myBuilder = utilisateursTarget.request();
        listUtilisateurs = myBuilder.get(new GenericType<List<Utilisateur>>() {});
        return listUtilisateurs;
    }
    
    private boolean verifInfosConnexion(Utilisateur userInput) {
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
    
    private void modifyEmploye(int id, String nom,String prenom,String teldom,String telport,
            String telpro,String adresse,String codepostal,String ville,String email) {
        
        Employe e = new Employe();
        e.setId(id);
        e.setNom(nom);
        e.setPrenom(prenom);
        e.setTeldom(teldom);
        e.setTelport(telport);
        e.setTelpro(telpro);
        e.setAdresse(adresse);
        e.setCodepostal(codepostal);
        e.setVille(ville);
        e.setEmail(email);
        employesTarget.request(MediaType.APPLICATION_XML).put(Entity.entity(e, MediaType.APPLICATION_XML));
    }
    
        private void addEmploye(String nom,String prenom,String teldom,String telport,
            String telpro,String adresse,String codepostal,String ville,String email) {
        
        Employe e = new Employe();
        e.setNom(nom);
        e.setPrenom(prenom);
        e.setTeldom(teldom);
        e.setTelport(telport);
        e.setTelpro(telpro);
        e.setAdresse(adresse);
        e.setCodepostal(codepostal);
        e.setVille(ville);
        e.setEmail(email);
        employesTarget.request(MediaType.APPLICATION_XML).post(Entity.entity(e, MediaType.APPLICATION_XML));
    }
    
        
    private void deleteEmploye(int id){
        employesTarget.path(Integer.toString(id)).request().delete();
    }
    
}
