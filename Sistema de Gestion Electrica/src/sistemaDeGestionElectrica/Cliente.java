/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema.de.gestion.electrica;


/**
 * Clase Cliente.
 * Yo uso esta clase para representar a un usuario final del servicio eléctrico, y sé que extiende la clase abstracta Usuario.
 */
public class Cliente extends Usuario {
    private int idCliente; // Este es mi identificador interno específico para el cliente.
    private int NIS; // Este es el Número de Identificación de Suministro, la clave única del cliente en el sistema.
    
    /**
     * Este es mi constructor por defecto, simplemente llama al constructor de la clase base (Usuario).
     */
    public Cliente(){
        super();
    }
    
    /*
     * Este constructor lo uso para inicializar los atributos que hereda de Usuario, además del NIS específico del Cliente.
     */
    public Cliente(int idUsuario, String nombre, String apellido, String direccion, String telefono, String correoElectronico, String contraseña, Permisos permisos, int NIS) {
        super(idUsuario, nombre, apellido, direccion, telefono, correoElectronico, contraseña, permisos); // Llamo al constructor de Usuario con todos los datos. 
        this.idCliente = idUsuario; // Asigno el mismo idUsuario como mi idCliente.
        this.NIS = NIS; // Inicializo el Número de Identificación de Suministro (NIS).
    }
    
    /*
     * Sobrescribo este método para que retorne el tipo de usuario junto con el NIS, lo que es más específico para un Cliente.
     */
    @Override
    public String getTipoUsuario() {
        return "Cliente NIS: " + this.NIS; // Retorno la etiqueta de Cliente y su NIS.
    }
    
    
    /**
     * Este método simula la acción de un cliente al iniciar el proceso de solicitar un nuevo servicio.
     */
    public void solicitarNuevoServicio() {
        // Aquí iría la lógica para crear un objeto SolicitudDeServicio, pero por ahora solo muestro un mensaje.
        System.out.println("El cliente " + this.getNombre() + " está iniciando una solicitud de servicio.");
    }
    
    // ------------------ Getters y Setters ------------------
    
    /**
     * @return Retorna el idCliente, mi ID específico de Cliente.
     */
    public int getIdCliente() {
        return idCliente; 
    }

    /**
     * @param idCliente Establece el idCliente, mi ID específico de Cliente.
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente; 
    }

    /**
     * @return Retorna el NIS, el Número de Identificación de Suministro.
     */
    public int getNIS() {
        return NIS; 
    }

    /**
     * @param NIS Establece el NIS, el Número de Identificación de Suministro.
     */
    public void setNIS(int NIS) {
        this.NIS = NIS; 
    }
    
    
}
