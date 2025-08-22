import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class Reservacion {
    private String id;
    private String responsable;
    private String evento;
    private int participante;
    private LocalDate fecha;
    private double deposito;
    private Cancha cancha;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public Reservacion(String id, String responsable, String evento, int participante,
                       LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, double deposito, Cancha cancha) {
        this.id = id;
        this.responsable = responsable;
        this.evento = evento;
        this.participante = participante;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.deposito = deposito;
        this.cancha = cancha;
    }

    public Duration calcularDuracioncan() {
        return Duration.between(horaInicio, horaFin);
    }

    public double calcularCosto() {
        double horas = calcularDuracioncan().toMinutes() / 60.0;
        return horas * cancha.getTiempoCosto();
    }

    public boolean verificarCapacidad() {
        return participante <= cancha.getCapacidad();
    }

    public boolean Traslapan(LocalTime otraInicio, LocalTime otraFin) {
        return horaInicio.isBefore(otraFin) && otraInicio.isBefore(horaFin);
    }

    public String getId() { return id; }
    public String getResponsable() { return responsable; }
    public String getEvento() { return evento; }
    public int getParticipante() { return participante; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public Cancha getCancha() { return cancha; }
}
