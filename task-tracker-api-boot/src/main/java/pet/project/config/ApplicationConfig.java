package pet.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pet.project.EnableCore;
import pet.project.EnableData;
import pet.project.EnableStore;
import pet.project.EnableWeb;

@Import({
        EnableStore.class,
        EnableCore.class,
        EnableData.class,
        EnableWeb.class,
})
@Configuration
public class ApplicationConfig {
}
