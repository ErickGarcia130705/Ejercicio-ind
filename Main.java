import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Cancha> canchas = new ArrayList<>();
        canchas.add(new Cancha(1, "Fútbol", 22, 120));
        canchas.add(new Cancha(2, "Baloncesto", 10, 90));
        canchas.add(new Cancha(3, "Tenis", 4, 70));
        canchas.add(new Cancha(4, "Fútbol", 22, 130));

        ListaEspera listaEspera = new ListaEspera();
        Admin admin = new Admin(canchas, listaEspera);

        while (true) {
            System.out.println("\n= MENU ");
            System.out.println("1) Registrar reservación");
            System.out.println("2) Mostrar reservas por cancha");
            System.out.println("3) Cancelar reservación");
            System.out.println("4) Ver lista de espera");
            System.out.println("5) Salir");
            System.out.print("Elige opcion: ");
            int op = -1;
            try { op = Integer.parseInt(sc.nextLine()); } catch (Exception ignored) {}

            if (op == 1) {
                System.out.print("Responsable: ");
                String responsable = sc.nextLine();
                System.out.print("Tipo de evento (Fútbol/Baloncesto/Tenis): ");
                String tipo = sc.nextLine();
                System.out.print("Participantes: ");
                int participantes = Integer.parseInt(sc.nextLine());
                System.out.print("Fecha: ");
                LocalDate fecha = LocalDate.parse(sc.nextLine());
                System.out.print("Hora inicio: ");
                LocalTime horaInicio = LocalTime.parse(sc.nextLine());
                System.out.print("Hora fin: ");
                LocalTime horaFin = LocalTime.parse(sc.nextLine());
                System.out.print("Depósito: ");
                double deposito = Double.parseDouble(sc.nextLine());

                Reservacion r = admin.registrarReservacion(responsable, tipo, participantes, fecha, horaInicio, horaFin, deposito);
                if (r != null) {
                    System.out.println("Reservación creada. ID: " + r.getId() +
                            " | Cancha #" + r.getCancha().getNumero() +
                            " | Costo estimado: " + r.calcularCosto());
                } else {
                    System.out.println("No hay cancha disponible. Solicitud enviada a lista de espera.");
                }

            } else if (op == 2) {
                System.out.print("Número de cancha: ");
                int nc = Integer.parseInt(sc.nextLine());
                var lista = admin.verReservasPorCancha(nc);
                if (lista.isEmpty()) {
                    System.out.println("No hay reservas para la cancha #" + nc);
                } else {
                    for (Reservacion r : lista) {
                        System.out.println("ID: " + r.getId() + " | Fecha: " + r.getFecha()
                                + " " + r.getHoraInicio() + "-" + r.getHoraFin()
                                + " | Resp: " + r.getResponsable()
                                + " | Evento: " + r.getEvento());
                    }
                }

            } else if (op == 3) {
                System.out.print("ID de reservación a cancelar: ");
                String id = sc.nextLine();
                boolean ok = admin.cancelarReservacion(id);
                System.out.println(ok ? "Cancelada (se intentó reasignación desde lista de espera)."
                                      : "No se encontró la reservación.");

            } else if (op == 4) {
                System.out.println("Solicitudes en espera: " + listaEspera.Espera());

            } else if (op == 5) {
                System.out.println("Saliendo...");
                break;

            } else {
                System.out.println("Opción inválida.");
            }
        }

        sc.close();
    }
}
