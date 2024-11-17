package com.mycompany.easyshopproyecto.servlets;

import com.mycompany.easyshopproyecto.dao.BalanceDAO;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class BalanceServlet extends HttpServlet {
    private BalanceDAO balanceDAO;

    @Override
    public void init() {
        balanceDAO = new BalanceDAO();
        System.out.println("BalanceServlet inicializado correctamente.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Evitar el cache en el navegador para tener valores veridicos
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setDateHeader("Expires", 0); // Proxies

        
        double totalIngresos = balanceDAO.obtenerTotalIngresos();
        double totalEgresos = balanceDAO.obtenerTotalEgresos();
        double balance = totalIngresos - totalEgresos;

        // Logs 
        System.out.println("Total Ingresos: " + totalIngresos);
        System.out.println("Total Egresos: " + totalEgresos);
        System.out.println("Balance: " + balance);

       
        request.setAttribute("totalIngresos", totalIngresos);
        request.setAttribute("totalEgresos", totalEgresos);
        request.setAttribute("balance", balance);

        
        request.getRequestDispatcher("balance.jsp").forward(request, response);
    }
}
