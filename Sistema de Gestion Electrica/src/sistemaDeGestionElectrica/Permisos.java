/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema.de.gestion.electrica;

/**
 * Clase Permisos.
 * Define el conjunto de derechos o rol asignado a un Usuario (ej: "Cliente", "Operario").
 */
public class Permisos {
    private int idPermisos; // Identificador único del conjunto de permisos.
    private String nombre; // Nombre del rol o conjunto de permisos (ej: "Administrador").
    private String descripcion; // Descripción detallada de las capacidades de este permiso.
    
    /*
     * Constructor por defecto.
     */
    public Permisos() {}
    
    /*
     * Constructor para inicializar todos los atributos del objeto Permisos.
     */
    public Permisos (int idPermisos, String nombre, String descripcion) {
        this.idPermisos = idPermisos;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // ------------------ Getters y Setters ------------------

    /*
     * @return the idPermisos
     */
    public int getIdPermisos() {
        return idPermisos; // Retorna el ID de los permisos.
    }

    /**
     * @param idPermisos the idPermisos to set
     */
    public void setIdPermisos(int idPermisos) {
        this.idPermisos = idPermisos; // Establece el ID de los permisos.
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre; // Retorna el nombre del rol.
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre; // Establece el nombre del rol.
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion; // Retorna la descripción del permiso.
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion; // Establece la descripción.
    }
    
    
}
