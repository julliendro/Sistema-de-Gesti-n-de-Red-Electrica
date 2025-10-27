/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema.de.gestion.electrica;

import java.util.Date;

/*
 * Clase Pago.
 * Representa la transacción de un pago realizado por un cliente para saldar una Factura.
 */
public class Pago {
    private int idPago; // Identificador único del pago.
    private double monto; // Cantidad de dinero pagada.
    private Date fechaDePago; // Fecha y hora en que se registró el pago.
    private String metodoDePago; // Forma de pago (ej: "Efectivo", "Tarjeta").
    private String estado; // Estado actual del pago (ej: "Aprobado", "Pendiente").
    private Factura factura; // Referencia a la Factura que este pago cubre.
    
    /*
     * Constructor por defecto.
     */
    public Pago() {}
    
    /*
     * Constructor para inicializar todos los atributos de un pago.
     */
    public Pago(int idPago, double monto, Date fechaDePago, String metodoDePago, String estado, Factura factura) {
        this.idPago = idPago;
        this.monto = monto;
        this.fechaDePago = fechaDePago;
        this.metodoDePago = metodoDePago;
        this.factura = factura;
        this.estado = estado;
    }

    // ------------------ Getters y Setters ------------------

    /**
     * @return the idPago
     */
    public int getIdPago() {
        return idPago; // Retorna el ID del pago.
    }

    /**
     * @param idPago the idPago to set
     */
    public void setIdPago(int idPago) {
        this.idPago = idPago; // Establece el ID del pago.
    }

    /**
     * @return the monto
     */
    public double getMonto() {
        return monto; // Retorna el monto pagado.
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(double monto) {
        this.monto = monto; // Establece el monto pagado.
    }

    /**
     * @return the fechaDePago
     */
    public Date getFechaDePago() {
        return fechaDePago; // Retorna la fecha de la transacción.
    }

    /**
     * @param fechaDePago the fechaDePago to set
     */
    public void setFechaDePago(Date fechaDePago) {
        this.fechaDePago = fechaDePago; // Establece la fecha de la transacción.
    }

    /**
     * @return the metodoDePago
     */
    public String getMetodoDePago() {
        return metodoDePago; // Retorna el método de pago utilizado.
    }

    /**
     * @param metodoDePago the metodoDePago to set
     */
    public void setMetodoDePago(String metodoDePago) {
        this.metodoDePago = metodoDePago; // Establece el método de pago.
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado; // Retorna el estado del pago.
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado; // Establece el estado del pago.
    }

    /**
     * @return the factura
     */
    public Factura getFactura() {
        return factura; // Retorna la Factura asociada.
    }

    /**
     * @param factura the factura to set
     */
    public void setFactura(Factura factura) {
        this.factura = factura; // Establece la Factura asociada.
    }
    
    
}
