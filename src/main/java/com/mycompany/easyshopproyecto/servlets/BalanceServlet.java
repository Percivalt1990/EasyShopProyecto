package com.mycompany.easyshopproyecto.servlets;

import com.mycompany.easyshopproyecto.dao.BalanceDAO;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class BalanceServlet extends HttpServlet {
    private BalanceDAO balanceDAO;

    // Constructor predeterminado
    public BalanceServlet() {
        this.balanceDAO = new BalanceDAO();
    }

    // Constructor para pruebas
    public BalanceServlet(BalanceDAO balanceDAO) {
        this.balanceDAO = balanceDAO;
    }

    @Override
    public void init() {
        if (this.balanceDAO == null) {
            this.balanceDAO = new BalanceDAO();
        }
        System.out.println("BalanceServlet inicializado correctamente.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Evitar el cache en el navegador
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        double totalIngresos = balanceDAO.obtenerTotalIngresos();
        double totalEgresos = balanceDAO.obtenerTotalEgresos();
        double balance = totalIngresos - totalEgresos;

        request.setAttribute("totalIngresos", totalIngresos);
        request.setAttribute("totalEgresos", totalEgresos);
        request.setAttribute("balance", balance);

        request.getRequestDispatcher("balance.jsp").forward(request, response);
    }
    public String getForwardedUrl(HttpServletRequest request) {
        return (String) request.getAttribute("javax.servlet.forward.servlet_path");
    }

}
