package com.mycompany.easyshopproyecto.servlets;

import com.mycompany.easyshopproyecto.dao.BalanceDAO;
import com.mycompany.easyshopproyecto.logica.Balance;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


public class BalanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BalanceDAO balanceDAO;

    @Override
    public void init() throws ServletException {
        balanceDAO = new BalanceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Balance balance = balanceDAO.obtenerBalanceDiario();
        
        request.setAttribute("totalIngresos", balance.getTotalVentas());
        request.setAttribute("totalEgresos", balance.getTotalCompras());
        request.setAttribute("balance", balance.getBalance());

        request.getRequestDispatcher("/balance.jsp").forward(request, response);
    }
}
