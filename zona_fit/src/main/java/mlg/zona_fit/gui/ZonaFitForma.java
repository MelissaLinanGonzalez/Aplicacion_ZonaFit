package mlg.zona_fit.gui;

import mlg.zona_fit.model.Cliente;
import mlg.zona_fit.service.ClienteServicio;
import mlg.zona_fit.service.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class ZonaFitForma extends JFrame{
    private JPanel panelPrincipal;
    private JTable clientesTabla;
    private JTextField nombreTexto;
    private JTextField apellidoTexto;
    private JTextField membresiaTexto;
    private JButton guardarButton;
    private JButton eliminarButton;
    private JButton limpiarButton;
    IClienteServicio clienteServicio;
    private DefaultTableModel tablaModeloClientes;
    private Integer idCliente;

    @Autowired
    public ZonaFitForma(ClienteServicio clienteServicio){
        this.clienteServicio = clienteServicio;
        iniciarForma();
        guardarButton.addActionListener(e -> guardarCliente());
        clientesTabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarClienteSeleccionado();
            }
        });
    }

    private void iniciarForma(){
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null); // Centra la ventana
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.tablaModeloClientes = new DefaultTableModel(0, 4);
        String[] cabeceros = {"Id", "Nombre", "Apellido", "Membresía"};
        this.tablaModeloClientes.setColumnIdentifiers(cabeceros);
        this.clientesTabla = new JTable(tablaModeloClientes);
        // Cargar listado de clientes
        listarClientes();
    }

    private void guardarCliente(){
        if (nombreTexto.getText().equals("")){
            mostrarMensaje("Proporciona un nombre");
            nombreTexto.requestFocusInWindow();
            return;
        }
        if (membresiaTexto.getText().equals("")){
            mostrarMensaje("Proporciona una membresía");
            membresiaTexto.requestFocusInWindow();
            return;
        }

        // Recuperamos los valores del formulario
        var nombre = nombreTexto.getText();
        var apellido = apellidoTexto.getText();
        var membresia = Integer.parseInt(membresiaTexto.getText());
        var cliente = new Cliente(this.idCliente, nombre, apellido, membresia);
        this.clienteServicio.guardarCliente(cliente); //Se inserta el nuevo objeto tipo cliente en la bbdd / se modifica el cliente
        if (this.idCliente == null){
            mostrarMensaje("Se añadió un nuevo cliente");
        } else {
            mostrarMensaje("Se actualizó el cliente seleccionado");
        }

        limpiarFormulario();
        listarClientes();
    }

    private void cargarClienteSeleccionado(){
        var renglon = clientesTabla.getSelectedRow();
        if(renglon != -1){ // -1 significa que no se seleccinó ningún registro
            var id = clientesTabla.getModel().getValueAt(renglon, 0).toString();
            this.idCliente = Integer.parseInt(id);
            var nombre = clientesTabla.getModel().getValueAt(renglon, 1).toString();
            this.nombreTexto.setText(nombre);
            var apellido = clientesTabla.getModel().getValueAt(renglon, 2).toString();
            this.apellidoTexto.setText(apellido);
            var membresia = clientesTabla.getModel().getValueAt(renglon, 3).toString();
            this.membresiaTexto.setText(membresia);
        }
    }

    private void limpiarFormulario(){
        nombreTexto.setText("");
        apellidoTexto.setText("");
        membresiaTexto.setText("");
        // Limpiamos el id del cliente seleccionado
        this.idCliente = null;
        // Deseleccionamos el registro de la tabla
        this.clientesTabla.getSelectionModel().clearSelection();
    }

    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void listarClientes(){
        this.tablaModeloClientes.setRowCount(0);
        var clientes = this.clienteServicio.listarClientes();
        clientes.forEach(cliente -> {
            Object[] reglonCliente = {
                    cliente.getId(),
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getMembresia()
            };
            this.tablaModeloClientes.addRow(reglonCliente);
        });
    }
}
