package mx.presentacion;


import mx.dao.EstudianteDAO;
import mx.dominio.Estudiante;

import java.sql.SQLOutput;
import java.util.Scanner;

public class SistemaEstudianteApp {
    public static void main(String[] args) {
        var salir = false;
        var consola = new Scanner(System.in);

        // Se crea una instancia clase servicio
        var estudianteDAO = new EstudianteDAO();
        while (!salir){
            try{
                mostrarMenu();
                salir = ejecutarOpciones(consola, estudianteDAO);
            }catch (Exception e){
                System.out.println("error en el ciclo: "+e.getMessage());
            }
            System.out.println("");

        }



    }
    private static void  mostrarMenu(){
        System.out.println("""
                ***  Sistema de estudiantes ****
                1. Listar estudiantes
                2. Buscar estudiantes
                3. Agregar estudiantes
                4. Modificar estudiantes
                5. Eliminar estudiantes
                6. Salir
                Elige una opcion:
                """);
    }
    public static boolean ejecutarOpciones(Scanner consola, EstudianteDAO estudianteDAO){
        var opcion = Integer.parseInt(consola.nextLine());
        var salir = false;
        switch (opcion){
            case 1 -> { // Listar estudiantes
                var estudiantes = estudianteDAO.listarEstudiantes();
                estudiantes.forEach(System.out::println);;
            }
            case 2 ->{ // Buscar estudiante por id
                System.out.println("teclea el id_estudiante a buscar");
                var idEstudiante = Integer.parseInt(consola.nextLine());
                var estudiante = new Estudiante(idEstudiante);
                var encontrado  = estudianteDAO.buscarEstudianteId(estudiante);
                if (encontrado)
                    System.out.println("Estudiante encontrado: " + estudiante);
                else
                    System.out.println("Estudiante no encontrado: " + estudiante);
            }
            case 3 ->{ // Agregar estudiantes
                System.out.println("Agregar estudiantes: ");
                System.out.print("Nombre:");
                var nombre = consola.nextLine();
                System.out.print("Apeliido:");
                var apellido = consola.nextLine();
                System.out.print("Telefono:");
                var telefono = consola.nextLine();
                System.out.print("Email:");
                var email = consola.nextLine();

                // Crear el objeto estudiantes (sin el id)

                var estudiante = new Estudiante(nombre, apellido, telefono, email);
                var agregado = estudianteDAO.agregarEstudiante(estudiante);

                if (agregado)
                    System.out.println("Estudiante agregado: " + estudiante);
                else
                    System.out.println("Estudiante NO agregado: " + estudiante);
            }
            case 4 ->{ // Modificar estudiante
                System.out.println("Modificar estudiante: ");
                System.out.println("Id estudiante");

                var idEstudiante =  Integer.parseInt(consola.nextLine());

                System.out.print("Nombre:");
                var nombre = consola.nextLine();
                System.out.print("Apeliido:");
                var apellido = consola.nextLine();
                System.out.print("Telefono:");
                var telefono = consola.nextLine();
                System.out.print("Email:");
                var email = consola.nextLine();

                // Crear el objeto estudiante a modificar
                var estudiante = new Estudiante(idEstudiante, nombre, apellido, telefono, email);
                var modificado = new EstudianteDAO().modificarEstudiante(estudiante);

                if (modificado)
                    System.out.println("Estudiante modificado: " + estudiante);
                else
                    System.out.println("Estudiante NO modificado: " + estudiante);
            }
            case 5 -> { // Eliminar estudiante
                System.out.println("Eliminar estudiante: ");
                System.out.print("Id estudiante: ");
                var idEstudiante = Integer.parseInt(consola.nextLine());
                var estudiante = new Estudiante(idEstudiante);
                var eliminado = new EstudianteDAO().eliminarEstudiante(estudiante);
                if (eliminado)
                    System.out.println("Estudiante eliminado: " + estudiante);
                else
                    System.out.println("Estudiante NO eliminado: " + estudiante);


            }
            case 6 ->{ // Salir
                System.out.println("Hasta pronto");
                salir = true;
            }
            default -> System.out.println("Opcion no reconocida ");
        }
        return salir;
    }
}