package it.epicode.ProgettoSettimanaleS20L5.eventi;

import it.epicode.ProgettoSettimanaleS20L5.common.CommonResponse;
import jakarta.persistence.EntityNotFoundException;
import jdk.jfr.Event;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public List<EventoResponse> getAllEventi() {
        return eventoRepository.findAll()
                .stream()
                .map(e -> new EventoResponse(e.getId(), e.getTitolo(), e.getDescrizione(), e.getLuogo(), e.getData(), e.getPostiDisponibili()))
                .toList();
    }

    public EventoResponse getEventoById(Long id) {
        Evento evento = eventoRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento con id " + id + " non trovato"));

        EventoResponse response = new EventoResponse();
        BeanUtils.copyProperties(evento, response);

        return response;
    }

    public CommonResponse saveEvento(EventoRequest request) {
        Evento evento = new Evento();
        BeanUtils.copyProperties(request, evento);

        eventoRepository.save(evento);

        return new CommonResponse(evento.getId());
    }

    public void deleteEvento(Long id) {
        Evento evento = eventoRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento con id " + id + " non trovato"));

        eventoRepository.delete(evento);
    }

    public void updateEvento(Long id, EventoRequest request) {
        Evento evento = eventoRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento con id " + id + " non trovato"));

        BeanUtils.copyProperties(request, evento);

        eventoRepository.save(evento);
    }

}
