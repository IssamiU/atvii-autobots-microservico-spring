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

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelos.AdicionadorLinkDocumento;
import com.autobots.automanager.modelos.DocumentoAtualizador;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
    
    @Autowired
    private DocumentoRepositorio repositorio;
    
    @Autowired
    private AdicionadorLinkDocumento adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Documento> obterDocumento(@PathVariable long id) {
        Optional<Documento> documentoOpt = repositorio.findById(id);
        
        if (!documentoOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Documento documento = documentoOpt.get();
        adicionadorLink.adicionarLink(documento);
        return new ResponseEntity<>(documento, HttpStatus.OK);
    }

    @GetMapping("/documentos")
    public ResponseEntity<List<Documento>> obterDocumentos() {
        List<Documento> documentos = repositorio.findAll();
        
        if (documentos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        adicionadorLink.adicionarLink(documentos);
        return new ResponseEntity<>(documentos, HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Documento> cadastrarDocumento(@RequestBody Documento documento) {
        if (documento.getId() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        
        Documento documentoSalvo = repositorio.save(documento);
        adicionadorLink.adicionarLink(documentoSalvo);
        
        return new ResponseEntity<>(documentoSalvo, HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Documento> atualizarDocumento(@RequestBody Documento atualizacao) {
        if (atualizacao.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        Optional<Documento> documentoOpt = repositorio.findById(atualizacao.getId());
        
        if (!documentoOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Documento documento = documentoOpt.get();
        DocumentoAtualizador atualizador = new DocumentoAtualizador();
        atualizador.atualizar(documento, atualizacao);
        
        Documento documentoAtualizado = repositorio.save(documento);
        adicionadorLink.adicionarLink(documentoAtualizado);
        
        return new ResponseEntity<>(documentoAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<Void> excluirDocumento(@RequestBody Documento exclusao) {
        if (exclusao.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        Optional<Documento> documentoOpt = repositorio.findById(exclusao.getId());
        
        if (!documentoOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        repositorio.delete(documentoOpt.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}