package br.com.banco.infrastructure.config;

import br.com.banco.application.mappers.ContaMapper;
import br.com.banco.application.mappers.TransferenciaMapper;
import br.com.banco.domain.adaptadores.services.ContaServiceImpl;
import br.com.banco.domain.adaptadores.services.TransferenciaServiceImpl;
import br.com.banco.domain.ports.interfaces.IContaServicePort;
import br.com.banco.domain.ports.interfaces.ITransferenciaServicePort;
import br.com.banco.domain.ports.repositories.IContaRepositoryPort;
import br.com.banco.domain.ports.repositories.ITransferenciaRepositoryPort;
import br.com.banco.infrastructure.adaptadores.repositories.ContaRepositoryImpl;
import br.com.banco.infrastructure.adaptadores.repositories.SpringContaRepository;
import br.com.banco.infrastructure.adaptadores.repositories.SpringTransferenciaRepository;
import br.com.banco.infrastructure.adaptadores.repositories.TransferenciaRepositoryImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguracao {

    @Bean
    public ITransferenciaServicePort transferenciaServicePort(ITransferenciaRepositoryPort transferenciaRepositoryPort, IContaRepositoryPort contaRepositoryPort, TransferenciaMapper transferenciaMapper) {
        return new TransferenciaServiceImpl(transferenciaRepositoryPort, transferenciaMapper, contaRepositoryPort);
    }

    @Bean
    public ITransferenciaRepositoryPort transferenciaRepositoryPort(SpringTransferenciaRepository transferenciaRepository, TransferenciaMapper transferenciaMapper) {
        return new TransferenciaRepositoryImpl(transferenciaRepository, transferenciaMapper);
    }

    @Bean
    public IContaServicePort contaServicePort(@Qualifier("contaRepositoryImpl") IContaRepositoryPort contaRepository, ContaMapper contaMapper) {
        return new ContaServiceImpl(contaRepository, contaMapper);
    }

    @Bean
    public IContaRepositoryPort contaRepositoryPort(SpringContaRepository contaRepository, ContaMapper contaMapper) {
        return new ContaRepositoryImpl(contaRepository, contaMapper);
    }

}