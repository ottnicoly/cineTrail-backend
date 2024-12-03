package com.nicolyott.cineTrail.service;

import com.nicolyott.cineTrail.entity.HistoricoPesquisa;
import com.nicolyott.cineTrail.repository.HistoricoPesquisaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoricoPesquisaService {

    @Autowired
    private HistoricoPesquisaRepository repository;

    public void adicionarHistorico(String pesquisa){
        HistoricoPesquisa historicoPesquisa = new HistoricoPesquisa(pesquisa);
        HistoricoPesquisa historico = repository.findByPesquisaEqualsIgnoreCase(pesquisa);
        try {
            if (historico.getPesquisa().equals(historicoPesquisa.getPesquisa())){
                repository.delete(historico);
            }
        } catch (NullPointerException e) {
            e.getMessage();
        }

        repository.save(historicoPesquisa);
    }

    public void apagarHistorico(String pesquisa){
        //historico invalido
        HistoricoPesquisa historico = repository.findByPesquisaEqualsIgnoreCase(pesquisa);
        repository.delete(historico);
    }

}
