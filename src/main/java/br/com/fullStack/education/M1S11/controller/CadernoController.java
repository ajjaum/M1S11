package br.com.fullStack.education.M1S11.controller;

import br.com.fullStack.education.M1S11.controller.dto.request.InserirCadernoRequest;
import br.com.fullStack.education.M1S11.controller.dto.request.InserirUsuarioRequest;
import br.com.fullStack.education.M1S11.controller.dto.response.CadernoResponse;
import br.com.fullStack.education.M1S11.service.CadernoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.StringTokenizer;

@RestController
@AllArgsConstructor
@RequestMapping("cadernos")
public class CadernoController {

    private final CadernoService cadernoService;

    @GetMapping
    public ResponseEntity<List<CadernoResponse>> retornarCadernos(
            @RequestHeader(name = "Authorization") String token
    ) {
        return ResponseEntity.ok(cadernoService.retornaCadernos(token.substring(7)));
    }

    @GetMapping("{id}")
    public ResponseEntity<CadernoResponse> buscarCardenoPorId(
            @PathVariable Long id,
            @RequestHeader(name = "Authorization") String token
    ) {

        return ResponseEntity.ok(cadernoService.buscaCadernoPorId(id, token.substring(7)));
    }

    @PostMapping
    public ResponseEntity<CadernoResponse> salvarCaderno(
            @RequestBody InserirCadernoRequest inserirCadernoRequest,
            @RequestHeader(name = "Authorization") String token
    ){
        return ResponseEntity.ok(cadernoService.salvaCaderno(inserirCadernoRequest, token.substring(7)));
    }

    @PutMapping("{id}")
    public ResponseEntity<CadernoResponse> atualizarCaderno(
            @PathVariable Long id,
            @RequestBody InserirCadernoRequest inserirCadernoRequest,
            @RequestHeader(name = "Authorization") String token
    ) {
        return ResponseEntity.ok(cadernoService.atualizaCaderno(id, inserirCadernoRequest, token.substring(7)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> excluirCaderno(@PathVariable Long id,
                                               @RequestHeader(name = "Authorization") String token
    ){
        cadernoService.excluiCaderno(id, token.substring(7));
        return ResponseEntity.noContent().build();
    }


}
