package com.mycompany.easyshopproyecto.dao;

import com.mycompany.easyshopproyecto.logica.Proveedores;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedoresDAO {
    private Connection connection;

    public ProveedoresDAO() {
        this.connection = ConexionDB.getConnection();
    }

    // Metodo para crear un nuevo proveedor
    public boolean crearProveedor(Proveedores proveedor) {
        String sql = "INSERT INTO proveedores (nombre, numero_telefonico, direccion, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, proveedor.getNombre());
            ps.setString(2, proveedor.getNumeroTelefonico());
            ps.setString(3, proveedor.getDireccion());
            ps.setString(4, proveedor.getEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Metodo para listar todos los proveedores
    public List<Proveedores> listarProveedores() {
        List<Proveedores> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM proveedores";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Proveedores proveedor = new Proveedores(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("numero_telefonico"),  // Modificado a numero_telefonico
                        rs.getString("direccion"),
                        rs.getString("email")
                );
                proveedores.add(proveedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proveedores;
    }

    // Metodo para buscar proveedores
    public List<Proveedores> buscarProveedores(String search) {
        List<Proveedores> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM proveedores WHERE nombre LIKE ? OR email LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + search + "%");
            ps.setString(2, "%" + search + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Proveedores proveedor = new Proveedores(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("numero_telefonico"),  // Modificado a numero_telefonico
                            rs.getString("direccion"),
                            rs.getString("email")
                    );
                    proveedores.add(proveedor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proveedores;
    }

    // Metodo para actualizar un proveedor
    public boolean actualizarProveedor(Proveedores proveedor) {
        String sql = "UPDATE proveedores SET nombre = ?, numero_telefonico = ?, direccion = ?, email = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, proveedor.getNombre());
            ps.setString(2, proveedor.getNumeroTelefonico());  // Modificado a numero_telefonico
            ps.setString(3, proveedor.getDireccion());
            ps.setString(4, proveedor.getEmail());
            ps.setInt(5, proveedor.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Metodo para eliminar un proveedor
    public boolean eliminarProveedor(int id) {
        String sql = "DELETE FROM proveedores WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Metodo para obtener un proveedor por ID
    public Proveedores obtenerProveedorPorId(int id) {
        String sql = "SELECT * FROM proveedores WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Proveedores(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("numero_telefonico"),  // Modificado a numero_telefonico
                            rs.getString("direccion"),
                            rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
