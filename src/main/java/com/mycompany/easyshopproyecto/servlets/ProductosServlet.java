package com.mycompany.easyshopproyecto.servlets;

import com.mycompany.easyshopproyecto.dao.ProductosDAO;
import com.mycompany.easyshopproyecto.logica.Productos;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductosServlet", urlPatterns = {"/ProductosServlet"})
public class ProductosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductosDAO productosDAO;

    @Override
    public void init() {
        productosDAO = new ProductosDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        List<Productos> productosFiltrados = (search != null && !search.isEmpty()) ?
                productosDAO.buscarProductos(search) : productosDAO.listarProductos();

        request.setAttribute("productosFiltrados", productosFiltrados);
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

    private void crearProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Productos producto = new Productos();
        producto.setIdCategoria(request.getParameter("categoria"));
        producto.setNombre(request.getParameter("nombre"));
        producto.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
        producto.setPrecio(Double.parseDouble(request.getParameter("precio")));
        producto.setDescripcion(request.getParameter("descripcion"));
        producto.setIdProveedor(request.getParameter("idProveedor"));

        productosDAO.crearProducto(producto);
        listarProductos(request, response);
    }

    private void editarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Productos producto = productosDAO.obtenerProductoPorId(id);
        if (producto != null) {
            producto.setIdCategoria(request.getParameter("categoria"));
            producto.setNombre(request.getParameter("nombre"));
            producto.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
            producto.setPrecio(Double.parseDouble(request.getParameter("precio")));
            producto.setDescripcion(request.getParameter("descripcion"));
            producto.setIdProveedor(request.getParameter("idProveedor"));

            productosDAO.actualizarProducto(producto);
        }
        listarProductos(request, response);
    }

    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productosDAO.eliminarProducto(id);
        listarProductos(request, response);
    }

    private void listarProductos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Productos> productos = productosDAO.listarProductos();
        request.setAttribute("productos", productos);
        request.getRequestDispatcher("productos.jsp").forward(request, response);
    }

    private void cargarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Productos producto = productosDAO.obtenerProductoPorId(id);
        if (producto != null) {
            request.setAttribute("producto", producto);
            request.getRequestDispatcher("editarProducto.jsp").forward(request, response);
        } else {
            listarProductos(request, response);
        }
    }
}
