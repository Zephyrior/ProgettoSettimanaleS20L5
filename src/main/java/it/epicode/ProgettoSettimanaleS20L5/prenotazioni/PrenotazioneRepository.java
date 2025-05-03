package it.epicode.ProgettoSettimanaleS20L5.prenotazioni;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

    @Query("SELECT p FROM Prenotazione p WHERE p.user.username = :username")
    List<Prenotazione> findByUsername(@Param("username") String username);
}