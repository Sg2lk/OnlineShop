package SistemaTienda;

/**
 * Esta tienda cuenta con un patrón de diseño MVC, es decir Modelo-Vista-Controlador.
 * La parte del "Modelo" se refiere a nuestro almacenamiento de la información de nuestros usuarios, de nuestros artículos, etc.
 * La parte de "Vista" consiste en todas las clases que generan las distintas ventanas con las diferentes utilidades de java swing.
 * La parte de "Controlador" son todas aquellas clases que sirven de intermediario entre la "Vista" y el "Modelo".
 * Toda información recogida dentro de las clases correspondientes a "Vista" pasan por un control de errores y se realiza
 * la operación deseada con esa información para escribirlos, actualizarlos o leerlos de nuestra base de datos.
 *
 * Esta clase es con la que inicia todo el programa llamando a la primera interfaz gráfica.
 */

public class Main {
    public static void main(String[] args) {
        new Inicio();
    }
}