package menu;

import java.util.Scanner;
import model.Articulo;
import model.Categoria;
import repository.Repositorio;
import utils.Secuencias;
import utils.Validaciones;

public class MenuCategoria extends Menu {

    private final Repositorio<Categoria> repositorioCategorias;
    private final Repositorio<Articulo> repositorioArticulos;

    public MenuCategoria(Scanner scanner, Repositorio<Categoria> repCat, Repositorio<Articulo> repArt) {
        super(scanner);
        this.repositorioCategorias = repCat;
        this.repositorioArticulos = repArt;
    }

    @Override
    public void mostrarMenu() {
        System.out.println("--- Menú de Categorías ---");
        System.out.println("1. Agregar Categoría");
        System.out.println("2. Listar Categorías");
        System.out.println("3. Consultar Categoría por código");
        System.out.println("4. Modificar Categoría");
        System.out.println("5. Eliminar Categoría");
        System.out.println("0. Volver al Menú Principal");
    }

    @Override
    public void ejecutar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1:
                    agregarCategoria();
                    break;
                case 2:
                    listarCategorias();
                    break;
                case 3:
                    consultarCategoria();
                    break;
                case 4:
                    modificarCategoria();
                    break;
                case 5:
                    eliminarCategoria();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción del 1 al 4.");
            }
        } while (opcion != 0);
    }

    private void agregarCategoria() {
        int codigoFinal;

        String nombre = leerTexto("\nIngrese el nombre de la categoría: ");
        Integer codigoReciclado = Secuencias.buscarCodigoReciclado(nombre);

        if (codigoReciclado != null) {
            if (leerSiNo("Se encontró una categoría eliminada con el mismo nombre. ¿Desea reutilizar el código que poseia antes" 
                + codigoReciclado + "?")) {
                codigoFinal = codigoReciclado;
                Secuencias.eliminarDeHistorial(nombre);
            } else {
                codigoFinal = Secuencias.generarCodigoCategoria();
            }
        } else {
            codigoFinal = Secuencias.generarCodigoCategoria();
        }

        String descripcion = leerTexto("Ingrese la descripción de la categoría: ");
        repositorioCategorias.agregar(new Categoria(codigoFinal, nombre, descripcion));
        limpiarPantalla();
        System.out.println("Categoría agregada exitosamente!");
        System.out.println();
    }

    private void listarCategorias() {
        limpiarPantalla();
        System.out.println("\n--- Lista de Categorías ---");
        if (repositorioCategorias.estaVacio()) {
            System.out.println("No hay categorías registradas.");
        }
        repositorioCategorias.listar().forEach(System.out::println);
        System.out.println();
    }

    private void consultarCategoria() {
        limpiarPantalla();
        if (repositorioCategorias.estaVacio()) {
            System.out.println("\nNo hay categorías disponibles para consultar.");
            return;
        }
        int codigo = leerEntero("\nIngrese el código de la categoría a consultar: ");
        Categoria categoria = repositorioCategorias.buscarPorCodigo(codigo);
        if (categoria != null) {
            System.out.println("Categoría encontrada: " + categoria);
        } else {
            System.out.println("Categoría no encontrada.");
        }
        System.out.println();
    }

    private void modificarCategoria() {
        limpiarPantalla();
        if (repositorioCategorias.estaVacio()) {
            System.out.println("\nNo hay categorías disponibles para modificar.");
            return;
        }
        int codigo = leerEntero("\nIngrese el código de la categoría a modificar: ");
        Categoria categoria = repositorioCategorias.buscarPorCodigo(codigo);
        if (categoria == null) {
            System.out.println("Categoría no encontrada.");
            return;
        }

        String nuevoNombre = leerTexto("Ingrese el nuevo nombre de la categoría, de no querer cambiar, no ponga nada (actual: " 
            + categoria.getNombre() + "): ");
        
        if (Validaciones.validarTextoNoVacio(nuevoNombre)) {
            boolean duplicado = repositorioCategorias.listar().stream()
            .anyMatch(c -> c.getNombre().equalsIgnoreCase(nuevoNombre) && c.getCodigo() != categoria.getCodigo());

            if (duplicado) {
                System.out.println("Error: Ya existe otra categoría con el nombre '" + nuevoNombre + "'.");
            } else {
                Secuencias.eliminarDeHistorial(nuevoNombre);
                categoria.setNombre(nuevoNombre);
                System.out.println("Nombre actualizado correctamente.");
            }
        } else {
            System.out.println("Nombre inválido. No se realizaron cambios en el nombre.");
        }

        String nuevaDescripcion = leerTexto("Ingrese la nueva descripción de la categoría (actual: " + categoria.getDescripcion() + "): ");
        if (Validaciones.validarTextoNoVacio(nuevaDescripcion)) {
            categoria.setDescripcion(nuevaDescripcion);
        } else {
            System.out.println("La descripción no puede estar vacía. No se modificará la descripción.");
        }

        System.out.println("Categoría modificada exitosamente.");
        System.out.println();
    }

    private void eliminarCategoria() {
        limpiarPantalla();
        if (repositorioCategorias.estaVacio()) {
            System.out.println("\nNo hay categorías disponibles para eliminar.");
            return;
        }
        int codigo = leerEntero("\nIngrese el código de la categoría a eliminar: ");
        Categoria categoria = repositorioCategorias.buscarPorCodigo(codigo);
        if (categoria == null) {
            System.out.println("Categoría no encontrada.");
            return;
        }

        boolean tieneArticulos = repositorioArticulos.listar().stream()
                .anyMatch(articulo -> articulo.getCodigo() == categoria.getCodigo());

        if (tieneArticulos) {
            System.out.println("No se puede eliminar la categoría porque tiene artículos asociados.");
        } else if (leerSiNo("\nEsta seguro de eliminar " + categoria.getNombre() + "?")){
            Secuencias.registrarBorrado(categoria.getNombre(), categoria.getCodigo());
            if (repositorioCategorias.eliminar(categoria)) {
                System.out.println("Categoría eliminada exitosamente.");
            } else {
                System.out.println("Error al eliminar la categoría.");
            }
        } else {
            System.out.println("Eliminación cancelada.");
        }
        System.out.println();
    }
}
