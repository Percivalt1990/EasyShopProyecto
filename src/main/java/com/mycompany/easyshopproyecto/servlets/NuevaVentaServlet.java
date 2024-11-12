package com.mycompany.easyshopproyecto.servlets;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.mycompany.easyshopproyecto.dao.NuevaVentaDAO;
import com.mycompany.easyshopproyecto.logica.ProductosVenta;
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
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        List<ProductosVenta> productosFiltrados = (search != null && !search.isEmpty()) ?
                nuevaVentaDAO.buscarProductos(search) : nuevaVentaDAO.listarProductos();

        request.setAttribute("listaProductos", productosFiltrados);

        // Añadir carritoVentas al request si deseas acceder a el en el JSP
        HttpSession session = request.getSession();
        List<ProductosVenta> carritoVentas = (List<ProductosVenta>) session.getAttribute("carritoVentas");
        request.setAttribute("carritoVentas", carritoVentas);

        request.getRequestDispatcher("nuevaVenta.jsp").forward(request, response);
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
            try {
                generarFactura(request, response);
            } catch (DocumentException ex) {
                Logger.getLogger(NuevaVentaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("cancel".equals(action)) {
            cancelarVenta(request, response);
        } else {
            response.getWriter().println("Acción no reconocida.");
        }
    }

    private void añadirProductoAlCarrito(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
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

                nuevaVentaDAO.actualizarInventario(idProducto, cantidad);  // Actualizar inventario en la base de datos
                session.setAttribute("carritoVentas", carritoVentas);
            } else {
                System.out.println("Cantidad insuficiente en inventario para el producto ID: " + idProducto);
            }

        } catch (NumberFormatException e) {
            System.out.println("Error al convertir el ID o la cantidad: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/NuevaVentaServlet");
    }

    private void eliminarProductoDelCarrito(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int idProducto = Integer.parseInt(request.getParameter("idProducto"));

            HttpSession session = request.getSession();
            List<ProductosVenta> carritoVentas = (List<ProductosVenta>) session.getAttribute("carritoVentas");

            if (carritoVentas != null) {
                carritoVentas.removeIf(producto -> producto.getId() == idProducto);
            }

            session.setAttribute("carritoVentas", carritoVentas);
        } catch (NumberFormatException e) {
            System.out.println("Error al convertir el ID del producto: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/NuevaVentaServlet");
    }

    private void generarFactura(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DocumentException {
        HttpSession session = request.getSession();
        List<ProductosVenta> carritoVentas = (List<ProductosVenta>) session.getAttribute("carritoVentas");

        if (carritoVentas != null && !carritoVentas.isEmpty()) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"factura.pdf\"");

            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            Paragraph titulo = new Paragraph("EasyShop", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22));
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            Paragraph infoEmpresa = new Paragraph("Medellin, Colombia \nNIT: 65498713213-9", FontFactory.getFont(FontFactory.HELVETICA, 12));
            infoEmpresa.setAlignment(Element.ALIGN_CENTER);
            document.add(infoEmpresa);

            Paragraph tipoFactura = new Paragraph("VENTA", FontFactory.getFont(FontFactory.HELVETICA, 14));
            tipoFactura.setAlignment(Element.ALIGN_CENTER);
            document.add(tipoFactura);

            document.add(Chunk.NEWLINE);

            LocalDateTime fechaActual = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaFormateada = fechaActual.format(formatter);
            Paragraph fecha = new Paragraph("Fecha: " + fechaFormateada, FontFactory.getFont(FontFactory.HELVETICA, 12));
            fecha.setAlignment(Element.ALIGN_RIGHT);
            document.add(fecha);

            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            PdfPCell c1 = new PdfPCell(new Phrase("Producto", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setPadding(10);
            table.addCell(c1);

            PdfPCell c2 = new PdfPCell(new Phrase("Cantidad", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            c2.setPadding(10);
            table.addCell(c2);

            PdfPCell c3 = new PdfPCell(new Phrase("Precio Unitario", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            c3.setHorizontalAlignment(Element.ALIGN_CENTER);
            c3.setPadding(10);
            table.addCell(c3);

            long totalFactura = 0;

            for (ProductosVenta producto : carritoVentas) {
                table.addCell(new PdfPCell(new Phrase(producto.getNombre())));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(producto.getCantidad()))));
                table.addCell(new PdfPCell(new Phrase("$" + producto.getPrecio())));
                totalFactura += producto.getCantidad() * producto.getPrecio();
            }

            document.add(table);
            document.add(Chunk.NEWLINE);

            PdfPTable totalTable = new PdfPTable(1);
            totalTable.setWidthPercentage(100);

            PdfPCell totalCell = new PdfPCell(new Phrase("Total: $" + totalFactura, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalCell.setPadding(10);
            totalTable.addCell(totalCell);

            document.add(totalTable);

            document.close();

            Long totalIngresos = (Long) session.getAttribute("totalIngresos");
            totalIngresos = (totalIngresos == null) ? totalFactura : totalIngresos + totalFactura;
            session.setAttribute("totalIngresos", totalIngresos);
        } else {
            response.getWriter().println("El carrito está vacío.");
        }
    }

    private void cancelarVenta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("carritoVentas");
        response.sendRedirect(request.getContextPath() + "/nuevaVenta.jsp");
    }
}
