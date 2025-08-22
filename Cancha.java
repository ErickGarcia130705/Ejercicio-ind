import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cancha {
    private int numero;
    private String tipo;
    private int capacidad;
    private double tiempoCosto;
    private List<Reservacion> agenda;

    public Cancha(int numero, String tipo, int capacidad, double tiempoCosto) {
        this.numero = numero;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.tiempoCosto = tiempoCosto;
        this.agenda = new ArrayList<>();
    }

    public boolean Disponible(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        for (Reservacion r : agenda) {
            if (r.getFecha().equals(fecha)) {
                boolean traslape = horaInicio.isBefore(r.getHoraFin()) && r.getHoraInicio().isBefore(horaFin);
                if (traslape) return false;
            }
        }
        return true;
    }

    public boolean Compatible(String tipoEvento, int numeroParticipantes) {
        return this.tipo.equalsIgnoreCase(tipoEvento) && numeroParticipantes <= this.capacidad;
    }

    public boolean eliminarReserva(String id) {
        Iterator<Reservacion> it = agenda.iterator();
        while (it.hasNext()) {
            Reservacion r = it.next();
            if (r.getId().equals(id)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public void agregarReservacion(Reservacion reservacion) {
        agenda.add(reservacion);
    }

    public List<Reservacion> obtenerReservasPorFecha(LocalDate fecha) {
        List<Reservacion> res = new ArrayList<>();
        for (Reservacion r : agenda) if (r.getFecha().equals(fecha)) res.add(r);
        return res;
    }

    public int getNumero() { return numero; }
    public String getTipo() { return tipo; }
    public int getCapacidad() { return capacidad; }
    public double getTiempoCosto() { return tiempoCosto; }
    public List<Reservacion> getAgenda() { return agenda; }
}
