package fuzzy.search;

import java.util.Scanner;

/**
 *
 * @author igoodbad
 */
public class OrdenBurbuja {

    public static void main(String[] args) {
        String[] A = {
            "Alberto Vera Padrón",
            "Juan Antonio Perez",
            "Rodolfo Juarez Fernandez",
            "Angela Martinez Ramirez",
            "Fernando Gasca",
            "Claudia Ivonne Pérez",
            "Erick Octavio Ruiz",
            "Fernanda Torrez",
            "Josefina Sampayo",
            "Alcantara Rivero Días"
        };

        // Inicio del metodo de ordenamiento de la Burbuja
        String aux;
        for (int i = 1; i <= A.length; i++) {
            for (int j = 0; j < A.length - i; j++) {
                if (A[j].compareTo(A[j + 1]) > 0) {
                    aux = A[j];
                    A[j] = A[j + 1];
                    A[j + 1] = aux;
                }
            }
        }
        // Fin del metodo de ordenamiento de la Burbuja

        // Imprimimos el array A ordenado. 
        System.out.println("\n array A ordenado: ");
        for (int i = 0; i < A.length; i++) {
            System.out.println(" A[" + i + "] = " + A[i]);
        }
    }

}
