/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inscribir_materias;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author VALENTINA
 */
public class Lista_Materias extends javax.swing.JFrame {
    private List<Materia> materiasInscritas = new ArrayList<>();
    private int creditosMaximos = 18;  // Ejemplo de límite de créditos
    private String rutaArchivo = "datos_materias.txt";
    private String rutaMatricula = "matricula.txt"; // Ruta del archivo de matrícula

    /**
     * Creates new form Lista_Materias
     */
    public Lista_Materias() {
        initComponents();
        mostrarMaterias();
        setLocationRelativeTo(null);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Label_ListaMaterias = new javax.swing.JLabel();
        Scroll_listadoMa = new javax.swing.JScrollPane();
        textAreaMaterias = new javax.swing.JTextArea();
        LabelIndicaciones = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        TextID = new javax.swing.JTextField();
        ButonAñadir = new javax.swing.JButton();
        ImagenVentana1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Label_ListaMaterias.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Label_ListaMaterias.setForeground(new java.awt.Color(255, 255, 255));
        Label_ListaMaterias.setText("LISTA DE MATERAS");
        getContentPane().add(Label_ListaMaterias, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, -1, -1));

        textAreaMaterias.setColumns(20);
        textAreaMaterias.setRows(5);
        Scroll_listadoMa.setViewportView(textAreaMaterias);

        getContentPane().add(Scroll_listadoMa, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 330, 140));

        LabelIndicaciones.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        LabelIndicaciones.setText("Escriba el ID de la materia que desea incribir");
        getContentPane().add(LabelIndicaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 222, 157));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 310, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 310, 20));

        TextID.setBackground(new java.awt.Color(255, 222, 157));
        getContentPane().add(TextID, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, 70, -1));

        ButonAñadir.setBackground(new java.awt.Color(153, 255, 102));
        ButonAñadir.setText("Añadir");
        ButonAñadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButonAñadirActionPerformed(evt);
            }
        });
        getContentPane().add(ButonAñadir, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 250, -1, -1));

        ImagenVentana1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Imagen_Ventana1.jpg"))); // NOI18N
        ImagenVentana1.setText("LISTA MATERIAS");
        ImagenVentana1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        getContentPane().add(ImagenVentana1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-6, -6, 410, 310));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButonAñadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButonAñadirActionPerformed
        try {
            // Obtén el texto del campo de texto
            String text = TextID.getText();
    
            // Convierte el texto a un número entero
            int idMateria = Integer.parseInt(text);
    
            // Procesa la materia
            procesarMateria(idMateria);
        } catch (NumberFormatException e) {
            // Maneja la excepción si el texto no es un número válido
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido", "Error de entrada", JOptionPane.ERROR_MESSAGE);
    
        }
        
    }//GEN-LAST:event_ButonAñadirActionPerformed

    /**
     * @param args the command line arguments
     */
    
    private void mostrarMaterias() {
        String rutaArchivo = "datos_materias.txt";
        mostrarArchivo(rutaArchivo);
    }

    public void mostrarArchivo(String rutaArchivo) {
       try (BufferedReader bufferedReader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                textAreaMaterias.append(linea + "\n");
            }
        } catch (IOException e) {
            textAreaMaterias.append("Error al leer el archivo: " + e.getMessage() + "\n");
        }
    }
    private void procesarMateria(int idMateria) {
       Materia materia = obtenerMateriaPorID(idMateria);

        if (materia != null) {
            // Verificar si la materia ya fue inscrita anteriormente
            if (materiasInscritas.contains(materia)) {
                JOptionPane.showMessageDialog(this, "Ya está inscrito en esta materia.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Salir del método
            }

            // Verificar conflicto de horario
            boolean conflicto = false;
            for (Materia inscrita : materiasInscritas) {
                if (materia.conflictoHorario(inscrita)) {
                    conflicto = true;
                    break;
                }
            }

            if (conflicto) {
                JOptionPane.showMessageDialog(this, "Conflicto de horario con otra materia inscrita.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Salir del método
            }

            // Verificar si hay créditos suficientes y cupos disponibles
            if (creditosMaximos >= materia.getCreditos() && materia.getCupoMaximo() > 0) {
                creditosMaximos -= materia.getCreditos();
                textAreaMaterias.append("- Materia: " + materia.getNombre() + "\n");
                textAreaMaterias.append("  Horario: " + materia.getHorario() + "\n");
                textAreaMaterias.append("  Créditos: " + materia.getCreditos() + "\n");
                agregarMateriaAHorario(materia);
                materiasInscritas.add(materia); // Agregar la materia a la lista de inscritas

                JOptionPane.showMessageDialog(this, "Materia matriculada correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);

                // Guardar la información en el archivo de matrícula
                guardarEnArchivoMatricula(materia);
            } else {
                JOptionPane.showMessageDialog(this, "No se puede matricular la materia. Créditos insuficientes o no hay cupos disponibles.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "ID de materia no válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void guardarEnArchivoMatricula(Materia materia) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(rutaMatricula, true))) {
            bufferedWriter.write("Materia: " + materia.getNombre() + "\n");
            bufferedWriter.write("Horario: " + materia.getHorario() + "\n");
            bufferedWriter.write("Créditos: " + materia.getCreditos() + "\n");
            bufferedWriter.write("\n");
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de matrícula: " + e.getMessage());
        }
    }
    
    
    public Materia obtenerMateriaPorID(int idMateria) {
      try (BufferedReader bufferedReader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            String nombre = null;
            int creditos = 0;
            int cupos = 0;
            String horario = null;
            boolean encontrado = false;

            while ((linea = bufferedReader.readLine()) != null) {
                linea = linea.trim();
                if (linea.startsWith("ID: " + idMateria)) {
                    encontrado = true;
                } else if (linea.startsWith("ID: ") && encontrado) {
                    break;
                }

                if (encontrado) {
                    if (linea.startsWith("Materia: ")) {
                        nombre = linea.substring(9).trim();
                    } else if (linea.startsWith("Créditos: ")) {
                        creditos = Integer.parseInt(linea.substring(10).trim());
                    } else if (linea.startsWith("Cupos: ")) {
                        cupos = Integer.parseInt(linea.substring(7).trim());
                    } else if (linea.startsWith("Horario: ")) {
                        horario = linea.substring(9).trim();
                    }

                    if (nombre != null && horario != null && creditos > 0 && cupos > 0) {
                        return new Materia(nombre, creditos, cupos, horario);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al leer el archivo o formato incorrecto: " + e.getMessage());
        }
        return null;
    }
    
    public void agregarMateriaAHorario(Materia materia) {
        String rutaHorario = "horario.txt";
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(rutaHorario, true))) {
            int cupoDisponible = materia.decrementarCupo();
            if (cupoDisponible >= 0) {
                bufferedWriter.write("Nombre: " + materia.getNombre() + "\n");
                bufferedWriter.write("Horario: " + materia.getHorario() + "\n");
                bufferedWriter.write("Créditos: " + materia.getCreditos() + "\n");
                bufferedWriter.write("Cupo Disponible: " + cupoDisponible + "\n");
                bufferedWriter.write("\n");
                System.out.println("Materia matriculada correctamente.");
            } else {
                System.out.println("No hay cupo disponible para esta materia.");
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de horario: " + e.getMessage());
        }
    }
    
    public static void main(String args[]) {
        /*Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Lista_Materias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Lista_Materias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Lista_Materias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Lista_Materias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Lista_Materias().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButonAñadir;
    private javax.swing.JLabel ImagenVentana1;
    private javax.swing.JLabel LabelIndicaciones;
    private javax.swing.JLabel Label_ListaMaterias;
    private javax.swing.JScrollPane Scroll_listadoMa;
    private javax.swing.JTextField TextID;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextArea textAreaMaterias;
    // End of variables declaration//GEN-END:variables
}
