package com.nicolyott.cineTrail.Principal;

import com.nicolyott.cineTrail.model.MovieData;
import com.nicolyott.cineTrail.service.ConsumoApi;
import com.nicolyott.cineTrail.service.ConverteDados;

public class Principal {

    public void exibeMenu(){

        ConverteDados converte = new ConverteDados();
        ConsumoApi consome = new ConsumoApi();

        String json = consome.obterDados("https://api.themoviedb.org/3/movie/11?api_key=36fec2882042d854ec07ae4fd510a19c");
        System.out.println(converte.converteDados(json, MovieData.class));


    }

}
