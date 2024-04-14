package br.com.fullStack.education.M1S11.controller.dto.response;

import br.com.fullStack.education.M1S11.datasource.entities.CadernoEntity;

public record NotaResponse(
        Long id,
        String title,
        String conteudo
) {
}
