/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inscribir_materias;

/**
 * @author VALENTINA
 */

public class Inscribir_Materias {
    
    public static void main(String[] args) {        
        
        ManejarArchivos manejarArchivos = new ManejarArchivos();
        manejarArchivos.crearArchivo();
        manejarArchivos.matriEstudiante();
        
    }
}
