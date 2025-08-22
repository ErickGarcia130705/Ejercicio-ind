import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Admin {
    private List<Cancha> canchas;
    private ListaEspera listaEspera;

    public Admin(List<Cancha> canchas, ListaEspera listaEspera) {
        this.canchas = canchas;
        this.listaEspera = listaEspera;
    }

    public Reservacion registrarReservacion(String responsable, String tipoEvento, int participantes,
                                            LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, double deposito) {
        Optional<Cancha> candidata = buscarCanchaDisponible(tipoEvento, participantes, fecha, horaInicio, horaFin);
        if (candidata.isPresent()) {
            Cancha c = candidata.get();
            Reservacion r = new Reservacion(
                    UUID.randomUUID().toString(),
                    responsable, tipoEvento, participantes, fecha, horaInicio, horaFin, deposito, c
            );
            c.agregarReservacion(r);
            return r;
        } else {
            listaEspera.agregarSolicitud(responsable, tipoEvento, participantes, fecha, horaInicio, horaFin);
            return null;
        }
    }

    public Boolean cancelarReservacion(String id) {
        for (Cancha c : canchas) {
            if (c.eliminarReserva(id)) {
                Optional<ListaEspera.Solicitud> sOpt = listaEspera.obtenerCancha(c.getTipo());
                if (sOpt.isPresent()) {
                    ListaEspera.Solicitud s = sOpt.get();
                    if (c.Compatible(s.tipoEvento, s.participantes) && c.Disponible(s.fecha, s.horaInicio, s.horaFin)) {
                        Reservacion r = new Reservacion(
                                UUID.randomUUID().toString(),
                                s.responsable, s.tipoEvento, s.participantes,
                                s.fecha, s.horaInicio, s.horaFin, 0.0, c
                        );
                        c.agregarReservacion(r);
                        listaEspera.eliminarSolicitud(s);
                    }
                }
                return true;
            }
        }
        return false;
    }

    private Optional<Cancha> buscarCanchaDisponible(String tipoEvento, int participantes,
                                                    LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        for (Cancha c : canchas) {
            if (c.Compatible(tipoEvento, participantes) && c.Disponible(fecha, horaInicio, horaFin)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    public List<Reservacion> verReservasPorCancha(int numeroCancha) {
        for (Cancha c : canchas) {
            if (c.getNumero() == numeroCancha) {
                return new ArrayList<>(c.getAgenda());
            }
        }
        return new ArrayList<>();
    }

    public void agregarReservacion(Reservacion reservacion) {
        reservacion.getCancha().agregarReservacion(reservacion);
    }
}
