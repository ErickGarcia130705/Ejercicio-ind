import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class ListaEspera {
    private Queue<Solicitud> solicitud = new LinkedList<>();

    static class Solicitud {
        String responsable;
        String tipoEvento;
        int participantes;
        LocalDate fecha;
        LocalTime horaInicio;
        LocalTime horaFin;
        Solicitud(String responsable, String tipoEvento, int participantes,
                  LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
            this.responsable = responsable;
            this.tipoEvento = tipoEvento;
            this.participantes = participantes;
            this.fecha = fecha;
            this.horaInicio = horaInicio;
            this.horaFin = horaFin;
        }
    }

    public Boolean agregarSolicitud(String responsable, String tipoEvento, int participantes,
                                    LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        return solicitud.add(new Solicitud(responsable, tipoEvento, participantes, fecha, horaInicio, horaFin));
    }

    public Optional<Solicitud> obtenerCancha(String tipoEvento) {
        for (Solicitud s : solicitud) {
            if (s.tipoEvento.equalsIgnoreCase(tipoEvento)) return Optional.of(s);
        }
        return Optional.empty();
    }

    public boolean eliminarSolicitud(Solicitud s) { return solicitud.remove(s); }

    public Boolean estaVacia() { return solicitud.isEmpty(); }

    public int Espera() { return solicitud.size(); }

    Optional<Solicitud> siguienteCompatible(String tipoEvento, int participantes,
                                            LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        for (Solicitud s : solicitud) {
            boolean mismoTipo = s.tipoEvento.equalsIgnoreCase(tipoEvento);
            boolean mismaFecha = s.fecha.equals(fecha);
            boolean franjaCompatible = (horaInicio.isBefore(s.horaFin) && s.horaInicio.isBefore(horaFin));
            boolean pax = participantes <= s.participantes || participantes == s.participantes;
            if (mismoTipo && mismaFecha && franjaCompatible && pax) return Optional.of(s);
        }
        return Optional.empty();
    }
}
