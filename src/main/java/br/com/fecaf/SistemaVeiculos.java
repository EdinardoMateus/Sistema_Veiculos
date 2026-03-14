package br.com.fecaf;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SistemaVeiculos {

    public static void main(String[] args) {
        SpringApplication.run(SistemaVeiculos.class, args);
        }
        @Bean
        CommandLineRunner initialization () {
            return args -> {
                System.out.println("---------   Seja Bem Vindo   ---------");
                System.out.println("       O Servidor está no ar !!!      ");
                System.out.println("     UniFECAF Edinardo Ra:136944 !!!  ");
                System.out.println("--------------------------------------");
            };
        }
}

