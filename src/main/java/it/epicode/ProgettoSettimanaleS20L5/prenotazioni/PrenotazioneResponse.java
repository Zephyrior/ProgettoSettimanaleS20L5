package it.epicode.ProgettoSettimanaleS20L5.prenotazioni;

import it.epicode.ProgettoSettimanaleS20L5.auth.AppUserResponse;
import it.epicode.ProgettoSettimanaleS20L5.eventi.EventoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrenotazioneResponse {

    private Long id;
    private LocalDate dataPrenotazione;
    private int postiPrenotati;
    private Long evento_Id;
    private Long user_Id;
}
