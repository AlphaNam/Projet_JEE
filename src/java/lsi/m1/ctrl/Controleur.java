
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsi.m1.ctrl;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lsi.m1.model.ActionsBD;
import lsi.m1.model.Utilisateur;
import static lsi.m1.utils.Constantes.*;
import org.apache.derby.impl.sql.compile.SQLParserConstants;

/**
 *
 * @author nitsu
 */
public class Controleur extends HttpServlet {

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
        ActionsBD actionsBD = new ActionsBD();
        session = request.getSession();
        
        if (session.getAttribute("loggedInUser")!= null){            
            if (actionsBD.getEmployes().isEmpty())
                request.setAttribute("emptyErrKey", MSG_VIDE_EMPLOYE);
            switch(request.getParameter("action")){
                case "deconnect" :
                    while(session.getAttributeNames().hasMoreElements()){
                        session.removeAttribute(session.getAttributeNames().nextElement());
                    }
                    request.getRequestDispatcher(JSP_LOGIN).forward(request,response);  
                    break;
                case "delete"  :
                    if (actionsBD.getEmployes().isEmpty())
                        request.setAttribute("emptyErrKey", MSG_VIDE_EMPLOYE);
                    else if(request.getParameter("sel")!=null){
                        actionsBD.deleteEmploye(new Integer(request.getParameter("sel")));
                        request.setAttribute("OkKey", MSG_SUPPRESSION_OK);
                    }
                    else
                        request.setAttribute("errKey", ERR_SELECTION_KO);
                    request.setAttribute("listeEmplKey", actionsBD.getEmployes());
                    request.getRequestDispatcher(JSP_LISTE_EMP).forward(request, response);   
                    break;
                case "details" :
                    if(request.getParameter("sel")!=null){                        
                        request.setAttribute("listeEmplKey", actionsBD.getEmployes());
                        session.setAttribute("selectionnedIdEmplForDetails",new Integer(request.getParameter("sel")));
                        request.setAttribute("singleUser", actionsBD.getSingleEmploye(new Integer(request.getParameter("sel"))));
                        request.getRequestDispatcher(JSP_DETAILS_EMP).forward(request, response);
                        break;
                    }
                    else{
                        request.setAttribute("errKey", ERR_SELECTION_KO);
                        request.setAttribute("listeEmplKey", actionsBD.getEmployes());
                        request.getRequestDispatcher(JSP_LISTE_EMP).forward(request, response);
                    }                    
                    break;
                case "add" :
                    request.getRequestDispatcher(JSP_DETAILS_EMP).forward(request, response);                  
                    break;         
                case "valider":
                    if (request.getParameter("nom").isEmpty() || request.getParameter("prenom").isEmpty()){
                        request.setAttribute("errKey", ERR_ADD_KO);
                        request.getRequestDispatcher(JSP_DETAILS_EMP).forward(request, response);
                        break;
                    }                        
                    actionsBD.addEmploye(request.getParameter("nom"),request.getParameter("prenom"),
                              request.getParameter("tel_dom"),request.getParameter("tel_mob"),
                              request.getParameter("tel_pro"),request.getParameter("adresse") ,
                              request.getParameter("codePostal") ,request.getParameter("ville") ,
                              request.getParameter("email"));
                    request.removeAttribute("emptyErrKey");
                    request.setAttribute("listeEmplKey", actionsBD.getEmployes());
                    request.getRequestDispatcher(JSP_LISTE_EMP).forward(request, response);
                    break;
                case "modify" :                    
                    if (request.getParameter("nom").isEmpty() || request.getParameter("prenom").isEmpty()){
                        request.setAttribute("errKey", ERR_ADD_KO);
                        request.getRequestDispatcher(JSP_DETAILS_EMP).forward(request, response);
                        break;
                    }    
                    actionsBD.modifyEmploye((int) session.getAttribute("selectionnedIdEmplForDetails"),request.getParameter("nom"),request.getParameter("prenom"),
                              request.getParameter("tel_dom"),request.getParameter("tel_mob"),
                              request.getParameter("tel_pro"),request.getParameter("adresse") ,
                              request.getParameter("codePostal") ,request.getParameter("ville") ,
                              request.getParameter("email"));
                    request.setAttribute("singleUser", actionsBD.getSingleEmploye((int) session.getAttribute("selectionnedIdEmplForDetails")));
                    request.getRequestDispatcher(JSP_DETAILS_EMP).forward(request, response);
                    break;
                case "seeList" :
                    request.setAttribute("listeEmplKey", actionsBD.getEmployes());
                    request.getRequestDispatcher(JSP_LISTE_EMP).forward(request, response);
                    break;
            }
        }
        else if(request.getParameter("loginForm")==null){            
            request.getRequestDispatcher(JSP_LOGIN).forward(request,response);
        }
        else{
            //session = request.getSession();
            userInput = new Utilisateur();

            userInput.setLogin(request.getParameter(FRM_LOGIN));
            userInput.setMdp(request.getParameter(FRM_MDP));

            request.setAttribute("userBean", userInput);

            if (userInput.getLogin().isEmpty() || userInput.getMdp().isEmpty()){
                request.setAttribute("errKey", ERR_INCOMPLETE_KO);
                request.getRequestDispatcher(JSP_LOGIN).forward(request, response);
            }
                    
            else if (actionsBD.verifInfosConnexion(userInput)) {
               if(userInput.getLogin().equals("admin"))
                   session.setAttribute("isAdmin", true);
                session.setAttribute("isEmploye", true);
                request.getSession().setAttribute("loggedInUser", userInput);
                request.setAttribute("listeEmplKey", actionsBD.getEmployes());                
                if (actionsBD.getEmployes().isEmpty())
                    request.setAttribute("emptyErrKey", MSG_VIDE_EMPLOYE);
                request.getRequestDispatcher(JSP_LISTE_EMP).forward(request, response);
            } else {
                request.setAttribute("errKey", ERR_INCORRECT_KO);
                request.getRequestDispatcher(JSP_LOGIN).forward(request, response);
            }

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

}
