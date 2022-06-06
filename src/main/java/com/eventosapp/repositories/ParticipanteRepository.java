package com.eventosapp.repositories;

import com.eventosapp.models.Evento;
import com.eventosapp.models.Participante;
import org.springframework.data.repository.CrudRepository;

public interface ParticipanteRepository extends CrudRepository<Participante, String> {
    Iterable<Participante> findByEvento(Evento evento);
    Participante findByMatricula(String matricula);
}
