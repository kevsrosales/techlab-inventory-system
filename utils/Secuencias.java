package utils;

import java.util.HashMap;
import java.util.Map;

public class Secuencias {
    
    private static int codigoArticulo = 1;
    private static int codigoCategoria = 1;
    private static Map<String, Integer> historialBorrados = new HashMap<>();

    private Secuencias() { }

    public static int generarCodigoArticulo() {
        return codigoArticulo++;
    }

    public static int generarCodigoCategoria() {
        return codigoCategoria++;
    }

    public static void registrarBorrado(String nombre, int codigo) {
        historialBorrados.put(nombre.toLowerCase(), codigo);
    }

    public static Integer buscarCodigoReciclado(String nombre) {
        return historialBorrados.get(nombre.toLowerCase());
    }

    public static void eliminarDeHistorial(String nombre) {
        historialBorrados.remove(nombre.toLowerCase());
    }
}
