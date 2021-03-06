/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import controller.AccountJpaController;
import controller.CustomerJpaController;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.RollbackFailureException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
import model.Customer;

/**
 *
 * @author Mild-TN
 */
public class EditProfileServlet extends HttpServlet {

  @PersistenceUnit(unitName = "ProjectTimeto4UPU")
  EntityManagerFactory emf;
  @Resource
  UserTransaction utx;

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    HttpSession session = request.getSession();
    String fname = request.getParameter("fname");
    String lname = request.getParameter("lname");
    String email = request.getParameter("email");
    String tel = request.getParameter("tel");
    String sex = request.getParameter("gender");
    String address = request.getParameter("address");
    String city = request.getParameter("city");
    String province = request.getParameter("province");
    String country = request.getParameter("country");
    String postCode = request.getParameter("postCode");
    CustomerJpaController customerJpaController = new CustomerJpaController(utx, emf);
    Customer customer = (Customer) session.getAttribute("customer");
    AccountJpaController accountJpaController = new AccountJpaController(utx, emf);
    Account accountSession = (Account) session.getAttribute("account");
    if (accountSession != null) {
      Account account = accountJpaController.findByEmail(accountSession.getEmail());
      if (account != null) {
        Customer customer1 = customerJpaController.findCustomer(account.getAccountId());
        if (customer1 != null) {
          try {
            customer1.editCustomer(fname,lname, tel, address, city, province, postCode, country);
            session.setAttribute("customer", customer);
            customerJpaController.edit(customer1);
            System.out.println(fname + "-" + lname + "-" + email + "-" + tel + "-" + sex + "-" + address + "-" + city + "-" + province + "-" + country + "-" + postCode + "-" + accountSession);
            System.out.println("edited");
            response.sendRedirect("Profile");
            return;
          } catch (NonexistentEntityException ex) {
            Logger.getLogger(EditProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
          } catch (RollbackFailureException ex) {
            Logger.getLogger(EditProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
          } catch (Exception ex) {
            Logger.getLogger(EditProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
      } else {
        response.sendRedirect("/Profile.jsp");
        return;
      }
    } else {
      getServletContext().getRequestDispatcher("/Login.jsp").forward(request, response);
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
