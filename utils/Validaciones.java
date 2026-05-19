package utils;

public final class Validaciones {
    
    private Validaciones() { }

    public static boolean validarTextoNoVacio(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    public static boolean validarLongitudMaxima(String texto, int longitudMaxima) {
        return texto != null && texto.length() <= longitudMaxima;
    }

    public static boolean validarNoNegativo(double numero) {
        return numero >= 0;
    }

    public static boolean validarNoNegativo(int numero) {
        return numero >= 0;
    }
}
