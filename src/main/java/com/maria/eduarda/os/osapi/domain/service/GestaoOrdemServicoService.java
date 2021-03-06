package com.maria.eduarda.os.osapi.domain.service;

import com.maria.eduarda.os.osapi.domain.exception.NegocioException;
import com.maria.eduarda.os.osapi.domain.model.Cliente;
import com.maria.eduarda.os.osapi.domain.model.Comentario;
import com.maria.eduarda.os.osapi.domain.model.OrdemServico;
import com.maria.eduarda.os.osapi.domain.model.StatusOrdemServico;
import com.maria.eduarda.os.osapi.domain.repository.ClienteRepository;
import com.maria.eduarda.os.osapi.domain.repository.ComentarioRepository;
import com.maria.eduarda.os.osapi.domain.repository.OrdemServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Service
public class GestaoOrdemServicoService {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    public OrdemServico criar(OrdemServico ordemServico){
        Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
                .orElseThrow(() -> new NegocioException("Cliente não encontrado"));

        ordemServico.setCliente(cliente);
        ordemServico.setStatus(StatusOrdemServico.ABERTA);
        ordemServico.setDataAbertura(OffsetDateTime.now());

        return ordemServicoRepository.save(ordemServico);
    }

    public Comentario adicionarComentario(Long ordemServicoId, String descricao){
        OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
                .orElseThrow(() -> new NegocioException("Ordem de serviço não encontrada"));

        Comentario comentario = new Comentario();
        comentario.setDataEnvio(OffsetDateTime.now());
        comentario.setDescricao(descricao);
        comentario.setOrdemServico(ordemServico);

        return comentarioRepository.save(comentario);
    }
}
