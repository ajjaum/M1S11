package br.com.fullStack.education.M1S11.controller.dto.response;

import br.com.fullStack.education.M1S11.datasource.entities.NotaEntity;
import br.com.fullStack.education.M1S11.datasource.entities.UsuarioEntity;

import java.util.List;

public record CadernoResponse (
        Long id,
        String nome
) {
}
