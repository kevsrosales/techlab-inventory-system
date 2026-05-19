package menu;

import java.util.Scanner;

public abstract class Menu {
    
    protected Scanner scanner;

    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    public abstract void mostrarMenu();
    public abstract void ejecutar();

    protected String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    protected int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
            }
        }
    }

    protected double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número decimal.");
            }
        }
    }

    protected boolean leerSiNo(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (s/n): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            if (respuesta.equals("s") || respuesta.equals("n")) {
                return respuesta.equals("s");
            } else {
                System.out.println("Entrada inválida. Por favor, ingrese 's' para sí o 'n' para no.");
            }
        }
    }

    protected void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < 3; i++) {
            System.out.println();
        }
    }
}
