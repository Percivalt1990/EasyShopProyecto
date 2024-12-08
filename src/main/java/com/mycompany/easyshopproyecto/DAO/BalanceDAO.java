 package com.mycompany.easyshopproyecto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BalanceDAO {
    private Connection connection;

    public BalanceDAO() {
        try {
            this.connection = ConexionDB.getConnection();
            if (this.connection == null) {
                System.out.println("Error: Conexión a la base de datos no establecida.");
            } else {
                System.out.println("Conexión a la base de datos establecida correctamente.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Metodo para obtener total ingresos
    public double obtenerTotalIngresos() {
        String query = "SELECT COALESCE(SUM(monto), 0) AS total_ingresos FROM ventas WHERE DATE(fecha) = CURRENT_DATE";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("total_ingresos");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener ingresos: " + e.getMessage());
        }
        return 0.0;
    }
    // Metodo para obtener total egresos
    public double obtenerTotalEgresos() {
        String query = "SELECT COALESCE(SUM(monto), 0) AS total_egresos FROM compras WHERE DATE(fecha) = CURRENT_DATE";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("total_egresos");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener egresos: " + e.getMessage());
        }
        return 0.0;
    }
}
