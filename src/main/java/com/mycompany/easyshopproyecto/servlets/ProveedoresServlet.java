package com.mycompany.easyshopproyecto.servlets;

import com.mycompany.easyshopproyecto.dao.ProveedoresDAO;
import com.mycompany.easyshopproyecto.logica.Proveedores;
import com.mycompany.easyshopproyecto.dao.ConexionDB;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProveedoresServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProveedoresDAO proveedorDAO;

    @Override
    public void init() throws ServletException {
        Connection connection = ConexionDB.getConnection();
        proveedorDAO = new ProveedoresDAO(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        try {
            List<Proveedores> proveedoresFiltrados = (search == null || search.isEmpty())
                    ? proveedorDAO.listarProveedores()
                    : proveedorDAO.buscarProveedores(search);
            request.setAttribute("proveedoresFiltrados", proveedoresFiltrados);
            request.getRequestDispatcher("proveedores.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener proveedores.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
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
                    doGet(request, response);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en la operación solicitada.");
        }
    }

    private void crearProveedor(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        Proveedores nuevoProveedor = new Proveedores();
        nuevoProveedor.setNombre(request.getParameter("nombre"));
        nuevoProveedor.setNumeroTelefonico(request.getParameter("telefono"));
        nuevoProveedor.setDireccion(request.getParameter("direccion"));
        nuevoProveedor.setEmail(request.getParameter("email"));
        proveedorDAO.crearProveedor(nuevoProveedor);
        response.sendRedirect("ProveedoresServlet");
    }

    private void editarProveedor(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Proveedores proveedor = proveedorDAO.obtenerProveedorPorId(id);
        if (proveedor == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Proveedor no encontrado.");
            return;
        }
        proveedor.setNombre(request.getParameter("nombre"));
        proveedor.setNumeroTelefonico(request.getParameter("telefono"));
        proveedor.setDireccion(request.getParameter("direccion"));
        proveedor.setEmail(request.getParameter("email"));
        proveedorDAO.actualizarProveedor(proveedor);
        response.sendRedirect("ProveedoresServlet");
    }

    private void eliminarProveedor(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        proveedorDAO.eliminarProveedor(id);
        response.sendRedirect("ProveedoresServlet");
    }

    private void cargarProveedor(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Proveedores proveedor = proveedorDAO.obtenerProveedorPorId(id);
        if (proveedor != null) {
            request.setAttribute("proveedor", proveedor);
            request.getRequestDispatcher("editarProveedor.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Proveedor no encontrado.");
        }
    }
}
