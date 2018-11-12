package fuzzy.search;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Ing Luis Alberto Pérez González
 */
public class Utilidades {

    /**
     * Función para obtener el dato ingresado por el usuario. Función multi
     * proposito.
     *
     * @param mensaje El dato que se le solicita al usuario
     * @param type El tipo de valor que se requiere retornar
     * @param interaccion
     * @return Object El valor generado {Integer|Double|String}
     * @throws java.io.IOException Para corresponder con el tratamiento
     * generalizado de excepciones
     */
    public static Object getData(String mensaje, String type, Integer interaccion) throws IOException {
        Scanner dato = null;
        byte[] text = null;

        String inputUser = null;
        Object returned = null;
        String sig = "n";
        String completed = "n";
        do {
            do {
                if (mensaje != null) {
                    System.out.print(mensaje);
                }
                dato = new Scanner(System.in, "ISO-8859-1");
                text = dato.nextLine().getBytes();
                inputUser = (new String(text, "UTF-8")).trim();
                if (inputUser.equalsIgnoreCase("-help") && interaccion == 2) {
                    System.out.println("ayuda");
                    sig = "n";
                } else {
                    sig = "s";
                }
            } while (sig.equals("n"));
            switch (type) {
                case "Integer":
                    try {
                        returned = Integer.parseInt(inputUser);
                        completed = "s";
                    } catch (NumberFormatException ex) {
                        completed = "n";
                        System.out.println(Mensajes.getInvalidData());
                    }
                    break;
                case "Double":
                    try {
                        returned = Double.parseDouble(inputUser);
                        completed = "s";
                    } catch (NumberFormatException ex) {
                        completed = "n";
                        System.out.println(Mensajes.getInvalidData());
                    }
                    break;
                case "String":
                    try {
                        returned = inputUser;
                        completed = "s";
                    } catch (Exception ex) {
                        completed = "n";
                        System.out.println(Mensajes.getInvalidData());
                    }
                    break;
                default:
                    throw new IOException(Mensajes.getUndefinedType());
            }
        } while (completed.equals("n"));
        return returned;
    }

    /**
     * Función para realizar ordenamiento asendente de cadenas
     *
     * @param listUnsorted La lista de datos a ordenar
     * @return String[] La lista de los datos ordenados
     */
    public static String[] sortAsc(List<String> listUnsorted) {
        String[] list = null;
        if (!listUnsorted.isEmpty()) {
            list = new String[listUnsorted.size()];
            for (int i = 0; i < listUnsorted.size(); i++) {
                list[i] = listUnsorted.get(i);
            }
            String aux;
            for (int i = 1; i <= list.length; i++) {
                for (int j = 0; j < list.length - i; j++) {
                    if (list[j].compareTo(list[j + 1]) > 0) {
                        aux = list[j];
                        list[j] = list[j + 1];
                        list[j + 1] = aux;
                    }
                }
            }
        } else {
            list = new String[0];
        }
        return list;
    }

    /**
     * Función para realizar la lectura de un archivo especificado
     *
     * @param archivo El nombre del archivo en el cual se realizara la busqueda
     * @return List La lista de los valores contenidos en el fichero
     * @throws java.io.IOException Tratamiento de errores generalizado
     */
    public static List<String> readFile(String archivo) throws IOException {
        List<String> lista = new ArrayList<>();
        try {
            FileReader f = new FileReader(archivo);
            String nombre;
            BufferedReader b = new BufferedReader(f);
            while ((nombre = b.readLine()) != null) {
                lista.add((nombre.replace("\n", "").replace("\r", "")).trim());
            }
            b.close();
        }catch(FileNotFoundException fnfe){
            throw new IOException(fnfe.getMessage());
        }
        return lista;
    }

}
