package com.mycompany.easyshopproyecto.servlets;

import com.mycompany.easyshopproyecto.dao.NuevaCompraDAO;
import com.mycompany.easyshopproyecto.logica.ProductosCompra;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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

        if (carritoCompras != null && !carritoCompras.isEmpty()) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"factura_compra.pdf\"");

            try (OutputStream out = response.getOutputStream()) {
                Document document = new Document();
                PdfWriter.getInstance(document, out);
                document.open();

                Paragraph titulo = new Paragraph("EasyShop - Factura de Compra", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22));
                titulo.setAlignment(Element.ALIGN_CENTER);
                document.add(titulo);

                Paragraph infoEmpresa = new Paragraph("Medellin, Colombia \nNIT: 65498713213-9", FontFactory.getFont(FontFactory.HELVETICA, 12));
                infoEmpresa.setAlignment(Element.ALIGN_CENTER);
                document.add(infoEmpresa);

                Paragraph tipoFactura = new Paragraph("COMPRA", FontFactory.getFont(FontFactory.HELVETICA, 14));
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

                PdfPTable table = new PdfPTable(4); // 4 columnas: Proveedor, Producto, Cantidad, Precio
                table.setWidthPercentage(100);

                PdfPCell c1 = new PdfPCell(new Phrase("Proveedor", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);

                PdfPCell c2 = new PdfPCell(new Phrase("Producto", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c2);

                PdfPCell c3 = new PdfPCell(new Phrase("Cantidad", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c3);

                PdfPCell c4 = new PdfPCell(new Phrase("Precio Unitario", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c4);

                long totalFactura = 0;

                for (ProductosCompra producto : carritoCompras) {
                    table.addCell(new PdfPCell(new Phrase(producto.getProveedor())));
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
                totalTable.addCell(totalCell);

                document.add(totalTable);
                document.close();

                session.setAttribute("totalEgresos", totalFactura);  // Actualizar el total en la sesión
            } catch (DocumentException e) {
                throw new IOException("Error al generar el documento PDF", e);
            }
        } else {
            response.getWriter().println("El carrito está vacío.");
        }
    }

    private void cancelarCompra(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("carritoCompras");
        response.sendRedirect(request.getContextPath() + "/nuevaCompra.jsp");
    }
}
