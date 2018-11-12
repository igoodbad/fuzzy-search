package fuzzy.search;

/**
 *
 * @author Ing Luis Alberto Pérez González
 */
public class Mensajes {
    
    /**
     * Constante que tiene el mensaje para datos no definidos
     * @type {String}
     */
    private final static String undefinedType = "El tipo de dato no fue definido.";
    
    /**
     * Constante que tiene el mensaje para datos invalidos
     * @type {String}
     */
    private final static String invalidData = "Por favor ingrese un dato valido.";
    
    /**
     * Constante que tiene el mensaje para error en la estructura del JSON
     * @type {String}
     */
    private final static String invalidJSON = "Error en el formato JSON.";
    
    /**
     * Constante que tiene el mensaje para opcion invalida
     * @type {String}
     */
    private final static String invalidOption = "Debe de indicar el número de alguna de las opciones.";
    
    /**
     * Constante que tiene el mensaje para usuario registrado
     * @type {String}
     */
    private final static String successAddUser = "Usuario agregado";

    public static String getUndefinedType() {
        return undefinedType;
    }

    public static String getInvalidData() {
        return invalidData;
    }

    public static String getInvalidJSON() {
        return invalidJSON;
    }

    public static String getInvalidOption() {
        return invalidOption;
    }

    public static String getSuccessAddUser() {
        return successAddUser;
    }


}
