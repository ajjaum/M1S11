package br.com.fullStack.education.M1S11.controller;

import br.com.fullStack.education.M1S11.controller.dto.request.InserirUsuarioRequest;
import br.com.fullStack.education.M1S11.datasource.entities.UsuarioEntity;
import br.com.fullStack.education.M1S11.datasource.repositories.PerfilRepository;
import br.com.fullStack.education.M1S11.datasource.repositories.UsuarioRepository;
import br.com.fullStack.education.M1S11.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/cadastro")
    public ResponseEntity<String> novoUsuario(
            @Validated @RequestBody InserirUsuarioRequest inserirUsuarioRequest
    ) {

        usuarioService.cadastraNovoUsuario(inserirUsuarioRequest);

        return ResponseEntity.ok("Usuario Salvo!");
    }
}
