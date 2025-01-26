package com.example.model;

/**
 * Clase que representa un usuario dentro del sistema.
 * Contiene información sobre el puesto, nombre, apellido, usuario y contraseña.
 */
public class Usuario {

    private int puesto;
    private String nombre;
    private String apellido;
    private String usuario;
    private String contrasena;

    /**
     * Constructor de la clase Usuario.
     *
     * @param puesto El puesto del usuario en el sistema (por ejemplo, administrador, empleado).
     * @param nombre El nombre del usuario.
     * @param apellido El apellido del usuario.
     * @param usuario El nombre de usuario (usuario único para iniciar sesión).
     * @param contrasena La contraseña del usuario.
     */
    public Usuario(int puesto, String nombre, String apellido, String usuario, String contrasena) {
        this.puesto = puesto;
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    /**
     * Obtiene el puesto del usuario.
     *
     * @return El puesto del usuario.
     */
    public int getPuesto() {
        return puesto;
    }

    /**
     * Establece el puesto del usuario.
     *
     * @param puesto El nuevo puesto del usuario.
     */
    public void setPuesto(int puesto) {
        this.puesto = puesto;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param nombre El nuevo nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el apellido del usuario.
     *
     * @return El apellido del usuario.
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Establece el apellido del usuario.
     *
     * @param apellido El nuevo apellido del usuario.
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return El nombre de usuario único.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Establece el nombre de usuario.
     *
     * @param usuario El nuevo nombre de usuario único.
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param contrasena La nueva contraseña del usuario.
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Devuelve una representación en cadena del objeto Usuario.
     *
     * @return Una cadena que representa al usuario.
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "puesto=" + puesto +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", usuario='" + usuario + '\'' +
                ", contrasena='" + contrasena + '\'' +
                '}';
    }
}