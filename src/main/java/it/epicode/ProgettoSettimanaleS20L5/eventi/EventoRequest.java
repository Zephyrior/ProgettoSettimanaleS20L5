package it.epicode.ProgettoSettimanaleS20L5.eventi;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoRequest {

    @NotBlank(message = "Il titolo è obbligatorio")
    private String titolo;

    @NotBlank(message = "La descrizione è obbligatoria")
    private String descrizione;

    @NotBlank(message = "Il luogo è obbligatorio")
    private String luogo;

    @NotNull(message = "La data dell'evento è obbligatoria")
    private LocalDate data;

    @NotNull(message = "Il numero di posti disponibili è obbligatorio")
    @Min(value = 1, message = "Il numero di posti disponibili deve essere almeno 1")
    private int postiDisponibili;
}
