package com.nicolyott.cineTrail.service;

import com.nicolyott.cineTrail.dto.FilmeDTO;
import com.nicolyott.cineTrail.entity.Favorito;
import com.nicolyott.cineTrail.entity.Filme;
import com.nicolyott.cineTrail.repository.FavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FavoritoService {

    @Autowired
    private FavoritoRepository repository;
    @Autowired
    private FilmeService service;

    private final String TMDB_API_KEY = "?api_key=36fec2882042d854ec07ae4fd510a19c";
    private final String TMDB_BASE_URL = "https://api.themoviedb.org/3/";
    RestTemplate restTemplate = new RestTemplate();

    public List<Favorito> obterFavoritos(){
        return null;
    }

    public void adicionarAosFavoritos(Long id){

    }

}
