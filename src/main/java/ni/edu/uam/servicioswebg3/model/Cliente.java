package ni.edu.uam.servicioswebg3.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class Cliente {
    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String pais;
    private String ciudad;
    private String direccion;
    private double ingresoAnual;
    private int puntajeCredito;
    private boolean tieneDeuda;
    private double montoDeuda;
    private String estado;
    private OffsetDateTime creadoEn;
    private OffsetDateTime actualizadoEn;

    public Cliente() {
    }

    /** Usado por la columna "Nombre" de la tabla. */
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getIngresoAnual() {
        return ingresoAnual;
    }

    public void setIngresoAnual(double ingresoAnual) {
        this.ingresoAnual = ingresoAnual;
    }

    public int getPuntajeCredito() {
        return puntajeCredito;
    }

    public void setPuntajeCredito(int puntajeCredito) {
        this.puntajeCredito = puntajeCredito;
    }

    public boolean isTieneDeuda() {
        return tieneDeuda;
    }

    public void setTieneDeuda(boolean tieneDeuda) {
        this.tieneDeuda = tieneDeuda;
    }

    public double getMontoDeuda() {
        return montoDeuda;
    }

    public void setMontoDeuda(double montoDeuda) {
        this.montoDeuda = montoDeuda;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public OffsetDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(OffsetDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public OffsetDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    public void setActualizadoEn(OffsetDateTime actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }
}
