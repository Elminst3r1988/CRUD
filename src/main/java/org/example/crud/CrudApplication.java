package org.example.crud;

import org.example.crud.properties.PropertyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CrudApplication {


    public static void main(String[] args) {

        SpringApplication.run(CrudApplication.class, args);

    }

}


