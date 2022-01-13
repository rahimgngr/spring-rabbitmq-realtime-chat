package com.rabbitmq.author;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@ComponentScan(basePackages = "com.rabbitmqchat")
public class HomeController {

    @GetMapping("/")
    public String home(Principal principal) {
        if (principal.getName().equals("anonymousUser"))
            return "Giriş yap.";
        else {
            return principal.getName();
        }
    }

    @GetMapping("/user")
    public String user(Principal principal) {
        return principal.getName();
    }

    @GetMapping("/admin")
    public String admin(Principal principal) {
        return principal.getName();
    }
/*
    @PostMapping("/cur")
    @ResponseBody
    public ResponseEntity<String> getCurrentUser() {
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        RestTemplate restTemplate = new RestTemplate(factory);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity<String> result = restTemplate.postForEntity(url, authentication.getName(), String.class);
        String responseBody = result.getBody();
        return ResponseEntity.ok(responseBody);
    }*/
}
