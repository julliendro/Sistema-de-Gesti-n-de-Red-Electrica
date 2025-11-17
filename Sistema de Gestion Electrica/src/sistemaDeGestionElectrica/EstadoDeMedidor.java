/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package sistema.de.gestion.electrica;

/**
 * Clase EstadoDeMedidor.
 * Defino el estado actual de funcionamiento o servicio del medidor con esta clase (ej: "Activo", "Suspendido", "Dado de Baja").
 */
public class EstadoDeMedidor{
    private int idEstadoDeMedidor; // Este es mi identificador único del estado.
    private String nombre; // Este es el nombre descriptivo del estado (ej: "Activo").
    
    /*
     * Este es mi constructor por defecto. Inicializo el estado con un valor predeterminado.
     */
    public EstadoDeMedidor(){
        super();
        this.nombre = "Desconocido"; // Asigno un nombre por defecto para evitar nulos.
    }

    /*
     * Este constructor lo uso para inicializar el ID y el nombre del estado al crear un objeto.
     */
    public EstadoDeMedidor(int idEstadoDeMedidor, String nombre) {
        this.idEstadoDeMedidor = idEstadoDeMedidor;
        this.nombre = nombre;
    }

    // ------------------ Getters y Setters ------------------
        
    /**
     * @return Retorna el idEstadoDeMedidor, el ID único del estado.
     */
    public int getIdEstadoDeMedidor() {
        return idEstadoDeMedidor; 
    }

    /**
     * @param idEstadoDeMedidor Establece el ID del estado.
     */
    public void setIdEstadoDeMedidor(int idEstadoDeMedidor) {
        this.idEstadoDeMedidor = idEstadoDeMedidor; 
    }

    /**
     * @return Retorna el nombre del estado (ej: Activo).
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