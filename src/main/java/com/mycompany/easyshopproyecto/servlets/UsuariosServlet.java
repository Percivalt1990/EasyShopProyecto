package com.mycompany.easyshopproyecto.servlets;

import com.mycompany.easyshopproyecto.dao.ConexionDB;
import com.mycompany.easyshopproyecto.dao.UsuarioDAO;
import com.mycompany.easyshopproyecto.logica.Usuarios;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UsuariosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO;
    private Connection connection;

    @Override
    public void init() throws ServletException {
        connection = ConexionDB.getConnection();
        usuarioDAO = new UsuarioDAO(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action == null || action.isEmpty()) {
                listarUsuarios(request, response);
            } else {
                switch (action) {
                    case "buscar":
                        buscarUsuario(request, response);
                        break;
                    case "cargar":
                        cargarUsuario(request, response);
                        break;
                    default:
                        listarUsuarios(request, response);
                        break;
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Error al procesar la solicitud GET", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "crear":
                    crearUsuario(request, response);
                    break;
                case "editar":
                    editarUsuario(request, response);
                    break;
                case "eliminar":
                    eliminarUsuario(request, response);
                    break;
                default:
                    listarUsuarios(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Error al procesar la solicitud POST", e);
        }
    }

    // Metodo para listar todos los usuarios
    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<Usuarios> usuarios = usuarioDAO.listarUsuarios();
        request.setAttribute("usuariosFiltrados", usuarios);
        request.getRequestDispatcher("usuarios.jsp").forward(request, response);
    }

    // Metodo para buscar usuarios por nombre o documento
    private void buscarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String search = request.getParameter("search");
        List<Usuarios> usuariosFiltrados = usuarioDAO.buscarUsuarioPorNombreODocumento(search);
        request.setAttribute("usuariosFiltrados", usuariosFiltrados);
        request.getRequestDispatcher("usuarios.jsp").forward(request, response);
    }

    // Metodo para cargar un usuario especifico para editarlo
    private void cargarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        Usuarios usuario = usuarioDAO.buscarUsuarioPorId(id);
        if (usuario != null) {
            request.setAttribute("usuario", usuario);
            request.getRequestDispatcher("editarUsuario.jsp").forward(request, response);
        } else {
            response.sendRedirect("UsuariosServlet");
        }
    }

    // Metodo para crear un nuevo usuario
    private void crearUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        Usuarios nuevoUsuario = new Usuarios();
        nuevoUsuario.setNombre(request.getParameter("nombre"));
        nuevoUsuario.setTipoDocumento(request.getParameter("tipoDocumento"));
        nuevoUsuario.setNumeroDocumento(request.getParameter("numeroDocumento"));
        nuevoUsuario.setEmail(request.getParameter("email"));
        nuevoUsuario.setTelefono(request.getParameter("telefono"));
        nuevoUsuario.setPassword(request.getParameter("password"));
        nuevoUsuario.setConfirmacion(request.getParameter("confirmacion"));
        nuevoUsuario.setPermisos(request.getParameter("permisos") != null);

        usuarioDAO.crearUsuario(nuevoUsuario);
        response.sendRedirect("login.jsp"); 
    }

    // Metodo para editar un usuario existente
    private void editarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        Usuarios usuario = usuarioDAO.buscarUsuarioPorId(id);

        if (usuario != null) {
            usuario.setNombre(request.getParameter("nombre"));
            usuario.setTipoDocumento(request.getParameter("tipoDocumento"));
            usuario.setNumeroDocumento(request.getParameter("numeroDocumento"));
            usuario.setEmail(request.getParameter("email"));
            usuario.setTelefono(request.getParameter("telefono"));
            usuario.setPassword(request.getParameter("password"));
            usuario.setConfirmacion(request.getParameter("confirmacion"));
            usuario.setPermisos(request.getParameter("permisos") != null);

            usuarioDAO.actualizarUsuario(usuario);
        }
        response.sendRedirect("UsuariosServlet");
    }

    // Metodo para eliminar un usuario
    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        usuarioDAO.eliminarUsuario(id);
        response.sendRedirect("UsuariosServlet");
    }

    @Override
    public void destroy() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
