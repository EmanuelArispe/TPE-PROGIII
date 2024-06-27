package src;

public class Tarea implements Comparable<Tarea> {
    private String id;
    private String nombre;
    private Integer tiempo;
    private Boolean critica;
    private Integer prioridad;

    public Tarea(String id, String nombre, Integer tiempo, Boolean critica, Integer prioridad) {
        this.id = id;
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.critica = critica;
        this.prioridad = prioridad;
    }
    public Tarea(){
        this.id = "-1";
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getTiempo() {
        return tiempo;
    }

    public Boolean getCritica() {
        return critica;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public boolean estoyEnRango(int prioridadInferior, int prioridadSuperior) {
        return (this.getPrioridad() >= prioridadInferior && this.getPrioridad() <= prioridadSuperior);
    }

    @Override
    public String toString() {
        return (!this.id.equals("-1")) ?  " Id = '" + getId() + '\'' +
                ", Nombre = '" + getNombre() + '\'' +
                ", Tiempo = " + getTiempo() +
                ", Critica = " + getCritica() +
                ", Prioridad = " + getPrioridad() : " Tarea inexistente ";
    }

    @Override
    public int compareTo(Tarea o) {
        return getTiempo().compareTo(o.getTiempo());
    }
}
