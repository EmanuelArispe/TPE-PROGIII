import src.Servicios;

public class Main {

    public static void main(String args[]) {
        Servicios servicios = new Servicios("./src/datasets/Procesadores.csv", "./src/datasets/Tareas.csv");

        System.out.println("Muestro tarea segun ID: ");
        System.out.println(servicios.servicio1("T2").toString());
        System.out.println();

        System.out.println("Servicios con prioridad entre 50 y 100 ");
        servicios.servicio3(50, 100).forEach(tarea -> System.out.println(tarea.toString()));
        System.out.println();

        System.out.println("Servicios No criticos");
        servicios.servicio2(false).forEach(tarea -> System.out.println(tarea.toString()));


        System.out.println("\n" + "Backtraking");
        System.out.println(servicios.servicioBacktraking(5).toString());

        System.out.println("\n" + "Greedy");
        System.out.println((servicios.servicioGreedy(5).toString()));

    }


}
