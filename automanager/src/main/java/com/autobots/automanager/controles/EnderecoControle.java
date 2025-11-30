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

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelos.AdicionadorLinkEndereco;
import com.autobots.automanager.modelos.EnderecoAtualizador;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
    
    @Autowired
    private EnderecoRepositorio repositorio;
    
    @Autowired
    private AdicionadorLinkEndereco adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> obterEndereco(@PathVariable long id) {
        Optional<Endereco> enderecoOpt = repositorio.findById(id);
        
        if (!enderecoOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Endereco endereco = enderecoOpt.get();
        adicionadorLink.adicionarLink(endereco);
        return new ResponseEntity<>(endereco, HttpStatus.OK);
    }

    @GetMapping("/enderecos")
    public ResponseEntity<List<Endereco>> obterEnderecos() {
        List<Endereco> enderecos = repositorio.findAll();
        
        if (enderecos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        adicionadorLink.adicionarLink(enderecos);
        return new ResponseEntity<>(enderecos, HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Endereco> cadastrarEndereco(@RequestBody Endereco endereco) {
        if (endereco.getId() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        
        Endereco enderecoSalvo = repositorio.save(endereco);
        adicionadorLink.adicionarLink(enderecoSalvo);
        
        return new ResponseEntity<>(enderecoSalvo, HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Endereco> atualizarEndereco(@RequestBody Endereco atualizacao) {
        if (atualizacao.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        Optional<Endereco> enderecoOpt = repositorio.findById(atualizacao.getId());
        
        if (!enderecoOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Endereco endereco = enderecoOpt.get();
        EnderecoAtualizador atualizador = new EnderecoAtualizador();
        atualizador.atualizar(endereco, atualizacao);
        
        Endereco enderecoAtualizado = repositorio.save(endereco);
        adicionadorLink.adicionarLink(enderecoAtualizado);
        
        return new ResponseEntity<>(enderecoAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<Void> excluirEndereco(@RequestBody Endereco exclusao) {
        if (exclusao.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        Optional<Endereco> enderecoOpt = repositorio.findById(exclusao.getId());
        
        if (!enderecoOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        repositorio.delete(enderecoOpt.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}