package com.example.demo.services;

import com.example.demo.dto.InfoUsuarioDTO;
import com.example.demo.exceptions.ForbiddenRequestException;
import com.example.demo.exceptions.RequestErrorMessage;
import com.example.demo.exceptions.UnauthorizedRequestException;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class JwtService {

    public JwtService() {}

    public InfoUsuarioDTO getInfo(String token) throws URISyntaxException, IOException, InterruptedException, UnauthorizedRequestException, ForbiddenRequestException {
        Gson gson = new Gson();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8081/info"))
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .POST(HttpRequest.BodyPublishers.ofString("{}"))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (200 <= response.statusCode() && response.statusCode() < 300) {
            return gson.fromJson(response.body(), InfoUsuarioDTO.class);
        } else if (response.statusCode() == 401) {
            throw new UnauthorizedRequestException("Token inválido");
        } else if (response.statusCode() == 403) {
            System.out.println(response.body());
            throw new ForbiddenRequestException("Token expirado");
        } else {
            RequestErrorMessage requestErrorMessage = gson.fromJson(response.body(), RequestErrorMessage.class);
            throw new RuntimeException(requestErrorMessage.message);
        }
    }
}
