package com.mycompany.easyshopproyecto.servlets;

import com.mycompany.easyshopproyecto.dao.ClientesDAO;
import com.mycompany.easyshopproyecto.logica.Clientes;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ClientesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ClientesDAO clientesDAO;

    @Override
    public void init() throws ServletException {
        // Instanciar el DAO
        clientesDAO = new ClientesDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        
        List<Clientes> clientesFiltrados;
        if (search != null && !search.isEmpty()) {
            clientesFiltrados = clientesDAO.buscarClientes(search);
        } else {
            clientesFiltrados = clientesDAO.listarClientes();
        }
        
        request.setAttribute("clientesFiltrados", clientesFiltrados);
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
        Clientes cliente = new Clientes();
        cliente.setNombre(request.getParameter("nombre"));
        cliente.setDocumento(request.getParameter("documento"));
        cliente.setTipoDocumento(request.getParameter("tipoDocumento"));
        cliente.setTelefono(request.getParameter("telefono"));
        cliente.setDireccion(request.getParameter("direccion"));
        cliente.setEmail(request.getParameter("email"));

        boolean creado = clientesDAO.crearCliente(cliente);
        if (creado) {
            response.sendRedirect("ClientesServlet");
        } else {
            request.setAttribute("error", "No se pudo crear el cliente");
            request.getRequestDispatcher("clientes.jsp").forward(request, response);
        }
    }

    private void editarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Clientes cliente = new Clientes();
        cliente.setId(id);
        cliente.setNombre(request.getParameter("nombre"));
        cliente.setDocumento(request.getParameter("documento"));
        cliente.setTipoDocumento(request.getParameter("tipoDocumento"));
        cliente.setTelefono(request.getParameter("telefono"));
        cliente.setDireccion(request.getParameter("direccion"));
        cliente.setEmail(request.getParameter("email"));

        boolean actualizado = clientesDAO.actualizarCliente(cliente);
        if (actualizado) {
            response.sendRedirect("ClientesServlet");
        } else {
            request.setAttribute("error", "No se pudo actualizar el cliente");
            request.getRequestDispatcher("editarCliente.jsp").forward(request, response);
        }
    }

    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean eliminado = clientesDAO.eliminarCliente(id);
        
        if (eliminado) {
            response.sendRedirect("ClientesServlet");
        } else {
            request.setAttribute("error", "No se pudo eliminar el cliente");
            listarClientes(request, response);
        }
    }

    private void cargarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Clientes cliente = clientesDAO.obtenerClientePorId(id);
        
        if (cliente != null) {
            request.setAttribute("cliente", cliente);
            request.getRequestDispatcher("editarCliente.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Cliente no encontrado");
            listarClientes(request, response);
        }
    }

    private void listarClientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Clientes> listaClientes = clientesDAO.listarClientes();
        request.setAttribute("clientesFiltrados", listaClientes);
        request.getRequestDispatcher("clientes.jsp").forward(request, response);
    }
}
