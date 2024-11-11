package com.mycompany.easyshopproyecto.dao;

import com.mycompany.easyshopproyecto.logica.Productos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductosDAO {
    private Connection connection;

    public ProductosDAO() {
        this.connection = ConexionDB.getConnection();
    }

    // Método para crear un nuevo producto
    public boolean crearProducto(Productos producto) {
        String sql = "INSERT INTO productos (id_categoria, nombre, cantidad, precio, descripcion, id_proveedor) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, producto.getIdCategoria());  // Usar idCategoria para el campo id_categoria en la base de datos
            ps.setString(2, producto.getNombre());
            ps.setInt(3, producto.getCantidad());
            ps.setDouble(4, producto.getPrecio());
            ps.setString(5, producto.getDescripcion());
            ps.setString(6, producto.getIdProveedor());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para listar todos los productos
    public List<Productos> listarProductos() {
        List<Productos> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Productos producto = new Productos(
                        rs.getInt("id"),
                        rs.getString("id_categoria"),  // Mapear id_categoria desde la base de datos a idCategoria en el objeto
                        rs.getString("nombre"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio"),
                        rs.getString("descripcion"),
                        rs.getString("id_proveedor")
                );
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    // Método para buscar productos por nombre o descripción
    public List<Productos> buscarProductos(String search) {
        List<Productos> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE nombre LIKE ? OR descripcion LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + search + "%");
            ps.setString(2, "%" + search + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Productos producto = new Productos(
                            rs.getInt("id"),
                            rs.getString("id_categoria"),  // Mapear id_categoria correctamente
                            rs.getString("nombre"),
                            rs.getInt("cantidad"),
                            rs.getDouble("precio"),
                            rs.getString("descripcion"),
                            rs.getString("id_proveedor")
                    );
                    productos.add(producto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    // Método para actualizar un producto
    public boolean actualizarProducto(Productos producto) {
        String sql = "UPDATE productos SET id_categoria = ?, nombre = ?, cantidad = ?, precio = ?, descripcion = ?, id_proveedor = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, producto.getIdCategoria());  // Asegurarse de usar idCategoria para id_categoria
            ps.setString(2, producto.getNombre());
            ps.setInt(3, producto.getCantidad());
            ps.setDouble(4, producto.getPrecio());
            ps.setString(5, producto.getDescripcion());
            ps.setString(6, producto.getIdProveedor());
            ps.setInt(7, producto.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar un producto por su ID
    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener un producto por su ID
    public Productos obtenerProductoPorId(int id) {
        String sql = "SELECT * FROM productos WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Productos(
                            rs.getInt("id"),
                            rs.getString("id_categoria"),  // Asegurarse de mapear id_categoria correctamente
                            rs.getString("nombre"),
                            rs.getInt("cantidad"),
                            rs.getDouble("precio"),
                            rs.getString("descripcion"),
                            rs.getString("id_proveedor")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
