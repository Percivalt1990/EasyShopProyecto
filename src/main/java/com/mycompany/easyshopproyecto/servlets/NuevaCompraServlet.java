package com.mycompany.easyshopproyecto.servlets;

import com.mycompany.easyshopproyecto.dao.NuevaCompraDAO;
import com.mycompany.easyshopproyecto.logica.ProductosCompra;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;



public class NuevaCompraServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private NuevaCompraDAO nuevaCompraDAO;

    @Override
    public void init() throws ServletException {
        nuevaCompraDAO = new NuevaCompraDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        List<ProductosCompra> productosFiltrados = (search != null && !search.isEmpty()) ?
                nuevaCompraDAO.buscarProductosCompra(search) : nuevaCompraDAO.listarProductosCompra();

        request.setAttribute("listaProductosCompra", productosFiltrados);

        HttpSession session = request.getSession();
        List<ProductosCompra> carritoCompras = (List<ProductosCompra>) session.getAttribute("carritoCompras");
        request.setAttribute("carritoCompras", carritoCompras);

        request.getRequestDispatcher("nuevaCompra.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            añadirProductoAlCarrito(request, response);
        } else if ("delete".equals(action)) {
            eliminarProductoDelCarrito(request, response);
        } else if ("generateInvoice".equals(action)) {
            generarFactura(request, response);
        } else if ("cancel".equals(action)) {
            cancelarCompra(request, response);
        } else {
            response.getWriter().println("Acción no reconocida.");
        }
    }

    private void añadirProductoAlCarrito(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int idProducto = Integer.parseInt(request.getParameter("idProducto"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));

            HttpSession session = request.getSession();
            List<ProductosCompra> carritoCompras = (List<ProductosCompra>) session.getAttribute("carritoCompras");

            if (carritoCompras == null) {
                carritoCompras = new ArrayList<>();
            }

            ProductosCompra productoEnInventario = nuevaCompraDAO.obtenerProductoPorId(idProducto);
            if (productoEnInventario != null) {
                boolean encontrado = false;

                for (ProductosCompra producto : carritoCompras) {
                    if (producto.getId() == idProducto) {
                        producto.setCantidad(producto.getCantidad() + cantidad);
                        encontrado = true;
                        break;
                    }
                }

                if (!encontrado) {
                    carritoCompras.add(new ProductosCompra(idProducto, productoEnInventario.getProveedor(), productoEnInventario.getNombre(), productoEnInventario.getPrecio(), cantidad));
                }

                session.setAttribute("carritoCompras", carritoCompras);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/NuevaCompraServlet");
    }

    private void eliminarProductoDelCarrito(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int idProducto = Integer.parseInt(request.getParameter("idProducto"));

            HttpSession session = request.getSession();
            List<ProductosCompra> carritoCompras = (List<ProductosCompra>) session.getAttribute("carritoCompras");

            if (carritoCompras != null) {
                carritoCompras.removeIf(producto -> producto.getId() == idProducto);
            }

            session.setAttribute("carritoCompras", carritoCompras);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/NuevaCompraServlet");
    }

    private void generarFactura(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        List<ProductosCompra> carritoCompras = (List<ProductosCompra>) session.getAttribute("carritoCompras");

        if (carritoCompras == null || carritoCompras.isEmpty()) {
            response.getWriter().println("No hay productos en el carrito.");
            return;
        }

        long totalFactura = carritoCompras.stream()
                .mapToLong(p -> p.getCantidad() * (long) p.getPrecio())
                .sum();

        // Registrar la compra en la base de datos
        boolean facturaRegistrada = nuevaCompraDAO.registrarFactura(carritoCompras, totalFactura);

        if (facturaRegistrada) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"factura_compra.pdf\"");
            try (OutputStream out = response.getOutputStream()) {
                generarPDF(out, carritoCompras, totalFactura);
            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().println("Error al generar la factura PDF.");
            }

            // Limpiar el carrito después de registrar la compra
            session.removeAttribute("carritoCompras");
        } else {
            response.getWriter().println("Error al registrar la compra en la base de datos.");
        }
    }

    private void generarPDF(OutputStream out, List<ProductosCompra> carritoCompras, long totalFactura) throws DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        Paragraph titulo = new Paragraph("Factura de Compra", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22));
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);

        document.add(new Paragraph("Fecha: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(4);
        table.addCell("Proveedor");
        table.addCell("Producto");
        table.addCell("Cantidad");
        table.addCell("Precio Unitario");

        for (ProductosCompra producto : carritoCompras) {
            table.addCell(producto.getProveedor());
            table.addCell(producto.getNombre());
            table.addCell(String.valueOf(producto.getCantidad()));
            table.addCell("$" + producto.getPrecio());
        }

        document.add(table);

        Paragraph total = new Paragraph("Total: $" + totalFactura, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
        total.setAlignment(Element.ALIGN_RIGHT);
        document.add(total);

        document.close();
    }




    private void cancelarCompra(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("carritoCompras");
        response.sendRedirect(request.getContextPath() + "/nuevaCompra.jsp");
    }
}
