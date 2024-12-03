package com.nicolyott.cineTrail.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicolyott.cineTrail.dto.FilmeDTO;
import com.nicolyott.cineTrail.exception.FilmeNotFoundException;
import com.nicolyott.cineTrail.exception.IdInvalidoException;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FilmeService {

    @Autowired
    private HistoricoPesquisaService historicoService;

    private final String TMDB_API_KEY = "?api_key=36fec2882042d854ec07ae4fd510a19c";
    private final String TMDB_BASE_URL = "https://api.themoviedb.org/3/";

    ConsumoApi consumoApi = new ConsumoApi();
    ConverteDados converteDados = new ConverteDados();

    //validar quando a requisição n é colocada nenhum valor e da erro

    public List<FilmeDTO> buscarFilmeNome(String filme) {
        //validar quando é colocado um nome não existente e a api retorna uma lista vazia no lugar do erro.
        String json = consumoApi.obterDados(TMDB_BASE_URL + "search/movie" + TMDB_API_KEY + "&query=" + filme.replaceAll(" ", "+"));
        List<FilmeDTO> filmeDTOList = obterLista(json);
        historicoService.adicionarHistorico(filme);

        return filmeDTOList;
    }

    public List<FilmeDTO> obterFilmesEmAlta() {
        String json = consumoApi.obterDados(TMDB_BASE_URL + "movie/popular" + TMDB_API_KEY);
        List<FilmeDTO> filmeDTOList = obterLista(json);

        return filmeDTOList;
    }

    public FilmeDTO buscarFilmeId(Integer idTmdb){
        if(!verificarId(idTmdb)){
            throw new IdInvalidoException("id invalido");
        }

        String json = consumoApi.obterDados(TMDB_BASE_URL + "movie/" + idTmdb + TMDB_API_KEY);
        FilmeDTO filmeDTO = converteDados.converteDados(json, FilmeDTO.class);

        return filmeDTO;
    }

    public boolean verificarId(Integer idTmdb) {
        String url = TMDB_BASE_URL + "movie/" + idTmdb + TMDB_API_KEY;
        int responseCode = consumoApi.verificarRequisição(url, "HEAD");

        return responseCode >= 200 && responseCode < 300;
    }

    public List<FilmeDTO> obterLista(String json) {
        Map<String, Object> resp = converteDados.converteDados(json, Map.class);
        List<Map<String, Object>> results = (List<Map<String, Object>>) resp.get("results");

        List<FilmeDTO> filmeDTOList = results.stream()
                .map(result -> {
                    try {
                        return converteDados
                                .converteDados(new ObjectMapper().writeValueAsString(result), FilmeDTO.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        return filmeDTOList;
    }

}
