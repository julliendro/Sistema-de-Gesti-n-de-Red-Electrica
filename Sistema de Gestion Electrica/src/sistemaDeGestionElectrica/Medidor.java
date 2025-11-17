/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema.de.gestion.electrica;



import java.util.Date;



/**

 * Clase Medidor.

 * Representa el dispositivo físico usado para registrar el consumo eléctrico.

 */

public class Medidor {

    private int idMedidor; // Identificador único del medidor.

    private String tipo; // Tipo de medidor (ej: "Digital", "Analógico").

    private String marca; // Fabricante del medidor.

    private String ubicacion; // Dirección o punto de instalación física.

    private double consumoKwh; // Última lectura registrada del consumo total acumulado en kWh.

    private Date fechaActual; // Fecha de la última lectura o actualización de datos.

    private Date fechaDeInstalacion; // Fecha en que el medidor fue instalado.

    private int anioDeFabricacion; // Año en que el medidor fue fabricado.

    private EstadoDeMedidor estadoDeMedidor; // Objeto que representa el estado operativo actual (ej: "Activo", "Suspendido").

    private Cliente cliente; // Referencia al Cliente que utiliza este medidor.

    

    /*

     * Constructor por defecto. Inicializa consumo y fecha actual.

     */

    public Medidor(){

        this.consumoKwh = 0.0;

        this.fechaActual = new Date();

    }



    /*

     * Constructor para inicializar todos los atributos del medidor.

     */

    public Medidor(int idMedidor, String tipo, String marca, String ubicacion, double consumoKwh, Date fechaActual, Date fechaDeInstalacion, int anioDeFabricacion, EstadoDeMedidor estadoDeMedidor, Cliente cliente) {

        this.idMedidor = idMedidor;

        this.tipo = tipo;

        this.marca = marca;

        this.ubicacion = ubicacion;

        this.consumoKwh = consumoKwh;

        this.fechaActual = fechaActual;

        this.fechaDeInstalacion = fechaDeInstalacion;

        this.anioDeFabricacion = anioDeFabricacion;

        this.estadoDeMedidor = estadoDeMedidor;

        this.cliente = cliente;

    }

    

    

    // ------------------ Getters y Setters ------------------

    

    /**

     * @return the idMedidor

     */

    public int getIdMedidor() {

        return idMedidor; // Retorna el ID único.

    }



    /**

     * @param idMedidor the idMedidor to set

     */

    public void setIdMedidor(int idMedidor) {

        this.idMedidor = idMedidor; // Establece el ID único.

    }



    /**

     * @return the tipo

     */

    public String getTipo() {

        return tipo; // Retorna el tipo de medidor.

    }



    /**

     * @param tipo the tipo to set

     */

    public void setTipo(String tipo) {

        this.tipo = tipo; // Establece el tipo.

    }



    /**

     * @return the marca

     */

    public String getMarca() {

        return marca; // Retorna la marca.

    }



    /**

     * @param marca the marca to set

     */

    public void setMarca(String marca) {

        this.marca = marca; // Establece la marca.

    }



    /**

     * @return the ubicacion

     */

    public String getUbicacion() {

        return ubicacion; // Retorna la ubicación física.

    }



    /**

     * @param ubicacion the ubicacion to set

     */

    public void setUbicacion(String ubicacion) {

        this.ubicacion = ubicacion; // Establece la ubicación.

    }



    /**

     * @return the consumoKwh

     */

    public double getConsumoKwh() {

        return consumoKwh; // Retorna el consumo acumulado.

    }



    /**

     * @return the fechaActual

     */

    public Date getFechaActual() {

        return fechaActual; // Retorna la fecha de la última actualización.

    }



    /**

     * @param fechaActual the fechaActual to set

     */

    public void setFechaActual(Date fechaActual) {

        this.fechaActual = fechaActual; // Establece la fecha de actualización.

    }



    /**

     * @return the fechaDeInstalacion

     */

    public Date getFechaDeInstalacion() {

        return fechaDeInstalacion; // Retorna la fecha de instalación.

    }



    /**

     * @param fechaDeInstalacion the fechaDeInstalacion to set

     */

    public void setFechaDeInstalacion(Date fechaDeInstalacion) {

        this.fechaDeInstalacion = fechaDeInstalacion; // Establece la fecha de instalación.

    }



    /**

     * @return the anioDeFabricacion

     */

    public int getAnioDeFabricacion() {

        return anioDeFabricacion; // Retorna el año de fabricación.

    }



    /**

     * @param anioDeFabricacion the anioDeFabricacion to set

     */

    public void setAnioDeFabricacion(int anioDeFabricacion) {

        this.anioDeFabricacion = anioDeFabricacion; // Establece el año de fabricación.

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

        this.cliente = cliente; // Establece el cliente asociado.

    }



    /**

     * @return the estadoDeMedidor

     */

    public EstadoDeMedidor getEstadoDeMedidor() {

        return estadoDeMedidor; // Retorna el estado operativo.

    }



    /**

     * @param estadoDeMedidor the estadoDeMedidor to set

     */

    public void setEstadoDeMedidor(EstadoDeMedidor estadoDeMedidor) {

        this.estadoDeMedidor = estadoDeMedidor; // Establece el estado operativo.

    }

    

    

}