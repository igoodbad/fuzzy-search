package fuzzy.search;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Clase Fuzzy-Search, para dar funcionalad CRUD con archivo TXT
 *
 * @author Ing Luis Alberto Pérez González
 * @package fuzzy.search
 * @version 1.0
 */
public class FuzzySearch extends Mensajes {

    /**
     * Constante que contiene el nombre del archivo en donde se guardan los
     * datos
     *
     * @type {String}
     */
    private final static String archivo = "fuzzy-search.txt";

    /**
     * Variable que contiene el número de intentos de crear el archivo con el
     * nombre asignado.
     *
     * @type {Integer}
     */
    private static Integer intentos = 0;

    /**
     * Variable que contiene el tipo de interaccion que desea el usuario.
     *
     * @type {Integer}
     */
    private static Integer interaccion = 0;

    /**
     * Variable que contiene el número de acción seleccionada
     *
     * @type {Integer}
     */
    private static Integer accion = 0;

    /**
     * Variable que contiene la entrada de prompt despues del comando ingresado
     *
     * @type {Integer}
     */
    private static String prompt = "";

    /**
     * Constante que contiene los tipos de comillas no aceptados
     *
     * @type {Array}
     */
    private final static String[][] quotes = {{"\\u0093", "“"}, {"\\u0094", "”"}};

    /**
     * Variable que contiene el objeto bidimencional con los comandos
     * disponibles
     *
     * @type {Object}
     */
    private final static Object[][] commands = {{"add ", 2}, {"fuzzy-search ", 1}, {"list", 3}};

    /**
     * Función inicializadora.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Comprobacion de existencia del archivo fuzzy-search.txt
            fileExist();
            // Ejecucion de operaciones con archivo fuzzy-search.txt
            stageWork();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Función para comprobar la existencia del archivo declarado.
     *
     * @return {void}
     */
    public static void fileExist() throws IOException {
        File existFile = new File(archivo);
        if (intentos == 5) {
            // Si en 5 intentos no se puede generar el archivo se genera la terminacion del proceso
            throw new IOException("Se han realizado " + intentos
                    + " para generar el archivo " + archivo
                    + ", no se ha podido generar correctamente y el proceso se ha detenido.");
        }
        if (!existFile.isFile()) {
            // En caso de no existir el archivo se realizan intentos para crear el archivo
            intentos++;
            BufferedWriter txtFile = new BufferedWriter(new FileWriter(archivo));
            txtFile.close();
            // Se hace recursiva la comprobacion del archivo hasta crear el archivo o acumular 5 intentos
            fileExist();
        }
    }

    /**
     * Función para establecer el tipo de interaccion que tendra el usuario con
     * el sistema.
     *
     * @return {void}
     */
    public static void stageWork() throws IOException {
        System.out.println("¿Como desea interactuar con la aplicación?, indique el número de la opcion deseada."
                + "\n1) Interacción manual (funciones disponibles: add,list,search)"
                + "\n2) Ayuda de un asistente (por medio de un menu selecciona la opción que desea realizar)");
        boolean noSelection = false;
        do {
            interaccion = (Integer) Utilidades.getData("Opción: ", "Integer", interaccion);
            switch (interaccion) {
                case 1:
                    withoutAssistant();
                    noSelection = false;
                    break;
                case 2:
                    withAssistant();
                    noSelection = false;
                    break;
                default:
                    System.out.println(Mensajes.getInvalidOption());
                    noSelection = true;
                    break;
            }
        } while (noSelection);

    }

    /**
     * Interaccion con el prompt directamente con comandos pre-establecidos
     *
     * @throws IOException
     */
    private static void withoutAssistant() throws IOException {
        boolean invalidOption = false;
        do {
            accion = 0;
            String accionUsuario = (String) Utilidades.getData("> ", "String", interaccion);
            for (Object[] item : commands) {
                if (accionUsuario.matches(item[0].toString() + "(.*)")) {
                    accion = (Integer) item[1];
                    prompt = accionUsuario.replace((String) item[0], "");
                }
            }
            switch (accion) {
                case 1:
                    searchName();
                    invalidOption = false;
                    break;
                case 2:
                    addName();
                    invalidOption = false;
                    break;
                case 3:
                    if (prompt.isEmpty()) {
                        listNames();
                        invalidOption = false;
                    } else {
                        invalidOption = true;
                        System.out.println("No se reconoce el comando.");
                    }

                    break;
                default:
                    invalidOption = true;
                    System.out.println("No se reconoce el comando.");
                    break;
            }
        } while (invalidOption);
    }

    /**
     * Funcion para interactuar con el prompt utilizando un menú como apoyo
     *
     * @throws IOException
     */
    private static void withAssistant() throws IOException {
        System.out.println("Bienvenido a Fuzzy Search, un manejador para su lista de personas.");
        System.out.println("¿Que acción desea realizar?, indique el número de la opcion deseada.");
        System.out.println("1) Buscar (fuzzy-search)\n"
                + "2) Agregar (add)\n"
                + "3) Listar todo (list)");
        boolean invalidOption = false;
        do {
            accion = 0;
            accion = (Integer) Utilidades.getData("Opción: ", "Integer", interaccion);
            switch (accion) {
                case 1:
                    searchName();
                    invalidOption = false;
                    break;
                case 2:
                    addName();
                    invalidOption = false;
                    break;
                case 3:
                    listNames();
                    invalidOption = false;
                    break;
                default:
                    invalidOption = true;
                    System.out.println("Solo puede seleccionar alguna de las opciones indicadas.");
                    break;
            }
        } while (invalidOption);
    }

    /**
     * Función para agregar un nombre al archivo declarado
     *
     * @return void
     */
    private static void addName() throws IOException {
        boolean formatError = false;
        do {
            String jsonInput = null;
            if (interaccion == 2) {
                System.out.println("Ingrese el nombre que quiere registrar con el siguiente formato: {\"name\": \"Nombre a Registrar\"}");
                jsonInput = (String) Utilidades.getData("add ", "String", interaccion);
            } else {
                if (prompt.equals("")) {
                    boolean readAgain = false;
                    do {
                        jsonInput = (String) Utilidades.getData("> ", "String", interaccion);
                        if (jsonInput.matches(commands[0][0].toString() + "(.*)")) {
                            jsonInput = (jsonInput.replace((String) commands[0][0], "")).trim();
                            readAgain = false;
                        } else {
                            readAgain = true;
                        }
                    } while (readAgain);
                } else {
                    jsonInput = prompt;
                    prompt = "";
                }
            }
            JSONObject obj = null;
            try {
                obj = new JSONObject(jsonInput);
                if (obj.has("name")) {
                    FileWriter fstream = new FileWriter(archivo, true);
                    BufferedWriter bw = new BufferedWriter(fstream);
                    bw.write(obj.optString("name"));
                    bw.newLine();
                    bw.close();
                    System.out.println(Mensajes.getSuccessAddUser());
                    formatError = false;
                } else {
                    throw new JSONException(checkQuote(jsonInput));
                }
            } catch (JSONException jsonEx) {
                System.out.println(Mensajes.getInvalidJSON() + " " + jsonEx.getMessage());
                formatError = true;
            }
        } while (formatError);
    }

    /**
     * Función para listar todos los nombres guardados en el archivo declarado
     *
     * @return void
     */
    private static void listNames() {
        try {
            JSONArray listNames = new JSONArray();
            List<String> listUnsort = new ArrayList<>();
            String nombre;
            FileReader f = new FileReader(archivo);
            BufferedReader b = new BufferedReader(f);
            while ((nombre = b.readLine()) != null) {
                nombre = (nombre.replace("\n", "").replace("\r", "")).trim();
                if (nombre.length() > 1) {
                    listUnsort.add(nombre);
                }
            }
            b.close();
            if (listUnsort.size() > 0) {
                Collections.sort(listUnsort);
                for (String sortName : listUnsort) {
                    listNames.put(new JSONObject().put("name", sortName));
                }
            }

            System.out.println(listNames.toString().replace("[{", "[\n{").replace(",", ",\n").replace("}]", "}\n]"));
        } catch (FileNotFoundException ioe) {
            System.out.println(ioe.getMessage());
        } catch (IOException | JSONException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    /**
     * Función para buscar un nombre los nombres guardados en el archivo
     * declarado
     *
     * @return void
     */
    private static void searchName() throws IOException {
        boolean formatError = false;
        do {
            List<String> listUnsort = Utilidades.readFile(archivo);
            List<String> vector = new ArrayList<>();
            List<String> aux = new ArrayList<>();
            JSONArray listNames = new JSONArray();
            String jsonInput = null;
            if (interaccion == 2) {
                System.out.println("Ingrese el nombre que quiere buscar con el siguiente formato: {\"search\": \"Nombre a buscar\"}");
                jsonInput = (String) Utilidades.getData("fuzzy-search ", "String", interaccion);
            } else {
                if (prompt.equals("")) {
                    boolean readAgain = false;
                    do {
                        jsonInput = (String) Utilidades.getData("> ", "String", interaccion);
                        if (jsonInput.matches(commands[0][0].toString() + "(.*)")) {
                            jsonInput = (jsonInput.replace((String) commands[1][0], "")).trim();
                            readAgain = false;
                        } else {
                            readAgain = true;
                        }
                    } while (readAgain);
                } else {
                    jsonInput = prompt;
                    prompt = "";
                }
            }
            JSONObject obj = null;
            try {
                obj = new JSONObject(jsonInput);
                if (obj.has("search")) {
                    char[] cadena = ((obj.optString("search").toString().toLowerCase()).toCharArray());
                    float lengthString = cadena.length;
                    int exactCoincidence = (int) lengthString;
                    int minCoincidence = (int) (Math.ceil(lengthString / 2));
                    int coincidencia = 0;
                    int items = 1;
                    // algoritmo de busqueda por aproximacion
                    /*Realizamos una primera busqueda*/
                    for (int z = 0; z < listUnsort.size(); z++) {
                        if ((listUnsort.get(z).toLowerCase()).matches("(.*)" + cadena[coincidencia] + "(.*)")) {
                            vector.add(listUnsort.get(z));
                        }
                    }
                    coincidencia++;
                    String compareString = "";
                    boolean itsFinal = false;
                    boolean lastIt = false;
                    // Iteramos sobre los items que coinciden con el primer caracter
                    do {
                        if (coincidencia == exactCoincidence) {
                            for (int nc = 0; nc < cadena.length; nc++) {
                                compareString += cadena[nc];
                            }
                        } else {
                            for (int nc = 0; nc <= coincidencia; nc++) {
                                compareString += cadena[nc];
                            }
                        }
                        //if (vector.size() >= 1 || itsFinal) {
                        if (vector.size() >= 1) {
                            for (int z = 0; z < vector.size(); z++) {
                                if ((vector.get(z).toLowerCase()).matches("(.*)" + compareString + "(.*)")) {
                                    aux.add(vector.get(z));
                                }
                            }
                            vector = aux;
                        } else {
                            //si llega a 0 atraza 1 y es la ultima iteracion
                            coincidencia--;
                        }
                        if (coincidencia == exactCoincidence && vector.size() == 1) {
                            lastIt = true;
                            itsFinal = true;
                        } else if (vector.size() == 0 && coincidencia <= exactCoincidence) {
                            lastIt = true;
                        }
                        if (!lastIt) {
                            itsFinal = true;
                        } else {
                            coincidencia++;
                        }
                    } while (lastIt && itsFinal);
                    if (vector.size() > 0) {
                        Collections.sort(vector);
                        for (String sortName : vector) {
                            listNames.put(new JSONObject().put("name", sortName));
                        }
                    }
                    System.out.println(listNames.toString().replace("[{", "[\n{").replace(",", ",\n").replace("}]", "}\n]"));
                } else {
                    throw new JSONException(checkQuote(jsonInput));
                }
            } catch (JSONException ex) {
                Logger.getLogger(FuzzySearch.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (formatError);
    }

    /**
     * Función para comprobar si las comillas ingresadas son validas para
     * objetos JSON
     *
     * @param verificar La cadena a verificar.
     * @return String Los caracteres invalidos
     */
    public static String checkQuote(String verificar) {
        String found = "";
        for (String[] quote : quotes) {
            if (verificar.matches("(.*)" + quote[0] + "(.*)")) {
                if (!found.isEmpty()) {
                    found += ",";
                }
                found += quote[1];
            }
        }
        if (!found.isEmpty()) {
            found = "Caracter(es) invalido(s): " + found;
        }
        return found;
    }
}
