package com.mycompany.easyshopproyecto.servlets;

import com.mycompany.easyshopproyecto.dao.ConexionDB;
import com.mycompany.easyshopproyecto.dao.UsuarioDAO;
import com.mycompany.easyshopproyecto.logica.Usuarios;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String numeroDocumento = request.getParameter("numeroDocumento"); 
        String password = request.getParameter("password"); 

        try (Connection connection = ConexionDB.getConnection()) {
            UsuarioDAO usuarioDAO = new UsuarioDAO(connection);
            Usuarios usuario = usuarioDAO.autenticar(numeroDocumento, password);

            if (usuario != null) {
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);
                session.setAttribute("usuarioId", usuario.getId()); // Guarda el usuarioId en la sesión
                session.setAttribute("nombreUsuario", usuario.getNombre()); // Guarda el nombre del usuario en la sesión
                response.sendRedirect("index.jsp");
            } else {
                request.setAttribute("errorMessage", "Usuario o contraseña incorrectos");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=Error en el servidor");
        }
    }



}
