package com.pedroperdona.eventosapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pedroperdona.eventosapp.models.Convidado;
import com.pedroperdona.eventosapp.models.Evento;
import com.pedroperdona.eventosapp.repository.ConvidadoRepository;
import com.pedroperdona.eventosapp.repository.EventoRepository;

@Controller
public class EventoController {

	@Autowired
	private EventoRepository eventoRepository;
	
	@Autowired
	private ConvidadoRepository convidadoRepository;

	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
	public String formulario() {
		return "evento/formularioEvento";
	}

	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST)
	public String formulario(Evento evento) {
		eventoRepository.save(evento);
		return "redirect:/cadastrarEvento";
	}

	@RequestMapping("/eventos")
	public ModelAndView listEventos() {
		
		Iterable<Evento> eventos = eventoRepository.findAll();
		
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("eventos", eventos);
		return modelAndView;
	}

	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ModelAndView listDetalhesEvento(@PathVariable("codigo") Integer codigo) {
		
		Evento evento = eventoRepository.findByCodigo(codigo);
		
		ModelAndView modelAndView = new ModelAndView("evento/detalhesEvento");
		modelAndView.addObject("evento", evento);
		
		Iterable<Convidado> convidados = convidadoRepository.findByEvento(evento);
		modelAndView.addObject("convidados", convidados);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
	public String saveDetalhesEvento(@PathVariable("codigo") Integer codigo, Convidado convidado) {
		
		Evento evento = eventoRepository.findByCodigo(codigo);
		convidado.setEvento(evento);
		
		convidadoRepository.save(convidado);
		
		return "redirect:/{codigo}";
	}

}
