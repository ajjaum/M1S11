package br.com.fullStack.education.M1S11.service;

import br.com.fullStack.education.M1S11.controller.dto.request.InserirCadernoRequest;
import br.com.fullStack.education.M1S11.controller.dto.response.CadernoResponse;
import br.com.fullStack.education.M1S11.datasource.entities.CadernoEntity;
import br.com.fullStack.education.M1S11.datasource.entities.UsuarioEntity;
import br.com.fullStack.education.M1S11.datasource.repositories.CadernoRepository;
import br.com.fullStack.education.M1S11.datasource.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CadernoService {

    private final CadernoRepository cadernoRepository;
    private final UsuarioRepository usuarioRepository;
    private TokenService tokenService;

    private Long transformaSubLong(String token) {
        return Long.valueOf(tokenService.buscaCampo(token, "sub"));
    }

    public List<CadernoResponse> retornaCadernos(String token){

        Long usuarioId = transformaSubLong(token);

        List<CadernoEntity> cadernoEntities = cadernoRepository.findAllByUsuarioId(usuarioId);

        return cadernoEntities.stream().map(c -> new CadernoResponse(c.getId(), c.getNome())).toList();
    }

    public CadernoResponse salvaCaderno(InserirCadernoRequest inserirCadernoRequest, String token) {
        Long usuarioId = transformaSubLong(token);

        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        CadernoEntity cadernoEntity = new CadernoEntity();
        cadernoEntity.setUsuario(usuario);
        cadernoEntity.setNome(inserirCadernoRequest.nome());

        CadernoEntity cadernoSalva = cadernoRepository.save(cadernoEntity);

        return new CadernoResponse(
                cadernoSalva.getId(),
                cadernoSalva.getNome()
        );
    }

    public CadernoResponse buscaCadernoPorId(Long id, String token) {
        Long usuarioId = transformaSubLong(token);

        CadernoEntity cadernoEntity = cadernoRepository.findByUsuarioIdAndId(usuarioId, id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        return new CadernoResponse(
                cadernoEntity.getId(),
                cadernoEntity.getNome());
    }

    public CadernoResponse atualizaCaderno(Long id,
                                           InserirCadernoRequest inserirCadernoRequest,
                                           String token) {

        Long usuarioId = transformaSubLong(token);

        CadernoEntity cadernoExistente = cadernoRepository.findByUsuarioIdAndId(usuarioId, id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        cadernoExistente.setNome(inserirCadernoRequest.nome());

        CadernoEntity cadernoAtualizada = cadernoRepository.save(cadernoExistente);

        return new CadernoResponse(
                cadernoAtualizada.getId(),
                cadernoAtualizada.getNome());
    }

    public void excluiCaderno(Long id, String token) {
        Long usuarioId = transformaSubLong(token);

        CadernoEntity caderno = cadernoRepository.findByUsuarioIdAndId(usuarioId, id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        // Remover a associação entre caderno e usuário
        caderno.setUsuario(null);

        // Salvar o caderno atualizado (sem associação com usuário)
        cadernoRepository.save(caderno);

        // Excluir o caderno
        cadernoRepository.delete(caderno);
    }
}
