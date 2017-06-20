/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basededatoscontornos;

import javax.swing.JOptionPane;

/**
 *
 * @author Diego
 */
public class BaseDeDatosContornos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        InterfaceSQLite db = new InterfaceSQLite("contornos.db");
        if(db.itsConnected()){
            System.out.println("All ok.");
        }else{
            System.out.println("It didn't work.");
        }
        int selector;
        do{
            selector = Integer.parseInt(JOptionPane.showInputDialog("1: crear tabla.\n2: select.\n3: insert.\n4: update.\n5: delete.\n6: salir."));
            switch(selector){
                case 1: db.createTable();
                        break;
                case 2: db.select();
                        break;
                case 3: db.crearFila(db.inString("Código"), db.inString("Asignatura"), db.inInt("Horas"), db.inString("Profesor"), db.inString("Curso"));
                        break;
                case 4: db.update(db.inInt("1: Código.\n2: Asignatura.\n3: Horas.\n4: Profesor.\n5: Curso\n6: Todo."), db.inString("Valor"), db.inString("Código a actualizar"));
                        break;
                case 5: db.delete(db.inString("Código a borrar"));
                        break;
            }
        }while(selector!=6);
        db.disconnect();
    }
}
