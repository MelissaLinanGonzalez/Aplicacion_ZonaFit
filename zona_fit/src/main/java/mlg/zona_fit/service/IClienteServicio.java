package mlg.zona_fit.service;

import mlg.zona_fit.model.Cliente;

import java.util.List;

public interface IClienteServicio {
    public List<Cliente> listarClientes();
    public Cliente buscarClientePorId(Integer id);
    public void guardarCliente(Cliente cliente); //Si el valor de la pk es nulo se hace una insercción, si tiene otro valor, realiza una actualizacion
    public void eliminarCliente(Cliente cliente);
}
