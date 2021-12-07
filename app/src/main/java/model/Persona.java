package model;

public class Persona {
    private String nomUsu, nombre, apellido, celular, perfil, email, direccion;

    public Persona() {
    }

    public Persona(String nomUsu, String nombre, String apellido, String celular, String perfil, String email, String direccion) {
        this.nomUsu = nomUsu;
        this.nombre = nombre;
        this.apellido = apellido;
        this.celular = celular;
        this.perfil = perfil;
        this.email = email;
        this.direccion = direccion;
    }

    public String getNomUsu() {
        return nomUsu;
    }
    public void setNomUsu(String nomUsu) {
        this.nomUsu = nomUsu;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getCelular() {
        return celular;
    }
    public void setCelular(String celular) {
        this.celular = celular;
    }
    public String getPerfil() {
        return perfil;
    }
    public void setPerfil(String clave) {
        this.perfil = clave;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
