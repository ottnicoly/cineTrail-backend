package com.nicolyott.cineTrail.controller;

import com.nicolyott.cineTrail.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SerieController {

    @Autowired
    private SerieService service;


}
