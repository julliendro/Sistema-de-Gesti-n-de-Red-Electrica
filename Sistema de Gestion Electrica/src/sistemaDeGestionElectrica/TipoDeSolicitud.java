/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema.de.gestion.electrica;

/**
 * Clase TipoDeSolicitud.
 * Representa los diferentes tipos de solicitudes que un cliente puede realizar 
 * (ej: Activación, Suspensión, Baja).
 */
public class TipoDeSolicitud {
    private int idTipoDeSolicitud; // Identificador único del tipo de solicitud.
    private String nombre; // Nombre descriptivo del tipo de solicitud.
    
    /**
     * Constructor por defecto.
     */
    public TipoDeSolicitud(){}

    /*
     * Constructor para inicializar los atributos de la clase.
     */
    public TipoDeSolicitud(int idTipoDeSolicitud, String nombre) {
        this.idTipoDeSolicitud = idTipoDeSolicitud; // Inicializa el ID del tipo de solicitud.
        this.nombre = nombre; // Inicializa el nombre del tipo de solicitud.
    }

    // ------------------ Getters y Setters ------------------

    /**
     * @return the idTipoDeSolicitud
     */
    public int getIdTipoDeSolicitud() {
        return idTipoDeSolicitud; // Retorna el ID del tipo de solicitud.
    }

    /**
     * @param idTipoDeSolicitud the idTipoDeSolicitud to set
     */
    public void setIdTipoDeSolicitud(int idTipoDeSolicitud) {
        this.idTipoDeSolicitud = idTipoDeSolicitud; // Establece el ID del tipo de solicitud.
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre; // Retorna el nombre descriptivo del tipo.
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre; // Establece el nombre del tipo
    }
}
