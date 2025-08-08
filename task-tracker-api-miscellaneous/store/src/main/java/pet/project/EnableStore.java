package pet.project;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan("pet.project")
@EnableJpaRepositories("pet.project.jpaRepositories")
@EntityScan("pet.project.entities")
public class EnableStore {
}
