package br.com.fullStack.education.M1S11.controller.dto.response;

public record LoginResponse (
        String valorJWT,
        long tempoExpiracao) {
}