package rs.raf.mjovanovic40.rag_internal_knowledge_base.config.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    /**
     * Creates and configures a {@link ModelMapper} bean.
     * <p>
     * This bean provides an object mapping utility that simplifies
     * mapping DTOs to entities and vice versa throughout the application.
     *
     * @return a configured {@link ModelMapper} instance
     *
     * @author Milan Jovanovic
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
