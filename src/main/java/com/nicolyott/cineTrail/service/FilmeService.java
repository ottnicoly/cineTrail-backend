package com.nicolyott.cineTrail.service;


import com.nicolyott.cineTrail.dto.FilmeDTO;
import com.nicolyott.cineTrail.entity.Filme;
import com.nicolyott.cineTrail.entity.HistoricoPesquisa;
import com.nicolyott.cineTrail.repository.FilmeRepository;
import com.nicolyott.cineTrail.repository.HistoricoPesquisaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository Movierepository;
    @Autowired
    private HistoricoPesquisaRepository pesquisaRepository;

    private final String TMDB_API_KEY = "?api_key=36fec2882042d854ec07ae4fd510a19c";
    private final String TMDB_BASE_URL = "https://api.themoviedb.org/3/";
    RestTemplate restTemplate = new RestTemplate();

    public List<FilmeDTO> pesquisaFilme(String movie) {
        String url = TMDB_BASE_URL + "search/movie" + TMDB_API_KEY + "&query=" + movie;
        List<FilmeDTO> movieDTOList = obterLista(url);

        HistoricoPesquisa historicoPesquisa = new HistoricoPesquisa(movie);
        pesquisaRepository.save(historicoPesquisa);

        return movieDTOList;
    }

    public List<FilmeDTO> obterFilmesEmAlta() {
        String url = TMDB_BASE_URL + "movie/popular" + TMDB_API_KEY;
        List<FilmeDTO> movieDTOList = obterLista(url);

        return movieDTOList;
    }

    public FilmeDTO obterFilme(Long id){
        String url = TMDB_BASE_URL + "movie/" + id + TMDB_API_KEY;

        ResponseEntity<FilmeDTO> resp = restTemplate.getForEntity(url, FilmeDTO.class);

        return resp.getBody();
    }

    public List<FilmeDTO> obterLista(String url) {
        ResponseEntity<Map> resp = restTemplate
                        .getForEntity((url), Map.class);
        List<Map<String, Object>> results = (List<Map<String, Object>>) resp.getBody().get("results");

        List<FilmeDTO> movieDTOList = results.stream().map(result -> new FilmeDTO(
                (String) result.get("original_title"),
                (String) result.get("overview"),
                String.valueOf(result.get("popularity")),
                (String) result.get("poster_path"),
                (String) result.get("release_date")
        )).collect(Collectors.toList());

        return movieDTOList;
    }

}
