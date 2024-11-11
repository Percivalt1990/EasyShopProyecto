
package com.mycompany.easyshopproyecto.servlets;

import com.mycompany.easyshopproyecto.logica.Facturas;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FacturasServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Facturas> facturas = new ArrayList<>();

        // Simulacion de base de datos con datos fijos en el propio servlet
        Facturas factura1 = new Facturas();
        factura1.setId(1);
        factura1.setIdCliente(1254);
        factura1.setIdUsuario(14);
        factura1.setFormaPago("Efectivo");
        factura1.setFecha("2024-01-15"); 
        factura1.setTotal((long) 258600.00);
        factura1.setPdfUrl("/pdfs/factura1.pdf");
        facturas.add(factura1);

        Facturas factura2 = new Facturas();
        factura2.setId(2);
        factura2.setIdCliente(2587);
        factura2.setIdUsuario(2);
        factura2.setFormaPago("Transferencia");
        factura2.setFecha(("2024-06-25"));
        factura2.setTotal((long) 45200.00);
        factura2.setPdfUrl("/pdfs/factura2.pdf");
        facturas.add(factura2);
        
        
         // Obtener el parametro de busqueda
          String search = request.getParameter("search");

        // Filtrar facturas si el parametro de busqueda no esta vacio
        List<Facturas> facturasFiltradas = new ArrayList<>();
        if (search != null && !search.isEmpty()) {
            for (Facturas factura : facturas) {
                if (String.valueOf(factura.getId()).contains(search) ||
                    String.valueOf(factura.getIdCliente()).contains(search) ||
                    String.valueOf(factura.getIdUsuario()).contains(search) ||
                    factura.getFormaPago().toLowerCase().contains(search.toLowerCase()) ||
                    factura.getFecha().contains(search)) {
                    facturasFiltradas.add(factura);
                }
            }
        } else {
            facturasFiltradas = facturas; // Si no hay busqueda, mostrar todas las facturas
        }

        // Pasar las facturas filtradas al JSP
        request.setAttribute("facturas", facturasFiltradas);

        // Redirigir a la pagina de facturas
        request.getRequestDispatcher("/facturas.jsp").forward(request, response);
        

        // Codigo para conexion real a la base de datos para cuando sea necesaria
        /*
        try (Connection conn = /*  metodo para obtener conexion */ /*
            String sql = "SELECT * FROM facturas"; // Asegurarse de que esta sea el nombre correcto de la tabla en el mySQL
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Factura factura = new Factura();
                factura.setId(rs.getInt("id"));
                factura.setIdCliente(rs.getInt("id_cliente"));
                factura.setIdUsuario(rs.getInt("id_usuario"));
                factura.setFormaPago(rs.getString("forma_pago"));
                factura.setFecha(rs.getDate("fecha"));
                factura.setTotal(rs.getDouble("total"));
                factura.setPdfUrl(rs.getString("pdf_url")); // Asegurarse de tener esta columna en la tabla para el pdf
                facturas.add(factura);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }
}

