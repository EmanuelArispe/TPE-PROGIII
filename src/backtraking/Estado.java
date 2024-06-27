package src.backtraking;

import src.Procesador;
import src.Tarea;

import java.util.HashMap;
import java.util.Iterator;

public class Estado {
    private HashMap<String, Procesador> solucion;
    private int tiempoSolucion;
    private int cantEstadoFinales;
    private int cantEstado;

    public Estado(int valor) {
        solucion = new HashMap<String, Procesador>();
        setTiempoSolucion(valor);
        setCantEstadoFinales(0);
        setCantEstado(0);
    }

    public void setTiempoSolucion(int tiempoSolucioActual) {
        this.tiempoSolucion = tiempoSolucioActual;
    }

    public int getTiempoSolucion() {
        return tiempoSolucion;
    }

    public int getCantEstadoFinales() {
        return cantEstadoFinales;
    }


    protected void setCantEstadoFinales(int cantEstado) {
        this.cantEstadoFinales = cantEstado;
    }

    public void sumarEstadoFinales() {
        setCantEstadoFinales(getCantEstadoFinales() + 1);
    }

    public int getCantEstado() {
        return cantEstado;
    }

    protected void setCantEstado(int cantEstado) {
        this.cantEstado = cantEstado;
    }

    public HashMap<String, Procesador> getSolucion() {
        return solucion;
    }

    public void sumarEstado() {
        setCantEstado(getCantEstado() + 1);
    }

    public void addTarea(Tarea newTarea, Procesador prod) {
        if (!getSolucion().containsKey(prod)) {
            getSolucion().put(prod.getId(), prod);
        }
        getSolucion().get(prod.getId()).addTarea(newTarea);
    }

    public void removeTarea(Tarea oldTarea, Procesador prod) {
        getSolucion().get(prod.getId()).deleteTarea(oldTarea);
    }

    public void clearHash() {
        getSolucion().clear();
    }


    private String solucionToString() {
        Iterator<Procesador> list = getSolucion().values().iterator();
        String cadena = "";
        while (list.hasNext()) {
            Procesador p1 = list.next();
            cadena += p1.toString() + "\n";
        }
        return cadena;
    }

    @Override
    public String toString() {
        return !solucion.isEmpty() ? " --- Mejor Estado Solucion ---" + "\n" + "\n" + solucionToString() +
                "Tiempo max Solucion = " + getTiempoSolucion() + "\n" +
                "Cantidad de Estado Finales = " + getCantEstadoFinales() + "\n" +
                "Cantidad de Estado Totales = " + getCantEstado() + "\n" + "\n" +
                " ------------------------------------------------------------------------------------------ "
                : "--- No se encontro una solucion --- " + "\n";
    }
}
