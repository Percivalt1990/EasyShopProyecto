package com.mycompany.easyshopproyecto.servlets;

import com.mycompany.easyshopproyecto.dao.FacturasDAO;
import com.mycompany.easyshopproyecto.logica.Facturas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FacturasServletTest {

    private FacturasServlet facturasServlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private FacturasDAO facturasDAO;
    private RequestDispatcher requestDispatcher;

   @BeforeEach
    void setUp() throws ServletException, IOException {
        facturasDAO = mock(FacturasDAO.class); // Mock del DAO
        facturasServlet = new FacturasServlet(); // Constructor sin argumentos
        facturasServlet.facturasDAO = facturasDAO; // Inyeccion manual del mock

        request = spy(new MockHttpServletRequest());
        response = new MockHttpServletResponse();

        requestDispatcher = mock(RequestDispatcher.class);
        doNothing().when(requestDispatcher).forward(request, response);
        doReturn(requestDispatcher).when(request).getRequestDispatcher("/facturas.jsp");
    }

    @Test
    @DisplayName("Test doGet: Buscar facturas con filtros")
    void testDoGetBuscarFacturasConFiltros() throws ServletException, IOException {
        request.setParameter("id", "1");
        request.setParameter("formaPago", "Efectivo");

        List<Facturas> facturasList = new ArrayList<>();
        facturasList.add(new Facturas(1, 3, 6, "Efectivo", LocalDateTime.now(), 50000L, "/ruta/pdf1.pdf"));
        when(facturasDAO.buscarFacturas(eq(1), any(), any(), eq("Efectivo"), any(), any())).thenReturn(facturasList);

        facturasServlet.doGet(request, response);

        assertNotNull(request.getAttribute("facturas"));
        assertEquals(facturasList, request.getAttribute("facturas"));
        verify(request).getRequestDispatcher("/facturas.jsp");
        verify(requestDispatcher).forward(request, response);
    }

}
