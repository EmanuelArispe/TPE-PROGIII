package src;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class Procesador implements Comparable<Procesador> {

    private String id;
    private String codigo;
    private Boolean refrigerado;
    private Integer anio;
    private LinkedList<Tarea> listTareas;
    private Integer tiempoProcesamiento;
    private Integer cantCriticas;

    private final Integer LIMITE = 2;


    public Procesador(String id, String codigo, Boolean refrigerado, Integer anio) {
        setId(id);
        setCodigo(codigo);
        setRefrigerado(refrigerado);
        setAnio(anio);
        setTiempoProcesamiento(0);
        setCantCriticas(0);
        listTareas = new LinkedList<Tarea>();
    }

    public Procesador(String id, String codigo, Boolean refrigerado, Integer anio, LinkedList<Tarea> listTareas, Integer tiempoProcesamiento, Integer cantCriticas) {
        setId(id);
        setCodigo(codigo);
        setRefrigerado(refrigerado);
        setAnio(anio);
        setTiempoProcesamiento(tiempoProcesamiento);
        setCantCriticas(cantCriticas);
        this.listTareas = listTareas;
    }

    public String getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public Boolean getRefrigerado() {
        return refrigerado;
    }

    public Integer getAnio() {
        return anio;
    }

    public Integer getTiempoProcesamiento() {
        return tiempoProcesamiento;
    }

    private LinkedList<Tarea> getListTareas() {
        return this.listTareas;
    }

    public Integer getCantCriticas() {
        return cantCriticas;
    }

    private void setId(String id) {
        this.id = id;
    }

    private void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    private void setRefrigerado(Boolean refrigerado) {
        this.refrigerado = refrigerado;
    }

    private void setAnio(Integer anio) {
        this.anio = anio;
    }

    private void setCantCriticas(Integer cantCriticas) {
        this.cantCriticas = cantCriticas;
    }

    private void setTiempoProcesamiento(Integer tiempoProcesamiento) {
        this.tiempoProcesamiento = tiempoProcesamiento;
    }

    public void addTarea(Tarea newTarea) {
        setTiempoProcesamiento(getTiempoProcesamiento() + newTarea.getTiempo());
        if (newTarea.getCritica()) setCantCriticas(getCantCriticas() + 1);
        getListTareas().addFirst(newTarea);
    }

    public void deleteTarea(Tarea oldTarea) {
        setTiempoProcesamiento(getTiempoProcesamiento() - oldTarea.getTiempo());
        if (oldTarea.getCritica()) setCantCriticas(getCantCriticas() - 1);
        getListTareas().remove(oldTarea);
    }

    public Procesador copy() {
        LinkedList<Tarea> newList = new LinkedList<Tarea>();
        newList.addAll(getListTareas());
        return new Procesador(getId(), getCodigo(), getRefrigerado(), getAnio(), newList, getTiempoProcesamiento(), getCantCriticas());
    }

    public boolean limiteCriticas() {
        return getCantCriticas() >= LIMITE;
    }

    private String listaToString() {
        String cadena = "";
        for (Tarea tarea : getListTareas()) {
            cadena += tarea.toString() + "\n";
        }
        return cadena;
    }

    @Override
    public String toString() {
        String tareas = (!getListTareas().isEmpty()) ? listaToString() : " No hay tareas asignadas " + "\n";
        return "Procesador: " +
                "Id='" + getId() + '\'' +
                ", Refrigerado = " + getRefrigerado() +
                ", Tiempo Procesamiento= " + getTiempoProcesamiento() +
                "\n" +
                "Lista de Tareas:  " + "\n" + tareas
                ;
    }

    @Override
    public int compareTo(@NotNull Procesador o) {
        return getTiempoProcesamiento().compareTo(o.getTiempoProcesamiento());
    }
}
