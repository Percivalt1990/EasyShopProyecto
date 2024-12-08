package com.mycompany.easyshopproyecto.dao;

import com.mycompany.easyshopproyecto.logica.Proveedores;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProveedoresDAOTest {

    private Connection connection;
    private ProveedoresDAO proveedoresDAO;

    @BeforeAll
    void setUp() {
        connection = ConexionDB.getConnection();
        assertNotNull(connection, "La conexión a la base de datos no debe ser nula.");
        proveedoresDAO = new ProveedoresDAO();
    }

    @Test
    @DisplayName("Test crearProveedor")
    void testCrearProveedor() {
        Proveedores proveedor = new Proveedores(0, "Nuevo Proveedor", "111222333", "Nueva Direccion", "nuevo@proveedor.com");
        boolean result = proveedoresDAO.crearProveedor(proveedor);
        assertTrue(result, "El proveedor debería ser creado correctamente.");
    }

    @Test
    @DisplayName("Test listarProveedores")
    void testListarProveedores() {
        List<Proveedores> proveedores = proveedoresDAO.listarProveedores();
        assertNotNull(proveedores, "La lista de proveedores no debe ser nula.");
        assertTrue(proveedores.size() > 0, "Debe haber al menos un proveedor en la lista.");
    }

    @Test
    @DisplayName("Test buscarProveedores")
    void testBuscarProveedores() {
        String search = "Proveedorprueba2"; //Se debe ajustar segun a la base de datos
        List<Proveedores> proveedores = proveedoresDAO.buscarProveedores(search);
        assertNotNull(proveedores, "La lista de proveedores no debe ser nula.");
        assertTrue(proveedores.size() > 0, "Debe haber al menos un proveedor que coincida con la búsqueda.");
    }

    @Test
    @DisplayName("Test actualizarProveedor")
    void testActualizarProveedor() {
        Proveedores proveedor = new Proveedores(1, "Proveedor Actualizado", "987654321", "Direccion Actualizada", "nuevoemail@proveedor.com");
        boolean result = proveedoresDAO.actualizarProveedor(proveedor);
        assertTrue(result, "El proveedor debería ser actualizado correctamente.");
    }

    @Test
    @DisplayName("Test eliminarProveedor")
    void testEliminarProveedor() {
        int idProveedor = 23; //Se debe ajustar segun a la base de datos
        boolean result = proveedoresDAO.eliminarProveedor(idProveedor);
        assertTrue(result, "El proveedor debería ser eliminado correctamente.");
    }

    @Test
    @DisplayName("Test obtenerProveedorPorId")
    void testObtenerProveedorPorId() {
        int idProveedor = 1; //Se debe ajustar segun a la base de datos
        Proveedores proveedor = proveedoresDAO.obtenerProveedorPorId(idProveedor);
        assertNotNull(proveedor, "El proveedor no debe ser nulo.");
        assertEquals(idProveedor, proveedor.getId(), "El ID del proveedor debe coincidir.");
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
