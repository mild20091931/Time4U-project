/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import controller.AccountJpaController;
import controller.RegisterJpaController;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.RollbackFailureException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import model.Account;
import model.Register;

/**
 *
 * @author Mild-TN
 */
public class ActivateKeyServlet extends HttpServlet {

  @PersistenceUnit(unitName = "ProjectTimeto4UPU")
  EntityManagerFactory emf;
  @Resource
  UserTransaction utx;

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    HttpSession session = request.getSession();
    String email = (String) session.getAttribute("emailRe");
    String activateKey = request.getParameter("activatekey");
    boolean isActivated = false;
    if (email != null && activateKey != null && activateKey.length() > 0) {
      RegisterJpaController regJpaCtrl = new RegisterJpaController(utx, emf);
      AccountJpaController accountJpaCtrl = new AccountJpaController(utx, emf);
      Register register = regJpaCtrl.findByEmail(email);
      if (register != null) {
        if (activateKey.equals(register.getActivatekey())) {
          register.setActivatedate(new Date());
          Account account = new Account(email, register.getPassword(), register);
          try {
            regJpaCtrl.edit(register);
            accountJpaCtrl.create(account);
            isActivated = true;
            request.setAttribute("isActivated", isActivated);
            getServletContext().getRequestDispatcher("/HomePage.jsp").forward(request, response);
          } catch (NonexistentEntityException ex) {
            Logger.getLogger(ActivateKeyServlet.class.getName()).log(Level.SEVERE, null, ex);
          } catch (RollbackFailureException ex) {
            Logger.getLogger(ActivateKeyServlet.class.getName()).log(Level.SEVERE, null, ex);
          } catch (Exception ex) {
            Logger.getLogger(ActivateKeyServlet.class.getName()).log(Level.SEVERE, null, ex);
          }
        } else {
          //Alert BY JS
          request.setAttribute("messageActivate", "Wrong!!!!! Try Again");
          response.sendRedirect("ActivateAccount.jsp");
        }
      } else {
        getServletContext().getRequestDispatcher("/index.html").forward(request, response);
      }
    } else {
      getServletContext().getRequestDispatcher("/index.html").forward(request, response);
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
