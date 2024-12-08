package com.mycompany.easyshopproyecto.dao;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/** Clase de prueba para BalanceDAO.*/
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BalanceDAOTest {

    private BalanceDAO balanceDAO;

    @BeforeAll
    void setUp() {
        balanceDAO = new BalanceDAO();
        assertNotNull(balanceDAO, "BalanceDAO no debe ser nulo.");
    }

    @Test
    @DisplayName("Test obtenerTotalIngresos")
    void testObtenerTotalIngresos() {
        double totalIngresos = balanceDAO.obtenerTotalIngresos();
        assertTrue(totalIngresos >= 0, "El total de ingresos debe ser mayor o igual a 0.");
        System.out.println("Total Ingresos: " + totalIngresos);
    }

    @Test
    @DisplayName("Test obtenerTotalEgresos")
    void testObtenerTotalEgresos() {
        double totalEgresos = balanceDAO.obtenerTotalEgresos();
        assertTrue(totalEgresos >= 0, "El total de egresos debe ser mayor o igual a 0.");
        System.out.println("Total Egresos: " + totalEgresos);
    }

    @AfterAll
    void tearDown() {
        System.out.println("Pruebas finalizadas.");
    }
}
