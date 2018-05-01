package com.pedroperdona.eventosapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.pedroperdona.eventosapp.models.Convidado;
import com.pedroperdona.eventosapp.models.Evento;

public interface ConvidadoRepository extends CrudRepository<Convidado, String>{

	Iterable<Convidado> findByEvento(Evento evento);

	Convidado findByCodigo(Integer codigo);

}
