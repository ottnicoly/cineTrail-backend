package com.nicolyott.cineTrail.controller;

import com.nicolyott.cineTrail.service.HistoricoPesquisaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HistoricoPesquisaController {

    @Autowired
    private HistoricoPesquisaService service;

}
