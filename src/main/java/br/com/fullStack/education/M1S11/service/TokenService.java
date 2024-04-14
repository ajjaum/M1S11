package br.com.fullStack.education.M1S11.service;

import br.com.fullStack.education.M1S11.controller.dto.request.LoginRequest;
import br.com.fullStack.education.M1S11.controller.dto.response.LoginResponse;
import br.com.fullStack.education.M1S11.datasource.entities.PerfilEntity;
import br.com.fullStack.education.M1S11.datasource.entities.UsuarioEntity;
import br.com.fullStack.education.M1S11.datasource.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TokenService {

    private final BCryptPasswordEncoder bCryptEncoder; // decifrar senhas
    private final JwtEncoder jwtEncoder; // codificar um JWT
    private final JwtDecoder jwtDencoder; // decofica um JWT
    private final UsuarioRepository usuarioRepository;

    private static long TEMPO_EXPIRACAO = 36000L; //contante de tempo de expiração em segundos

    public LoginResponse gerarToken(
            @RequestBody LoginRequest loginRequest
    ){

        UsuarioEntity usuarioEntity = usuarioRepository.findByNomeUsuario(loginRequest.nomeUsuario())
                .orElseThrow(() -> new RuntimeException("Erro, usuário não existe")); //método sem parametros

        if (!usuarioEntity.senhaValida(loginRequest, bCryptEncoder)) { //valida se usuario existe e se esta com login correto
            throw new RuntimeException("Erro ao Logar, senha incorreta");
        }

        Instant now = Instant.now(); // momento atual

        //escopo do token
        String scope = usuarioEntity.getPerfis() //papés do usuário - ou funções do usuário
                .stream()
                .map(PerfilEntity::getNomePerfil)// Nomes dos papéis do usuário
                .collect(Collectors.joining(" ")); //concatena cada item encontrado como uma string separada por " "

        // campos do token
        JwtClaimsSet claims = JwtClaimsSet.builder() // campos do JWT
                .issuer("aplicacaodemo") // emissor -> corpo JWT
                .issuedAt(now) // criado no momento atual
                .expiresAt(now.plusSeconds(TEMPO_EXPIRACAO)) // expira em 36000L milisegundos
                .subject(usuarioEntity.getId().toString()) //nome do usuário
                .claim("scope", scope) // cria um campo dentro do corpo do JWT
                .build();

        var valorJWT = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(valorJWT, TEMPO_EXPIRACAO); // corpo de resposta é um objeto de LoginResponse
    }


    public String buscaCampo(String token, String claim) {
        return jwtDencoder
                .decode(token) // decifra o token
                .getClaims() // busca um campo especifico do token
                .get(claim)    // definindo o campo a ser buscado
                .toString(); // transforma a resposta em string
    }
}
