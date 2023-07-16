package br.com.banco.application.mappers;

import br.com.banco.application.dtos.ContaDTO;
import br.com.banco.domain.models.Conta;
import br.com.banco.infrastructure.adaptadores.persistence.entities.ContaEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ContaMapper {
    public Conta mapDtoToModel(ContaDTO contaDTO) {
        Conta conta = new Conta();
        conta.setNomeResponsavel(contaDTO.getNomeResponsavel());
        return conta;
    }

    public Optional<Conta> mapEntityToModel(ContaEntity contaEntity) {
        if (contaEntity == null) {
            return Optional.empty();
        }
        Conta conta = new Conta();
        conta.setId(contaEntity.getId());
        conta.setNomeResponsavel(contaEntity.getNomeResponsavel());

        return Optional.of(conta);
    }

    public ContaDTO mapModelToDto(Conta conta) {
        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setNomeResponsavel(conta.getNomeResponsavel());
        contaDTO.setId(conta.getId());
        return contaDTO;
    }

    public ContaEntity mapModelToEntity(Conta conta) {
        ContaEntity contaEntity = new ContaEntity();
        contaEntity.setNomeResponsavel(conta.getNomeResponsavel());
        return contaEntity;
    }

    public List<Conta> mapDtoListToModelList(List<ContaDTO> contasDTOs) {
        return contasDTOs.stream()
                .map(this::mapDtoToModel)
                .collect(Collectors.toList());
    }

    public List<Optional<Conta>> mapEntityListToModelList(List<ContaEntity> contasEntities) {
        if (contasEntities == null) {
            return List.of();
        }
        return contasEntities.stream()
                .map(this::mapEntityToModel)
                .collect(Collectors.toList());
    }

    public List<ContaDTO> mapModelListToDtoList(List<Conta> contas) {
        return contas.stream()
                .map(this::mapModelToDto)
                .collect(Collectors.toList());
    }

    public Page<ContaDTO> mapToDtoPage(Page<ContaEntity> contaPageDto) {
        return contaPageDto.map(this::mapEntityToDto);
    }

    private ContaDTO mapEntityToDto(ContaEntity contaEntity) {
        ContaDTO contaDto = new ContaDTO();
        contaDto.setNomeResponsavel(contaEntity.getNomeResponsavel());
        contaDto.setId(contaEntity.getId());
        return contaDto;
    }
}