package com.berkson.wish.wishlist.bdd;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created By : Berkson Ximenes
 * Date : 20/01/2026
 * 
 * Configuração para usar o MongoDB que já está rodando via Docker Compose.
 * Certifique-se de que o MongoDB esteja rodando: docker compose up -d
 */
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberSpringConfig {
}
