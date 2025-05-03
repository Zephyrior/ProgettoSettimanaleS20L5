package it.epicode.ProgettoSettimanaleS20L5.eventi;

import it.epicode.ProgettoSettimanaleS20L5.prenotazioni.Prenotazione;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "eventi")

public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 50)
    private String titolo;

    @Column(nullable = false, length = 100)
    private String descrizione;

    @Column(nullable = false, length = 100)
    private String luogo;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private int postiDisponibili;

    @OneToMany(mappedBy = "evento")
    private List<Prenotazione> prenotazioni = new ArrayList<>();
}
