package com.mycompany.easyshopproyecto.dao;

import com.mycompany.easyshopproyecto.logica.Usuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    // Autenticar usuario para inicio de sesión
    public Usuarios autenticar(String numeroDocumento, String password) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE numero_documento = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, numeroDocumento);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToUsuario(resultSet);
            }
        }
        return null;
    }

    // Crear un nuevo usuario
    public boolean crearUsuario(Usuarios usuario) throws SQLException {
        String query = "INSERT INTO usuarios (nombre, tipo_documento, numero_documento, email, telefono, password, confirmacion, permisos) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getTipoDocumento());
            statement.setString(3, usuario.getNumeroDocumento());
            statement.setString(4, usuario.getEmail());
            statement.setString(5, usuario.getTelefono());
            statement.setString(6, usuario.getPassword());
            statement.setString(7, usuario.getConfirmacion());
            statement.setBoolean(8, usuario.getPermisos() != null && usuario.getPermisos());
            return statement.executeUpdate() > 0;
        }
    }

    // Listar todos los usuarios
    public List<Usuarios> listarUsuarios() throws SQLException {
        List<Usuarios> usuarios = new ArrayList<>();
        String query = "SELECT * FROM usuarios";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                usuarios.add(mapResultSetToUsuario(resultSet));
            }
        }
        return usuarios;
    }

    // Buscar usuario por ID
    public Usuarios buscarUsuarioPorId(int id) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToUsuario(resultSet);
            }
        }
        return null;
    }

    // Buscar usuario por nombre o documento
    public List<Usuarios> buscarUsuarioPorNombreODocumento(String criterio) throws SQLException {
        List<Usuarios> usuarios = new ArrayList<>();
        String query = "SELECT * FROM usuarios WHERE nombre LIKE ? OR numero_documento LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + criterio + "%");
            statement.setString(2, "%" + criterio + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                usuarios.add(mapResultSetToUsuario(resultSet));
            }
        }
        return usuarios;
    }

    // Actualizar datos de usuario
    public boolean actualizarUsuario(Usuarios usuario) throws SQLException {
        String query = "UPDATE usuarios SET nombre = ?, tipo_documento = ?, numero_documento = ?, email = ?, telefono = ?, password = ?, confirmacion = ?, permisos = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getTipoDocumento());
            statement.setString(3, usuario.getNumeroDocumento());
            statement.setString(4, usuario.getEmail());
            statement.setString(5, usuario.getTelefono());
            statement.setString(6, usuario.getPassword());
            statement.setString(7, usuario.getConfirmacion());
            statement.setBoolean(8, usuario.getPermisos() != null && usuario.getPermisos());
            statement.setInt(9, usuario.getId());
            return statement.executeUpdate() > 0;
        }
    }

    // Eliminar usuario por ID
    public boolean eliminarUsuario(int id) throws SQLException {
        String query = "DELETE FROM usuarios WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    // Mapear ResultSet a objeto Usuario
    private Usuarios mapResultSetToUsuario(ResultSet resultSet) throws SQLException {
        Usuarios usuario = new Usuarios();
        usuario.setId(resultSet.getInt("id"));
        usuario.setNombre(resultSet.getString("nombre"));
        usuario.setTipoDocumento(resultSet.getString("tipo_documento"));
        usuario.setNumeroDocumento(resultSet.getString("numero_documento"));
        usuario.setEmail(resultSet.getString("email"));
        usuario.setTelefono(resultSet.getString("telefono"));
        usuario.setPassword(resultSet.getString("password"));
        usuario.setConfirmacion(resultSet.getString("confirmacion"));
        usuario.setPermisos(resultSet.getBoolean("permisos"));
        return usuario;
    }
}
