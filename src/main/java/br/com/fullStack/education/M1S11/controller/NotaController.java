package br.com.fullStack.education.M1S11.controller;

import br.com.fullStack.education.M1S11.controller.dto.request.InserirNotaRequest;
import br.com.fullStack.education.M1S11.controller.dto.response.NotaResponse;
import br.com.fullStack.education.M1S11.service.NotaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("notas")
public class NotaController {

    private final NotaService notaService;

    @GetMapping
    public ResponseEntity<List<NotaResponse>> retornarNotas(
            @RequestHeader(name = "Authorization") String token
    ){
        return ResponseEntity.ok(notaService.retornaNotas(token.substring(7)));
    }

    @GetMapping("{id}")
    public ResponseEntity<NotaResponse> buscarNotaPorId(
                                                        @PathVariable Long id,
                                                        @RequestHeader(name = "Authorization") String token
    ){
        return ResponseEntity.ok(notaService.buscaNotaPorId(id, token.substring(7)));
    }

    @PostMapping
    public ResponseEntity<NotaResponse> salvarNota(
            @RequestHeader(name = "Authorization") String token,
            @RequestBody InserirNotaRequest inserirNotaRequest
    ){
        return ResponseEntity.ok(notaService.salvaNota(inserirNotaRequest,token.substring(7)));
    }

    @PutMapping("{id}")
    public ResponseEntity<NotaResponse> atualizarNota(@RequestHeader(name = "Authorization") String token,
                                                      @PathVariable Long id,
                                                      @RequestBody InserirNotaRequest inserirNotaRequest) {
        return ResponseEntity.ok(notaService.atualizaNota(id, inserirNotaRequest, token.substring(7)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> excluirNota(@PathVariable Long id,
                                            @RequestHeader(name = "Authorization") String token
    ){
        notaService.excluiNota(id, token.substring(7));
        return ResponseEntity.noContent().build();
    }


}
