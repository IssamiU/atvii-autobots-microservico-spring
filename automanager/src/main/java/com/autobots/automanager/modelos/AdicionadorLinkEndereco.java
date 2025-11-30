package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.EnderecoControle;
import com.autobots.automanager.entidades.Endereco;

@Component
public class AdicionadorLinkEndereco implements AdicionadorLink<Endereco> {

    @Override
    public void adicionarLink(List<Endereco> lista) {
        for (Endereco endereco : lista) {
            adicionarLink(endereco);
        }
    }

    @Override
    public void adicionarLink(Endereco endereco) {
        long id = endereco.getId();
        
        Link linkProprio = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder
                .methodOn(EnderecoControle.class)
                .obterEndereco(id))
            .withSelfRel();
        endereco.add(linkProprio);
        
        Link linkEnderecos = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder
                .methodOn(EnderecoControle.class)
                .obterEnderecos())
            .withRel("enderecos");
        endereco.add(linkEnderecos);
        
        Link linkAtualizar = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder
                .methodOn(EnderecoControle.class)
                .atualizarEndereco(endereco))
            .withRel("atualizar");
        endereco.add(linkAtualizar);
        
        Link linkDeletar = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder
                .methodOn(EnderecoControle.class)
                .excluirEndereco(endereco))
            .withRel("deletar");
        endereco.add(linkDeletar);
    }
}