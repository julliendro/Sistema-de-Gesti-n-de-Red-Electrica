/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema.de.gestion.electrica;

/**
 * Clase EstadoDeMedidor.
 * Define el estado actual de funcionamiento o servicio del medidor (ej: "Activo", "Suspendido", "Dado de Baja").
 */
public class EstadoDeMedidor{
    private int idEstadoDeMedidor; // Identificador único del estado.
    private String nombre; // Nombre descriptivo del estado (ej: "Activo").
    
    /*
     * Constructor por defecto. Inicializa el estado con un valor predeterminado.
     */
    public EstadoDeMedidor(){
        super();
        this.nombre = "Desconocido"; // Asigna un nombre por defecto.
    }

    /*
     * Constructor para inicializar el ID y el nombre del estado.
     */
    public EstadoDeMedidor(int idEstadoDeMedidor, String nombre) {
        this.idEstadoDeMedidor = idEstadoDeMedidor;
        this.nombre = nombre;
    }

    // ------------------ Getters y Setters ------------------
        
    /**
     * @return the idEstadoDeMedidor
     */
    public int getIdEstadoDeMedidor() {
        return idEstadoDeMedidor; // Retorna el ID único del estado.
    }

    /**
     * @param idEstadoDeMedidor the idEstadoDeMedidor to set
     */
    public void setIdEstadoDeMedidor(int idEstadoDeMedidor) {
        this.idEstadoDeMedidor = idEstadoDeMedidor; // Establece el ID del estado.
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre; // Retorna el nombre del estado (ej: Activo).
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre; // Establece el nombre del estado.
    }
    
    
}
