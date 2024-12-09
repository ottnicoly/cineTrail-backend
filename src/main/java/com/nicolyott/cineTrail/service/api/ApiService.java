package com.nicolyott.cineTrail.service.api;

import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Service
public class ApiService {

    static {
        disableSSLCertificateChecking();
    }

    private static void disableSSLCertificateChecking() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
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
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao desabilitar verificação de certificado SSL", e);
        }
    }

    private HttpURLConnection createConnection(String urlAddress, String method) throws IOException{
        URL url = new URL(urlAddress);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        return connection;
    }

    public String fetchData(String urlAddress) {
        try {
            HttpURLConnection connection = createConnection(urlAddress, "GET");
            StringBuilder builder = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
                String line;
                while ((line = br.readLine()) != null){
                    builder.append(line);
                }
            }
            return builder.toString();
        } catch (IOException e){
            throw new RuntimeException("Erro ao buscar dados da URL: " + urlAddress, e);
        }
    }

    public int checkRequest(String urlAddress, String method) {
        try {
            HttpURLConnection connection = createConnection(urlAddress, method);
            connection.connect();
            return connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
