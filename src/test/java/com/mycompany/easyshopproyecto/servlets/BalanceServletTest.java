package com.mycompany.easyshopproyecto.servlets;

import com.mycompany.easyshopproyecto.dao.BalanceDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BalanceServletTest {

    private BalanceServlet balanceServlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private BalanceDAO balanceDAO;
    private RequestDispatcher requestDispatcher;

    @BeforeEach
    void setUp() throws ServletException, IOException {
        // Mock del DAO
        balanceDAO = mock(BalanceDAO.class);
        // Configurar valores de retorno del mock
        when(balanceDAO.obtenerTotalIngresos()).thenReturn(10000.0);
        when(balanceDAO.obtenerTotalEgresos()).thenReturn(5000.0);

        // Inicializar el servlet con el mock
        balanceServlet = new BalanceServlet(balanceDAO);

        // Creacion de mocks de request y response
        request = spy(new MockHttpServletRequest());
        response = new MockHttpServletResponse();

        // Mock del RequestDispatcher
        requestDispatcher = mock(RequestDispatcher.class);
        doAnswer(invocation -> {
            // Simular el comportamiento del forward configurando el atributo
            request.setAttribute("javax.servlet.forward.servlet_path", "balance.jsp");
            return null;
        }).when(requestDispatcher).forward(request, response);

        // Configurar el comportamiento de getRequestDispatcher en el request
        doReturn(requestDispatcher).when(request).getRequestDispatcher("balance.jsp");
    }

    @Test
    @DisplayName("Test doGet: Verificar cálculo y reenvío")
    void testDoGetResponse() throws ServletException, IOException {
        // Ejecucion del metodo doGet
        balanceServlet.doGet(request, response);

        // Verificar los atributos enviados al JSP
        assertEquals(10000.0, request.getAttribute("totalIngresos"), "El total de ingresos debe ser 10000.0");
        assertEquals(5000.0, request.getAttribute("totalEgresos"), "El total de egresos debe ser 5000.0");
        assertEquals(5000.0, request.getAttribute("balance"), "El balance debe ser 5000.0");

        // Verificar el reenviado al JSP
        String forwardedUrl = (String) request.getAttribute("javax.servlet.forward.servlet_path");
        assertEquals("balance.jsp", forwardedUrl, "Debe redirigir a balance.jsp");
    }

    @Test
    @DisplayName("Test doGet: Verificar cabeceras de caché")
    void testDoGetHeaders() throws ServletException, IOException {
        // Ejecucion del metodo doGet
        balanceServlet.doGet(request, response);

        // Validar cabeceras
        assertEquals("no-cache, no-store, must-revalidate", response.getHeader("Cache-Control"));
        assertEquals("no-cache", response.getHeader("Pragma"));
        assertEquals(0, response.getDateHeader("Expires"));
    }
}
