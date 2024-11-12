package com.mycompany.easyshopproyecto.dao;

import com.mycompany.easyshopproyecto.logica.ProductosVenta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NuevaVentaDAO {
    private Connection connection;

    public NuevaVentaDAO() {
        this.connection = ConexionDB.getConnection(); 
    }

    // Método para listar todos los productos disponibles para la venta
    public List<ProductosVenta> listarProductos() {
        List<ProductosVenta> productos = new ArrayList<>();
        String query = "SELECT * FROM productos";  // Ajusta el nombre de la tabla si es necesario
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ProductosVenta producto = new ProductosVenta(
                        rs.getInt("id"), 
                        rs.getString("nombre"), 
                        rs.getInt("precio"), 
                        rs.getInt("cantidad")
                );
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    // Método para buscar productos por nombre (similar al método de filtro en el servlet)
    public List<ProductosVenta> buscarProductos(String search) {
        List<ProductosVenta> productos = new ArrayList<>();
        String query = "SELECT * FROM productos WHERE nombre LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "%" + search + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductosVenta producto = new ProductosVenta(
                            rs.getInt("id"), 
                            rs.getString("nombre"), 
                            rs.getInt("precio"), 
                            rs.getInt("cantidad")
                    );
                    productos.add(producto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }
    
    // Método para registrar el total de una venta en la tabla ventas
    public boolean registrarVenta(long totalVenta) {
        String query = "INSERT INTO ventas (total) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, totalVenta);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para actualizar el inventario después de una venta
    public boolean actualizarInventario(int idProducto, int cantidadVendida) {
        String query = "UPDATE productos SET cantidad = cantidad - ? WHERE id = ? AND cantidad >= ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, cantidadVendida);
            ps.setInt(2, idProducto);
            ps.setInt(3, cantidadVendida);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para obtener detalles de un producto específico por su ID (útil para agregar al carrito)
    public ProductosVenta obtenerProductoPorId(int id) {
        String query = "SELECT * FROM productos WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ProductosVenta(
                            rs.getInt("id"), 
                            rs.getString("nombre"), 
                            rs.getInt("precio"), 
                            rs.getInt("cantidad")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
