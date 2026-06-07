package br.com.routeworker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"br.com.routeworker", "br.com.airouteoptimizercore.domain"})
@EnableJpaRepositories(basePackages = {"br.com.routeworker.repository"})
public class RouteWorkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RouteWorkerApplication.class, args);
    }

}
