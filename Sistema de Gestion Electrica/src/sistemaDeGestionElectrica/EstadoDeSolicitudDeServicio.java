/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema.de.gestion.electrica;

/**
 * Clase EstadoDeSolicitudDeServicio.
 * Uso esta clase para representar los posibles estados por los que pasa la gestión de una Solicitud (ej: "Pendiente", "Aprobada", "Rechazada").
 */
public class EstadoDeSolicitudDeServicio{
    private int idEstadoDeSolicitudDeServicio; // Este es mi identificador único del estado de la solicitud.
    private String nombre; // Este es el nombre del estado (ej: "Pendiente").
    
    /**
     * Este es mi constructor por defecto. Inicializo el estado con el valor inicial de "Pendiente".
     */
    public EstadoDeSolicitudDeServicio(){
        this.nombre = "Pendiente";
    }

    /*
     * Este constructor lo uso para inicializar el ID y el nombre del estado desde la base de datos o al crear un objeto.
     */
    public EstadoDeSolicitudDeServicio(int idEstadoDeSolicitudDeServicio, String nombre) {
        this.idEstadoDeSolicitudDeServicio = idEstadoDeSolicitudDeServicio;
        this.nombre = nombre;
    }

    /**
     * Este método me ayuda a verificar si el estado actual es un estado terminal (es decir, el proceso finalizó).
     * @return Retorna true si es "Completada" o "Rechazada".
     */
    public boolean esEstadoFinal() {
        // Compruebo si el nombre del estado (ignorando mayúsculas) es "Completada" o "Rechazada".
        return this.getNombre().equalsIgnoreCase("Completada") || this.getNombre().equalsIgnoreCase("Rechazada");
    }

    // ------------------ Getters y Setters ------------------

    /**
     * @return Retorna el idEstadoDeSolicitudDeServicio, el ID del estado.
     */
    public int getIdEstadoDeSolicitudDeServicio() {
        return idEstadoDeSolicitudDeServicio; 
    }

    /**
     * @param idEstadoDeSolicitudDeServicio Establece el ID del estado.
     */
    public void setIdEstadoDeSolicitudDeServicio(int idEstadoDeSolicitudDeServicio) {
        this.idEstadoDeSolicitudDeServicio = idEstadoDeSolicitudDeServicio; 
    }

    /**
     * @return Retorna el nombre del estado.
     */
    public String getNombre() {
        return nombre; 
    }

    /**
     * @param nombre Establece el nombre del estado.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre; 
    }
    
    
}