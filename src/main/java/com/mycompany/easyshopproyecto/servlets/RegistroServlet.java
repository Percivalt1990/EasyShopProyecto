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

public class RegistroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        Connection connection = ConexionDB.getConnection();
        usuarioDAO = new UsuarioDAO(connection);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            boolean usuarioCreado = usuarioDAO.crearUsuario(usuario);
            if (usuarioCreado) {
                // Redirigir a login.jsp con un mensaje de éxito
                request.setAttribute("mensajeExito", "Registro exitoso. Ahora puede iniciar sesión.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                // Redirigir a registro.jsp con un mensaje de error
                request.setAttribute("mensajeError", "No se pudo registrar el usuario. Inténtelo de nuevo.");
                request.getRequestDispatcher("registro.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensajeError", "Error en el sistema. No se pudo realizar el registro.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
        }
    }
}
