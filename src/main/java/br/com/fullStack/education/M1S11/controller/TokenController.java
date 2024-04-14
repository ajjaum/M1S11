package br.com.fullStack.education.M1S11.controller;


import br.com.fullStack.education.M1S11.controller.dto.request.LoginRequest;
import br.com.fullStack.education.M1S11.controller.dto.response.LoginResponse;
import br.com.fullStack.education.M1S11.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class TokenController {

    private final TokenService tokenService;
    private static long TEMPO_EXPIRACAO = 36000L; //contante de tempo de expiração em segundos

    @PostMapping("/login") //post para gerar o token
    public ResponseEntity<LoginResponse> gerarToken(
            @RequestBody LoginRequest loginRequest
    ){

        LoginResponse response = tokenService.gerarToken(loginRequest);

        return ResponseEntity.ok( // Objeto usado para criar um corpo de resposta
                response // corpo de resposta é um objeto de LoginResponse
        );

    }
}
