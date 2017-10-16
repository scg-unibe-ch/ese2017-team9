package hello;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "hello")
@EntityScan(basePackages = "hello")
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    /*@Bean
    public CommandLineRunner demo(UserRepository repository){
        return (args) -> {
            repository.save(new User("manu", "Manuela", "Eschler", "manu@email.ch", "manu"));
        };
    }*/
}
