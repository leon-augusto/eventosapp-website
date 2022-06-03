package com.eventosapp.controllers;

import com.eventosapp.models.Evento;
import com.eventosapp.models.Participante;
import com.eventosapp.repositories.EventoRepository;
import com.eventosapp.repositories.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EventoController {

    @Autowired
    private EventoRepository er;

    @Autowired
    private ParticipanteRepository pr;

    @RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
    public String form(){
        return "evento/formEvento";
    }

    @RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST)
    public String form(Evento evento){

        er.save(evento);

        return "redirect:/cadastrarEvento";
    }

    @RequestMapping("/eventos")
    public ModelAndView listaEventos(){
        ModelAndView mv = new ModelAndView("index");
        Iterable<Evento> eventos = er.findAll();
        mv.addObject("eventos", eventos);
        return mv;
    }

    @RequestMapping(value="/{codigo}", method = RequestMethod.GET)
    public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo){
        ModelAndView mv = new ModelAndView("evento/detalhesEvento");
        Evento evento = er.findByCodigo(codigo);
        mv.addObject("evento", evento);
        Iterable<Participante> participantes = pr.findByEvento(evento);
        mv.addObject("participantes", participantes);
        return mv;
    }

    @RequestMapping(value="/{codigo}", method = RequestMethod.POST)
    public String detalhesEventoPost(@PathVariable("codigo") long codigo, Participante participante){
        Evento evento = er.findByCodigo(codigo);
        participante.setEvento(evento);
        pr.save(participante);
        return "redirect:/{codigo}";
    }
}
