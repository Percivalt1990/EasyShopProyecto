package com.mycompany.easyshopproyecto.servlets;

import com.mycompany.easyshopproyecto.dao.ProveedoresDAO;
import com.mycompany.easyshopproyecto.logica.Proveedores;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProveedoresServlet", urlPatterns = {"/ProveedoresServlet"})
public class ProveedoresServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProveedoresDAO proveedoresDAO;

    @Override
    public void init() {
        proveedoresDAO = new ProveedoresDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        List<Proveedores> proveedoresFiltrados = (search != null && !search.isEmpty()) ?
                proveedoresDAO.buscarProveedores(search) : proveedoresDAO.listarProveedores();

        request.setAttribute("proveedoresFiltrados", proveedoresFiltrados);
        request.getRequestDispatcher("proveedores.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "crear":
                crearProveedor(request, response);
                break;
            case "editar":
                editarProveedor(request, response);
                break;
            case "eliminar":
                eliminarProveedor(request, response);
                break;
            case "cargar":
                cargarProveedor(request, response);
                break;
            default:
                listarProveedores(request, response);
                break;
        }
    }

    private void crearProveedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Proveedores proveedor = new Proveedores();
        proveedor.setNombre(request.getParameter("nombre"));
        proveedor.setNumeroTelefonico(request.getParameter("telefono"));
        proveedor.setDireccion(request.getParameter("direccion"));
        proveedor.setEmail(request.getParameter("email"));

        proveedoresDAO.crearProveedor(proveedor);
        listarProveedores(request, response);
    }

    private void editarProveedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Proveedores proveedor = proveedoresDAO.obtenerProveedorPorId(id);
        if (proveedor != null) {
            proveedor.setNombre(request.getParameter("nombre"));
            proveedor.setNumeroTelefonico(request.getParameter("telefono"));
            proveedor.setDireccion(request.getParameter("direccion"));
            proveedor.setEmail(request.getParameter("email"));

            proveedoresDAO.actualizarProveedor(proveedor);
        }
        listarProveedores(request, response);
    }

    private void eliminarProveedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        proveedoresDAO.eliminarProveedor(id);
        listarProveedores(request, response);
    }

    private void listarProveedores(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Proveedores> proveedores = proveedoresDAO.listarProveedores();
        request.setAttribute("proveedores", proveedores);
        request.getRequestDispatcher("proveedores.jsp").forward(request, response);
    }

    private void cargarProveedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Proveedores proveedor = proveedoresDAO.obtenerProveedorPorId(id);
        if (proveedor != null) {
            request.setAttribute("proveedor", proveedor);
            request.getRequestDispatcher("editarProveedor.jsp").forward(request, response);
        } else {
            listarProveedores(request, response);
        }
    }
}
