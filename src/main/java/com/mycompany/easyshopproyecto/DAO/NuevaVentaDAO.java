package com.mycompany.easyshopproyecto.dao;

import com.mycompany.easyshopproyecto.logica.ProductosVenta;
import com.mycompany.easyshopproyecto.logica.Clientes;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NuevaVentaDAO {
    private Connection connection;

    public NuevaVentaDAO() {
        this.connection = ConexionDB.getConnection(); 
    }

    // Metodo para listar todos los productos disponibles para la venta
    public List<ProductosVenta> listarProductos() {
        List<ProductosVenta> productos = new ArrayList<>();
        String query = "SELECT * FROM productos";
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

    // Metodo para buscar productos por nombre
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
    
    // Metodo para registrar una factura y registrar ventas
    public boolean registrarFactura(int clienteId, int usuarioId, String formaPago, long totalVenta, String pdfUrl) {
        String queryFactura = "INSERT INTO facturas (clienteId, usuarioId, formapago, fecha, totalVenta, pdfUrl) VALUES (?, ?, ?, ?, ?, ?)";
        String queryVenta = "INSERT INTO ventas (tipo, monto, fecha) VALUES ('ingreso', ?, CURRENT_DATE)";

        try (PreparedStatement psFactura = connection.prepareStatement(queryFactura);
             PreparedStatement psVenta = connection.prepareStatement(queryVenta)) {

            // Si clienteId es 0, usa el cliente generico
            psFactura.setInt(1, clienteId > 0 ? clienteId : 0); // Cliente generico si es 0
            psFactura.setInt(2, usuarioId);
            psFactura.setString(3, formaPago);
            psFactura.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            psFactura.setLong(5, totalVenta);
            psFactura.setString(6, pdfUrl);
            psFactura.executeUpdate();

            psVenta.setLong(1, totalVenta);
            psVenta.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Metodo para actualizar el inventario despues de una venta
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

    // Metodo para obtener detalles de un producto especifico por su id
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

    // Metodo para buscar solo el nombre del cliente por documento
    public Clientes buscarClientePorDocumento(String documento) {
        String query = "SELECT * FROM clientes WHERE documento = ?"; // Seleccionar todos los campos
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, documento);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Clientes cliente = new Clientes();
                    cliente.setId(rs.getInt("id"));  // Obtén el ID
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setDocumento(rs.getString("documento"));
                    cliente.setTipoDocumento(rs.getString("tipo_documento"));
                    cliente.setTelefono(rs.getString("telefono"));
                    cliente.setDireccion(rs.getString("direccion"));
                    cliente.setEmail(rs.getString("email"));
                    return cliente;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

   



}
