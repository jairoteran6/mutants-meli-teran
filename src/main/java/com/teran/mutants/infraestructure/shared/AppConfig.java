package com.teran.mutants.infraestructure.shared;


import com.teran.mutants.domain.service.MutantService;

import com.teran.mutants.infraestructure.persistence.DnaSequenceReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Configuration: Util para poder inyectar un bean que no es parte de nuestro código, por ejemplo librerias
 * de terceros y que no podemos anotar esas librerías con @xxx ya que no son de nuestro contexto
 * <p>
 * En este caso se usó para limpiar el domain (implementación clase service ) de uso de framework
 * y así en un futuro poder llevarse ése código en donde sea requerido y  sin acoplamiento al
 * framework
 */
@Configuration
public class AppConfig {

    @Autowired(required=true)
    DnaSequenceReactiveRepository mutantRepository;

    @Bean
    MutantService getMutantService() {
        return new MutantService(this.mutantRepository);
    }


}
