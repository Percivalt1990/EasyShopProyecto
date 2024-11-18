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
    //Meodo para listar los productos para compra
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
    
    
    // Metodo para registrar una factura de compra y registrar el monto en compras
    public boolean registrarFactura(List<ProductosCompra> carritoCompras, long totalCompra) {
        String queryFacturaCompra = "INSERT INTO facturascompra (proveedor, producto, precio, cantidad, fecha) VALUES (?, ?, ?, ?, CURRENT_DATE)";
        String queryCompra = "INSERT INTO compras (tipo, monto, fecha) VALUES ('egreso', ?, CURRENT_DATE)";
        String queryActualizarInventario = "UPDATE productos SET cantidad = cantidad + ? WHERE id = ?";

        try (PreparedStatement psFacturaCompra = connection.prepareStatement(queryFacturaCompra);
             PreparedStatement psCompra = connection.prepareStatement(queryCompra);
             PreparedStatement psActualizarInventario = connection.prepareStatement(queryActualizarInventario)) {

            // Registrar cada producto en facturascompra
            for (ProductosCompra producto : carritoCompras) {
                psFacturaCompra.setString(1, producto.getProveedor());
                psFacturaCompra.setString(2, producto.getNombre());
                psFacturaCompra.setDouble(3, producto.getPrecio());
                psFacturaCompra.setInt(4, producto.getCantidad());
                psFacturaCompra.addBatch();

                // Aumentar el inventario en base a la cantidad comprada
                psActualizarInventario.setInt(1, producto.getCantidad());
                psActualizarInventario.setInt(2, producto.getId());
                psActualizarInventario.addBatch();
            }
            psFacturaCompra.executeBatch();
            psActualizarInventario.executeBatch();

            // Registrar el total en compras
            psCompra.setLong(1, totalCompra);
            psCompra.executeUpdate();

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

    
}
