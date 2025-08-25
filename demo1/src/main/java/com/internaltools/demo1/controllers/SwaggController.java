package com.internaltools.demo1.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwaggController {

    @Operation(
            summary = "Salut ! Salut !",
            description = "Retourne un simple message pour tester l'intégration Swagger"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message renvoyé avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/salut")
    public String salutSwagg() {
        return " Swagger fonctionne, ouff !";
    }
}
