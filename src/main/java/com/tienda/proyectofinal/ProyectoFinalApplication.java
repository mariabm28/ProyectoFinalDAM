package com.tienda.proyectofinal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.io.IOException;

@SpringBootApplication
public class ProyectoFinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProyectoFinalApplication.class, args);
    }

    @Autowired
    private DataSource dataSource;

    @Bean
    public CommandLineRunner verificarBD() {
        return args -> {
            System.out.println("URL de conexión a BD: " + dataSource.getConnection().getMetaData().getURL());
        };
    }
}
