
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema.de.gestion.electrica;
import java.util.Date;
/**
 * Clase abstracta que sirve como base para todos los tipos de usuarios (Cliente, Operario, etc.).
 *
 */
public abstract class Usuario {
    private int idUsuario; // Identificador único del usuario.
    private String nombre; // Nombre de pila del usuario.
    private String apellido; // Apellido del usuario.
    private String direccion; // Dirección de residencia u oficina.
    private String telefono; // Número de teléfono de contacto.
    private String correoElectronico; // Correo electrónico del usuario.
    private String contrasena; // Contraseña o hash de la contraseña.
    private Date fechaRegistro; // Fecha en que el usuario fue registrado.
    private Permisos permisos; // Objeto que define el rol y las capacidades del usuario.
    
    /*
     * Constructor por defecto. Inicializa solo la fecha de registro.
     */
    public Usuario() {
        this.fechaRegistro = new Date(); // Asigna la fecha actual como fecha de registro.
    }
    
    /*
     * Constructor para inicializar todos los atributos básicos del usuario.
     */
    public Usuario(int idUsuario, String nombre, String apellido, String direccion, String telefono, String correoElectronico, String contrasena, Permisos permisos) {
        this.idUsuario = idUsuario; // Inicializa el ID del usuario.
        this.nombre = nombre; // Inicializa el nombre.
        this.apellido = apellido; // Inicializa el apellido.
        this.direccion = direccion; // Inicializa la dirección.
        this.telefono = telefono; // Inicializa el teléfono.
        this.correoElectronico = correoElectronico; // Inicializa el correo electrónico.
        this.contrasena = contrasena; // Inicializa la contraseña.
        this.permisos = permisos; // Inicializa el objeto Permisos.
        this.fechaRegistro = new Date(); // Asigna la fecha de registro al momento de la creación.
    }
    
    /*
     * Método abstracto que obliga a las subclases a definir su tipo (ej: "Cliente", "Operario").
     */
    public abstract String getTipoUsuario(); // Debe ser implementado por las clases que hereden de Usuario.
    
    //public String mostrarDatosBase() { // Línea comentada original

    /*
     * Simula la validación de credenciales para iniciar sesión.
     * @return true si las credenciales coinciden, false en caso contrario.
     */
    public boolean iniciarSesion(String email, String password){
        // Verifica si el email y la contraseña coinciden con los del objeto.
        if(this.correoElectronico.equals(email) && this.contrasena.equals(password)){
            System.out.println(this.getTipoUsuario() + " " + this.nombre + " ha iniciado sesión"); // Mensaje de éxito al iniciar sesión.
            return true; // Retorna verdadero si las credenciales son correctas.
        }
        return false; // Retorna falso si las credenciales no son correctas.
    }
    
    /*
     * Retorna una cadena formateada con los datos de contacto del usuario.
     */
    public String obtenerDatosContacto() {
        return "Dirección: " + direccion + ", Teléfono: " + telefono + ", Email: " + correoElectronico; // Concatena y retorna los datos de contacto.
    }
    
    // ------------------ Getters y Setters ------------------

    public int getIdUsuario() {
        return idUsuario; // Retorna el identificador único.
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario; // Establece el identificador único.
    }

    public String getNombre() {
        return nombre; // Retorna el nombre del usuario.
    }

    public void setNombre(String nombre) {
        this.nombre = nombre; // Establece el nombre del usuario.
    }

    public String getApellido() {
        return apellido; // Retorna el apellido del usuario.
    }

    public void setApellido(String apellido) {
        this.apellido = apellido; // Establece el apellido del usuario.
    }

    public String getDireccion() {
        return direccion; // Retorna la dirección.
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion; // Establece la dirección.
    }

    public String getTelefono() {
        return telefono; // Retorna el número de teléfono.
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono; // Establece el número de teléfono.
    }

    public String getCorreoElectronico() {
        return correoElectronico; // Retorna el correo electrónico.
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico; // Establece el correo electrónico.
    }

    public String getContrasena() {
        return contrasena; // Retorna la contraseña (en una aplicación real, sería un hash).
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena; // Establece la contraseña.
    }

    public Date getFechaRegistro() {
        return fechaRegistro; // Retorna la fecha de registro del usuario.
    }

    public Permisos getPermisos() {
        return permisos; // Retorna el objeto Permisos asociado al usuario.
    }

    public void setPermisos(Permisos permisos) {
        this.permisos = permisos; // Establece el objeto Permisos asociado.
    }
    
    
    
}
