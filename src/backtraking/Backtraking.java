package src.backtraking;

import src.Procesador;
import src.Tarea;

import java.util.Iterator;
import java.util.LinkedList;

public class Backtraking {

    private Estado mejorSolucion;

    private final int VALOR_INICIAL = -1;

    public Backtraking() {
        this.mejorSolucion = new Estado(-1);
    }

    private Estado getMejorSolucion() {
        return mejorSolucion;
    }

    public Estado backtraking(Integer tiempo, LinkedList<Tarea> criticas, LinkedList<Tarea> noCriticas, LinkedList<Procesador> listProd) {
        Estado actual = new Estado(0);
        if ((criticas.size() / 2) > listProd.size()) return actual;

        criticas.addAll(noCriticas);
        back(tiempo, actual, criticas, listProd);
        getMejorSolucion().setCantEstado(actual.getCantEstado());
        getMejorSolucion().setCantEstadoFinales(actual.getCantEstadoFinales());
        return getMejorSolucion();
    }

    private void back(Integer tiempo, Estado actual, LinkedList<Tarea> listTareas, LinkedList<Procesador> listProd) {
        actual.sumarEstado();
        if (listTareas.isEmpty()) {
            actual.sumarEstadoFinales();
            if (esMejorSolucion(actual)) {
                setMejorSolucion(actual);
            }
        } else {

            Tarea newTarea = listTareas.removeFirst();
            for (Procesador newProd : listProd) {
                if (cumpleCondicion(newProd, newTarea, tiempo) && !poda(newProd)) {
                    actual.addTarea(newTarea, newProd);
                    back(tiempo, actual, listTareas, listProd);
                    actual.removeTarea(newTarea, newProd);
                }
            }
            listTareas.addFirst(newTarea);
        }
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

    private boolean esMejorSolucion(Estado actual) {
        return getMejorSolucion().getTiempoSolucion() == -1 || getMaxTiempoProcesamiento(actual) < getMejorSolucion().getTiempoSolucion();
    }

    private void setMejorSolucion(Estado actual) {
        getMejorSolucion().clearHash();
        copy(actual);
        getMejorSolucion().setTiempoSolucion(getMaxTiempoProcesamiento(actual));
    }

    private void copy(Estado actual) {
        actual.getSolucion().values().iterator()
                .forEachRemaining(prod -> {
                    Procesador newProd = prod.copy();
                    getMejorSolucion().getSolucion().put(newProd.getId(), newProd);
                });

    }

    private Integer getMaxTiempoProcesamiento(Estado actual) {
        Iterator<Procesador> listProd = actual.getSolucion().values().iterator();
        Integer tiempoMax = 0;
        while (listProd.hasNext()) {
            Procesador p1 = listProd.next();
            if (p1.getTiempoProcesamiento() > tiempoMax) tiempoMax = p1.getTiempoProcesamiento();
        }
        return tiempoMax;
    }

    private boolean poda(Procesador prod) {
        return getMejorSolucion().getTiempoSolucion() != VALOR_INICIAL && prod.getTiempoProcesamiento() > getMejorSolucion().getTiempoSolucion();
    }
}
