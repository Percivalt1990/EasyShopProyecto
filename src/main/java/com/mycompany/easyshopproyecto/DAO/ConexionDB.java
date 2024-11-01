package com.mycompany.easyshopproyecto.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/easyshop?useSSL=false&allowPublicKeyRetrieval=true&autoReconnect=true";
    private static final String USER = "root"; // Cambia a tu usuario si es diferente
    private static final String PASSWORD = ""; // Cambia a tu contraseña si la tienes configurada

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Registrar el driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer conexión con la base de datos
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: el driver de MySQL no fue encontrado.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error de conexión a la base de datos.");
            e.printStackTrace();
        }
        return connection;
    }
}
