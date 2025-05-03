package it.epicode.ProgettoSettimanaleS20L5.prenotazioni;

import it.epicode.ProgettoSettimanaleS20L5.common.CommonResponse;
import it.epicode.ProgettoSettimanaleS20L5.eventi.EventoResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @PreAuthorize("hasRole('ORGANIZER')")
    @GetMapping
    public List<PrenotazioneResponse> getAllPrenotazioni() {
        return prenotazioneService.getAllPrenotazioni();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public PrenotazioneResponse getPrenotazioneById(@PathVariable Long id) {
        return prenotazioneService.getPrenotazioneById(id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("eventi/user")
    public List<EventoResponse> getEventiByUser() {
        return prenotazioneService.getEventiByUser();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse createPrenotazione(@RequestBody @Valid PrenotazioneRequest request) {
        return prenotazioneService.createPrenotazione(request);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePrenotazione(@PathVariable Long id) {
        prenotazioneService.deletePrenotazione(id);
    }

//    @PreAuthorize("isAuthenticated()")
//    @PutMapping("/{id}")
//    public void updatePrenotazione(@PathVariable Long id, @RequestBody @Valid PrenotazioneRequest request) {
//        prenotazioneService.updatePrenotazione(id, request);
//    }
}
