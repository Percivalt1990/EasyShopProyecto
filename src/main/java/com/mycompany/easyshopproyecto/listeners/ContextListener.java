package com.mycompany.easyshopproyecto.listeners;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("La aplicación web se ha iniciado.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("La aplicación web se está deteniendo.");
        try {
            // Detiene correctamente los hilos relacionados con MySQL
            AbandonedConnectionCleanupThread.checkedShutdown();
            System.out.println("Conexiones de MySQL cerradas correctamente.");
        } catch (Exception e) { // Cambiado a Exception para capturar cualquier problema
            System.err.println("Error al cerrar las conexiones de MySQL:");
            e.printStackTrace();
        }
    }
}
