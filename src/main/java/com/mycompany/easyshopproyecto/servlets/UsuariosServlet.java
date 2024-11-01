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

    @Override
    public void init() throws ServletException {
        Connection connection = ConexionDB.getConnection();
        usuarioDAO = new UsuarioDAO(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            listarUsuarios(request, response);
        } else if ("buscar".equals(action)) {
            buscarUsuario(request, response);
        } else if ("cargar".equals(action)) {
            cargarUsuario(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

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
    }

    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Usuarios> usuarios = usuarioDAO.listarUsuarios();
            request.setAttribute("usuariosFiltrados", usuarios);
            request.getRequestDispatcher("usuarios.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al listar usuarios.");
        }
    }

    private void buscarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        try {
            List<Usuarios> usuarios = usuarioDAO.buscarUsuarios(search);
            request.setAttribute("usuariosFiltrados", usuarios);
            request.getRequestDispatcher("usuarios.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al buscar usuarios.");
        }
    }

    private void crearUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuarios usuario = new Usuarios();
        usuario.setNombre(request.getParameter("nombre"));
        usuario.setTipoDocumento(request.getParameter("tipoDocumento"));
        usuario.setNumeroDocumento(request.getParameter("numeroDocumento"));
        usuario.setEmail(request.getParameter("email"));
        usuario.setTelefono(request.getParameter("telefono"));
        usuario.setPassword(request.getParameter("password"));
        usuario.setConfirmacion(request.getParameter("confirmacion"));
        usuario.setPermisos("on".equals(request.getParameter("permisos")));

        try {
            usuarioDAO.crearUsuario(usuario);
            response.sendRedirect("UsuariosServlet");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al registrar usuario.");
        }
    }

    private void editarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuarios usuario = new Usuarios();
        usuario.setId(Integer.parseInt(request.getParameter("id")));
        usuario.setNombre(request.getParameter("nombre"));
        usuario.setTipoDocumento(request.getParameter("tipoDocumento"));
        usuario.setNumeroDocumento(request.getParameter("numeroDocumento"));
        usuario.setEmail(request.getParameter("email"));
        usuario.setTelefono(request.getParameter("telefono"));
        usuario.setPassword(request.getParameter("password"));
        usuario.setConfirmacion(request.getParameter("confirmacion"));
        usuario.setPermisos("on".equals(request.getParameter("permisos")));

        try {
            if (usuarioDAO.actualizarUsuario(usuario)) {
                response.sendRedirect("UsuariosServlet");
            } else {
                request.setAttribute("error", "Error al actualizar usuario.");
                request.getRequestDispatcher("editarUsuario.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al actualizar usuario.");
        }
    }

    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            usuarioDAO.eliminarUsuario(id);
            response.sendRedirect("UsuariosServlet");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al eliminar usuario.");
        }
    }

    private void cargarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Cargando usuario para editar...");
        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println("ID recibido: " + id);
        try {
            Usuarios usuario = usuarioDAO.obtenerUsuarioPorId(id);
            if (usuario != null) {
                System.out.println("Usuario encontrado: " + usuario.getNombre());
                request.setAttribute("usuario", usuario);
                request.getRequestDispatcher("editarUsuario.jsp").forward(request, response);
            } else {
                System.out.println("Usuario no encontrado.");
                response.sendRedirect("usuarios.jsp?error=UsuarioNoEncontrado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al cargar usuario.");
        }
    }


}
