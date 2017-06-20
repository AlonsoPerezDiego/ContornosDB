/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basededatoscontornos;

import com.sun.istack.internal.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego
 */
public class InterfaceSQLite {
    private boolean connected = false;
    Connection connection;
    Statement stmt;

    public InterfaceSQLite() {
    }
    
    /**
     * Connects the data base.
     * @param dataBase 
     */
    public InterfaceSQLite(String dataBase) {
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataBase);
        }catch(SQLException ex){
            System.out.println("No se pudo conectar: \n" + ex);
        }
    }
    
    public boolean itsConnected(){
        if(connection!=null){
                connected = true;
        }
        return connected;
    }
    
    public void disconnect(){
        try{
            connection.close();
        }catch(SQLException ex){
            Logger.getLogger(InterfaceSQLite.class).log(Level.SEVERE, null, ex);
        }
    }
    public void createTable() {
        try {
            stmt = connection.createStatement();
            
            String sql = "CREATE TABLE COLEGIO "
                        + " (CODIGO CHAR(9) PRIMARY KEY  NOT NULL,"
                    + " ASIGNATURA      TEXT   NOT NULL, "
                    + " HORAS           INT    NOT NULL, "
                    + " PROFESOR        CHAR(50), "
                    + " CURSO           CHAR(12))";
            stmt.executeUpdate(sql);
            stmt.close();
            System.out.println("Table created successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            String sql = "DROP TABLE COLEGIO;";
            try {
                stmt.executeUpdate(sql);
                createTable();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(InterfaceSQLite.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Table created successfully");
    }
    
    public void select(){
        System.out.println("------------------------\nSELECT:\n");
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM COLEGIO;"); 
            System.out.println(rs.getString("CODIGO"));
            System.out.println(rs.getString("ASIGNATURA"));
            System.out.println(rs.getInt("HORAS"));
            System.out.println(rs.getString("PROFESOR"));
            System.out.println(rs.getString("CURSO"));
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(InterfaceSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String inString(String s){
        return JOptionPane.showInputDialog(s);
    }
    
    public int inInt(String s){
        try{
            return Integer.parseInt(JOptionPane.showInputDialog(s));
        }catch(Exception ex){
            return 0;
        }
    }
    
    public void delete(String valor){
    try {
      stmt = connection.createStatement();
      String sql = "DELETE from COLEGIO WHERE CODIGO='" + valor + "';";
      stmt.executeUpdate(sql);
      stmt.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
    }
    System.out.println("Operation done successfully");
    }
    
    public void crearFila(String codigo, String asignatura, int horas, String profesor, String curso) {
        try {
            stmt = connection.createStatement();
            String sql = "INSERT INTO COLEGIO (CODIGO,ASIGNATURA,HORAS,PROFESOR,CURSO) "
                    + "VALUES ( '" + codigo + "', '" + asignatura + "', " + horas + ", '" + profesor + "', " + curso + ");";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Records created successfully");
    }
    
    public void update(int campo, String valor, String valorC) {
        try {
            stmt = connection.createStatement();
            String sql = "UPDATE COLEGIO set " + actualizacion(campo, valor)  + " WHERE CODIGO='" + valorC  + "';";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Operation done successfully");
    }
    
    private String actualizacion(int campo, String valor){
        String seleccion;
        switch(campo){
            case 1: seleccion="CODIGO= '" + valor + "'";
                    break;
            case 2: seleccion="ASIGNATURA= '" + valor + "'";
                    break;
            case 3: seleccion="HORAS= " + Integer.parseInt(valor);
                    break;
            case 4: seleccion="PROFESOR= '" + valor + "'";
                    break;
            case 5: seleccion="CURSO= '" + valor + "'";
                    break;
            default:seleccion="";
                    break;
        }
        return seleccion;
    }
}
