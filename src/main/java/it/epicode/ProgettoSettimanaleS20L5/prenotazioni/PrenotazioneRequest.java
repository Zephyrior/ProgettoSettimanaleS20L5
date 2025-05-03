package it.epicode.ProgettoSettimanaleS20L5.prenotazioni;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrenotazioneRequest {


    private LocalDate dataPrenotazione = LocalDate.now();

    @Min(value = 1, message = "Il numero di posti prenotati deve essere almeno 1")
    private int postiPrenotati;

    @NotNull(message = "L'ID dell'evento è obbligatorio")
    private Long evento_Id;
}
