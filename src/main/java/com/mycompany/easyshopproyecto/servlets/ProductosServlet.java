package com.mycompany.easyshopproyecto.servlets;

import com.mycompany.easyshopproyecto.logica.Productos;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Mapeo del servlet a /ProductosServlet
@WebServlet(name = "ProductosServlet", urlPatterns = {"/ProductosServlet"})
public class ProductosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Lista temporal para almacenar productos aqui se debe reemplazar por la base de datos
    private List<Productos> productos = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // Inicializar lista de productos de prueba si esta vacia
        if (productos.isEmpty()) {
            //elementos de prueba
            Productos producto1 = new Productos(1, "Granos", "Arroz", 200, 1500, "Arroz blanco de grano largo", "1"); // ID = 1, Categoría = Granos, Nombre = Arroz, Cantidad = 200, Precio = 2.5, Descripción = Arroz blanco de grano largo, ID Proveedor = 1
            Productos producto2 = new Productos(2, "Despensa", "Aceite de cocina", 150, 3800, "Aceite vegetal 1 litro", "2");
            Productos producto3 = new Productos(3, "Lácteos", "Leche", 180, 2400, "Leche entera 1 litro", "3"); 
            Productos producto4 = new Productos(4, "Panadería", "Pan de molde", 100, 1400, "Pan de molde integral", "4"); 
            Productos producto5 = new Productos(5, "Huevos", "Huevos", 250, 6000, "Docena de huevos", "5"); 
            Productos producto6 = new Productos(6, "Azúcar y Endulzantes", "Azúcar", 180, 2100, "Azúcar blanca 1 kg", "6"); 
            Productos producto7 = new Productos(7, "Especias", "Sal", 120, 2100, "Sal refinada 500g", "7"); 
            Productos producto8 = new Productos(8, "Harinas", "Harina", 160, 1800, "Harina de trigo 1 kg", "8"); 
            Productos producto9 = new Productos(9, "Granos", "Frijoles", 140, 2900, "Frijol rojo 1 kg", "9"); 
            Productos producto10 = new Productos(10, "Pasta", "Pasta", 170, 3000, "Pasta de espagueti 500g", "10"); 



            // agregar los productos a la lista de prueba
            productos.add(producto1);
            productos.add(producto2);
            productos.add(producto3);
            productos.add(producto4);
            productos.add(producto5);
            productos.add(producto6);
            productos.add(producto7);
            productos.add(producto8);
            productos.add(producto9);
            productos.add(producto10);
        }

            // Obtener el parametro de busqueda
            String search = request.getParameter("search");

            // Filtrar productos si el parametro de busqueda no esta vacio
            List<Productos> productosFiltrados = new ArrayList<>();
            if (search != null && !search.isEmpty()) {
                for (Productos producto : productos) {
                    if (producto.getNombre().toLowerCase().contains(search.toLowerCase()) || 
                        producto.getDescripcion().toLowerCase().contains(search.toLowerCase())) {
                        productosFiltrados.add(producto);
                    }
                }
            } else {
                productosFiltrados = productos; // Si no hay busqueda, mostrar todos los productos
            }

            // Pasar los productos filtrados a la vista (JSP)
            request.setAttribute("productosFiltrados", productosFiltrados);

            // Redirigir a la pagina de productos
            request.getRequestDispatcher("productos.jsp").forward(request, response);
    }
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "crear":
                crearProducto(request, response);
                break;
            case "editar":
                editarProducto(request, response);
                break;
            case "eliminar":
                eliminarProducto(request, response);
                break;
            case "cargar":
                cargarProducto(request, response);
                break;
            default:
                listarProductos(request, response);
                break;
        }
    }

    // metodo para crear un nuevo elemento producto
    private void crearProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los parámetros del formulario
        String nombre = request.getParameter("nombre");
        String categoria = request.getParameter("categoria");
        String cantidadStr = request.getParameter("cantidad");
        String precioStr = request.getParameter("precio");
        String descripcion = request.getParameter("descripcion");
        String idProveedor = request.getParameter("idProveedor");

      

        // Generar un ID automaticamente
        int id = productos.size() + 1; 
        // Convertir la cadena de cantidad a un numero entero y asignacion de vacio a 0
        int cantidad = (cantidadStr != null && !cantidadStr.isEmpty()) ? Integer.parseInt(cantidadStr) : 0;
        //Convertir la cadena de precio a un numero decimal y asignacion de vacio a 0.0 (double)
        double precio = (precioStr != null && !precioStr.isEmpty()) ? Double.parseDouble(precioStr) : 0.0;

        // Crear un nuevo producto
        Productos producto = new Productos(id, categoria, nombre, cantidad, precio, descripcion, idProveedor);
        
        //Añadir el nuevo producto a la lista
        productos.add(producto);

        // Imprimir datos recibidos para depuracion en consola para verifiacion
        System.out.println("Datos recibidos: ");
        System.out.println("ID: " + id);
        System.out.println("Categoría: " + categoria);
        System.out.println("Nombre: " + nombre);
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Precio: " + precio);
        System.out.println("Descripción: " + descripcion);
        System.out.println("ID Proveedor: " + idProveedor);

        // Redirigir a la pagina de productos despues de crear
        listarProductos(request, response);
    }

    // metodo para editar un producto existente
    private void editarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los parametros del formulario
        String idStr = request.getParameter("id");
        String categoria = request.getParameter("categoria");
        String nombre = request.getParameter("nombre");
        String cantidadStr = request.getParameter("cantidad");
        String precioStr = request.getParameter("precio");
        String descripcion = request.getParameter("descripcion");
        String idProveedor = request.getParameter("idProveedor");

        // Validar que los campos obligatorios tengan valor asignado
        if (nombre == null || nombre.isEmpty()) {
            request.setAttribute("error", "El nombre del producto es obligatorio.");
            request.getRequestDispatcher("editarProducto.jsp").forward(request, response);
            return;
        }

        // Validar que el ID esté presente
        int id = (idStr != null && !idStr.isEmpty()) ? Integer.parseInt(idStr) : 0;
        
        // Validar que el ID no sea 0
        if (id == 0) {
            request.setAttribute("error", "ID de producto no valido.");
            request.getRequestDispatcher("editarProducto.jsp").forward(request, response);
            return;
        }

        //inicia el bucle para encontrar el producto por ID y actualizar los datos
        boolean productoEncontrado = false;
        for (Productos producto : productos) {
            if (producto.getId() == id) {
                producto.setCategoria(categoria);
                producto.setNombre(nombre);
                producto.setCantidad((cantidadStr != null && !cantidadStr.isEmpty()) ? Integer.parseInt(cantidadStr) : 0);
                producto.setPrecio((precioStr != null && !precioStr.isEmpty()) ? Double.parseDouble(precioStr) : 0.0);
                producto.setDescripcion(descripcion);
                producto.setIdProveedor(idProveedor);
                productoEncontrado = true;
                break;//Finaliza y sale del bucle cuando ya se encuentra el producto
            }
        }
        // Si no se encuentra producto devuelve un mensaje de error 
        if (!productoEncontrado) {
            request.setAttribute("error", "Producto no encontrado con ID: " + id);
            request.getRequestDispatcher("editarProducto.jsp").forward(request, response);
            return;
        }

        // Imprimir datos actualizados en consola para verificacion
        System.out.println("Datos recibidos de la actualizacion: ");
        System.out.println("ID: " + id);
        System.out.println("Categoría: " + categoria);
        System.out.println("Nombre: " + nombre);
        System.out.println("Cantidad: " + cantidadStr);
        System.out.println("Precio: " + precioStr);
        System.out.println("Descripción: " + descripcion);
        System.out.println("ID Proveedor: " + idProveedor);

        // Redirigir a la pagina de listado de productos
        response.sendRedirect("ProductosServlet");//lo redirige a doGET para nueva peticion
    }

    // metodo para eliminar un producto
    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // Obtener el ID del producto a eliminar
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            request.setAttribute("error", "ID de producto no proporcionado.");
            listarProductos(request, response);
            return;
        }

        int id = Integer.parseInt(idStr);

        // Eliminar el producto de la lista
        boolean eliminado = productos.removeIf(producto -> producto.getId() == id);

        if (eliminado) {
            System.out.println("Producto eliminado con ID: " + id);
        } else {
            request.setAttribute("error", "Producto no encontrado con ID: " + id);
        }

        // Redirigir a la pagina de productos
        listarProductos(request, response);
    }

    // metodo para listar todos los productos
    private void listarProductos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //pasa la lista productos al JSP
        request.setAttribute("productos", productos);

        // Imprimir la lista de productos para verificacion
        System.out.println("Listado de productos: ");
        for (Productos producto : productos) {
            System.out.println("ID: " + producto.getId() + ", Nombre: " + producto.getNombre());
        }

        request.getRequestDispatcher("productos.jsp").forward(request, response);
    }

    // metodo para cargar un producto especifico para edicion
    private void cargarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el ID del producto
        String idStr = request.getParameter("id");
        
        if (idStr == null || idStr.isEmpty()) {
            request.setAttribute("error", "ID de producto no proporcionado.");
            listarProductos(request, response);
            return;
        }

        int id = Integer.parseInt(idStr);

        // Buscar el producto por ID
        Productos producto = null;
        for (Productos p : productos) {
            if (p.getId() == id) {
                producto = p;
                break;
            }
        }

        if (producto != null) {
            request.setAttribute("producto", producto);
            request.getRequestDispatcher("editarProducto.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Producto no encontrado con ID: " + id);
            listarProductos(request, response);
        }
    }
}
