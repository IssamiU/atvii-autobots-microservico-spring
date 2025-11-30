package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.TelefoneControle;
import com.autobots.automanager.entidades.Telefone;

@Component
public class AdicionadorLinkTelefone implements AdicionadorLink<Telefone> {

    @Override
    public void adicionarLink(List<Telefone> lista) {
        for (Telefone telefone : lista) {
            adicionarLink(telefone);
        }
    }

    @Override
    public void adicionarLink(Telefone telefone) {
        long id = telefone.getId();
        
        Link linkProprio = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder
                .methodOn(TelefoneControle.class)
                .obterTelefone(id))
            .withSelfRel();
        telefone.add(linkProprio);
        
        Link linkTelefones = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder
                .methodOn(TelefoneControle.class)
                .obterTelefones())
            .withRel("telefones");
        telefone.add(linkTelefones);
        
        Link linkAtualizar = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder
                .methodOn(TelefoneControle.class)
                .atualizarTelefone(telefone))
            .withRel("atualizar");
        telefone.add(linkAtualizar);
        
        Link linkDeletar = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder
                .methodOn(TelefoneControle.class)
                .excluirTelefone(telefone))
            .withRel("deletar");
        telefone.add(linkDeletar);
    }
}