/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema.de.gestion.electrica;

/**
 * Clase Operario.
 * Representa a un empleado de la empresa con funciones específicas, hereda de Usuario.
 */
public class Operario extends Usuario{
    private int idOperario; // ID interno del operario (puede ser diferente al idUsuario).
    private String idUniversal; // Identificador único usado para login (ej: nombre de usuario).
    private String departamento; // Departamento al que pertenece el operario (ej: "Técnico", "Facturación").
    
    /*
     * Constructor por defecto, llama al constructor de la clase base (Usuario).
     */
    public Operario(){
        super();
    }
    
    /*
     * Constructor para inicializar todos los atributos del Operario, incluyendo los heredados.
     */
    public Operario(int idUsuario, String nombre, String apellido, String direccion, String telefono, String correoElectronico, String contrasena, Permisos permisos, String idUniversal, String departamento) {
        super(idUsuario, nombre, apellido, direccion, telefono, correoElectronico, contrasena, permisos); // Llama al constructor de Usuario.
        this.idUniversal = idUniversal;
        this.departamento = departamento;
    }
    
    /*
     * Sobrescribe el método para retornar el tipo de usuario como "Operario" y su departamento.
     */
    @Override
    public String getTipoUsuario(){
        return "Operario (" + this.getDepartamento() + ")";
    }

    
    /*
     * Método de negocio: Permite al operario cambiar el estado de un medidor.
     */
    public void cambiarEstadoMedidor (Medidor medidor, EstadoDeMedidor nuevoEstado){
        if (medidor != null){
            medidor.setEstadoDeMedidor(nuevoEstado); // Asigna el nuevo estado al medidor.
            System.out.println("Operario " + this.getApellido() + " cambió el estado del Medidor # " + medidor.getIdMedidor() + " a: " + nuevoEstado.getNombre());
        }
    }

    
    /*
     * Método placeholder para la activación de un servicio asociado a una solicitud.
     */
    public void activarServicio(int nisCliente, int solicitudId){
        // Lógica para activar el servicio de un cliente después de aprobar una solicitud.
    }
    
    // ------------------ Getters y Setters ------------------

    /**
     * @return the idOperario
     */
    public int getIdOperario() {
        return idOperario; // Retorna el ID interno del operario.
    }

    /**
     * @param idOperario the idOperario to set
     */
    public void setIdOperario(int idOperario) {
        this.idOperario = idOperario; // Establece el ID interno.
    }

    /**
     * @return the idUniversal
     */
    public String getIdUniversal() {
        return idUniversal; // Retorna el ID usado para autenticación.
    }

    /**
     * @param idUniversal the idUniversal to set
     */
    public void setIdUniversal(String idUniversal) {
        this.idUniversal = idUniversal; // Establece el ID de autenticación.
    }

    /**
     * @return the departamento
     */
    public String getDepartamento() {
        return departamento; // Retorna el departamento de trabajo.
    }

    /**
     * @param departamento the departamento to set
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento; // Establece el departamento.
    }
    
    
}
