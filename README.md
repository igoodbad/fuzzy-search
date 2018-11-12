# fuzzy-search
Aplicación interactiva 

Esta aplicación proporciona interacción con un archivo txt. La interacción puede darse directamente por comandos de consola o con ayuda de un menú simplificado

Funciones:

add: permite agregar un nuevo nombre, se debe usar la sintaxis 
    add {"name": "Juan Antonio Perez"}
list: permite listar todos los nombres registrados en el archivo txt de forma ascendente, solo se debe ingresar el comando
    list
search: permite realizar búsquedas dentro del archivo txt, se debe utilizar la sintaxis
    fuzzy-search {"search": "Alver"}
    
Algoritmo de búsqueda.

Se aplica un algoritmo de búsqueda por descarte y hit de coincidencias, está estructurado con un método burbuja y paralelamente trata de implementar una búsqueda binaria de tipo cascada fraccional, dicha búsqueda genera un Array ordenado que descarta los demás elementos que no coinciden con un criterio.
Principalmente ejecuta una primera iteración la cual elimina la mayoría de los elementos que no cumplen con el primer carácter del criterio buscado.

El algoritmo trata de suavizar la búsqueda binaria de cascada fraccional, la cual es muy estricta con el argumento introducido, ya que trata de solventar una búsqueda exacta y en esta ocasión lo que se requiere es una búsqueda que retorne la o las palabras que más coincidencias tenga.
