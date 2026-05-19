package menu;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import model.Articulo;
import model.Categoria;
import model.ArticuloAlimenticio;
import model.ArticuloElectronico;
import repository.Repositorio;
import utils.Secuencias;
import utils.Validaciones;

public class MenuArticulos extends Menu {

    private final Repositorio<Categoria> repositorioCategorias;
    private final Repositorio<Articulo> repositorioArticulos;

    public MenuArticulos(Scanner scanner, Repositorio<Categoria> repCat, Repositorio<Articulo> repArt) {
        super(scanner);
        this.repositorioCategorias = repCat;
        this.repositorioArticulos = repArt;
    }

    @Override
    public void mostrarMenu() {
        System.out.println("--- Menú de Artículos ---");
        System.out.println("1. Agregar Artículo");
        System.out.println("2. Listar Artículos");
        System.out.println("3. Consultar Artículo");
        System.out.println("4. Modificar Artículo");
        System.out.println("5. Eliminar Artículo");
        System.out.println("6. Listar Categorías");
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
                    agregarArticulo();
                    break;
                case 2:
                    listarArticulos();
                    break;
                case 3:
                    consultarArticulo();
                    break;
                case 4:
                    modificarArticulo();
                    break;
                case 5:
                    eliminarArticulo();
                    break;
                case 6:
                    listarCategorias();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción del 1 al 6.");
            }
        } while (opcion != 0);
    }

    private LocalDate pedirFecha(){
        while (true) {
            String fechaInput = leerTexto("Ingrese la fecha de caducidad (yyyy-MM-dd): ");
            try {
                return LocalDate.parse(fechaInput);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha no válido. Por favor, ingrese la fecha en formato yyyy-MM-dd.");
            }
        }
    }

    public void agregarArticulo() {
        if (repositorioCategorias.estaVacio()) {
            System.out.println("No hay categorías disponibles. Por favor, agregue una categoría antes de agregar un artículo.");
            return;
        }

        String nombre = leerTexto("\nIngrese el nombre del artículo: ");
        if (!Validaciones.validarTextoNoVacio(nombre)) {
            System.out.println("El nombre del artículo no puede estar vacío.");
            return;
        }

        double precio = leerDouble("Ingrese el precio del artículo: ");
        if (precio < 0) {
            System.out.println("El precio no puede ser negativo.");
            return;
        }

        int codigoCategoria = leerEntero("Ingrese el código de la categoría: ");
        Categoria categoria = repositorioCategorias.buscarPorCodigo(codigoCategoria);
        if (categoria == null) {
            System.out.println("Categoría no encontrada. Por favor, ingrese un código de categoría válido.");
            return;
        }

        Articulo articulo;
        if (leerSiNo("¿Es un artículo alimenticio?")) {
            LocalDate fechaCaducidad = pedirFecha();
            articulo = new ArticuloAlimenticio(Secuencias.generarCodigoArticulo(), nombre, precio, categoria, fechaCaducidad);
        } else {
            int garantia = leerEntero("Ingrese la garantía en meses: ");
            articulo = new ArticuloElectronico(Secuencias.generarCodigoArticulo(), nombre, precio, categoria, garantia);
        }

        if (repositorioArticulos.agregar(articulo)) {
            limpiarPantalla();
            System.out.println("Artículo agregado exitosamente.");
        } else {
            System.out.println("Error al agregar el artículo. Verifique los datos e intente nuevamente.");
        }
        System.out.println();
    }

    public void listarArticulos() {
        limpiarPantalla();
        if (repositorioArticulos.estaVacio()) {
            System.out.println("No hay artículos disponibles para mostrar.");
            return;
        }
        System.out.println("\n--- Lista de Artículos ---");
        repositorioArticulos.listar().forEach(System.out::println);
        System.out.println();
    }

    public void consultarArticulo() {
        limpiarPantalla();
        int codigo = leerEntero("\nIngrese el código del artículo a consultar: ");
        Articulo articulo = repositorioArticulos.buscarPorCodigo(codigo);
        if (articulo != null) {
            System.out.println("Artículo encontrado: " + articulo);
        } else {
            System.out.println("Artículo no encontrado.");
        }
        System.out.println();
    }

    public void modificarArticulo() {
        limpiarPantalla();
        int codigo = leerEntero("\nIngrese el código del artículo a modificar: ");
        Articulo articulo = repositorioArticulos.buscarPorCodigo(codigo);
        if (articulo == null) {
            System.out.println("Artículo no encontrado.");
            return;
        }

        String nuevoNombre = leerTexto("\nIngrese el nuevo nombre del artículo (deje vacío para mantener el actual): ");
        if (Validaciones.validarTextoNoVacio(nuevoNombre)) {
            articulo.setNombre(nuevoNombre);
        }

        String precioInput = leerTexto("\nIngrese el nuevo precio del artículo (deje vacío para mantener el actual): ");
        if (Validaciones.validarTextoNoVacio(precioInput)) {
            try {
                double nuevoPrecio = Double.parseDouble(precioInput);
                if (nuevoPrecio >= 0) {
                    articulo.setPrecio(nuevoPrecio);
                } else {
                    System.out.println("El precio no puede ser negativo. Manteniendo el precio actual.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada de precio no válida. Manteniendo el precio actual.");
            }
        }

        if (repositorioArticulos.modificar(articulo)) {
            System.out.println("Artículo modificado exitosamente.");
        } else {
            System.out.println("Error al modificar el artículo. Verifique los datos e intente nuevamente.");
        }
        System.out.println();
    }

    public void eliminarArticulo() {
        limpiarPantalla();
        int codigo = leerEntero("\nIngrese el código del artículo a eliminar: ");
        Articulo articulo = repositorioArticulos.buscarPorCodigo(codigo);
        if (articulo == null) {
            System.out.println("Artículo no encontrado.");
            return;
        }

        if (leerSiNo("\n¿Está seguro de eliminar el artículo " + articulo.getNombre() + "?")) {
            if (repositorioArticulos.eliminar(articulo)) {
                Secuencias.registrarBorrado(articulo.getNombre(), articulo.getCodigo());
                System.out.println("Artículo eliminado exitosamente.");
            } else {
                System.out.println("Error al eliminar el artículo.");
            }
        } else {
            System.out.println("Eliminación cancelada.");
        }
        System.out.println();
    }

    public void listarCategorias() {
        limpiarPantalla();
        if (repositorioCategorias.estaVacio()) {
            System.out.println("No hay categorías disponibles para mostrar.");
            return;
        }
        System.out.println("\n--- Lista de Categorías ---");
        repositorioCategorias.listar().forEach(System.out::println);
        System.out.println();
    }
}
