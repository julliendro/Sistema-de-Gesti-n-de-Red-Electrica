/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema.de.gestion.electrica;


/**
 * Clase Cliente.
 * Representa a un usuario final del servicio eléctrico, extiende la clase abstracta Usuario.
 */
public class Cliente extends Usuario {
    private int idCliente; // Identificador interno específico para el cliente.
    private int NIS; // Número de Identificación de Suministro, clave única del cliente en el sistema.
    
    /**
     * Constructor por defecto, llama al constructor de la clase base (Usuario).
     */
    public Cliente(){
        super();
    }
    
    /*
     * Constructor para inicializar los atributos heredados de Usuario y el NIS del Cliente.
     */
    public Cliente(int idUsuario, String nombre, String apellido, String direccion, String telefono, String correoElectronico, String contrasena, Permisos permisos, int NIS) {
        super(idUsuario, nombre, apellido, direccion, telefono, correoElectronico, contrasena, permisos); // Llama al constructor de Usuario. 
        this.NIS = NIS; // Inicializa el Número de Identificación de Suministro.
    }
    
    /*
     * Sobrescribe el método para retornar el tipo de usuario junto con su NIS.
     */
    @Override
    public String getTipoUsuario() {
        return "Cliente NIS: " + this.NIS;
    }
    
    
    /**
     * Simula la acción de un cliente al iniciar el proceso de solicitar un nuevo servicio.
     */
    public void solicitarNuevoServicio() {
        // Lógica para crear un objeto SolicitudDeServicio
        System.out.println("El cliente " + this.getNombre() + " está iniciando una solicitud de servicio.");
    }
    
    // ------------------ Getters y Setters ------------------
    
    /**
     * @return the idCliente
     */
    public int getIdCliente() {
        return idCliente; // Retorna el ID específico de Cliente.
    }

    /**
     * @param idCliente the idCliente to set
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente; // Establece el ID específico de Cliente.
    }

    /**
     * @return the NIS
     */
    public int getNIS() {
        return NIS; // Retorna el Número de Identificación de Suministro.
    }

    /**
     * @param NIS the NIS to set
     */
    public void setNIS(int NIS) {
        this.NIS = NIS; // Establece el Número de Identificación de Suministro.
    }
    
    
}
