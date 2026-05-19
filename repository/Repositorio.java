package repository;

import java.util.ArrayList;
import java.util.List;

import Interfaces.Identificable;
import model.Articulo;

public class Repositorio<T extends Identificable> {

    private final ArrayList<T> lista = new ArrayList<>();

    public boolean agregar(T objeto) {
        if (objeto != null && buscarPorCodigo(objeto.getCodigo()) == null) {
            return lista.add(objeto);
        } 
        return false;
    }
    
    public List<T> listar() {
        return new ArrayList<>(lista);
    }

    public T buscarPorCodigo(int codigo) {
        for (T objeto : lista) {
            if (objeto.getCodigo() == codigo) {
                return objeto;
            }
        }
        return null;
    }

    public boolean eliminar(T objeto) {
        return lista.remove(objeto);
    }

    public boolean estaVacio() {
        return lista.isEmpty();
    }

    public int cantidad() {
        return lista.size();
    }

    public boolean modificar(Articulo articulo) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCodigo() == articulo.getCodigo()) {
                lista.set(i, (T) articulo);
                return true;
            }
        }
        return false;
    }

}
