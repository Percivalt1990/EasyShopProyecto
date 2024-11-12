package com.mycompany.easyshopproyecto.dao;

import com.mycompany.easyshopproyecto.logica.Balance;
import java.sql.*;
import java.time.LocalDate;

public class BalanceDAO {
    private Connection connection;

    public BalanceDAO() {
        this.connection = ConexionDB.getConnection();
    }

    public Balance obtenerBalanceDiario() {
        String query = "SELECT " +
                       "(SELECT IFNULL(SUM(total), 0) FROM ventas WHERE DATE(fecha) = CURDATE()) AS total_ventas, " +
                       "(SELECT IFNULL(SUM(total), 0) FROM compras WHERE DATE(fecha) = CURDATE()) AS total_compras, " +
                       "(SELECT IFNULL(SUM(total), 0) FROM ventas WHERE DATE(fecha) = CURDATE()) - " +
                       "(SELECT IFNULL(SUM(total), 0) FROM compras WHERE DATE(fecha) = CURDATE()) AS balance";

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Balance(
                    rs.getLong("total_ventas"),
                    rs.getLong("total_compras"),
                    rs.getLong("balance")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
