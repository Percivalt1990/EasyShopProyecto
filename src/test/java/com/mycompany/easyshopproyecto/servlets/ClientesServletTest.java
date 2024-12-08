package com.mycompany.easyshopproyecto.servlets;

import com.mycompany.easyshopproyecto.dao.ClientesDAO;
import com.mycompany.easyshopproyecto.logica.Clientes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientesServletTest {

    private ClientesServlet clientesServlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private ClientesDAO clientesDAO;
    private RequestDispatcher requestDispatcher;

   

    @BeforeEach
    void setUp() throws Exception {
        clientesDAO = mock(ClientesDAO.class);
        clientesServlet = new ClientesServlet();

        // Usar reflection para modificar el campo privado
        Field clientesDAOField = ClientesServlet.class.getDeclaredField("clientesDAO");
        clientesDAOField.setAccessible(true);
        clientesDAOField.set(clientesServlet, clientesDAO);

        request = spy(new MockHttpServletRequest());
        response = new MockHttpServletResponse();

        requestDispatcher = mock(RequestDispatcher.class);
        doNothing().when(requestDispatcher).forward(request, response);
        doReturn(requestDispatcher).when(request).getRequestDispatcher(anyString());
    }


    @Test
    @DisplayName("Test doGet: Listar todos los clientes")
    void testDoGetListarClientes() throws ServletException, IOException {
        // Configurar datos de prueba
        List<Clientes> listaClientes = new ArrayList<>();
        listaClientes.add(new Clientes(1, "Juan Pérez", "123456", "CC", "3001234567", "Calle 123", "juan@example.com"));
        when(clientesDAO.listarClientes()).thenReturn(listaClientes);

        // Ejecutar doGet
        clientesServlet.doGet(request, response);

        // Verificar que los clientes fueron enviados al JSP
        assertNotNull(request.getAttribute("clientesFiltrados"));
        assertEquals(listaClientes, request.getAttribute("clientesFiltrados"));
        verify(request).getRequestDispatcher("clientes.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    @DisplayName("Test doPost: Crear cliente")
    void testDoPostCrearCliente() throws ServletException, IOException {
        // Configurar parámetros de la solicitud
        request.setParameter("action", "crear");
        request.setParameter("nombre", "Nuevo Cliente");
        request.setParameter("documento", "654321");
        request.setParameter("tipoDocumento", "TI");
        request.setParameter("telefono", "3006543210");
        request.setParameter("direccion", "Calle 456");
        request.setParameter("email", "nuevo@example.com");

        when(clientesDAO.crearCliente(any(Clientes.class))).thenReturn(true);

        // Ejecutar doPost
        clientesServlet.doPost(request, response);

        // Verificar redirección
        assertEquals("ClientesServlet", response.getRedirectedUrl());
    }

    @Test
    @DisplayName("Test doPost: Editar cliente")
    void testDoPostEditarCliente() throws ServletException, IOException {
        // Configurar parámetros de la solicitud
        request.setParameter("action", "editar");
        request.setParameter("id", "1");
        request.setParameter("nombre", "Cliente Editado");
        request.setParameter("documento", "123456");
        request.setParameter("tipoDocumento", "CC");
        request.setParameter("telefono", "3009876543");
        request.setParameter("direccion", "Calle Nueva");
        request.setParameter("email", "editado@example.com");

        when(clientesDAO.actualizarCliente(any(Clientes.class))).thenReturn(true);

        // Ejecutar doPost
        clientesServlet.doPost(request, response);

        // Verificar redirección
        assertEquals("ClientesServlet", response.getRedirectedUrl());
    }

    @Test
    @DisplayName("Test doPost: Eliminar cliente")
    void testDoPostEliminarCliente() throws ServletException, IOException {
        // Configurar parámetros de la solicitud
        request.setParameter("action", "eliminar");
        request.setParameter("id", "1");

        when(clientesDAO.eliminarCliente(1)).thenReturn(true);

        // Ejecutar doPost
        clientesServlet.doPost(request, response);

        // Verificar redirección
        assertEquals("ClientesServlet", response.getRedirectedUrl());
    }

    @Test
    @DisplayName("Test doPost: Cargar cliente para editar")
    void testDoPostCargarCliente() throws ServletException, IOException {
        // Configurar parámetros de la solicitud
        request.setParameter("action", "cargar");
        request.setParameter("id", "1");

        Clientes cliente = new Clientes(1, "Juan Pérez", "123456", "CC", "3001234567", "Calle 123", "juan@example.com");
        when(clientesDAO.obtenerClientePorId(1)).thenReturn(cliente);

        // Ejecutar doPost
        clientesServlet.doPost(request, response);

        // Verificar atributos enviados al JSP
        assertEquals(cliente, request.getAttribute("cliente"));
        verify(request).getRequestDispatcher("editarCliente.jsp");
        verify(requestDispatcher).forward(request, response);
    }
}
