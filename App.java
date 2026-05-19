import java.util.Scanner;

import menu.MenuArticulos;
import menu.MenuCategoria;
import model.Articulo;
import model.Categoria;
import repository.Repositorio;


public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Repositorio<Categoria> repositorioCategorias = new Repositorio<>();
        Repositorio<Articulo> repositorioArticulos = new Repositorio<>();

        MenuCategoria menuCategoria = new MenuCategoria(scanner, repositorioCategorias, repositorioArticulos);
        MenuArticulos menuArticulos = new MenuArticulos(scanner, repositorioCategorias, repositorioArticulos);

        limpiarConsola();
        int opcion;
        do {
            limpiarConsola();
            System.out.println("\n=== Menú Principal ===");
            System.out.println("1. Gestionar Categorías");
            System.out.println("2. Gestionar Artículos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        limpiarConsola();
                        menuCategoria.ejecutar();
                        break;
                    case 2:
                        limpiarConsola();
                        menuArticulos.ejecutar();
                        break;
                    case 0:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción inválida. Por favor, seleccione una opción del 1 al 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
                opcion = -1;
            }            
        } while (opcion != 0);
        scanner.close();

    }    

    private static void limpiarConsola() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < 3; i++) {
            System.out.println();
        }
    }
}
