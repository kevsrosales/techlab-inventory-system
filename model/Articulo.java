package model;

import Interfaces.Calculable;
import Interfaces.Identificable;

public abstract class Articulo implements Identificable, Calculable{
    
    protected int codigo;
    protected String nombre;
    protected double precio;
    protected Categoria categoria;

    public Articulo(int codigo, String nombre, double precio, Categoria categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public abstract String getTipoArticulo();

    @Override
    public int getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        return String.format("Código: %d | Nombre: %s | Precio: %.2f | Categoría: %s | ",
                codigo, nombre, precio, categoria.getNombre());
    }
    
}
