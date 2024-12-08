package com.mycompany.easyshopproyecto.dao;

import com.mycompany.easyshopproyecto.logica.Clientes;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClientesDAOTest {

    private ClientesDAO clientesDAO;

    @BeforeAll
    void setUp() {
        clientesDAO = new ClientesDAO();
        assertNotNull(clientesDAO, "ClientesDAO no debe ser nulo.");
    }

    @Test
    @DisplayName("Test crearCliente")
    void testCrearCliente() {
        Clientes nuevoCliente = new Clientes(0, "Jose marez", "12825798", "CC", "5550034", "Calle cs53", "josrez@example.com");
        boolean creado = clientesDAO.crearCliente(nuevoCliente);
        assertTrue(creado, "El cliente debería haberse creado correctamente.");
    }

    @Test
    @DisplayName("Test listarClientes")
    void testListarClientes() {
        List<Clientes> clientes = clientesDAO.listarClientes();
        assertNotNull(clientes, "La lista de clientes no debería ser nula.");
        assertTrue(clientes.size() > 0, "Debería haber al menos un cliente en la lista.");
    }

    @Test
    @DisplayName("Test obtenerClientePorId")
    void testObtenerClientePorId() {
        Clientes cliente = clientesDAO.obtenerClientePorId(1); // Cambia el ID según tus datos.
        assertNotNull(cliente, "El cliente con ID 1 debería existir.");
        assertEquals(1, cliente.getId(), "El ID del cliente debería ser 1.");
    }

    @Test
    @DisplayName("Test actualizarCliente")
    void testActualizarCliente() {
        Clientes cliente = clientesDAO.obtenerClientePorId(1); // Cambia el ID según tus datos.
        assertNotNull(cliente, "El cliente con ID 1 debería existir.");

        cliente.setNombre("Nombre Actualizado");
        boolean actualizado = clientesDAO.actualizarCliente(cliente);
        assertTrue(actualizado, "El cliente debería haberse actualizado correctamente.");

        Clientes clienteActualizado = clientesDAO.obtenerClientePorId(1);
        assertEquals("Nombre Actualizado", clienteActualizado.getNombre(), "El nombre del cliente debería haberse actualizado.");
    }

    @Test
    @DisplayName("Test eliminarCliente")
    void testEliminarCliente() {
        Clientes nuevoCliente = new Clientes(0, "Jose marez", "12825798", "CC", "5550034", "Calle cs53", "josrez@example.com");
        boolean creado = clientesDAO.crearCliente(nuevoCliente);
        assertTrue(creado, "El cliente debería haberse creado correctamente.");

        List<Clientes> clientes = clientesDAO.listarClientes();
        Clientes clienteAEliminar = clientes.get(clientes.size() - 1); // Selecciona el último cliente agregado.

        boolean eliminado = clientesDAO.eliminarCliente(clienteAEliminar.getId());
        assertTrue(eliminado, "El cliente debería haberse eliminado correctamente.");
    }

    @Test
    @DisplayName("Test buscarClientes")
    void testBuscarClientes() {
        List<Clientes> clientes = clientesDAO.buscarClientes("Juan");
        assertNotNull(clientes, "La lista de resultados no debería ser nula.");
        assertTrue(clientes.size() > 0, "Debería haber al menos un cliente en los resultados.");
    }

    @AfterAll
    void tearDown() {
        System.out.println("Pruebas de ClientesDAO finalizadas.");
    }
}
