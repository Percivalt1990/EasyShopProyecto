package com.mycompany.easyshopproyecto.servlets;

import com.mycompany.easyshopproyecto.dao.ConexionDB;
import com.mycompany.easyshopproyecto.dao.UsuarioDAO;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ResetPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        Connection connection = ConexionDB.getConnection();
        usuarioDAO = new UsuarioDAO(connection);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String numeroDocumento = request.getParameter("numeroDocumento");
        String nuevaContrasena = request.getParameter("newPassword");
        String confirmarContrasena = request.getParameter("confirmPassword");

        if (nuevaContrasena.equals(confirmarContrasena)) {
            try {
                boolean resultado = usuarioDAO.actualizarContrasena(numeroDocumento, nuevaContrasena, confirmarContrasena);
                if (resultado) {
                    response.sendRedirect("login.jsp?message=Contraseña actualizada exitosamente");
                } else {
                    request.setAttribute("errorMessage", "Error al actualizar la contraseña. Verifica el número de documento.");
                    request.getRequestDispatcher("ResetPassword.jsp").forward(request, response);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Ocurrió un error. Intenta nuevamente.");
                request.getRequestDispatcher("ResetPassword.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Las contraseñas no coinciden.");
            request.getRequestDispatcher("ResetPassword.jsp").forward(request, response);
        }
    }
}
