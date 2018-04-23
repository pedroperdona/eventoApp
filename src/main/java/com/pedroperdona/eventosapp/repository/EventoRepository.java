package com.pedroperdona.eventosapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.pedroperdona.eventosapp.models.Evento;

public interface EventoRepository extends CrudRepository<Evento, String> {

	Evento findByCodigo(Integer codigo);

}
