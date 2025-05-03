package it.epicode.ProgettoSettimanaleS20L5.prenotazioni;

import it.epicode.ProgettoSettimanaleS20L5.auth.AppUser;
import it.epicode.ProgettoSettimanaleS20L5.eventi.Evento;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Prenotazione")

public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataPrenotazione = LocalDate.now();

    @Column(nullable = false)
    private int postiPrenotati;

    @ManyToOne
    private Evento evento;

    @ManyToOne
    private AppUser user;
}