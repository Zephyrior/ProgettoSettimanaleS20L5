package it.epicode.ProgettoSettimanaleS20L5.prenotazioni;

import com.github.javafaker.App;
import it.epicode.ProgettoSettimanaleS20L5.auth.AppUser;
import it.epicode.ProgettoSettimanaleS20L5.auth.AppUserRepository;
import it.epicode.ProgettoSettimanaleS20L5.common.CommonResponse;
import it.epicode.ProgettoSettimanaleS20L5.eventi.Evento;
import it.epicode.ProgettoSettimanaleS20L5.eventi.EventoRepository;
import it.epicode.ProgettoSettimanaleS20L5.eventi.EventoResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Validated
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private AppUserRepository appUserRepository;


        public List<PrenotazioneResponse> getAllPrenotazioni() {
            List<Prenotazione> prenotazioni = prenotazioneRepository.findAll();
            return prenotazioni.stream()
                    .map( m  ->
                            new PrenotazioneResponse(
                            m.getId(),
                            m.getDataPrenotazione(),
                            m.getPostiPrenotati(),
                            m.getEvento().getId(),
                            m.getUser().getId()))
                    .toList();
        }

        public PrenotazioneResponse getPrenotazioneById(Long id) {
            Prenotazione prenotazione = prenotazioneRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Prenotazione non trovata"));

            PrenotazioneResponse response = new PrenotazioneResponse();
                    response.setId(prenotazione.getId());
                    response.setDataPrenotazione(prenotazione.getDataPrenotazione());
                    response.setPostiPrenotati(prenotazione.getPostiPrenotati());
                    response.setEvento_Id(prenotazione.getEvento().getId());
                    response.setUser_Id(prenotazione.getUser().getId());

                    return response;
        }

        public CommonResponse createPrenotazione(PrenotazioneRequest request) {

            AppUser user = getUserByUsername();

            Evento evento = eventoRepository
                    .findById(request.getEvento_Id())
                    .orElseThrow(() -> new EntityNotFoundException("Evento con id " + request.getEvento_Id() + " non trovato"));

            if(!canReserve(request.getEvento_Id(), request.getPostiPrenotati(), request.getDataPrenotazione())) {
                throw new IllegalArgumentException("Non è possibile effettuare la prenotazione");
            } else {
                Prenotazione prenotazione = new Prenotazione();
                prenotazione.setDataPrenotazione(request.getDataPrenotazione());
                prenotazione.setPostiPrenotati(request.getPostiPrenotati());
                prenotazione.setEvento(evento);
                prenotazione.setUser(user);
                prenotazioneRepository.save(prenotazione);

                PrenotazioneResponse response = new PrenotazioneResponse();
                response.setId(prenotazione.getId());
                response.setDataPrenotazione(prenotazione.getDataPrenotazione());
                response.setPostiPrenotati(prenotazione.getPostiPrenotati());
                response.setEvento_Id(prenotazione.getEvento().getId());
                response.setUser_Id(prenotazione.getUser().getId());

                evento.setPostiDisponibili(evento.getPostiDisponibili() - request.getPostiPrenotati());
                eventoRepository.save(evento);

                return new CommonResponse(response.getId());
            }

        }

        public Boolean canReserve(Long id, int postiPrenotati, LocalDate dataPrenotazione) {
            Evento evento = eventoRepository
                    .findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Evento con id " + id + " non trovato"));

            if (postiPrenotati > evento.getPostiDisponibili() || dataPrenotazione.isAfter(evento.getData())) {
                return false;
            }
            return true;
        }

        public AppUser getUserByUsername() {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return appUserRepository.findByUsername(username)
                    .orElseThrow(() -> new EntityNotFoundException("Utente non trovato con username: " + username));
        }

        public void deletePrenotazione(Long id) {
            Prenotazione prenotazione = prenotazioneRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Prenotazione non trovata"));

            AppUser user = getUserByUsername();
            if (!prenotazione.getUser().equals(user)) {
                throw new IllegalArgumentException("Non sei autorizzato a cancellare questa prenotazione");
            }

            Evento evento = prenotazione.getEvento();
            evento.setPostiDisponibili(evento.getPostiDisponibili() + prenotazione.getPostiPrenotati());
            eventoRepository.save(evento);

            prenotazioneRepository.delete(prenotazione);
        }

//        public void updatePrenotazione(Long id, PrenotazioneRequest request) {
//            Prenotazione prenotazione = prenotazioneRepository.findById(id)
//                    .orElseThrow(() -> new EntityNotFoundException("Prenotazione non trovata"));
//
//            int postiPrenotati = prenotazione.getPostiPrenotati();
//
//            AppUser user = getUserByUsername();
//            if (!prenotazione.getUser().equals(user)) {
//                throw new IllegalArgumentException("Non sei autorizzato a modificare questa prenotazione");
//            }
//
//            Evento evento = eventoRepository.findById(request.getEvento_Id())
//                    .orElseThrow(() -> new EntityNotFoundException("Evento con id " + request.getEvento_Id() + " non trovato"));
//
//            if (!canReserve(request.getEvento_Id(), request.getPostiPrenotati(), request.getDataPrenotazione())) {
//                throw new IllegalArgumentException("Non è possibile effettuare la prenotazione");
//            }
//
//            prenotazione.setDataPrenotazione(request.getDataPrenotazione());
//            prenotazione.setPostiPrenotati(request.getPostiPrenotati());
//            prenotazione.setEvento(evento);
//            prenotazioneRepository.save(prenotazione);
//
//            evento.setPostiDisponibili(evento.getPostiDisponibili() - request.getPostiPrenotati() + postiPrenotati);
//            eventoRepository.save(evento);
//            System.out.println("Updating prenotazione with ID: " + id);
//            System.out.println("Fetched prenotazione ID: " + prenotazione.getId());
//        }

        public List<EventoResponse> getEventiByUser(){

            AppUser user = getUserByUsername();

            return prenotazioneRepository.findByUsername(user.getUsername()).stream()
                    .map(prenotazione -> {
                        EventoResponse response = new EventoResponse();
                        BeanUtils.copyProperties(prenotazione.getEvento(), response);
                        return response;
                    })
                    .toList();

        }

    }
