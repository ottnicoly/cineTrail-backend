package com.nicolyott.cineTrail.service;

import com.nicolyott.cineTrail.dto.FilmeDTO;
import com.nicolyott.cineTrail.entity.Favorito;
import com.nicolyott.cineTrail.entity.Filme;
import com.nicolyott.cineTrail.repository.FavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FavoritoService {

    @Autowired
    private FavoritoRepository repository;
    @Autowired
    private FilmeService service;

    public List<Favorito> obterFavoritos(){
        return repository.findAll();
    }

    public void adicionarAosFavoritos(Long filmeId){
       FilmeDTO filmeDTO = service.pesquisarFilmeId(filmeId);
       Favorito favorito = new Favorito();
       favorito.setTitulo(filmeDTO.titulo());
       favorito.setIdTmdb(filmeDTO.idTmdb());
       favorito.setDataFavorito(LocalDateTime.now().toString());

       repository.save(favorito);
    }

}
