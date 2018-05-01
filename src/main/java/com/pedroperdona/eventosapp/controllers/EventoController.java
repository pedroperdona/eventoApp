package com.pedroperdona.eventosapp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String formulario(@Valid Evento evento, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos obrigatórios");
			return "redirect:/cadastrarEvento";
		}

		eventoRepository.save(evento);
		attributes.addFlashAttribute("mensagem", "Evento cadastrado com sucesso.");

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
	public String saveDetalhesEvento(@PathVariable("codigo") Integer codigoEvento, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/{codigo}";
		}

		Evento evento = eventoRepository.findByCodigo(codigoEvento);
		convidado.setEvento(evento);

		convidadoRepository.save(convidado);
		attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso");

		return "redirect:/{codigo}";
	}

	@RequestMapping("/deletarEvento")
	public String deleteEvento(Integer codigo) {
		
		Evento evento = eventoRepository.findByCodigo(codigo);
		eventoRepository.delete(evento);
		
		return "redirect:/eventos";
	}
	
	@RequestMapping("/deletarConvidado")
	public String deleteConvidado(Integer codigo) {
		
		Convidado convidado = convidadoRepository.findByCodigo(codigo);
		convidadoRepository.delete(convidado);
		return "redirect:/" + convidado.getEvento().getCodigo().toString();
	}

}
