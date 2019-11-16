
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsi.m1.ctrl;

import java.io.IOException;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lsi.m1.model.EmployeSB;
import lsi.m1.model.Utilisateur;
import lsi.m1.model.UtilisateurSB;
import static lsi.m1.utils.Constantes.*;

/**
 *
 * @author nitsu
 */
public class Controleur extends HttpServlet {
    
    @EJB
    private UtilisateurSB utilisateurSB;
    @EJB
    private EmployeSB employeSB;
    Utilisateur userInput;
    HttpSession session;

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
            if (employeSB.getEmployes().isEmpty())
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
                    request.setAttribute("listeEmplKey", employeSB.getEmployes());
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
        if (employeSB.getEmployes().isEmpty())
            request.setAttribute("emptyErrKey", MSG_VIDE_EMPLOYE);
        else if(request.getParameter("sel")!=null){
            employeSB.deleteEmploye(new Integer(request.getParameter("sel")));
            request.setAttribute("OkKey", MSG_SUPPRESSION_OK);
        }
        else
            request.setAttribute("errKey", ERR_SELECTION_DELETE_KO);
        showUpdatedEmployeeDetailsPage(request,response);
    }
    
    private void details(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        if(request.getParameter("sel")!=null){                        
            request.setAttribute("listeEmplKey", employeSB.getEmployes());
            session.setAttribute("selectionnedIdEmplForDetails",new Integer(request.getParameter("sel")));
            request.setAttribute("singleUser", employeSB.getSingleEmploye(new Integer(request.getParameter("sel"))));
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
        employeSB.addEmploye(request.getParameter("nom"),request.getParameter("prenom"),
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
        employeSB.modifyEmploye((int) session.getAttribute("selectionnedIdEmplForDetails"),request.getParameter("nom"),request.getParameter("prenom"),
                  request.getParameter("tel_dom"),request.getParameter("tel_mob"),
                  request.getParameter("tel_pro"),request.getParameter("adresse") ,
                  request.getParameter("codePostal") ,request.getParameter("ville") ,
                  request.getParameter("email"));
        request.setAttribute("singleUser", employeSB.getSingleEmploye((int) session.getAttribute("selectionnedIdEmplForDetails")));
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

        else if (utilisateurSB.verifInfosConnexion(userInput)) {
           if(userInput.getLogin().equals("admin"))
               session.setAttribute("isAdmin", true);
            session.setAttribute("isEmploye", true);
            request.getSession().setAttribute("loggedInUser", userInput);
            request.setAttribute("listeEmplKey", employeSB.getEmployes());                
            if (employeSB.getEmployes().isEmpty())
                request.setAttribute("emptyErrKey", MSG_VIDE_EMPLOYE);
            showUpdatedEmployeeDetailsPage(request,response);
        } else {
            request.setAttribute("errKey", ERR_INCORRECT_KO);
            request.getRequestDispatcher(JSP_LOGIN).forward(request, response);
        }
    }
    
    private void showUpdatedEmployeeDetailsPage(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        request.setAttribute("listeEmplKey", employeSB.getEmployes());
        request.getRequestDispatcher(JSP_LISTE_EMP).forward(request, response);        
    }
    
    
}
