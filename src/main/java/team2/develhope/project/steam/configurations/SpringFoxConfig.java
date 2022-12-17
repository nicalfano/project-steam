package team2.develhope.project.steam.configurations;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;


@Configuration
public class SpringFoxConfig {

        @Bean
        public Docket api() {
            return new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .apis(RequestHandlerSelectors.any())
                    .paths(PathSelectors.any())
                    .build().apiInfo(new ApiInfo(
                            "Vapore",
                            "Backend di una piattaforma di fruizione di videogiochi, sulla falsa riga della piattaforma più utilizzata al mondo",
                            "1.0.0",
                            "https://it.wikipedia.org/wiki/Licenza_MIT",
                            new Contact("Team2, Java6, Develhope", "https://github.com/nicalfano/project-steam", "nicalfano@hotmail.it"),
                            "MIT", "https://it.wikipedia.org/wiki/Licenza_MIT",
                            Collections.emptyList()
                    ));
        }
    }




