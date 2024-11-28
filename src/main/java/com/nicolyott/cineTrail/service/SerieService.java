package com.nicolyott.cineTrail.service;

import com.nicolyott.cineTrail.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SerieService {

    @Autowired
    private SerieRepository repository;



}
