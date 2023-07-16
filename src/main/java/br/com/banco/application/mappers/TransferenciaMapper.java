package br.com.banco.application.mappers;


import br.com.banco.application.dtos.TransferenciaDTO;
import br.com.banco.domain.enums.TipoTransferencia;
import br.com.banco.domain.models.Conta;
import br.com.banco.domain.models.Transferencia;
import br.com.banco.infrastructure.adaptadores.persistence.entities.ContaEntity;
import br.com.banco.infrastructure.adaptadores.persistence.entities.TransferenciaEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransferenciaMapper {

    public Transferencia mapDtoToModel(TransferenciaDTO transferenciaDTO) {
        Transferencia transferencia = new Transferencia();
        transferencia.setDataTransferencia(transferenciaDTO.getDataTransferencia());
        transferencia.setValor(transferenciaDTO.getValor());
        transferencia.setTipo(transferenciaDTO.getTipo());
        transferencia.setNomeOperadorTransacao(transferenciaDTO.getNomeOperadorTransacao());
        transferencia.setContaId(transferenciaDTO.getConta());
        return transferencia;
    }


    public Transferencia mapEntityToModel(TransferenciaEntity transferenciaEntity) {
        Transferencia transferencia = new Transferencia();
        transferencia.setDataTransferencia(transferenciaEntity.getDataTransferencia());
        transferencia.setValor(transferenciaEntity.getValor());
        transferencia.setTipo(transferenciaEntity.getTipo());
        transferencia.setNomeOperadorTransacao(transferenciaEntity.getNomeOperadorTransacao());

        Conta contaModel = new Conta();
        contaModel.setNomeResponsavel(transferenciaEntity.getConta().getNomeResponsavel());
        contaModel.setId(transferenciaEntity.getConta().getId());

        transferencia.setContaId(contaModel);
        return transferencia;
    }

    public TransferenciaDTO mapModelToDto(Transferencia transferencia) {
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO();
        transferenciaDTO.setDataTransferencia(transferencia.getDataTransferencia());
        transferenciaDTO.setValor(transferencia.getValor());
        transferenciaDTO.setTipo(transferencia.getTipo());
        transferenciaDTO.setNomeOperadorTransacao(transferencia.getNomeOperadorTransacao());
        transferenciaDTO.setConta(transferencia.getContaId());
        return transferenciaDTO;
    }

    public TransferenciaEntity mapModelToEntity(Transferencia transferencia) {
        TransferenciaEntity transferenciaEntity = new TransferenciaEntity();
        transferenciaEntity.setDataTransferencia(transferencia.getDataTransferencia());
        transferenciaEntity.setValor(transferencia.getValor());
        transferenciaEntity.setTipo(transferencia.getTipo());
        transferenciaEntity.setNomeOperadorTransacao(transferencia.getNomeOperadorTransacao());

        ContaEntity contaEntity = new ContaEntity();
        contaEntity.setNomeResponsavel(transferencia.getContaId().getNomeResponsavel());
        contaEntity.setId(transferencia.getContaId().getId());

        transferenciaEntity.setConta(contaEntity);
        return transferenciaEntity;
    }


    public List<Transferencia> mapDtoListToModelList(List<TransferenciaDTO> transferenciasDTO) {
        return transferenciasDTO.stream()
                .map(this::mapDtoToModel)
                .collect(Collectors.toList());
    }

    public List<Transferencia> mapEntityListToModelList(List<TransferenciaEntity> transferenciasEntities) {
        return transferenciasEntities.stream()
                .map(this::mapEntityToModel)
                .collect(Collectors.toList());
    }

    public List<TransferenciaDTO> mapModelListToDtoList(List<Transferencia> transferencias) {
        return transferencias.stream()
                .map(this::mapModelToDto)
                .collect(Collectors.toList());
    }

    public Transferencia mapTransferencia(TransferenciaDTO transferenciaDTO, Conta destinatario, Conta origem) {
        Transferencia transferencia = mapDtoToModel(transferenciaDTO);
        transferencia.setNomeOperadorTransacao(destinatario.getNomeResponsavel());
        transferencia.setContaId(origem);
        transferencia.setDataTransferencia(LocalDateTime.now());
        transferencia.setTipo(TipoTransferencia.TRANSFERENCIA);
        return transferencia;
    }

    public Transferencia mapTransferenciaDeposito(TransferenciaDTO transferenciaDTO, Conta destinatario) {
        Transferencia transferencia = mapDtoToModel(transferenciaDTO);
        transferencia.setValor(transferencia.getValor());
        transferencia.setNomeOperadorTransacao(destinatario.getNomeResponsavel());
        transferencia.setContaId(destinatario);
        transferencia.setDataTransferencia(LocalDateTime.now());
        transferencia.setTipo(TipoTransferencia.DEPOSITO);
        return transferencia;
    }

    public Transferencia mapTransferenciaSaque(TransferenciaDTO transferenciaDTO, Conta destinatario) {
        Transferencia transferencia = mapDtoToModel(transferenciaDTO);
        transferencia.setValor(transferencia.getValor());
        transferencia.setNomeOperadorTransacao(destinatario.getNomeResponsavel());
        transferencia.setContaId(destinatario);
        transferencia.setDataTransferencia(LocalDateTime.now());
        transferencia.setTipo(TipoTransferencia.SAQUE);
        return transferencia;
    }


    public Page<TransferenciaDTO> mapToDtoPage(Page<TransferenciaEntity> contaPageDto) {
        return contaPageDto.map(this::mapEntityToDto);
    }

    private TransferenciaDTO mapEntityToDto(TransferenciaEntity transferenciaEntity) {
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO();
        transferenciaDTO.setTipo(transferenciaEntity.getTipo());
        transferenciaDTO.setValor(transferenciaEntity.getValor());
        transferenciaDTO.setDataTransferencia(transferenciaEntity.getDataTransferencia());
        transferenciaDTO.setNomeOperadorTransacao(transferenciaEntity.getNomeOperadorTransacao());

        Conta contaModel = new Conta();
        contaModel.setNomeResponsavel(transferenciaEntity.getConta().getNomeResponsavel());
        contaModel.setId(transferenciaEntity.getConta().getId());

        transferenciaDTO.setConta(contaModel);
        return transferenciaDTO;
    }
}