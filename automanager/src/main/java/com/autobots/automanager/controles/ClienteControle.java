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

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.modelos.AdicionadorLinkCliente;
import com.autobots.automanager.modelos.ClienteAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController
@RequestMapping("/cliente") 
public class ClienteControle {
    
    @Autowired
    private ClienteRepositorio repositorio;
    
    @Autowired
    private AdicionadorLinkCliente adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obterCliente(@PathVariable long id) {
        Optional<Cliente> clienteOpt = repositorio.findById(id);
        
        if (!clienteOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Cliente cliente = clienteOpt.get();
        adicionadorLink.adicionarLink(cliente); 
        return new ResponseEntity<>(cliente, HttpStatus.OK); 
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> obterClientes() {
        List<Cliente> clientes = repositorio.findAll();
        
        if (clientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        adicionadorLink.adicionarLink(clientes); 
        return new ResponseEntity<>(clientes, HttpStatus.OK); 
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente) {
        if (cliente.getId() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        
        Cliente clienteSalvo = repositorio.save(cliente);
        adicionadorLink.adicionarLink(clienteSalvo); 
        
        return new ResponseEntity<>(clienteSalvo, HttpStatus.CREATED); 
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Cliente> atualizarCliente(@RequestBody Cliente atualizacao) {
        if (atualizacao.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        }
        
        Optional<Cliente> clienteOpt = repositorio.findById(atualizacao.getId());
        
        if (!clienteOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
        
        Cliente cliente = clienteOpt.get();
        ClienteAtualizador atualizador = new ClienteAtualizador();
        atualizador.atualizar(cliente, atualizacao);
        
        Cliente clienteAtualizado = repositorio.save(cliente);
        adicionadorLink.adicionarLink(clienteAtualizado);
        
        return new ResponseEntity<>(clienteAtualizado, HttpStatus.OK); 
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<Void> excluirCliente(@RequestBody Cliente exclusao) {
        if (exclusao.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        }
        
        Optional<Cliente> clienteOpt = repositorio.findById(exclusao.getId());
        
        if (!clienteOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
        
        repositorio.delete(clienteOpt.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
    }
}