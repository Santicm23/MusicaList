package com.example.demo.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Calendar;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.dto.InfoUsuarioDTO;
import com.example.demo.exceptions.ForbiddenRequestException;
import com.example.demo.exceptions.RequestErrorMessage;
import com.example.demo.exceptions.UnauthorizedRequestException;
import com.example.demo.services.JwtService;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.Generated;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String HEADER = "Authorization";
    public static final String PREFIX = "Bearer ";
    public JwtService jwtService;

    @Generated
    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Generated
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            if (!existJwt(request.getHeader(HEADER))) {
                chain.doFilter(request, response);
                return;
            }
            String jwt = getJwt(request);
            InfoUsuarioDTO infoUsuarioDTO = jwtService.getInfo(jwt);
            if (infoUsuarioDTO != null) {
                Long id = infoUsuarioDTO.getId();
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(id, null, null);
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                SecurityContextHolder.clearContext();
            }
            chain.doFilter(request, response);
        } catch (ForbiddenRequestException e) {
            RequestErrorMessage message = new RequestErrorMessage("Token inválido", Calendar.getInstance().getTime(), HttpStatus.FORBIDDEN);
            sendCustomError(response, message);
        } catch (UnauthorizedRequestException e) {
            RequestErrorMessage message = new RequestErrorMessage(e.getMessage(), Calendar.getInstance().getTime(), HttpStatus.UNAUTHORIZED);
            sendCustomError(response, message);
        } catch (URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Generated
    private boolean existJwt(String authHeader) {
        return !(authHeader == null || !authHeader.startsWith(PREFIX));
    }

    @Generated
    private String getJwt(HttpServletRequest request) throws ForbiddenRequestException {
        return request.getHeader(HEADER);
    }

    @Generated
    private void sendCustomError(HttpServletResponse response, RequestErrorMessage message) throws IOException {
        Gson gson = new Gson();
        String messageString = gson.toJson(message);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(message.status);

        PrintWriter out = response.getWriter();
        out.print(messageString);
        out.flush();
    }
}
