
package com.mycompany.easyshopproyecto.servlets;

import com.mycompany.easyshopproyecto.logica.Clientes;
import com.mycompany.easyshopproyecto.logica.Facturas;
import com.mycompany.easyshopproyecto.logica.Usuarios;
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

        // Crear instancias de Cliente y Usuario simuladas
        Clientes cliente1 = new Clientes();
        cliente1.setId(2587);  // Aquí puedes agregar más detalles del cliente
        cliente1.setNombre("Juan Pérez");

        Usuarios usuario1 = new Usuarios();
        usuario1.setId(14);  // Aquí puedes agregar más detalles del usuario
        usuario1.setNombre("Carlos López");

        // Crear factura 1
        Facturas factura1 = new Facturas();
        factura1.setId(1);
        factura1.setCliente(cliente1);  // Asignar el objeto Cliente
        factura1.setUsuario(usuario1);  // Asignar el objeto Usuario
        factura1.setFormaPago("Efectivo");
        factura1.setFecha("2024-01-15");
        factura1.setTotal(258600L);
        factura1.setPdfUrl("/pdfs/factura1.pdf");
        facturas.add(factura1);

        // Crear otra instancia de Cliente y Usuario para la factura 2
        Clientes cliente2 = new Clientes();
        cliente2.setId(5555);  // Otro cliente
        cliente2.setNombre("María González");

        Usuarios usuario2 = new Usuarios();
        usuario2.setId(2);  // Otro usuario
        usuario2.setNombre("Ana Fernández");

        // Crear factura 2
        Facturas factura2 = new Facturas();
        factura2.setId(2);
        factura2.setCliente(cliente2);  // Asignar el objeto Cliente
        factura2.setUsuario(usuario2);  // Asignar el objeto Usuario
        factura2.setFormaPago("Transferencia");
        factura2.setFecha("2024-06-25");
        factura2.setTotal(45200L);
        factura2.setPdfUrl("/pdfs/factura2.pdf");
        facturas.add(factura2);

        // Obtener el parámetro de búsqueda
        String search = request.getParameter("search");

        // Filtrar facturas si el parámetro de búsqueda no está vacío
        List<Facturas> facturasFiltradas = new ArrayList<>();
        if (search != null && !search.isEmpty()) {
            for (Facturas factura : facturas) {
                // Ahora tienes que acceder a los datos del cliente y usuario a través de los objetos
                if (String.valueOf(factura.getId()).contains(search) ||
                    String.valueOf(factura.getCliente().getId()).contains(search) ||
                    String.valueOf(factura.getUsuario().getId()).contains(search) ||
                    factura.getFormaPago().toLowerCase().contains(search.toLowerCase()) ||
                    factura.getFecha().contains(search)) {
                    facturasFiltradas.add(factura);
                }
            }
        } else {
            facturasFiltradas = facturas; // Si no hay búsqueda, mostrar todas las facturas
        }

        // Pasar las facturas filtradas al JSP
        request.setAttribute("facturas", facturasFiltradas);

        // Redirigir a la página de facturas
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

