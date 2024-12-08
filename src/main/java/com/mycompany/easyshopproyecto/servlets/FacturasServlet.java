package com.mycompany.easyshopproyecto.servlets;

import com.mycompany.easyshopproyecto.dao.FacturasDAO;
import com.mycompany.easyshopproyecto.logica.Facturas;
import com.mycompany.easyshopproyecto.dao.ConexionDB;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FacturasServlet extends HttpServlet {
    protected FacturasDAO facturasDAO;

    @Override
    public void init() throws ServletException {
        Connection connection = ConexionDB.getConnection();
        facturasDAO = new FacturasDAO(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String clienteIdParam = request.getParameter("clienteId");
        String usuarioIdParam = request.getParameter("usuarioId");
        String formaPago = request.getParameter("formaPago");
        String fechaParam = request.getParameter("fecha");
        String totalParam = request.getParameter("total");

        Integer id = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : null;
        Integer clienteId = (clienteIdParam != null && !clienteIdParam.isEmpty()) ? Integer.parseInt(clienteIdParam) : null;
        Integer usuarioId = (usuarioIdParam != null && !usuarioIdParam.isEmpty()) ? Integer.parseInt(usuarioIdParam) : null;
        LocalDate fecha = (fechaParam != null && !fechaParam.isEmpty())
            ? LocalDate.parse(fechaParam, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            : null;
        Long total = (totalParam != null && !totalParam.isEmpty()) ? Long.parseLong(totalParam) : null;

        List<Facturas> facturas;
        if (id != null || clienteId != null || usuarioId != null || formaPago != null || fecha != null || total != null) {
            facturas = facturasDAO.buscarFacturas(id, clienteId, usuarioId, formaPago, fecha, total);
        } else {
            facturas = facturasDAO.listarFacturas();
        }

        request.setAttribute("facturas", facturas);
        request.getRequestDispatcher("/facturas.jsp").forward(request, response);
    }


}
