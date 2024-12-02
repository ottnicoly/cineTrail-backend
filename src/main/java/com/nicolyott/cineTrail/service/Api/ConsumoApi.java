package com.nicolyott.cineTrail.service.Api;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.X509Certificate;

public class ConsumoApi {

    private static void disableSSLCertificateChecking() {
        try {
            // Create a TrustManager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HostnameVerifier allHostsValid = (hostname, session) -> true;
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public String obterDados(String endereco) {
        disableSSLCertificateChecking(); // Desativa a verificação SSL
        StringBuilder resposta = new StringBuilder();

        try {
            // Configura a URL e a conexão
            URL url = new URL(endereco);
            HttpsURLConnection conexao = (HttpsURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setConnectTimeout(5000);
            conexao.setReadTimeout(5000);

            // Lê a resposta do servidor
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conexao.getInputStream()))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    resposta.append(linha);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consumir API: " + e.getMessage(), e);
        }

        return resposta.toString();
    }

    //TMDB API
    //TOKEN = eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzNmZlYzI4ODIwNDJkODU0ZWMwN2FlNGZkNTEwYTE5YyIsIm5iZiI6MTczMjgxMDM5MS45ODkxOTQ0LCJzdWIiOiI2NzQ4OTVjZTJlZDM5YTNhZjE3MWU3MjgiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.hOyrT-GcTKdK2dGcUGye_ybbLePPc3PlJd7iWIk0dkw
    //CHAVE = 36fec2882042d854ec07ae4fd510a19c
    //URL = https://api.themoviedb.org/3/movie/11?api_key=

}
