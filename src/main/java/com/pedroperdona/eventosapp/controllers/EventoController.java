package com.pedroperdona.eventosapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pedroperdona.eventosapp.models.Evento;
import com.pedroperdona.eventosapp.repository.EventoRepository;

@Controller
public class EventoController {

	@Autowired
	private EventoRepository repository;

	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
	public String formulario() {
		return "evento/formularioEvento";
	}
	
	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST)
	public String formulario(Evento evento) {
		
		repository.save(evento);
		
		return "redirect:/cadastrarEvento";
	}

}
