/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema.de.gestion.electrica;

/**
 * Clase EstadoDeSolicitudDeServicio.
 * Representa los posibles estados por los que pasa la gestión de una Solicitud (ej: "Pendiente", "Aprobada", "Rechazada").
 */
public class EstadoDeSolicitudDeServicio{
    private int idEstadoDeSolicitudDeServicio; // Identificador único del estado.
    private String nombre; // Nombre del estado (ej: "Pendiente").
    
    /**
     * Constructor por defecto. Inicializa el estado con "Pendiente".
     */
    public EstadoDeSolicitudDeServicio(){
        this.nombre = "Pendiente";
    }

    /*
     * Constructor para inicializar el ID y el nombre del estado.
     */
    public EstadoDeSolicitudDeServicio(int idEstadoDeSolicitudDeServicio, String nombre) {
        this.idEstadoDeSolicitudDeServicio = idEstadoDeSolicitudDeServicio;
        this.nombre = nombre;
    }

    /**
     * Verifica si el estado actual es un estado terminal (final del proceso).
     * @return true si es "Completada" o "Rechazada".
     */
    public boolean esEstadoFinal() {
        return this.getNombre().equalsIgnoreCase("Completada") || this.getNombre().equalsIgnoreCase("Rechazada");
    }

    // ------------------ Getters y Setters ------------------

    /**
     * @return the idEstadoDeSolicitudDeServicio
     */
    public int getIdEstadoDeSolicitudDeServicio() {
        return idEstadoDeSolicitudDeServicio; // Retorna el ID del estado.
    }

    /**
     * @param idEstadoDeSolicitudDeServicio the idEstadoDeSolicitudDeServicio to set
     */
    public void setIdEstadoDeSolicitudDeServicio(int idEstadoDeSolicitudDeServicio) {
        this.idEstadoDeSolicitudDeServicio = idEstadoDeSolicitudDeServicio; // Establece el ID del estado.
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre; // Retorna el nombre del estado.
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre; // Establece el nombre del estado.
    }
    
    
}