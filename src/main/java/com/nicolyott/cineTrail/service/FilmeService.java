package com.nicolyott.cineTrail.service;


import com.nicolyott.cineTrail.dto.FilmeDTO;
import com.nicolyott.cineTrail.entity.HistoricoPesquisa;
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
    private HistoricoPesquisaRepository pesquisaRepository;

    private final String TMDB_API_KEY = "?api_key=36fec2882042d854ec07ae4fd510a19c";
    private final String TMDB_BASE_URL = "https://api.themoviedb.org/3/";
    RestTemplate restTemplate = new RestTemplate();

    public List<FilmeDTO> pesquisaFilmeNome(String filme) {
        String url = TMDB_BASE_URL + "search/movie" + TMDB_API_KEY + "&query=" + filme;
        List<FilmeDTO> filmeDTOList = obterLista(url);

        HistoricoPesquisa historicoPesquisa = new HistoricoPesquisa(filme);
        pesquisaRepository.save(historicoPesquisa);

        return filmeDTOList;
    }

    public List<FilmeDTO> obterFilmesEmAlta() {
        String url = TMDB_BASE_URL + "movie/popular" + TMDB_API_KEY;
        List<FilmeDTO> filmeDTOList = obterLista(url);

        return filmeDTOList;
    }

    public FilmeDTO pesquisarFilmeId(Long id){
        String url = TMDB_BASE_URL + "movie/" + id + TMDB_API_KEY;

        ResponseEntity<FilmeDTO> resp = restTemplate.getForEntity(url, FilmeDTO.class);

        return resp.getBody();
    }

    public List<FilmeDTO> obterLista(String url) {
        ResponseEntity<Map> resp = restTemplate
                        .getForEntity((url), Map.class);
        List<Map<String, Object>> results = (List<Map<String, Object>>) resp.getBody().get("results");

        List<FilmeDTO> filmeDTOList = results.stream().map(result -> new FilmeDTO(
                (String) result.get("original_title"),
                (String) result.get("overview"),
                String.valueOf(result.get("popularity")),
                (String) result.get("poster_path"),
                (String) result.get("release_date"),
                (Integer) result.get("id")
        )).collect(Collectors.toList());

        return filmeDTOList;
    }

}
