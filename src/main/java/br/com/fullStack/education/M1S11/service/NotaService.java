package br.com.fullStack.education.M1S11.service;

import br.com.fullStack.education.M1S11.controller.dto.request.InserirNotaRequest;
import br.com.fullStack.education.M1S11.controller.dto.response.NotaResponse;
import br.com.fullStack.education.M1S11.datasource.entities.CadernoEntity;
import br.com.fullStack.education.M1S11.datasource.entities.NotaEntity;
import br.com.fullStack.education.M1S11.datasource.entities.UsuarioEntity;
import br.com.fullStack.education.M1S11.datasource.repositories.CadernoRepository;
import br.com.fullStack.education.M1S11.datasource.repositories.NotaRepository;
import br.com.fullStack.education.M1S11.datasource.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NotaService {

    private final NotaRepository notaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CadernoRepository cadernoRepository;
    private final TokenService tokenService;

    private Long transformaSubLong(String token) {
        return Long.valueOf(tokenService.buscaCampo(token, "sub"));
    }

    public List<NotaResponse> retornaNotas(String token){

        Long idUsuario = transformaSubLong(token);

        List<NotaEntity> notaEntities = notaRepository.findAllByUsuarioId(idUsuario);
        List<NotaResponse> notaResponseList = new ArrayList<>();

        notaEntities.forEach( t-> notaResponseList.add(
                new NotaResponse(t.getId(), t.getTitulo(), t.getConteudo())
        ));

        return notaResponseList;

    }

    public NotaResponse salvaNota(InserirNotaRequest inserirNotaRequest, String token) {
        Long usurioId = transformaSubLong(token);

        UsuarioEntity usuario = usuarioRepository.findById(usurioId)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        // Obtenha o caderno com o qual a nota está associada
        CadernoEntity caderno = cadernoRepository.findFirstByUsuarioId(usurioId)
                .orElseThrow(() -> new RuntimeException("Não foi possível criar uma nota, pois não há nenhum caderno vinculado."));

        NotaEntity notaEntity = new NotaEntity();
        notaEntity.setUsuario(usuario);
        notaEntity.setCaderno(caderno); // Defina o caderno associado à nota
        notaEntity.setTitulo(inserirNotaRequest.titulo());
        notaEntity.setConteudo(inserirNotaRequest.conteudo());

        NotaEntity notaSalva = notaRepository.save(notaEntity);
        return new NotaResponse(notaSalva.getId(), notaSalva.getTitulo(), notaSalva.getConteudo());
    }

    public NotaResponse buscaNotaPorId(Long id, String token) {
        Long usuarioId = transformaSubLong(token);

        NotaEntity notaEntity = notaRepository.findByUsuarioIdAndId(usuarioId, id)
                .orElseThrow(() -> new RuntimeException("Nota não encontrada"));

        return new NotaResponse(notaEntity.getId(), notaEntity.getTitulo(), notaEntity.getConteudo());
    }

    public NotaResponse atualizaNota(Long id, InserirNotaRequest incluiNotaRequest, String token) {
        Long usuarioId = transformaSubLong(token);

        NotaEntity notaExistente = notaRepository.findByUsuarioIdAndId(usuarioId, id)
                .orElseThrow(() -> new RuntimeException("Nota não encontrada"));

        // Atualiza os campos da nota com base nos dados da requisição
        notaExistente.setTitulo(incluiNotaRequest.titulo());
        notaExistente.setConteudo(incluiNotaRequest.conteudo());

        // Salva a nota atualizada no banco de dados
        NotaEntity notaAtualizada = notaRepository.save(notaExistente);

        // Retorna uma instância de NotaResponse com os dados da nota atualizada
        return new NotaResponse(notaAtualizada.getId(), notaAtualizada.getTitulo(), notaAtualizada.getConteudo());
    }

    public void excluiNota(Long id, String token) {
        Long usuarioId = transformaSubLong(token);

        // Verificar se o nota pertence ao usuário antes de excluí-lo
        NotaEntity nota = notaRepository.findByUsuarioIdAndId(usuarioId, id)
                .orElseThrow(() -> new RuntimeException("Nota não encontrada"));

        // Remover a associação entre caderno e usuário
        nota.setUsuario(null);

        // Salvar o caderno atualizado (sem associação com usuário)
        notaRepository.save(nota);

        // Excluir o caderno

        // Excluir o nota
        notaRepository.delete(nota);
    }
}
