package in.ashokit.ecomm.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        exposeIds(config);
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        Set<Class<?>> domainTypes = entityManager.getMetamodel()
                .getEntities()
                .stream()
                .map(EntityType::getJavaType)
                .collect(Collectors.toSet());
        config.exposeIdsFor(domainTypes.toArray(new Class[0]));
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*"); // allows dynamic frontend URLs
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
