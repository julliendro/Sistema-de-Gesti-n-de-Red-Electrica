/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package sistema.de.gestion.electrica;

import java.util.Date;

/**
 * Clase Factura.
 * Uso esta clase para representar la cuenta de cobro que se genera por el consumo eléctrico de un período.
 */
public class Factura {
    private int idFactura; // Este es mi identificador único de la factura.
    private double monto; // Este es el monto total que el cliente debe pagar.
    private Date fechaEmision; // Esta es la fecha en que se generó la factura.
    private Date fechaVencimiento; // Esta es la fecha límite para que el cliente realice el pago.
    private double consumoKwh; // Yo registro aquí el consumo eléctrico medido en ese período, en kWh.
    private String estadoDeFactura; // Este campo indica el estado actual (ej: "Pendiente", "Pagada", "Vencida").
    private Cliente cliente; // Yo mantengo una referencia al Cliente responsable del pago.
    private Medidor medidor; // Yo registro el Medidor del cual se tomó el consumo para esta factura.
    
    /*
     * Este es mi constructor por defecto, que no inicializa nada.
     */
    public Factura(){}
    
    /*
     * Este es mi constructor completo, lo uso para inicializar todos los atributos de la factura.
     */
    public Factura(int idFactura, double monto, Date fechaEmision, Date fechaVencimiento, double consumoKwh, String estadoDeFactura, Cliente cliente, Medidor medidor) {
        this.idFactura = idFactura;
        this.monto = monto;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.consumoKwh = consumoKwh;
        this.cliente = cliente;
        this.medidor = medidor;
        this.estadoDeFactura = estadoDeFactura;
    }

    // ------------------ Getters y Setters ------------------

    /**
     * @return Retorna el ID de la factura.
     */
    public int getIdFactura() {
        return idFactura; 
    }

    /**
     * @param idFactura Establece el ID de la factura.
     */
    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura; 
    }

    /**
     * @return Retorna el monto total a pagar.
     */
    public double getMonto() {
        return monto; 
    }

    /**
     * @param monto Establece el monto total.
     */
    public void setMonto(double monto) {
        this.monto = monto; 
    }

    /**
     * @return Retorna la fecha de emisión de la factura.
     */
    public Date getFechaEmision() {
        return fechaEmision; 
    }

    /**
     * @param fechaEmision Establece la fecha de emisión.
     */
    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision; 
    }

    /**
     * @return Retorna la fecha de vencimiento.
     */
    public Date getFechaVencimiento() {
        return fechaVencimiento; 
    }

    /**
     * @param fechaVencimiento Establece la fecha de vencimiento.
     */
    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento; 
    }

    /**
     * @return Retorna el consumo en kWh facturado.
     */
    public double getConsumoKwh() {
        return consumoKwh; 
    }

    /**
     * @param consumoKwh Establece el consumo facturado.
     */
    public void setConsumoKwh(double consumoKwh) {
        this.consumoKwh = consumoKwh; 
    }

    /**
     * @return Retorna el estado actual de la factura.
     */
    public String getEstadoDeFactura() {
        return estadoDeFactura; 
    }

    /**
     * @param estadoDeFactura Establece el estado.
     */
    public void setEstadoDeFactura(String estadoDeFactura) {
        this.estadoDeFactura = estadoDeFactura; 
    }

    /**
     * @return Retorna el objeto Cliente asociado a la factura.
     */
    public Cliente getCliente() {
        return cliente; 
    }

    /**
     * @param cliente Establece el cliente.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente; 
    }

    /**
     * @return Retorna el objeto Medidor asociado.
     */
    public Medidor getMedidor() {
        return medidor; 
    }

    /**
     * @param medidor Establece el medidor.
     */
    public void setMedidor(Medidor medidor) {
        this.medidor = medidor; 
    }
    
    
}