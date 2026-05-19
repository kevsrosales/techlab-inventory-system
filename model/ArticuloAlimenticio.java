package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import utils.Validaciones;

public class ArticuloAlimenticio extends Articulo {

    private LocalDate fechaCaducidad;

    public ArticuloAlimenticio(int codigo, String nombre, double precio, Categoria categoria, LocalDate fechaCaducidad) {
        super(codigo, nombre, precio, categoria);
        this.fechaCaducidad = fechaCaducidad;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        if (!Validaciones.validarNoNegativo(ChronoUnit.DAYS.between(LocalDate.now(), fechaCaducidad))) {
            throw new IllegalArgumentException("La fecha de caducidad no puede ser anterior a la fecha actual.");
        }
        this.fechaCaducidad = fechaCaducidad;
    }

    @Override
    public String getTipoArticulo() {
        return "Alimenticio";
    }

    @Override
    public double calcularPrecioFinal() {
        long diasFaltantes = ChronoUnit.DAYS.between(LocalDate.now(), fechaCaducidad);

        if (diasFaltantes <= 5) {
            return precio * 0.80;
        } else if (diasFaltantes <= 10) {
            return precio * 0.90;
        }
        return precio;
    }

    public String toString(){
        return super.toString() + "Fecha de Caducidad: " + fechaCaducidad;
    }
    
}
