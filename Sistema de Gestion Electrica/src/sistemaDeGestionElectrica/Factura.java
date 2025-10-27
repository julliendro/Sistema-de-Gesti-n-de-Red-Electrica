/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema.de.gestion.electrica;

import java.util.Date;

/**
 * Clase Factura.
 * Representa la cuenta de cobro generada por el consumo eléctrico de un período.
 */
public class Factura {
    private int idFactura; // Identificador único de la factura.
    private double monto; // Monto total a pagar.
    private Date fechaEmision; // Fecha en que se generó la factura.
    private Date fechaVencimiento; // Fecha límite para realizar el pago.
    private double consumoKwh; // Consumo eléctrico medido en ese período, en kWh.
    private String estadoDeFactura; // Estado actual (ej: "Pendiente", "Pagada", "Vencida").
    private Cliente cliente; // Referencia al Cliente responsable del pago.
    private Medidor medidor; // Medidor del cual se tomó el consumo para esta factura.
    
    /*
     * Constructor por defecto.
     */
    public Factura(){}
    
    /*
     * Constructor para inicializar todos los atributos de la factura.
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
     * @return the idFactura
     */
    public int getIdFactura() {
        return idFactura; // Retorna el ID de la factura.
    }

    /**
     * @param idFactura the idFactura to set
     */
    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura; // Establece el ID.
    }

    /**
     * @return the monto
     */
    public double getMonto() {
        return monto; // Retorna el monto total.
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(double monto) {
        this.monto = monto; // Establece el monto total.
    }

    /**
     * @return the fechaEmision
     */
    public Date getFechaEmision() {
        return fechaEmision; // Retorna la fecha de emisión.
    }

    /**
     * @param fechaEmision the fechaEmision to set
     */
    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision; // Establece la fecha de emisión.
    }

    /**
     * @return the fechaVencimiento
     */
    public Date getFechaVencimiento() {
        return fechaVencimiento; // Retorna la fecha de vencimiento.
    }

    /**
     * @param fechaVencimiento the fechaVencimiento to set
     */
    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento; // Establece la fecha de vencimiento.
    }

    /**
     * @return the consumoKwh
     */
    public double getConsumoKwh() {
        return consumoKwh; // Retorna el consumo en kWh facturado.
    }

    /**
     * @param consumoKwh the consumoKwh to set
     */
    public void setConsumoKwh(double consumoKwh) {
        this.consumoKwh = consumoKwh; // Establece el consumo facturado.
    }

    /**
     * @return the estadoDeFactura
     */
    public String getEstadoDeFactura() {
        return estadoDeFactura; // Retorna el estado actual de la factura.
    }

    /**
     * @param estadoDeFactura the estadoDeFactura to set
     */
    public void setEstadoDeFactura(String estadoDeFactura) {
        this.estadoDeFactura = estadoDeFactura; // Establece el estado.
    }

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente; // Retorna el cliente asociado.
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente; // Establece el cliente.
    }

    /**
     * @return the medidor
     */
    public Medidor getMedidor() {
        return medidor; // Retorna el medidor asociado.
    }

    /**
     * @param medidor the medidor to set
     */
    public void setMedidor(Medidor medidor) {
        this.medidor = medidor; // Establece el medidor.
    }
    
    
}
