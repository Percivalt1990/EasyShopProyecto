package com.mycompany.easyshopproyecto.servlets;

import com.mycompany.easyshopproyecto.dao.ConexionDB;
import com.mycompany.easyshopproyecto.dao.UsuarioDAO;
import com.mycompany.easyshopproyecto.logica.Usuarios;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        Connection connection = ConexionDB.getConnection();
        usuarioDAO = new UsuarioDAO(connection);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String numeroDocumento = request.getParameter("numeroDocumento");
        String password = request.getParameter("password");

        try {
            Usuarios usuario = usuarioDAO.autenticar(numeroDocumento, password);
            if (usuario != null) {
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);
                response.sendRedirect("index.jsp");
            } else {
                request.setAttribute("error", "Credenciales inválidas");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al autenticar usuario.");
        }
    }
}
