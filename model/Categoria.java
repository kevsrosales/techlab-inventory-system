package model;

import Interfaces.Identificable;
import utils.Validaciones;

public class Categoria implements Identificable {

    private int codigo;
    private String nombre;
    private String descripcion;

    public Categoria(int codigo, String nombre, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (Validaciones.validarTextoNoVacio(nombre)) {
            this.nombre = nombre;
        } else {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío.");
        }
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (Validaciones.validarTextoNoVacio(descripcion)) {
            this.descripcion = descripcion;
        } else {
            throw new IllegalArgumentException("La descripción no puede ser nula o vacía.");
        }
    }

    @Override
    public int getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    } 
}
