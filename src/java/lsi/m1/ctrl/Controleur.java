
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
            switch(request.getParameter("action")){
                case "delete"  :
                    if(request.getParameter("sel")!=null){
                        actionsBD.deleteEmploye(new Integer(request.getParameter("sel")));
                        request.setAttribute("listeEmplKey", actionsBD.getEmployes());
                        request.getRequestDispatcher(JSP_LISTE_EMP).forward(request, response);
                    }                    
                case "details" :
                    if(request.getParameter("sel")!=null){                        
                        request.setAttribute("listeEmplKey", actionsBD.getEmployes());
                        session.setAttribute("selectionnedIdEmplForDetails",new Integer(request.getParameter("sel")));
                        request.setAttribute("singleUser", actionsBD.getSingleEmploye(new Integer(request.getParameter("sel"))));
                        request.getRequestDispatcher(JSP_DETAILS_EMP).forward(request, response);
                    }
                    else{
                        request.setAttribute("listeEmplKey", actionsBD.getEmployes());
                        request.getRequestDispatcher(JSP_LISTE_EMP).forward(request, response);
                    }
                    break;
                case "add" :
                    request.getRequestDispatcher(JSP_DETAILS_EMP).forward(request, response);                  
                    break;         
                case "valider":
                    actionsBD.addEmploye(request.getParameter("nom"),request.getParameter("prenom"),
                              request.getParameter("tel_dom"),request.getParameter("tel_mob"),
                              request.getParameter("tel_pro"),request.getParameter("adresse") ,
                              request.getParameter("codePostal") ,request.getParameter("ville") ,
                              request.getParameter("email"));
                    request.setAttribute("listeEmplKey", actionsBD.getEmployes());
                    request.getRequestDispatcher(JSP_LISTE_EMP).forward(request, response); 
                case "modify" :
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

            if (actionsBD.verifInfosConnexion(userInput)) {
                request.getSession().setAttribute("loggedInUser", userInput);
                request.setAttribute("listeEmplKey", actionsBD.getEmployes());
                request.getRequestDispatcher(JSP_LISTE_EMP).forward(request, response);
            } else {
                request.setAttribute("errKey", ERR_CONNEXION_KO);
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
