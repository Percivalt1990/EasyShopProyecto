package com.mycompany.easyshopproyecto.servlets;

import com.mycompany.easyshopproyecto.dao.ClientesDAO;
import com.mycompany.easyshopproyecto.logica.Clientes;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClientesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ClientesDAO clientesDAO = new ClientesDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        List<Clientes> clientes;

        if (search != null && !search.isEmpty()) {
            clientes = clientesDAO.listarClientes().stream()
                    .filter(cliente -> cliente.getNombre().toLowerCase().contains(search.toLowerCase()) ||
                            cliente.getDocumento().contains(search))
                    .toList();
        } else {
            clientes = clientesDAO.listarClientes(); // Obtener todos los clientes
        }

        request.setAttribute("clientesFiltrados", clientes);
        request.getRequestDispatcher("clientes.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "crear":
                crearCliente(request, response);
                break;
            case "editar":
                editarCliente(request, response);
                break;
            case "eliminar":
                eliminarCliente(request, response);
                break;
            case "cargar":
                cargarCliente(request, response);
                break;
            default:
                listarClientes(request, response);
                break;
        }
    }

    private void crearCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Clientes cliente = new Clientes(
                0,
                request.getParameter("nombre"),
                request.getParameter("documento"),
                request.getParameter("tipoDocumento"),
                request.getParameter("telefono"),
                request.getParameter("direccion"),
                request.getParameter("email")
        );

        if (clientesDAO.crearCliente(cliente)) {
            response.sendRedirect("ClientesServlet");
        } else {
            request.setAttribute("error", "Error al crear cliente.");
            listarClientes(request, response);
        }
    }

    private void editarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Clientes cliente = new Clientes(
                id,
                request.getParameter("nombre"),
                request.getParameter("documento"),
                request.getParameter("tipoDocumento"),
                request.getParameter("telefono"),
                request.getParameter("direccion"),
                request.getParameter("email")
        );

        if (clientesDAO.actualizarCliente(cliente)) {
            response.sendRedirect("ClientesServlet");
        } else {
            request.setAttribute("error", "Error al editar cliente.");
            listarClientes(request, response);
        }
    }

    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        if (clientesDAO.eliminarCliente(id)) {
            response.sendRedirect("ClientesServlet");
        } else {
            request.setAttribute("error", "Error al eliminar cliente.");
            listarClientes(request, response);
        }
    }

    private void listarClientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Clientes> clientes = clientesDAO.listarClientes();
        request.setAttribute("clientes", clientes);
        request.getRequestDispatcher("clientes.jsp").forward(request, response);
    }

    private void cargarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Clientes cliente = clientesDAO.obtenerClientePorId(id);

        if (cliente != null) {
            request.setAttribute("cliente", cliente);
            request.getRequestDispatcher("editarCliente.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Cliente no encontrado.");
            listarClientes(request, response);
        }
    }
}
