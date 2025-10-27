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
    private Usuario usuario; // Referencia al Cliente o Usuario al que se le brinda el servicio.
    private Medidor medidor; // Referencia al Medidor físico asociado a este servicio.
    
    
    /*
     * Constructor por defecto.
     */
    public Servicio(){}
    
    /*
     * Constructor para inicializar todos los atributos del servicio.
     */
    public Servicio(int idServicio, Date fechaActivacion, Date fechaSuspension, Date fechaBaja, Usuario usuario, Medidor medidor) {
        this.idServicio = idServicio;
        this.fechaActivacion = fechaActivacion;
        this.fechaSuspension = fechaSuspension;
        this.fechaBaja = fechaBaja;
        this.usuario = usuario;
        this.medidor = medidor;
    }
    
    /*
     * Retorna el estado actual del servicio, consultando el estado del medidor asociado.
     * @return El nombre del estado del medidor o "Estado Desconocido".
     */
    public String getEstadoActual(){
        // Verifica que existan el medidor y su estado antes de intentar acceder al nombre.
        if (this.medidor != null && this.medidor.getEstadoDeMedidor() != null){
            return this.medidor.getEstadoDeMedidor().getNombre();
        }
        return "Estado Desconocido";
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
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario; // Retorna el Cliente asociado.
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario; // Establece el Cliente asociado.
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
