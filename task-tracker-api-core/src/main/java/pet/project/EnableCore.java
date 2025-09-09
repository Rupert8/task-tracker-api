package pet.project;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan("pet.project")
@Import({
        EnableStore.class
})
@Configuration
public class EnableCore {
}
