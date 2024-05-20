package inscribir_materias;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManejarArchivos {
    private final String rutaArchivo = "datos_materias.txt";
    private final String rutaMatricula = "matricula.txt";

    // Método para crear el archivo de materias
    public void crearArchivo() {
        String[] materias = {"Calculo-Integral-301", "Calculo-Integral-302", "Programacºion-II-301", "Programacion-II-302", "Fisica-II-301", "Fisica-II-302", "Lectura-M1", "Lectura-T1", "Ingles-M1", "Ingles-T1", "Ciudadania-M1", "Ciudadania-T1"};
        int[] id = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        int[] creditos = {4, 4, 3, 3, 4, 4, 2, 2, 2, 2, 2, 2};
        int[] cupos = {30, 30, 30, 30, 30, 30, 40, 40, 40, 40, 40, 40};
        String[] horarios = {"Martes:11:00-13:00", "Miércoles:11:00-13:00", "Lunes:07:00-10:00", "Martes:07:00-09:00", "Lunes:10:00-12:00", "Lunes:10:00-12:00", "Jueves:07:00-09:00", "Jueves:15:00-17:00", "Lunes:10:00-12:00", "Jueves:15:00-17:00", "Jueves:11:00-13:00", "Viernes:13:00-15:00"};

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (int i = 0; i < materias.length; i=i+1) {
                bufferedWriter.write("Materia: " + materias[i] + "\n");
                bufferedWriter.write("ID: " + id[i] + "\n");
                bufferedWriter.write("Créditos: " + creditos[i] + "\n");
                bufferedWriter.write("Cupos: " + cupos[i] + "\n");
                bufferedWriter.write("Horario: " + horarios[i] + "\n");
                bufferedWriter.write("\n"); // Separador entre cada entrada
            }
            System.out.println("Archivo creado satisfactoriamente.");
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    // Método para manejar la matrícula del estudiante
    public void matriEstudiante() {
        try (PrintWriter linea = new PrintWriter(new FileWriter(rutaMatricula, true));
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Digite código:");
            String codigo = br.readLine();
            System.out.println("Digite nombre:");
            String nombre = br.readLine();
            System.out.println("Digite créditos máximos:");
            int creditosMaximos = Integer.parseInt(br.readLine());

            linea.println("Código: " + codigo);
            linea.println("Nombre: " + nombre);
            linea.println("Créditos máximos: " + creditosMaximos);
            linea.println("Materias:");

            List<Materia> materiasInscritas = new ArrayList<>(); // Lista para almacenar las materias inscritas

            while (true) {
                System.out.println("¿Desea agregar una materia? (si/no):");
                String respuesta = br.readLine();

                if (!respuesta.equalsIgnoreCase("si")) {
                    break;
                }

                mostrarArchivo(rutaArchivo);
                System.out.println("Digite el ID de la materia:");
                int idMateria = Integer.parseInt(br.readLine());
                Materia materia = obtenerMateriaPorID(idMateria);

                if (materia != null) {
                    // Verificar si la materia ya fue inscrita anteriormente
                    if (materiasInscritas.contains(materia)) {
                        System.out.println("Ya está inscrito en esta materia.");
                        continue; // Continuar con el siguiente ciclo
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
                        System.out.println("Conflicto de horario con otra materia inscrita.");
                        continue; // Continuar con el siguiente ciclo
                    }

                    // Verificar si hay créditos suficientes y cupos disponibles
                    if (creditosMaximos >= materia.getCreditos() && materia.getCupoMaximo() > 0) {
                        creditosMaximos -= materia.getCreditos();
                        linea.println("- Materia: " + materia.getNombre());
                        linea.println("  Horario: " + materia.getHorario());
                        linea.println("  Créditos: " + materia.getCreditos());
                        agregarMateriaAHorario(materia);
                        materiasInscritas.add(materia); // Agregar la materia a la lista de inscritas

                        System.out.println("Materia matriculada correctamente.");
                        System.out.println("Créditos restantes: " + creditosMaximos);
                        System.out.println("Cupos restantes de " + materia.getNombre() + ": " + materia.getCupoMaximo());
                    } else {
                        System.out.println("No se puede matricular la materia. Créditos insuficientes o no hay cupos disponibles.");
                    }
                } else {
                    System.out.println("ID de materia no válido.");
                }
            }
            System.out.println("Matrícula realizada correctamente.");
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error creando o cerrando el archivo: " + e.getMessage());
        }
    }

    // Método para mostrar el archivo de materias
    public void mostrarArchivo(String rutaArchivo) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
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
            }

            if (encontrado && nombre != null && horario != null && creditos > 0 && cupos > 0) {
                return new Materia(nombre, creditos, cupos, horario);
            }
        }
    } catch (IOException | NumberFormatException e) {
        System.err.println("Error al leer el archivo o formato incorrecto: " + e.getMessage());
    }
    return null;
}


    // Método para agregar la materia al archivo de Horario
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
}