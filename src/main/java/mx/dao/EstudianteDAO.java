package mx.dao;

import mx.dominio.Estudiante;

import java.beans.beancontext.BeanContextServicesSupport;
import java.rmi.server.ExportException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyPermission;

import static mx.conexion.Conexion.getConexion;

// Data Access Object
public class EstudianteDAO {
    public List<Estudiante> listarEstudiantes(){
        List<Estudiante> estudiantes = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        String sql = "SELECT * FROM estudiante ORDER BY id_estudiante";
        try{
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery(); //cuando recuperamos algo de la base de datos, cuando leemos informacion

            while(rs.next()){
                var estudiante =  new Estudiante();
                estudiante.setIdEstudiante(rs.getInt("id_estudiante"));
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));
                estudiantes.add(estudiante);
            }
        }catch (Exception e){
            System.out.println("error al seleccionar datos: " +e.getMessage());
        }
        finally {
            try{
                con.close();
            }catch (Exception e){
                System.out.println("error al cerrar conexion: " + e.getMessage() );
            }
        }
        return estudiantes;
    }
    // findById
    public boolean buscarEstudianteId( Estudiante estudiante ){
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        String sql = "SELECT * FROM estudiante WHERE id_estudiante = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, estudiante.getIdEstudiante());
            rs = ps.executeQuery();
            if (rs.next()){
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));
                return true;
            }
        }catch (Exception e){
            System.out.println("Ocurrio un error al buscar un estudiante: " + e.getMessage());
        }
        finally {
            try{
                con.close();
            }catch (Exception e){
                System.out.println("Ocurrio un error al cerrar conexion: " + e.getMessage());
            }
        }
        return false;
    }

    public boolean agregarEstudiante(Estudiante estudiante) {
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "INSERT INTO estudiante(nombre, apellido, telefono, email) " +
                " VALUES (?, ?, ?, ?) ";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getApellido());
            ps.setString(3, estudiante.getTelefono());
            ps.setString(4, estudiante.getEmail());
            ps.execute();
            return true;
        }catch (Exception e){
            System.out.println("Error al agregar estudiante: "+e.getMessage());
        }

        finally {
            try{
                con.close();
            }catch (Exception e){
                System.out.println("Error con.close:  "+ e.getMessage());
            }
        }
        return false;

    }

    public boolean modificarEstudiante(Estudiante estudiante) {
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "UPDATE estudiante SET nombre=?, apellido=?, telefono=?, " +
                "email=? WHERE id_estudiante=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString( 1, estudiante.getNombre() );
            ps.setString( 2, estudiante.getApellido() );
            ps.setString( 3, estudiante.getTelefono() );
            ps.setString( 4, estudiante.getEmail() );
            ps.setInt(5, estudiante.getIdEstudiante());
            ps.execute();
            return true;

        }catch (Exception e){
            System.out.println("error al modificar estudiante: "+e.getMessage());
        }
        finally {
            try{
                con.close();
            }catch (Exception e) {
                System.out.println("Error al cerrar conexion: "+e.getMessage());
            }
        }

        return false;
    }

    public boolean eliminarEstudiante (Estudiante estudiante){
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "DELETE FROM estudiante WHERE id_estudiante = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1,estudiante.getIdEstudiante());
            ps.execute();
            return  true;
        }catch (Exception e){
            System.out.println("Error al eliminar estudiante: " + e.getMessage());
        }

        finally {
            try {
                con.close();
            }
            catch (Exception e){
                System.out.println("Error al cerrar conexion: "+e.getMessage());
            }
        }
        return false;
    }


    public static void main(String[] args) {
        var estudianteDAO = new EstudianteDAO();
        // Agregar estudiantes
//        var nuevoEstudiante = new Estudiante(1,"pancho", "hernandez", "324342342", "pancho@google.com");
//        var agregado = estudianteDAO.agregarEstudiante(nuevoEstudiante);
//        if (agregado)
//            System.out.println("estudiante agregado: " +nuevoEstudiante);
//        else
//            System.out.println("Error no se agrego el estudiante: " + nuevoEstudiante);

        // Modificar estudiante
//        var estudianteModificar = new Estudiante(1, "Juan Carlos", "Juarez"
//                , "12345678" , "juan@google.com");
//        var modificado  = estudianteDAO.modificarEstudiante(estudianteModificar);
//        if (modificado)
//            System.out.println("estudiante modificado: " + estudianteModificar);
//        else
//            System.out.println("estudiante no modificado: "+estudianteModificar);

        // Eliminar estudiante
        var estudianteEliminar = new Estudiante(3);
        var eliminado = estudianteDAO.eliminarEstudiante(estudianteEliminar);

        if (eliminado)
            System.out.println("Eliminado con exito: " + estudianteEliminar);
        else
            System.out.println("No se elimino el estudiante: " + estudianteEliminar);

        // Listar estudiantes

        System.out.println("Listado de estudiantes");
        List<Estudiante> estudiantes = estudianteDAO.listarEstudiantes();
        estudiantes.forEach(System.out::println);

        // Buscar por id
//        var estudiante1 = new Estudiante(2);
//        System.out.println("estudiante antes de la busqueda: " +estudiante1);
//        var encontrado = estudianteDAO.buscarEstudianteId(estudiante1);
//        if (encontrado) {
//            System.out.println("estudiante encontrado: " + estudiante1);
//        }else {
//            System.out.println("no se encontro: " + estudiante1.getIdEstudiante());
//        }

    }
}
