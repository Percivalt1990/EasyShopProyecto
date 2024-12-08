package com.mycompany.easyshopproyecto.dao;

import com.mycompany.easyshopproyecto.logica.Usuarios;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioDAOTest {

    private Connection connection;
    private UsuarioDAO usuarioDAO;

    @BeforeAll
    void setUp() {
        connection = ConexionDB.getConnection();
        assertNotNull(connection, "La conexión a la base de datos no debe ser nula.");
        usuarioDAO = new UsuarioDAO(connection);
    }

    @Test
    @DisplayName("Test crearUsuario")
    void testCrearUsuario() {
        Usuarios usuario = new Usuarios(0, "Juan Perez", "CC", "16549854", "juan@example.com", "599656", "password1232", "password1232", true);
        try {
            boolean result = usuarioDAO.crearUsuario(usuario);
            assertTrue(result, "El usuario debería ser creado correctamente.");
        } catch (SQLException e) {
            fail("Error durante la creación del usuario: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test listarUsuarios")
    void testListarUsuarios() {
        try {
            List<Usuarios> usuarios = usuarioDAO.listarUsuarios();
            assertNotNull(usuarios, "La lista de usuarios no debe ser nula.");
            assertTrue(usuarios.size() > 0, "Debe haber al menos un usuario en la lista.");
        } catch (SQLException e) {
            fail("Error al listar usuarios: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test autenticar")
    void testAutenticar() {
        try {
            Usuarios usuario = usuarioDAO.autenticar("16549854", "password1232");
            assertNotNull(usuario, "El usuario no debe ser nulo si las credenciales son correctas.");
            assertEquals("16549854", usuario.getNumeroDocumento(), "El número de documento debe coincidir.");
        } catch (SQLException e) {
            fail("Error durante la autenticación: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test buscarUsuarioPorId")
    void testBuscarUsuarioPorId() {
        int id = 1;  //Se debe ajustar segun a la base de datos
        try {
            Usuarios usuario = usuarioDAO.buscarUsuarioPorId(id);
            assertNotNull(usuario, "El usuario no debe ser nulo si existe.");
            assertEquals(id, usuario.getId(), "El ID debe coincidir.");
        } catch (SQLException e) {
            fail("Error al buscar usuario por ID: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test buscarUsuarioPorNombreODocumento")
    void testBuscarUsuarioPorNombreODocumento() {
        try {
            String criterio = "Juan";
            List<Usuarios> usuarios = usuarioDAO.buscarUsuarioPorNombreODocumento(criterio);
            assertNotNull(usuarios, "La lista de usuarios no debe ser nula.");
            assertTrue(usuarios.size() > 0, "Debe haber al menos un usuario que coincida con el criterio.");
        } catch (SQLException e) {
            fail("Error al buscar usuario por nombre o documento: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test actualizarUsuario")
    void testActualizarUsuario() {
        Usuarios usuario = new Usuarios(1, "Juan jose", "CC", "1254828", "nuevoemail@example.com", "7777177", "newpassword", "newpassword", true);
        try {
            boolean result = usuarioDAO.actualizarUsuario(usuario);
            assertTrue(result, "El usuario debería ser actualizado correctamente.");
        } catch (SQLException e) {
            fail("Error durante la actualización del usuario: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test actualizarContrasena")
    void testActualizarContrasena() {
        try {
            boolean result = usuarioDAO.actualizarContrasena("12345678", "newpassword123", "newpassword123");
            assertTrue(result, "La contraseña debería ser actualizada correctamente.");
        } catch (SQLException e) {
            fail("Error al actualizar contraseña: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test eliminarUsuario")
    void testEliminarUsuario() {
        int id = 27; //Se debe ajustar segun a la base de datos
        try {
            boolean result = usuarioDAO.eliminarUsuario(id);
            assertTrue(result, "El usuario debería ser eliminado correctamente.");
        } catch (SQLException e) {
            fail("Error al eliminar usuario: " + e.getMessage());
        }
    }

    @AfterAll
    void tearDown() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
