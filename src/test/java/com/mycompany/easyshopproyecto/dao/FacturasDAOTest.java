package com.mycompany.easyshopproyecto.dao;

import com.mycompany.easyshopproyecto.logica.Facturas;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FacturasDAOTest {

    private Connection connection;
    private FacturasDAO facturasDAO;

    @BeforeAll
    void setUp() {
        connection = ConexionDB.getConnection(); 
        assertNotNull(connection, "La conexión a la base de datos no debe ser nula.");
        facturasDAO = new FacturasDAO(connection);
    }

    @Test
    @DisplayName("Test listarFacturas")
    void testListarFacturas() {
        List<Facturas> facturas = facturasDAO.listarFacturas();
        assertNotNull(facturas, "La lista de facturas no debe ser nula.");
        assertTrue(facturas.size() > 0, "Debe haber al menos una factura en la lista.");
    }

    @Test
    @DisplayName("Test buscarFacturas con filtros")
    void testBuscarFacturasConFiltros() {
       //Se debe ajustar segun a la base de datos
        Integer id = 111;
        Integer clienteId = null; // se deja como null para no filtrar por clienteId
        Integer usuarioId = null;
        String formaPago = "Efectivo";
        LocalDate fecha = LocalDate.now();
        Long totalVenta = null;

        List<Facturas> facturas = facturasDAO.buscarFacturas(id, clienteId, usuarioId, formaPago, fecha, totalVenta);
        assertNotNull(facturas, "La lista de facturas no debe ser nula.");
        assertTrue(facturas.size() > 0, "Debe haber al menos una factura que coincida con los filtros.");
        assertEquals(id, facturas.get(0).getId(), "El ID de la factura debe coincidir con el filtro.");
    }

    @Test
    @DisplayName("Test buscarFacturas sin filtros")
    void testBuscarFacturasSinFiltros() {
        List<Facturas> facturas = facturasDAO.buscarFacturas(null, null, null, null, null, null);
        assertNotNull(facturas, "La lista de facturas no debe ser nula.");
        assertTrue(facturas.size() > 0, "Debe haber al menos una factura en la lista.");
    }

    @Test
    @DisplayName("Test buscarFacturas por clienteId")
    void testBuscarFacturasPorClienteId() {
        // cambiar el clienteId segun los datos en base de datos
        Integer clienteId = 3;

        List<Facturas> facturas = facturasDAO.buscarFacturas(null, clienteId, null, null, null, null);
        assertNotNull(facturas, "La lista de facturas no debe ser nula.");
        assertTrue(facturas.size() > 0, "Debe haber al menos una factura para el cliente especificado.");
        assertEquals(clienteId, facturas.get(0).getClienteId(), "El clienteId de la factura debe coincidir con el filtro.");
    }

    @AfterAll
    void tearDown() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
