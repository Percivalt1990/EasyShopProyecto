package com.mycompany.easyshopproyecto.servlets;

import com.mycompany.easyshopproyecto.dao.ConexionDB;
import com.mycompany.easyshopproyecto.dao.UsuarioDAO;
import com.mycompany.easyshopproyecto.logica.Usuarios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;


class LoginServletTest {

    private LoginServlet loginServlet;
    private UsuarioDAO usuarioDAO;

    @BeforeEach
    void setUp() throws SQLException {
        loginServlet = new LoginServlet();
        Connection connection = ConexionDB.getConnection();
        assertNotNull(connection, "La conexión a la base de datos no debe ser nula.");
        usuarioDAO = new UsuarioDAO(connection);

        // Preparar un número de documento único para las pruebas
        String numeroDocumentoPrueba = "5554322"; // Único y no existente en la base de datos
        Usuarios usuarioExistente = buscarUsuarioPorNumeroDocumento(numeroDocumentoPrueba);
        if (usuarioExistente != null) {
            usuarioDAO.eliminarUsuario(usuarioExistente.getId());
        }

        // Insertar un usuario de prueba
        Usuarios usuario = new Usuarios(0, "Michael Carter", "Cedula de Ciudadania", numeroDocumentoPrueba,
                "michael.carter@example.com", "3005674321", "securePass123", "securePass123", true);
        boolean creado = usuarioDAO.crearUsuario(usuario);
        assertTrue(creado, "El usuario de prueba debe crearse correctamente.");
    }

    private Usuarios buscarUsuarioPorNumeroDocumento(String numeroDocumento) throws SQLException {
        return usuarioDAO.listarUsuarios().stream()
                .filter(usuario -> numeroDocumento.equals(usuario.getNumeroDocumento()))
                .findFirst()
                .orElse(null);
    }

    @Test
    @DisplayName("Test de Login con credenciales válidas")
    void testLoginCredencialesValidas() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Simular parámetros del formulario
        request.setParameter("numeroDocumento", "5554322");
        request.setParameter("password", "securePass123");

        // Ejecutar el servlet
        loginServlet.doPost(request, response);

        // Verificar la redirección
        assertEquals("index.jsp", response.getRedirectedUrl(), "El usuario debe ser redirigido a index.jsp.");

        // Verificar la sesión
        HttpSession session = request.getSession(false);
        assertNotNull(session, "La sesión no debe ser nula.");
        assertEquals("Michael Carter", session.getAttribute("nombreUsuario"), "El nombre del usuario debe estar en la sesión.");
    }

    @Test
    @DisplayName("Test de Login con credenciales inválidas")
    void testLoginCredencialesInvalidas() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Simular parámetros inválidos
        request.setParameter("numeroDocumento", "99999999");
        request.setParameter("password", "wrongPassword");

        // Ejecutar el servlet
        loginServlet.doPost(request, response);

        // Verificar que el mensaje de error se establece
        assertEquals("Usuario o contraseña incorrectos", request.getAttribute("errorMessage"),
                "Debe mostrar un mensaje de error indicando credenciales incorrectas.");
    }

    

}
