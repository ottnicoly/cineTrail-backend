package com.nicolyott.cineTrail.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {

    public String obterDados(String endereco) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String json = response.body();
        return json;
    }

    //url = http://www.omdbapi.com/?t=lost&apikey=349299d0


    //TMDB API
    //TOKEN = eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzNmZlYzI4ODIwNDJkODU0ZWMwN2FlNGZkNTEwYTE5YyIsIm5iZiI6MTczMjgxMDM5MS45ODkxOTQ0LCJzdWIiOiI2NzQ4OTVjZTJlZDM5YTNhZjE3MWU3MjgiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.hOyrT-GcTKdK2dGcUGye_ybbLePPc3PlJd7iWIk0dkw
    //CHAVE = 36fec2882042d854ec07ae4fd510a19c
    //URL = https://api.themoviedb.org/3/movie/11?api_key=

}
