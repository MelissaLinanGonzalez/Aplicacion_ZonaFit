package mlg.zona_fit;

import mlg.zona_fit.model.Cliente;
import mlg.zona_fit.service.IClienteServicio;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ZonaFitApplication implements CommandLineRunner {

	@Autowired
	private IClienteServicio clienteServicio;

	private static final Logger logger = LoggerFactory.getLogger(ZonaFitApplication.class);

	String nl = System.lineSeparator(); // Caracter de salto de línea genérico

	public static void main(String[] args) {
		logger.info("Iniciando la aplicación");
		// Levanta la fábrica de spring
		SpringApplication.run(ZonaFitApplication.class, args);
		logger.info("Aplicación finalizada");
	}

	@Override
	public void run(String... args) throws Exception {
		zonaFitApp();
	}

	private void zonaFitApp(){
		logger.info(nl + nl + "*** Aplicación Zona Fit (GYM) ***");

		boolean salir = false;
		Scanner entrada = new Scanner(System.in);

		while (!salir){
			var opcion = mostrarMenu(entrada);
			salir = ejecutarOpciones(entrada, opcion);
			logger.info(nl);
		}
	}

	private int mostrarMenu(Scanner entrada){
		logger.info(nl + """
				1. Listar Clientes
				2. Buscar Cliente
				3. Añadir Cliente
				4. Modificar Cliente
				5. Eliminar Cliente
				6. Salir
				Seleccione una opción:\s""");
		return entrada.nextInt();
	}

	private boolean ejecutarOpciones(Scanner entrada, int opcion){
		boolean salir = false;
		switch (opcion){
			case 1 -> {
				logger.info(nl + "--- Listado de Clientes ---" + nl);
				List<Cliente> clientes = clienteServicio.listarClientes();
				clientes.forEach(cliente -> logger.info("{}{}", cliente, nl));
			}
			case 2 -> {
				logger.info(nl + "--- Buscar Cliente por Id ---" + nl);
				logger.info("Id cliente a buscar: ");
				int idClinete = entrada.nextInt();
				Cliente cliente = clienteServicio.buscarClientePorId(idClinete);
				if (cliente != null){
					logger.info("Cliente encontrado: " + cliente + nl);
				} else {
					logger.info("Cliente NO encontrado: " + cliente + nl);
				}
			}
			case 3 -> {
				logger.info(nl + "--- Añadir Cliente ---" + nl);
				entrada.nextLine();
				logger.info("Introduce el nombre: ");
				String nombre = entrada.nextLine();
				logger.info("Introduce el apellido: ");
				String apellido = entrada.nextLine();
				logger.info("Introduce el número de membresía: ");
				int membresia = entrada.nextInt();
				Cliente nuevoCliente = new Cliente();
				nuevoCliente.setNombre(nombre);
				nuevoCliente.setApellido(apellido);
				nuevoCliente.setMembresia(membresia);
				clienteServicio.guardarCliente(nuevoCliente);
				logger.info("Cliente añadido correctamente: " + nuevoCliente + nl);
			}
			case 4 -> {
				logger.info(nl + "--- Modificar Cliente ---" + nl);
				logger.info("Introduce el id del cliente que deseas modificar: ");
				int idModifica = entrada.nextInt();
				Cliente cliente = clienteServicio.buscarClientePorId(idModifica);
				if (cliente != null){
					entrada.nextLine();
					logger.info("Nombre modificado (o el mismo si no lo desea modificar): ");
					String nombre = entrada.nextLine();
					logger.info("Apellido modificado (o el mismo si no lo desea momdificar): ");
					String apellido = entrada.nextLine();
					logger.info("Membresía modificada (o la misma si no lo desea modificar): ");
					int membresia = entrada.nextInt();
					cliente.setNombre(nombre);
					cliente.setApellido(apellido);
					cliente.setMembresia(membresia);
					clienteServicio.guardarCliente(cliente);
					logger.info("Cliente modificado correctamente: " + cliente + nl);
				} else {
					logger.info("Cliente no encontrado");
				}
			}
			case 5 -> {
				logger.info(nl + "--- Eliminar Cliente ---" + nl);
				List<Cliente> clientes = clienteServicio.listarClientes();
				clientes.forEach(cliente -> logger.info("{}{}", cliente, nl));
				logger.info("Introduce el id del cliente que deseas eliminar: ");
				int idEliminar = entrada.nextInt();
				Cliente clienteEliminar = clienteServicio.buscarClientePorId(idEliminar);
				if (clienteEliminar != null){
					clienteServicio.eliminarCliente(clienteEliminar);
					logger.info("Cliente eliminado: " + clienteEliminar);
				} else {
					logger.info("Cliente no encontrado " + clienteEliminar);
				}
			}
			case 6 -> {
				logger.info("*** Hasta pronto ***" + nl + nl);
				salir = true;
			}
			default -> logger.info("Opción inválida: " + opcion + nl);
		}
		return salir;
	}
}