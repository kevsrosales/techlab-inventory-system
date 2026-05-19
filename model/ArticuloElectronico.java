package model;

import utils.Validaciones;

public class ArticuloElectronico extends Articulo {

    private int garantiaMeses;

    public ArticuloElectronico(int codigo, String nombre, double precio, Categoria categoria, int garantiaMeses) {
        super(codigo, nombre, precio, categoria);
        this.garantiaMeses = garantiaMeses;
    }

    public int getGarantiaMeses() {
        return garantiaMeses;
    }

    public void setGarantiaMeses(int garantiaMeses) {
        if (Validaciones.validarNoNegativo(garantiaMeses)) {
            this.garantiaMeses = garantiaMeses;
        } else {
            throw new IllegalArgumentException("La garantía en meses no puede ser negativa.");
        }
    }

    @Override
    public String getTipoArticulo() {
        return "Electrónico";
    }

    @Override
    public double calcularPrecioFinal() {
        if (garantiaMeses > 12) {
            return precio * 1.10;
        } 
        return precio;
    }
    

}
