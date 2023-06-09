package com.maurilio.autenticacao.controller;

import com.maurilio.autenticacao.model.Usuario;
import com.maurilio.autenticacao.security.AuthToken;
import com.maurilio.autenticacao.security.TokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/free")
    public String sayFreeHello(){
        return "Este é um end point liberado";
    }

    @GetMapping("/auth")
    public String sayAuthHello(){
        return "Este é um end point que precisa de autenticação";
    }

    @PostMapping("/login")
    public ResponseEntity<AuthToken> login (@RequestBody Usuario usuario){
        if(usuario.getLogin().equals("maurilio") && usuario.getPassword().equals("mteste")){
            return ResponseEntity.ok(TokenUtil.encodeToken(usuario));
        }

        return ResponseEntity.status(403).build();
    }
}
