/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema.de.gestion.electrica;

import java.util.Date;


/**
 * Clase SolicitudDeServicio.
 * Representa el registro de una petición hecha por un cliente para cambiar el estado de su servicio (ej: activar, suspender).
 *
 * @author IYAX
 */
public class SolicitudDeServicio {
    private int idSolicitud; // Identificador único de la solicitud.
    private Date fechaSolicitud; // Fecha en que se registró la solicitud.
    private String descripcion; // Detalle o comentario adicional proporcionado por el cliente.
    private Cliente cliente; // Referencia al objeto Cliente que realiza la solicitud.
    private EstadoDeMedidor estadoDeMedidor; // El estado actual del medidor al momento de crear la solicitud.
    private TipoDeSolicitud tipoDeSolicitud; // El tipo de acción solicitada (ej: "Activacion").
    private EstadoDeSolicitudDeServicio estadoDeSolicitudDeServicio; // El estado actual de la gestión de la solicitud (ej: "Pendiente").
    
    /*
     * Constructor por defecto. Inicializa la fecha de solicitud con el momento actual.
     */
    public SolicitudDeServicio(){
        this.fechaSolicitud = new Date(); // Inicializa la fecha de solicitud con la fecha y hora actuales.
    }
    
    /*
     * Constructor para inicializar todos los atributos de la solicitud.
     */
    public SolicitudDeServicio(int idSolicitud, String descripcion, Cliente cliente, EstadoDeMedidor estadoDeMedidor, TipoDeSolicitud tipoDeSolicitud, EstadoDeSolicitudDeServicio estadoDeSolicitudDeServicio) {
        this.idSolicitud = idSolicitud; // Inicializa el ID de la solicitud.
        this.descripcion = descripcion; // Inicializa la descripción.
        this.cliente = cliente; // Asigna el objeto Cliente.
        this.estadoDeMedidor = estadoDeMedidor; // Asigna el estado del Medidor.
        this.tipoDeSolicitud = tipoDeSolicitud; // Asigna el tipo de solicitud.
        this.estadoDeSolicitudDeServicio = estadoDeSolicitudDeServicio; // Asigna el estado de la solicitud.
        this.fechaSolicitud = new Date(); // Asigna la fecha actual de creación.
    }
    
    /*
     * Método para cambiar el estado de la gestión de la solicitud.
     */
    public void actualizarEstado(EstadoDeSolicitudDeServicio nuevoEstado) {
        this.estadoDeSolicitudDeServicio = nuevoEstado; // Actualiza el estado de la solicitud.
        
    }

    // ------------------ Getters y Setters ------------------

    public int getIdSolicitud() {
        return idSolicitud; // Retorna el ID de la solicitud.
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud; // Establece el ID de la solicitud.
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud; // Retorna la fecha de creación.
    }

    public String getDescripcion() {
        return descripcion; // Retorna la descripción o comentario.
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion; // Establece la descripción.
    }

    public Cliente getCliente() {
        return cliente; // Retorna el objeto Cliente asociado.
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente; // Establece el objeto Cliente asociado.
    }

    public EstadoDeMedidor getEstadoDeMedidor() {
        return estadoDeMedidor; // Retorna el estado del Medidor al momento de solicitar.
    }

    public void setEstadoDeMedidor(EstadoDeMedidor estadoDeMedidor) {
        this.estadoDeMedidor = estadoDeMedidor; // Establece el estado del Medidor.
    }

    public TipoDeSolicitud getTipoDeSolicitud() {
        return tipoDeSolicitud; // Retorna el objeto TipoDeSolicitud.
    }

    public void setTipoDeSolicitud(TipoDeSolicitud tipoDeSolicitud) {
        this.tipoDeSolicitud = tipoDeSolicitud; // Establece el objeto TipoDeSolicitud.
    }

    public EstadoDeSolicitudDeServicio getEstadoDeSolicitudDeServicio() {
        return estadoDeSolicitudDeServicio; // Retorna el estado de la Solicitud (ej: Pendiente).
    }
    
    
}

