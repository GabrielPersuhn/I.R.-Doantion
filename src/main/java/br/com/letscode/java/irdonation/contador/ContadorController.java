package br.com.letscode.java.irdonation.contador;

import br.com.letscode.java.irdonation.cliente.Cliente;
import br.com.letscode.java.irdonation.cliente.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/contador")
public class ContadorController {

    private final ContadorService service;

    private final ClienteService clienteService;

    @GetMapping("/listarContadores")
    public ResponseEntity<?> list() {
        try {
            return (ResponseEntity<?>) this.service.listAll();
        }
        catch (Exception e) {
            return new ResponseEntity<>("Não há contadores cadastrados", HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/atendimento") // TODO Procura por clientes ainda não atendidos (transformar em FILA)
    public Cliente listClientes() {
        this.clienteService.listall().poll();
        return this.clienteService.listall().remove();
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Contador> criarContador(@RequestBody Contador contador) {
        this.service.cadastrarContador(contador);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<?> remover(@PathVariable Long cpf){
        try {
            service.deleteByCpf(cpf);
            return new ResponseEntity<>("CPF " + cpf + " removido com sucesso", HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>("CPF " + cpf + " não encontrado", HttpStatus.NOT_FOUND);
        }
    }

//    @PutMapping("/{id}")
//    public Cliente alterarInformacoes(@PathVariable Long id, @RequestBody String imdbId, Authentication auth)    {
//        return this.service.answer(id, imdbId, auth.getName());
//    }
}