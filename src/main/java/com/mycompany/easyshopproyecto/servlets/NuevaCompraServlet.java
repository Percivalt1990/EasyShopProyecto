
package com.mycompany.easyshopproyecto.servlets;

import com.mycompany.easyshopproyecto.logica.ProductosCompra;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NuevaCompraServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
        System.out.println("Init ejecutado");
        List<ProductosCompra> productos = new ArrayList<>();
        productos.add(new ProductosCompra(1, "Proveedor A", "Coca Cola", 2500, 10));
        productos.add(new ProductosCompra(2, "Proveedor B", "Arroz", 3000, 50));
        productos.add(new ProductosCompra(3, "Proveedor A", "Frijoles", 2500, 40));
        productos.add(new ProductosCompra(4, "Proveedor C", "Aceite", 7000, 20));
        productos.add(new ProductosCompra(5, "Proveedor B", "Leche", 1800, 30));
        productos.add(new ProductosCompra(6, "Proveedor A", "Pan", 2100, 25));
        productos.add(new ProductosCompra(7, "Proveedor D", "Azúcar", 1500, 15));
        productos.add(new ProductosCompra(8, "Proveedor E", "Sal", 500, 60));
        productos.add(new ProductosCompra(9, "Proveedor F", "Jugo de Naranja", 2000, 12));
        productos.add(new ProductosCompra(10, "Proveedor G", "Huevos", 3500, 18));
        productos.add(new ProductosCompra(11, "Proveedor H", "Harina de Maíz", 2200, 35));
        productos.add(new ProductosCompra(12, "Proveedor I", "Queso", 5000, 10));
        productos.add(new ProductosCompra(13, "Proveedor J", "Café", 8500, 40));
        productos.add(new ProductosCompra(14, "Proveedor K", "Té", 1500, 50));
        productos.add(new ProductosCompra(15, "Proveedor L", "Mantequilla", 3500, 20));
        productos.add(new ProductosCompra(16, "Proveedor M", "Yogurt", 2800, 18));
        productos.add(new ProductosCompra(17, "Proveedor N", "Detergente", 12000, 15));
        productos.add(new ProductosCompra(18, "Proveedor O", "Papel Higiénico", 8000, 24));
        productos.add(new ProductosCompra(19, "Proveedor P", "Jabón de manos", 3500, 30));
        productos.add(new ProductosCompra(20, "Proveedor Q", "Shampoo", 9500, 15));
        productos.add(new ProductosCompra(21, "Proveedor R", "Cereal", 4500, 25));
        productos.add(new ProductosCompra(22, "Proveedor S", "Galletas", 3000, 50));
        productos.add(new ProductosCompra(23, "Proveedor T", "Papas Fritas", 2500, 60));
        productos.add(new ProductosCompra(24, "Proveedor U", "Atún", 4000, 35));
        productos.add(new ProductosCompra(25, "Proveedor V", "Pollo", 12000, 20));
        productos.add(new ProductosCompra(26, "Proveedor W", "Carne de Res", 15000, 15));
        productos.add(new ProductosCompra(27, "Proveedor X", "Carne de Cerdo", 13000, 18));
        productos.add(new ProductosCompra(28, "Proveedor Y", "Pescado", 17000, 10));
        productos.add(new ProductosCompra(29, "Proveedor Z", "Zanahorias", 1800, 50));
        productos.add(new ProductosCompra(30, "Proveedor A", "Tomates", 2500, 40));
        productos.add(new ProductosCompra(31, "Proveedor A", "Papas", 2300, 45));
        productos.add(new ProductosCompra(32, "Proveedor A", "Cebolla", 1800, 35));
        productos.add(new ProductosCompra(33, "Proveedor A", "Ajo", 1200, 60));
        productos.add(new ProductosCompra(34, "Proveedor A", "Plátanos", 2200, 30));
        productos.add(new ProductosCompra(35, "Proveedor A", "Manzanas", 3200, 25));
        productos.add(new ProductosCompra(36, "Proveedor G", "Bananas", 2000, 40));
        productos.add(new ProductosCompra(37, "Proveedor H", "Uvas", 3500, 20));
        productos.add(new ProductosCompra(38, "Proveedor I", "Lechuga", 1200, 50));
        productos.add(new ProductosCompra(39, "Proveedor J", "Espinacas", 1500, 30));
        productos.add(new ProductosCompra(40, "Proveedor K", "Cilantro", 500, 60));

        
        getServletContext().setAttribute("listaProductosCompra", productos);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        @SuppressWarnings("unchecked")
        List<ProductosCompra> listaProductosCompra = (List<ProductosCompra>) getServletContext().getAttribute("listaProductosCompra");

        if (listaProductosCompra == null) {
            listaProductosCompra = new ArrayList<>();
            getServletContext().setAttribute("listaProductosCompra", listaProductosCompra);
            System.out.println("Advertencia: listaProductosCompra era null, se ha inicializado una nueva lista.");
        }

        String search = request.getParameter("search");
        List<ProductosCompra> productosFiltrados = new ArrayList<>();

        if (search != null && !search.isEmpty()) {
            for (ProductosCompra producto : listaProductosCompra) {
                if (producto.getNombre().toLowerCase().contains(search.toLowerCase())) {
                    productosFiltrados.add(producto);
                }
            }
        } else {
            productosFiltrados = listaProductosCompra;
        }

        request.setAttribute("listaProductosCompra", productosFiltrados);

        // Añadir carritoCompras al request si deseas acceder a él en el JSP
        HttpSession session = request.getSession();
        List<ProductosCompra> carritoCompras = (List<ProductosCompra>) session.getAttribute("carritoCompras");
        request.setAttribute("carritoCompras", carritoCompras);

        request.getRequestDispatcher("nuevaCompra.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        System.out.println("Action parameter: " + action); // Para depuración

        if ("add".equals(action)) {
            añadirProductoAlCarrito(request, response);
        } else if ("delete".equals(action)) {
            eliminarProductoDelCarrito(request, response);
        } else if ("generateInvoice".equals(action)) {
            try {  
                generarFactura(request, response);
            } catch (DocumentException ex) {
                Logger.getLogger(NuevaCompraServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
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

            boolean encontrado = false;

            for (ProductosCompra producto : carritoCompras) {
                if (producto.getId() == idProducto) { // Cambiar a un método adecuado para comprobar
                    producto.setCantidad(producto.getCantidad() + cantidad);
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                // Recuperar listaProductos del ServletContext
                @SuppressWarnings("unchecked")
                List<ProductosCompra> listaProductosCompra = (List<ProductosCompra>) getServletContext().getAttribute("listaProductosCompra");

                if (listaProductosCompra == null) {
                    listaProductosCompra = new ArrayList<>();
                    getServletContext().setAttribute("listaProductosCompra", listaProductosCompra);
                    System.out.println("Advertencia: listaProductosCompra era null en añadirProductoAlCarrito(), se ha inicializado una nueva lista.");
                }

                for (ProductosCompra producto : listaProductosCompra) {
                    if (producto.getId() == idProducto) {
                        // Se añade el producto al carrito
                        carritoCompras.add(new ProductosCompra(producto.getId(), producto.getProveedor(), producto.getNombre(), producto.getPrecio(), cantidad));
                        break;
                    }
                }
            }

            session.setAttribute("carritoCompras", carritoCompras);
        } catch (NumberFormatException e) {
            System.out.println("Error al convertir el ID del producto o cantidad: " + e.getMessage());
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
            System.out.println("Error al convertir el ID del producto: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/NuevaCompraServlet");
    }
    
    private void generarFactura(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DocumentException {
    HttpSession session = request.getSession();
    List<ProductosCompra> carritoCompras = (List<ProductosCompra>) session.getAttribute("carritoCompras");

    if (carritoCompras != null && !carritoCompras.isEmpty()) {
        // Configurar la respuesta para PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"factura.pdf\"");

        // Crear el documento PDF
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Centrar título e información de la empresa
            Paragraph titulo = new Paragraph("EasyShop", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22));
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            Paragraph infoEmpresa = new Paragraph("Medellin, Colombia \nNIT: 65498713213-9", FontFactory.getFont(FontFactory.HELVETICA, 12));
            infoEmpresa.setAlignment(Element.ALIGN_CENTER);
            document.add(infoEmpresa);
            
            Paragraph tipoFactura = new Paragraph("COMPRA", FontFactory.getFont(FontFactory.HELVETICA, 14));
            tipoFactura.setAlignment(Element.ALIGN_CENTER);
            document.add(tipoFactura);

            document.add(Chunk.NEWLINE);  // Espacio en blanco

            // Añadir fecha de emisión
            LocalDateTime fechaActual = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaFormateada = fechaActual.format(formatter);
            Paragraph fecha = new Paragraph("Fecha: " + fechaFormateada, FontFactory.getFont(FontFactory.HELVETICA, 12));
            fecha.setAlignment(Element.ALIGN_RIGHT);  // Alinear a la derecha
            document.add(fecha);

            document.add(Chunk.NEWLINE);  // Espacio en blanco

            // Añadir encabezado de productos
            Paragraph productosTitulo = new Paragraph("Productos", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
            productosTitulo.setAlignment(Element.ALIGN_LEFT);
            document.add(productosTitulo);

            document.add(Chunk.NEWLINE);  // Espacio en blanco

            // Crear una tabla para los productos
            PdfPTable table = new PdfPTable(4); // 4 columnas:Proveedor, Producto, Cantidad, Precio
            table.setWidthPercentage(100); // Ancho de la tabla
            table.setSpacingBefore(10f); // Espacio antes de la tabla
            table.setSpacingAfter(10f);  // Espacio después de la tabla
            
            //Añadir encabezados de tabla
            PdfPCell c1 = new PdfPCell(new Phrase("Proveedor", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setPadding(10);
            table.addCell(c1);

            // Añadir encabezados a la tabla
            PdfPCell c2 = new PdfPCell(new Phrase("Producto", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            c2.setPadding(10);
            table.addCell(c2);

            PdfPCell c3 = new PdfPCell(new Phrase("Cantidad", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            c3.setHorizontalAlignment(Element.ALIGN_CENTER);
            c3.setPadding(10);
            table.addCell(c3);

            PdfPCell c4 = new PdfPCell(new Phrase("Precio Unitario", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            c4.setHorizontalAlignment(Element.ALIGN_CENTER);
            c4.setPadding(10);
            table.addCell(c4);

            long totalFactura = 0;

            // Añadir productos del carrito a la tabla
            for (ProductosCompra producto : carritoCompras) {
                table.addCell(new PdfPCell(new Phrase(producto.getProveedor())));
                table.addCell(new PdfPCell(new Phrase(producto.getNombre())));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(producto.getCantidad()))));
                table.addCell(new PdfPCell(new Phrase("$" + producto.getPrecio())));

                totalFactura += producto.getCantidad() * producto.getPrecio();  // Calcula el total de la factura
            }

            document.add(table);

            document.add(Chunk.NEWLINE);  // Espacio en blanco

            // Mostrar total de la factura en un recuadro
            PdfPTable totalTable = new PdfPTable(1); // 1 columna para el total
            totalTable.setWidthPercentage(100);

            PdfPCell totalCell = new PdfPCell(new Phrase("Total: $" + totalFactura, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalCell.setPadding(10);
            totalTable.addCell(totalCell);

            document.add(totalTable);

            document.close();

            // Actualizar ingresos totales
            Long totalEgresos = (Long) session.getAttribute("totalEgresos");
            if (totalEgresos == null) {
                totalEgresos = 0L;  // Inicializar si es la primera venta
            }
            totalEgresos += totalFactura;  // Sumar el total de la factura a los ingresos
            session.setAttribute("totalEgresos", totalEgresos);  // Guardar en la sesión

        } catch (DocumentException e) {
            throw new IOException("Error al generar el documento PDF", e);
        }

    } else {
        response.getWriter().println("El carrito está vacío.");
    }
}

    
    private void cancelarCompra(HttpServletRequest request, HttpServletResponse response) throws IOException {
    HttpSession session = request.getSession();
    // Limpiar el carrito de ventas
    session.removeAttribute("carritoCompras");

    // Redirigir de nuevo a la página de nueva venta
    response.sendRedirect(request.getContextPath() + "/nuevaCompra.jsp");
    }
}
