package com.mycompany.easyshopproyecto.dao;

import com.mycompany.easyshopproyecto.logica.Proveedores;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedoresDAO {
    private Connection connection;

    public ProveedoresDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para crear un nuevo proveedor
    public boolean crearProveedor(Proveedores proveedor) throws SQLException {
        String query = "INSERT INTO proveedores (nombre, numeroTelefonico, direccion, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, proveedor.getNombre());
            statement.setString(2, proveedor.getNumeroTelefonico());
            statement.setString(3, proveedor.getDireccion());
            statement.setString(4, proveedor.getEmail());
            return statement.executeUpdate() > 0;
        }
    }

    // Método para listar todos los proveedores
    public List<Proveedores> listarProveedores() throws SQLException {
        List<Proveedores> proveedores = new ArrayList<>();
        String query = "SELECT * FROM proveedores";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Proveedores proveedor = new Proveedores();
                proveedor.setId(resultSet.getInt("id"));
                proveedor.setNombre(resultSet.getString("nombre"));
                proveedor.setNumeroTelefonico(resultSet.getString("numeroTelefonico"));
                proveedor.setDireccion(resultSet.getString("direccion"));
                proveedor.setEmail(resultSet.getString("email"));
                proveedores.add(proveedor);
            }
        }
        return proveedores;
    }

    // Método para buscar proveedores
    public List<Proveedores> buscarProveedores(String search) throws SQLException {
        List<Proveedores> proveedores = new ArrayList<>();
        String query = "SELECT * FROM proveedores WHERE nombre LIKE ? OR email LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + search + "%");
            statement.setString(2, "%" + search + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Proveedores proveedor = new Proveedores();
                    proveedor.setId(resultSet.getInt("id"));
                    proveedor.setNombre(resultSet.getString("nombre"));
                    proveedor.setNumeroTelefonico(resultSet.getString("numeroTelefonico"));
                    proveedor.setDireccion(resultSet.getString("direccion"));
                    proveedor.setEmail(resultSet.getString("email"));
                    proveedores.add(proveedor);
                }
            }
        }
        return proveedores;
    }

    // Método para actualizar un proveedor
    public boolean actualizarProveedor(Proveedores proveedor) throws SQLException {
        String query = "UPDATE proveedores SET nombre = ?, numeroTelefonico = ?, direccion = ?, email = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, proveedor.getNombre());
            statement.setString(2, proveedor.getNumeroTelefonico());
            statement.setString(3, proveedor.getDireccion());
            statement.setString(4, proveedor.getEmail());
            statement.setInt(5, proveedor.getId());
            return statement.executeUpdate() > 0;
        }
    }

    // Método para eliminar un proveedor
    public boolean eliminarProveedor(int id) throws SQLException {
        String query = "DELETE FROM proveedores WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    // Método para obtener un proveedor por ID
    public Proveedores obtenerProveedorPorId(int id) throws SQLException {
        String query = "SELECT * FROM proveedores WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Proveedores proveedor = new Proveedores();
                    proveedor.setId(resultSet.getInt("id"));
                    proveedor.setNombre(resultSet.getString("nombre"));
                    proveedor.setNumeroTelefonico(resultSet.getString("numeroTelefonico"));
                    proveedor.setDireccion(resultSet.getString("direccion"));
                    proveedor.setEmail(resultSet.getString("email"));
                    return proveedor;
                }
            }
        }
        return null;
    }
}
