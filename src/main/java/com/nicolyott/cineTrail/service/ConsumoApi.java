package com.nicolyott.cineTrail.service;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;

public class ConsumoApi {

    private static void disableSSLCertificateChecking() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
            };
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
        disableSSLCertificateChecking();
        StringBuilder resposta = new StringBuilder();

        try {
            URL url = new URL(endereco);
            HttpsURLConnection conexao = (HttpsURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setConnectTimeout(5000);
            conexao.setReadTimeout(5000);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conexao.getInputStream()))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    resposta.append(linha);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resposta.toString();
    }

    public int verificarRequisição(String url, String metodo){
        disableSSLCertificateChecking();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(metodo);
            connection.connect();
            return connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
