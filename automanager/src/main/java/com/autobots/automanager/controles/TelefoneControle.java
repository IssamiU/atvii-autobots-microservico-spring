package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelos.AdicionadorLinkTelefone;
import com.autobots.automanager.modelos.TelefoneAtualizador;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {
    
    @Autowired
    private TelefoneRepositorio repositorio;
    
    @Autowired
    private AdicionadorLinkTelefone adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Telefone> obterTelefone(@PathVariable long id) {
        Optional<Telefone> telefoneOpt = repositorio.findById(id);
        
        if (!telefoneOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Telefone telefone = telefoneOpt.get();
        adicionadorLink.adicionarLink(telefone);
        return new ResponseEntity<>(telefone, HttpStatus.OK);
    }

    @GetMapping("/telefones")
    public ResponseEntity<List<Telefone>> obterTelefones() {
        List<Telefone> telefones = repositorio.findAll();
        
        if (telefones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        adicionadorLink.adicionarLink(telefones);
        return new ResponseEntity<>(telefones, HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Telefone> cadastrarTelefone(@RequestBody Telefone telefone) {
        if (telefone.getId() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        
        Telefone telefoneSalvo = repositorio.save(telefone);
        adicionadorLink.adicionarLink(telefoneSalvo);
        
        return new ResponseEntity<>(telefoneSalvo, HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Telefone> atualizarTelefone(@RequestBody Telefone atualizacao) {
        if (atualizacao.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        Optional<Telefone> telefoneOpt = repositorio.findById(atualizacao.getId());
        
        if (!telefoneOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Telefone telefone = telefoneOpt.get();
        TelefoneAtualizador atualizador = new TelefoneAtualizador();
        atualizador.atualizar(telefone, atualizacao);
        
        Telefone telefoneAtualizado = repositorio.save(telefone);
        adicionadorLink.adicionarLink(telefoneAtualizado);
        
        return new ResponseEntity<>(telefoneAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<Void> excluirTelefone(@RequestBody Telefone exclusao) {
        if (exclusao.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        Optional<Telefone> telefoneOpt = repositorio.findById(exclusao.getId());
        
        if (!telefoneOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        repositorio.delete(telefoneOpt.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}