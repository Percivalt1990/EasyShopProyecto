package com.mycompany.easyshopproyecto.dao;

import com.mycompany.easyshopproyecto.logica.Facturas;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FacturasDAO {
    private Connection connection;

    public FacturasDAO(Connection connection) {
        this.connection = connection;
    }

    // Metodo para listar todas las facturas
    public List<Facturas> listarFacturas() {
        List<Facturas> facturas = new ArrayList<>();
        String query = "SELECT * FROM facturas";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Facturas factura = new Facturas();
                factura.setId(rs.getInt("id"));
                factura.setClienteId(rs.getInt("clienteId"));
                factura.setUsuarioId(rs.getInt("usuarioId"));
                factura.setFormaPago(rs.getString("formapago"));
                factura.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                factura.setTotal(rs.getLong("totalVenta"));
                factura.setPdfUrl(rs.getString("pdfUrl"));  // Aquí se incluye el pdfUrl
                facturas.add(factura);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facturas;
    }

    // Metodo de busqueda con filtros opcionales
    public List<Facturas> buscarFacturas(Integer id, Integer clienteId, Integer usuarioId, String formaPago, LocalDate fecha, Long totalVenta) {
        List<Facturas> facturas = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM facturas WHERE 1=1");

        if (id != null) query.append(" AND id = ?");
        if (clienteId != null) query.append(" AND clienteId = ?");
        if (usuarioId != null) query.append(" AND usuarioId = ?");
        if (formaPago != null && !formaPago.isEmpty()) query.append(" AND formapago = ?");
        if (fecha != null) query.append(" AND DATE(fecha) = ?");
        if (totalVenta != null) query.append(" AND totalVenta = ?");

        try (PreparedStatement ps = connection.prepareStatement(query.toString())) {
            int paramIndex = 1;
            if (id != null) ps.setInt(paramIndex++, id);
            if (clienteId != null) ps.setInt(paramIndex++, clienteId);
            if (usuarioId != null) ps.setInt(paramIndex++, usuarioId);
            if (formaPago != null && !formaPago.isEmpty()) ps.setString(paramIndex++, formaPago);
            if (fecha != null) ps.setDate(paramIndex++, Date.valueOf(fecha));
            if (totalVenta != null) ps.setLong(paramIndex++, totalVenta);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Facturas factura = new Facturas();
                    factura.setId(rs.getInt("id"));
                    factura.setClienteId(rs.getInt("clienteId"));
                    factura.setUsuarioId(rs.getInt("usuarioId"));
                    factura.setFormaPago(rs.getString("formapago"));
                    factura.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                    factura.setTotal(rs.getLong("totalVenta"));
                    factura.setPdfUrl(rs.getString("pdfUrl"));
                    facturas.add(factura);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facturas;
    }
}
