package com.mycompany.easyshopproyecto.servlets;

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
import com.mycompany.easyshopproyecto.logica.ProductosVenta;
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

public class NuevaVentaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
        System.out.println("Init ejecutado");
        List<ProductosVenta> productos = new ArrayList<>();
        productos.add(new ProductosVenta(1, "Coca Cola", 2500, 10));
        productos.add(new ProductosVenta(2, "Arroz", 3000, 50));
        productos.add(new ProductosVenta(3, "Frijoles", 2500, 40));
        productos.add(new ProductosVenta(4, "Aceite", 7000, 20));
        productos.add(new ProductosVenta(5, "Leche", 1800, 30));
        productos.add(new ProductosVenta(6, "Pan", 2100, 25));
        productos.add(new ProductosVenta(7, "Azúcar", 1500, 15));
        productos.add(new ProductosVenta(8, "Sal", 500, 60));
        productos.add(new ProductosVenta(9, "Jugo de Naranja", 2000, 12));
        productos.add(new ProductosVenta(10, "Huevos", 3500, 18));
        productos.add(new ProductosVenta(11, "Harina de Maíz", 2200, 35));
        productos.add(new ProductosVenta(12, "Queso", 5000, 10));
        productos.add(new ProductosVenta(13, "Café", 8500, 40));
        productos.add(new ProductosVenta(14, "Té", 1500, 50));
        productos.add(new ProductosVenta(15, "Mantequilla", 3500, 20));
        productos.add(new ProductosVenta(16, "Yogurt", 2800, 18));
        productos.add(new ProductosVenta(17, "Detergente", 12000, 15));
        productos.add(new ProductosVenta(18, "Papel Higiénico", 8000, 24));
        productos.add(new ProductosVenta(19, "Jabón de manos", 3500, 30));
        productos.add(new ProductosVenta(20, "Shampoo", 9500, 15));
        productos.add(new ProductosVenta(21, "Cereal", 4500, 25));
        productos.add(new ProductosVenta(22, "Galletas", 3000, 50));
        productos.add(new ProductosVenta(23, "Papas Fritas", 2500, 60));
        productos.add(new ProductosVenta(24, "Atún", 4000, 35));
        productos.add(new ProductosVenta(25, "Pollo", 12000, 20));
        productos.add(new ProductosVenta(26, "Carne de Res", 15000, 15));
        productos.add(new ProductosVenta(27, "Carne de Cerdo", 13000, 18));
        productos.add(new ProductosVenta(28, "Pescado", 17000, 10));
        productos.add(new ProductosVenta(29, "Zanahorias", 1800, 50));
        productos.add(new ProductosVenta(30, "Tomates", 2500, 40));
        productos.add(new ProductosVenta(31, "Papas", 2300, 45));
        productos.add(new ProductosVenta(32, "Cebolla", 1800, 35));
        productos.add(new ProductosVenta(33, "Ajo", 1200, 60));
        productos.add(new ProductosVenta(34, "Plátanos", 2200, 30));
        productos.add(new ProductosVenta(35, "Manzanas", 3200, 25));
        productos.add(new ProductosVenta(36, "Bananas", 2000, 40));
        productos.add(new ProductosVenta(37, "Uvas", 3500, 20));
        productos.add(new ProductosVenta(38, "Lechuga", 1200, 50));
        productos.add(new ProductosVenta(39, "Espinacas", 1500, 30));
        productos.add(new ProductosVenta(40, "Cilantro", 500, 60));

        // Agrega mas productos segun sea necesario

        getServletContext().setAttribute("listaProductos", productos);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        @SuppressWarnings("unchecked")
        List<ProductosVenta> listaProductos = (List<ProductosVenta>) getServletContext().getAttribute("listaProductos");

        if (listaProductos == null) {
            // Esto no deberia suceder, pero manejamos el caso por si acaso
            listaProductos = new ArrayList<>();
            getServletContext().setAttribute("listaProductos", listaProductos);
            System.out.println("Advertencia: listaProductos era null, se ha inicializado una nueva lista.");
        }

        String search = request.getParameter("search");
        List<ProductosVenta> productosFiltrados = new ArrayList<>();

        if (search != null && !search.isEmpty()) {
            for (ProductosVenta producto : listaProductos) {
                if (producto.getNombre().toLowerCase().contains(search.toLowerCase())) {
                    productosFiltrados.add(producto);
                }
            }
        } else {
            productosFiltrados = listaProductos;
        }

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
        System.out.println("Action parameter: " + action); // Para depuracion

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

            boolean encontrado = false;

            for (ProductosVenta producto : carritoVentas) {
                if (producto.getId() == idProducto) {
                    producto.setCantidad(producto.getCantidad() + cantidad);
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                // Recuperar listaProductos del ServletContext
                @SuppressWarnings("unchecked")
                List<ProductosVenta> listaProductos = (List<ProductosVenta>) getServletContext().getAttribute("listaProductos");

                if (listaProductos == null) {
                    // Esto no deberia suceder, pero manejamos el caso por si acaso
                    listaProductos = new ArrayList<>();
                    getServletContext().setAttribute("listaProductos", listaProductos);
                    System.out.println("Advertencia: listaProductos era null en añadirProductoAlCarrito(), se ha inicializado una nueva lista.");
                }

                for (ProductosVenta producto : listaProductos) {
                    if (producto.getId() == idProducto) {
                        carritoVentas.add(new ProductosVenta(producto.getId(), producto.getNombre(), producto.getPrecio(), cantidad));
                        break;
                    }
                }
            }

            session.setAttribute("carritoVentas", carritoVentas);
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
            
            Paragraph tipoFactura = new Paragraph("VENTA", FontFactory.getFont(FontFactory.HELVETICA, 14));
            tipoFactura.setAlignment(Element.ALIGN_CENTER);
            document.add(tipoFactura);

            document.add(Chunk.NEWLINE);  // Espacio en blanco separa los tituos de la tabla

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
            PdfPTable table = new PdfPTable(3); // 3 columnas: Producto, Cantidad, Precio
            table.setWidthPercentage(100); // Ancho de la tabla
            table.setSpacingBefore(10f); // Espacio antes de la tabla
            table.setSpacingAfter(10f);  // Espacio despues de la tabla

            // Añadir encabezados a la tabla
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

            // Añadir productos del carrito a la tabla
            for (ProductosVenta producto : carritoVentas) {
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
            Long totalIngresos = (Long) session.getAttribute("totalIngresos");
            if (totalIngresos == null) {
                totalIngresos = 0L;  // Inicializar si es la primera venta
            }
            totalIngresos += totalFactura;  // Sumar el total de la factura a los ingresos
            session.setAttribute("totalIngresos", totalIngresos);  // Guardar en la sesion

        } catch (DocumentException e) {
            throw new IOException("Error al generar el documento PDF", e);
        }

    } else {
        response.getWriter().println("El carrito está vacío.");
    }
}

    private void cancelarVenta(HttpServletRequest request, HttpServletResponse response) throws IOException {
    HttpSession session = request.getSession();
    // Limpiar el carrito de ventas
    session.removeAttribute("carritoVentas");

    // Redirigir de nuevo a la página de nueva venta
    response.sendRedirect(request.getContextPath() + "/nuevaVenta.jsp");
    }
}
