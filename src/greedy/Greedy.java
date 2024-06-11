package src.greedy;

import src.Procesador;
import src.Tarea;
import src.backtraking.Estado;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class Greedy {

    private Estado solucionFinal;

    public Greedy() {
        this.solucionFinal = new Estado(0);
    }

    public Estado getSolucionFinal() {
        return solucionFinal;
    }


    /*
     *En nuestra implementación de la estrategia Greedy en Java, hemos diseñado un algoritmo que maximiza la eficiencia al ordenar
     * las tareas según su tiempo de procesamiento, desde la más pesada hasta la más liviana.
     * Posteriormente, buscamos el candidato más idóneo que cumpla con las restricciones establecidas por la cátedra
     * y tenga el menor tiempo de procesamiento disponible en el procesador asignado.
     * Es crucial notar que el éxito de Greedy depende de la existencia de un candidato factible para cada tarea asignada, si no lo cual,
     * el algoritmo no podrá generar resultados satisfactorios.
     */
    public Estado greedy(int tiempo, LinkedList<Tarea> tareasCriticas, LinkedList<Tarea> tareasNoCritica, LinkedList<Procesador> listProd) {
        if (tareasCriticas.size() / 2 > listProd.size()) return getSolucionFinal();
        tareasCriticas.addAll(tareasNoCritica);
        Collections.sort(tareasCriticas);
        greedySolucion(tiempo, tareasCriticas, listProd);
        return getSolucionFinal();
    }

    private void greedySolucion(int tiempo, LinkedList<Tarea> listTareas, LinkedList<Procesador> listProd) {
        while (!listTareas.isEmpty()) {
            Tarea nextTarea = listTareas.removeFirst();
            Procesador newProd = obtenerProcesadorFactible(tiempo, listProd, nextTarea);
            if (newProd == null) {
                getSolucionFinal().clearHash();
                return;
            }
            getSolucionFinal().addTarea(nextTarea, newProd);
        }
        if (listTareas.isEmpty()) {
            getSolucionFinal().sumarEstadoFinales();
            getSolucionFinal().setTiempoSolucion(getMaxTiempoProcesamiento());
        }
    }

    private Procesador obtenerProcesadorFactible(int tiempo, LinkedList<Procesador> listProd, Tarea nextTarea) {
        Procesador prodFactible = null;
        for (Procesador prod : listProd) {
            getSolucionFinal().sumarEstado();
            if ((prodFactible == null) || (cumpleCondicion(prod, nextTarea, tiempo) && (prod.compareTo(prodFactible) < 0))) {
                prodFactible = prod;
            }
        }
        return prodFactible;
    }

    private boolean cumpleCondicion(Procesador prod, Tarea tarea, int tiempo) {
        return !superaLimiteTareasCriticas(prod, tarea) && !excedeTiempoSinRefrigeracion(prod, tarea, tiempo);
    }

    private boolean superaLimiteTareasCriticas(Procesador prod, Tarea tarea) {
        return tarea.getCritica() && prod.limiteCriticas();
    }

    private boolean excedeTiempoSinRefrigeracion(Procesador prod, Tarea tarea, Integer tiempo) {
        return !prod.getRefrigerado() && tarea.getTiempo() > tiempo;
    }

    private Integer getMaxTiempoProcesamiento() {
        Iterator<Procesador> listProd = getSolucionFinal().getSolucion().values().iterator();
        Integer tiempoMax = 0;
        while (listProd.hasNext()) {
            Procesador p1 = listProd.next();
            if (p1.getTiempoProcesamiento() > tiempoMax) tiempoMax = p1.getTiempoProcesamiento();
        }
        return tiempoMax;
    }
}
