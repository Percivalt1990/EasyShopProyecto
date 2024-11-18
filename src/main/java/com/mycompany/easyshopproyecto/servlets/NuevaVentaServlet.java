package com.mycompany.easyshopproyecto.servlets;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.mycompany.easyshopproyecto.dao.NuevaVentaDAO;
import com.mycompany.easyshopproyecto.logica.ProductosVenta;
import com.mycompany.easyshopproyecto.logica.Clientes;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NuevaVentaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private NuevaVentaDAO nuevaVentaDAO;

    @Override
    public void init() throws ServletException {
        nuevaVentaDAO = new NuevaVentaDAO();
        System.out.println("DAO Inicializado");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");

        String search = request.getParameter("search");
        List<ProductosVenta> productosFiltrados = (search != null && !search.isEmpty()) ?
                nuevaVentaDAO.buscarProductos(search) : nuevaVentaDAO.listarProductos();
        request.setAttribute("listaProductos", productosFiltrados);

        List<ProductosVenta> carritoVentas = (List<ProductosVenta>) session.getAttribute("carritoVentas");
        Clientes cliente = (Clientes) session.getAttribute("cliente");

        request.setAttribute("carritoVentas", carritoVentas);
        request.setAttribute("cliente", cliente);

        if ("buscarCliente".equals(action)) {
            buscarCliente(request, response, session);
        } else if ("skipCliente".equals(action)) {
            // llamado cliente generico
            Clientes clienteGenerico = new Clientes();
            clienteGenerico.setId(0); // Cliente generico con ID = 0
            clienteGenerico.setNombre("Sin Cliente");
            clienteGenerico.setDocumento("N/A");
            clienteGenerico.setTipoDocumento("N/A");
            clienteGenerico.setTelefono("N/A");
            clienteGenerico.setDireccion("N/A");
            clienteGenerico.setEmail("N/A");

            session.setAttribute("cliente", clienteGenerico); // Guardar cliente generico en la sesion
            response.sendRedirect("nuevaVenta.jsp");
        } else {
            request.getRequestDispatcher("nuevaVenta.jsp").forward(request, response);
        }
    }


    private void buscarCliente(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        String clienteDocumentoParam = request.getParameter("clienteDocumento");
        if (clienteDocumentoParam != null && !clienteDocumentoParam.trim().isEmpty()) {
            Clientes cliente = nuevaVentaDAO.buscarClientePorDocumento(clienteDocumentoParam);
            if (cliente != null) {
                session.setAttribute("cliente", cliente);
            } else {
                session.setAttribute("error", "Cliente no encontrado.");
            }
        } else {
            session.setAttribute("error", "Por favor, ingrese un documento válido.");
        }
        response.sendRedirect("NuevaVentaServlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("POST request recibido. Acción: " + action);

        if ("add".equals(action)) {
            añadirProductoAlCarrito(request, response);
        } else if ("delete".equals(action)) {
            eliminarProductoDelCarrito(request, response);
        } else if ("generateInvoice".equals(action)) {
            try {
                generarFactura(request, response);
            } catch (DocumentException ex) {
                Logger.getLogger(NuevaVentaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("cancel".equals(action)) {
            cancelarVenta(request, response);
        } else if ("omitCliente".equals(action)) {
        //  Bandera en la sesion para indicar que el cliente es opcional
            request.getSession().setAttribute("clienteOpcional", true);

            
            response.sendRedirect("nuevaVenta.jsp");
        } 
        else {
            System.out.println("Acción no reconocida: " + action);
            response.getWriter().println("Acción no reconocida.");
        }
    }

    private void añadirProductoAlCarrito(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        HttpSession session = request.getSession();
        List<ProductosVenta> carritoVentas = (List<ProductosVenta>) session.getAttribute("carritoVentas");
        if (carritoVentas == null) {
            carritoVentas = new ArrayList<>();
        }

        ProductosVenta productoEnInventario = nuevaVentaDAO.obtenerProductoPorId(idProducto);
        if (productoEnInventario != null && productoEnInventario.getCantidad() >= cantidad) {
            boolean encontrado = false;

            for (ProductosVenta producto : carritoVentas) {
                if (producto.getId() == idProducto) {
                    producto.setCantidad(producto.getCantidad() + cantidad);
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                carritoVentas.add(new ProductosVenta(idProducto, productoEnInventario.getNombre(), productoEnInventario.getPrecio(), cantidad));
            }

            nuevaVentaDAO.actualizarInventario(idProducto, cantidad);
            session.setAttribute("carritoVentas", carritoVentas);
        }

        response.sendRedirect("NuevaVentaServlet");
    }

    private void eliminarProductoDelCarrito(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        HttpSession session = request.getSession();
        List<ProductosVenta> carritoVentas = (List<ProductosVenta>) session.getAttribute("carritoVentas");

        if (carritoVentas != null) {
            carritoVentas.removeIf(producto -> producto.getId() == idProducto);
        }

        session.setAttribute("carritoVentas", carritoVentas);
        response.sendRedirect("NuevaVentaServlet");
    }

    private void cancelarVenta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("carritoVentas");
        session.removeAttribute("cliente");
        response.sendRedirect("nuevaVenta.jsp");
    }

    private void generarFactura(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DocumentException {
        HttpSession session = request.getSession();

        List<ProductosVenta> carritoVentas = (List<ProductosVenta>) session.getAttribute("carritoVentas");
        Clientes cliente = (Clientes) session.getAttribute("cliente"); // Cliente puede ser null
        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String nombreUsuario = (String) session.getAttribute("nombreUsuario");
        String formaPago = request.getParameter("metodoPago");

        if (usuarioId == null || nombreUsuario == null) {
            response.getWriter().println("Usuario no identificado. Por favor, inicie sesión nuevamente.");
            return;
        }

        if (carritoVentas == null || carritoVentas.isEmpty()) {
            response.getWriter().println("No hay productos en el carrito.");
            return;
        }

        if (formaPago == null) {
            response.getWriter().println("Seleccione un método de pago.");
            return;
        }

        // Asignar cliente generico si el cliente es null
        if (cliente == null) {
            cliente = new Clientes();
            cliente.setId(0); // Cliente generico con ID = 0
            cliente.setNombre("Sin Cliente");
            cliente.setDocumento("N/A");
            cliente.setTipoDocumento("N/A");
            cliente.setTelefono("N/A");
            cliente.setDireccion("N/A");
            cliente.setEmail("N/A");
        }

        long totalFactura = carritoVentas.stream().mapToLong(p -> p.getCantidad() * (long) p.getPrecio()).sum();
        String pdfUrl = "/path/to/generated/pdf/venta_" + System.currentTimeMillis() + ".pdf";
        session.setAttribute("totalIngresos", totalFactura);

        // Registrar la factura usando el cliente generico si es necesario
        boolean facturaRegistrada = nuevaVentaDAO.registrarFactura(
                cliente.getId(), // Usa el ID del cliente generico si el cliente es null
                usuarioId, formaPago, totalFactura, pdfUrl
        );

        if (facturaRegistrada) {
            generarPDF(response, carritoVentas, cliente, totalFactura, formaPago, nombreUsuario);

            // Limpiar datos de la sesion para iniciar una nueva venta
            session.removeAttribute("carritoVentas");
            session.removeAttribute("cliente");

           
            response.sendRedirect("venta.jsp");

            System.out.println("hasta aca funciono");
        } else {
            response.getWriter().println("Error al registrar la factura.");
        }
    }




    private void generarPDF(HttpServletResponse response, List<ProductosVenta> carritoVentas, Clientes cliente, long totalFactura, String metodoPago, String nombreUsuario) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"factura_venta.pdf\"");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Paragraph titulo = new Paragraph("Factura de Venta", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22));
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);

        Paragraph nombreProyecto = new Paragraph("EasyShop", FontFactory.getFont(FontFactory.HELVETICA, 18));
        nombreProyecto.setAlignment(Element.ALIGN_CENTER);
        document.add(nombreProyecto);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Fecha: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        document.add(new Paragraph("Cliente: " + cliente.getNombre()));
        document.add(new Paragraph("Método de Pago: " + metodoPago));
        document.add(new Paragraph("Vendedor: " + nombreUsuario));

        document.add(new Paragraph(" "));
        PdfPTable table = new PdfPTable(3);
        table.addCell("Producto");
        table.addCell("Cantidad");
        table.addCell("Precio Unitario");

        for (ProductosVenta producto : carritoVentas) {
            table.addCell(producto.getNombre());
            table.addCell(String.valueOf(producto.getCantidad()));
            table.addCell("$" + producto.getPrecio());
        }

        document.add(table);
        document.add(new Paragraph(" "));
        Paragraph total = new Paragraph("Total: $" + totalFactura, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
        total.setAlignment(Element.ALIGN_RIGHT);
        document.add(total);

        document.close();
    }
}
