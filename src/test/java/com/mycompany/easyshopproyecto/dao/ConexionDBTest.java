package com.mycompany.easyshopproyecto.dao;

import org.junit.jupiter.api.Test;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class ConexionDBTest {

    @Test
    public void testGetConnection() {
        Connection connection = ConexionDB.getConnection();
        assertNotNull(connection, "La conexión no debe ser nula.");
    }
}
