package it.epicode.ProgettoSettimanaleS20L5.eventi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class EventoRunner implements CommandLineRunner {


    @Autowired
    private EventoRepository eventoRepository;

    @Override
    public void run(String... args) throws Exception {

        Evento evento1 = new Evento();
        evento1.setTitolo("Evento 1");
        evento1.setDescrizione("Descrizione evento 1");
        evento1.setLuogo("Luogo evento 1");
        evento1.setData(LocalDate.now().plusDays(30));
        evento1.setPostiDisponibili(10);

        Evento evento2 = new Evento();
        evento2.setTitolo("Evento 2");
        evento2.setDescrizione("Descrizione evento 2");
        evento2.setLuogo("Luogo evento 2");
        evento2.setData(LocalDate.now().plusDays(7));
        evento2.setPostiDisponibili(20);

        Evento evento3 = new Evento();
        evento3.setTitolo("Evento 3");
        evento3.setDescrizione("Descrizione evento 3");
        evento3.setLuogo("Luogo evento 3");
        evento3.setData(LocalDate.now().minusDays(15));
        evento3.setPostiDisponibili(50);

        eventoRepository.saveAll(List.of(evento1, evento2, evento3));
    }
}
