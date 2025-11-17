/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema.de.gestion.electrica;

import java.util.Date;

/**
 * Clase Servicio.
 * Representa la relación activa entre un Cliente y un Medidor, incluyendo las fechas clave
 * de su ciclo de vida (activación, suspensión, baja).
 */
public class Servicio {
    private int idServicio; // Identificador único del servicio.
    private Date fechaActivacion; // Fecha en que el servicio fue activado por última vez.
    private Date fechaSuspension; // Fecha en que el servicio fue suspendido por última vez.
    private Date fechaBaja; // Fecha de terminación definitiva del servicio.
    private Cliente cliente; // Referencia al Cliente o Usuario al que se le brinda el servicio.
    private Medidor medidor; // Referencia al Medidor físico asociado a este servicio.
    
    
    /*
     * Constructor por defecto.
     */
    public Servicio(){}
    
    /*
     * Constructor para inicializar todos los atributos del servicio.
     */
    public Servicio(int idServicio, Date fechaActivacion, Date fechaSuspension, Date fechaBaja, Cliente cliente, Medidor medidor) {
        this.idServicio = idServicio; // Inicializa el ID del servicio.
        this.fechaActivacion = fechaActivacion; // Inicializa la fecha de activación.
        this.fechaSuspension = fechaSuspension; // Inicializa la fecha de suspensión.
        this.fechaBaja = fechaBaja; // Inicializa la fecha de baja.
        this.cliente = cliente; // Asigna el objeto Cliente.
        this.medidor = medidor; // Asigna el objeto Medidor.
    }
    
    /*
     * Retorna el estado actual del servicio, consultando el estado del medidor asociado.
     * @return El nombre del estado del medidor o "Estado Desconocido".
     */
    public String getEstadoActual(){
        // Verifica que existan el medidor y su estado antes de intentar acceder al nombre.
        if (this.medidor != null && this.medidor.getEstadoDeMedidor() != null){
            return this.medidor.getEstadoDeMedidor().getNombre(); // Retorna el nombre del estado del medidor.
        }
        return "Estado Desconocido"; // Retorna un estado por defecto si no hay medidor o estado.
    }
    
    // ------------------ Getters y Setters ------------------

    /**
     * @return the idServicio
     */
    public int getIdServicio() {
        return idServicio; // Retorna el ID del servicio.
    }

    /**
     * @param idServicio the idServicio to set
     */
    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio; // Establece el ID del servicio.
    }

    /**
     * @return the fechaActivacion
     */
    public Date getFechaActivacion() {
        return fechaActivacion; // Retorna la fecha de activación.
    }

    /**
     * @param fechaActivacion the fechaActivacion to set
     */
    public void setFechaActivacion(Date fechaActivacion) {
        this.fechaActivacion = fechaActivacion; // Establece la fecha de activación.
    }

    /**
     * @return the fechaSuspension
     */
    public Date getFechaSuspension() {
        return fechaSuspension; // Retorna la fecha de suspensión.
    }

    /**
     * @param fechaSuspension the fechaSuspension to set
     */
    public void setFechaSuspension(Date fechaSuspension) {
        this.fechaSuspension = fechaSuspension; // Establece la fecha de suspensión.
    }

    /**
     * @return the fechaBaja
     */
    public Date getFechaBaja() {
        return fechaBaja; // Retorna la fecha de baja.
    }

    /**
     * @param fechaBaja the fechaBaja to set
     */
    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja; // Establece la fecha de baja.
    }

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente; // Retorna el Cliente asociado.
    }

    /**
     * @param cliente the usuario to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente; // Establece el Cliente asociado.
    }

    /**
     * @return the medidor
     */
    public Medidor getMedidor() {
        return medidor; // Retorna el Medidor asociado.
    }

    /**
     * @param medidor the medidor to set
     */
    public void setMedidor(Medidor medidor) {
        this.medidor = medidor; // Establece el Medidor asociado.
    }
    
}
