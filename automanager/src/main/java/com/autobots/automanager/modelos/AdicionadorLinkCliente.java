package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.ClienteControle;
import com.autobots.automanager.entidades.Cliente;

@Component
public class AdicionadorLinkCliente implements AdicionadorLink<Cliente> {

    @Autowired
    private AdicionadorLinkDocumento adicionadorLinkDocumento;
    
    @Autowired
    private AdicionadorLinkEndereco adicionadorLinkEndereco;
    
    @Autowired
    private AdicionadorLinkTelefone adicionadorLinkTelefone;

    @Override
    public void adicionarLink(List<Cliente> lista) {
        for (Cliente cliente : lista) {
            adicionarLink(cliente);
        }
    }

    @Override
    public void adicionarLink(Cliente cliente) {
        long id = cliente.getId();
        
        Link linkProprio = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder
                .methodOn(ClienteControle.class)
                .obterCliente(id))
            .withSelfRel();
        cliente.add(linkProprio);
        
        Link linkClientes = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder
                .methodOn(ClienteControle.class)
                .obterClientes())
            .withRel("clientes");
        cliente.add(linkClientes);
        
        Link linkAtualizar = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder
                .methodOn(ClienteControle.class)
                .atualizarCliente(cliente))
            .withRel("atualizar");
        cliente.add(linkAtualizar);
        
        Link linkDeletar = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder
                .methodOn(ClienteControle.class)
                .excluirCliente(cliente))
            .withRel("deletar");
        cliente.add(linkDeletar);
        
        if (cliente.getDocumentos() != null && !cliente.getDocumentos().isEmpty()) {
            adicionadorLinkDocumento.adicionarLink(cliente.getDocumentos());
        }
        
        if (cliente.getEndereco() != null) {
            adicionadorLinkEndereco.adicionarLink(cliente.getEndereco());
        }
        
        if (cliente.getTelefones() != null && !cliente.getTelefones().isEmpty()) {
            adicionadorLinkTelefone.adicionarLink(cliente.getTelefones());
        }
    }
}