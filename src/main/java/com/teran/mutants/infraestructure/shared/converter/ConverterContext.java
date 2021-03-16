package com.teran.mutants.infraestructure.shared.converter;


import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;


@org.springframework.context.annotation.Configuration
public class ConverterContext {

    @Bean
    @Scope("singleton")
    ModelMapper getMapper()
    {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setCollectionsMergeEnabled(true)
                .setImplicitMappingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        return modelMapper;
    }

}