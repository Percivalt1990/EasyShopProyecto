package com.mycompany.easyshopproyecto.dao;

import com.mycompany.easyshopproyecto.logica.ProductosCompra;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NuevaCompraDAO {
    private Connection connection;

    public NuevaCompraDAO() {
        this.connection = ConexionDB.getConnection();
    }

    public List<ProductosCompra> listarProductosCompra() {
        List<ProductosCompra> productos = new ArrayList<>();
        String query = "SELECT p.id, p.id_proveedor, p.nombre, p.precio, p.cantidad, pr.nombre AS nombre_proveedor " +
                       "FROM productos p JOIN proveedores pr ON p.id_proveedor = pr.id";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ProductosCompra producto = new ProductosCompra(
                        rs.getInt("id"),
                        rs.getString("nombre_proveedor"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("cantidad")
                );
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }
    
    // Método para registrar el total de una compra en la tabla compras
    public boolean registrarCompra(long totalCompra) {
        String query = "INSERT INTO compras (total) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, totalCompra);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ProductosCompra> buscarProductosCompra(String search) {
        List<ProductosCompra> productos = new ArrayList<>();
        String query = "SELECT p.id, p.id_proveedor, p.nombre, p.precio, p.cantidad, pr.nombre AS nombre_proveedor " +
                       "FROM productos p JOIN proveedores pr ON p.id_proveedor = pr.id " +
                       "WHERE p.nombre LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "%" + search + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductosCompra producto = new ProductosCompra(
                            rs.getInt("id"),
                            rs.getString("nombre_proveedor"),
                            rs.getString("nombre"),
                            rs.getDouble("precio"),
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

    public boolean actualizarInventario(int idProducto, int cantidadComprada) {
        String query = "UPDATE productos SET cantidad = cantidad + ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, cantidadComprada);
            ps.setInt(2, idProducto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ProductosCompra obtenerProductoPorId(int id) {
        String query = "SELECT p.id, p.id_proveedor, p.nombre, p.precio, p.cantidad, pr.nombre AS nombre_proveedor " +
                       "FROM productos p JOIN proveedores pr ON p.id_proveedor = pr.id " +
                       "WHERE p.id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ProductosCompra(
                            rs.getInt("id"),
                            rs.getString("nombre_proveedor"),
                            rs.getString("nombre"),
                            rs.getDouble("precio"),
                            rs.getInt("cantidad")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean registrarCompra(List<ProductosCompra> carritoCompras, long total) {
        String queryCompra = "INSERT INTO compras (fecha, total) VALUES (?, ?)";
        String queryDetalle = "INSERT INTO detalle_compras (id_compra, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement psCompra = connection.prepareStatement(queryCompra, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psDetalle = connection.prepareStatement(queryDetalle)) {

            psCompra.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            psCompra.setLong(2, total);
            psCompra.executeUpdate();

            ResultSet generatedKeys = psCompra.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idCompra = generatedKeys.getInt(1);

                for (ProductosCompra producto : carritoCompras) {
                    psDetalle.setInt(1, idCompra);
                    psDetalle.setInt(2, producto.getId());
                    psDetalle.setInt(3, producto.getCantidad());
                    psDetalle.setDouble(4, producto.getPrecio());
                    psDetalle.addBatch();
                }
                psDetalle.executeBatch();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
