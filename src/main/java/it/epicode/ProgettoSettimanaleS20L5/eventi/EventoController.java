package it.epicode.ProgettoSettimanaleS20L5.eventi;

import it.epicode.ProgettoSettimanaleS20L5.common.CommonResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventi")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<EventoResponse> getAllEventi() {
        return eventoService.getAllEventi();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public EventoResponse getEventoById(@PathVariable Long id) {
        return eventoService.getEventoById(id);
    }

    @PreAuthorize("hasRole('ORGANIZER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse saveEvento( @RequestBody @Valid EventoRequest request) {
        return eventoService.saveEvento(request);
    }

    @PreAuthorize("hasRole('ORGANIZER')")
    @DeleteMapping ("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvento(@PathVariable Long id) {
        eventoService.deleteEvento(id);
    }

}
