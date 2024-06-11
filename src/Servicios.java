package src;

import org.jetbrains.annotations.NotNull;
import src.Tarea;
import src.backtraking.Backtraking;
import src.backtraking.Estado;
import src.greedy.Greedy;
import src.utils.CSVReader;

import java.util.*;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {
    private HashMap<String, Tarea> almacenTareas;
    private LinkedList<Procesador> almacenProcesadores;
    private LinkedList<Tarea> tareasCriticas;
    private LinkedList<Tarea> tareasNoCriticas;
    private LinkedList<Tarea> listTareas;

    /*
     * La complejidad temporal del constructor es O(n).
     * La complejidad temporal de la función readTasks es O(t), donde t es el número de tareas, ya que cada línea del archivo se procesa una vez y las operaciones dentro del bucle tienen un costo constante.
     * La complejidad temporal de la función readProcessors es O(p), donde p es el número de procesadores, debido a que también procesa cada línea del archivo una vez con operaciones de costo constante.
     * Por lo tanto, el costo computacional del constructor Servicios es O(t + p), combinando las complejidades de ambas funciones O(n).
     */
    public Servicios(String pathProcesadores, String pathTareas) {
        almacenTareas = new HashMap<String, Tarea>();
        tareasCriticas = new LinkedList<Tarea>();
        tareasNoCriticas = new LinkedList<Tarea>();
        listTareas = new LinkedList<Tarea>();
        CSVReader reader = new CSVReader();
        almacenProcesadores = reader.readProcessors(pathProcesadores);
        reader.readTasks(pathTareas, almacenTareas, tareasCriticas, tareasNoCriticas, listTareas);
    }

    private LinkedList<Tarea> getTareasCriticas() {
        return tareasCriticas;
    }

    private LinkedList<Tarea> getTareasNoCriticas() {
        return tareasNoCriticas;
    }

    private LinkedList<Procesador> getAlmacenProcesadores() {
        return almacenProcesadores;
    }

    private HashMap<String, Tarea> getAlmacenTareas() {
        return almacenTareas;
    }

    private LinkedList<Tarea> getListTareas() {
        return listTareas;
    }

    /*
    *La complejidad computacional temporal de la función servicio1 es O(1),
    *ya que todas las operaciones realizadas (acceso al mapa, acceso a los atributos y creación del objeto)
    *tienen una complejidad constante.
    */
    public Tarea servicio1(String ID) {
        return new Tarea(getAlmacenTareas().get(ID).getId(), getAlmacenTareas().get(ID).getNombre(),
                getAlmacenTareas().get(ID).getTiempo(), getAlmacenTareas().get(ID).getCritica(),
                getAlmacenTareas().get(ID).getPrioridad());
    }

    /*
     * La complejidad computacional de la función servicio2 es O(1).
     * Simplemente devuelven referencias a listas ya existentes (tareasCriticas y tareasNoCriticas),
     * sin realizar operaciones adicionales que dependan del tamaño de las listas.
     */
    public List<Tarea> servicio2(boolean esCritica) {
        return esCritica ? getTareasCriticas() : getTareasNoCriticas();
    }

    /*
     * La complejidad computacional de la función servicio3 es O(n).
     * Esto se debe a que la función itera sobre todos los elementos del iterador una vez (O(n)) y para cada elemento que cumple la condición,
     * realiza una operación de copia que tiene una complejidad constante (O(1)).
     * Por lo tanto, la complejidad total de la función es lineal respecto al número de tareas en el iterador (O(n)).
     * Cabe aclarar que se opto por esta implementancion porque siempre va a ser O(n) y la implementacion del codigo y la estructura no son complejas
     * pero existen otras opticiones como crear un arbol binario donde se ordena por la prioridad aunque en el peor de los casos seria O(n) aunque
     * tendria casos que se asemejarian a (Log2 n).
     */
    public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
        List<Tarea> listPrioridadTareas = new LinkedList<Tarea>();

        getListTareas().iterator().forEachRemaining(nextTarea -> {
            if (nextTarea.estoyEnRango(prioridadInferior, prioridadSuperior)) {
                listPrioridadTareas.add(new Tarea(nextTarea.getId(),
                        nextTarea.getNombre(), nextTarea.getTiempo(),
                        nextTarea.getCritica(), nextTarea.getPrioridad()));
            }
        });
        return listPrioridadTareas;
    }


    public Estado servicioBacktraking(int tiempo) {
        return new Backtraking().backtraking(tiempo, getTareasCriticas(), getTareasNoCriticas(), getAlmacenProcesadores());
    }

    public Estado servicioGreedy(int tiempo) {
        return new Greedy().greedy(tiempo, getTareasCriticas(), getTareasNoCriticas(), getAlmacenProcesadores());
    }

}
