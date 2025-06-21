package net.fatima.bdccensetspringmvc;

import net.fatima.bdccensetspringmvc.entities.Product;
import net.fatima.bdccensetspringmvc.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication
public class BdccEnsetSpringMvcApplication {

    public static void main(String[] args) {

        SpringApplication.run(BdccEnsetSpringMvcApplication.class, args);
    }

    @Bean
    public CommandLineRunner start(ProductRepository productRepository) {
        return args -> {
            productRepository.save(Product.builder()
                    .name("computer")
                    .price(100.0)
                    .quantity(12)
                    .build());
            productRepository.save(Product.builder()
                    .name("Printer")
                    .price(1299)
                    .quantity(5)
                    .build());
            productRepository.save(Product.builder()
                    .name("Smart Phone")
                    .price(1276)
                    .quantity(55)
                    .build());
            productRepository.findAll().forEach(p ->{
                System.out.println(p.toString());
            });
        };
    }

}
