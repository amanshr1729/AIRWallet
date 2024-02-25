package com.example.wallet_service.Service;

import com.example.wallet_service.Model.User;
import com.example.wallet_service.Model.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

WebClient webClient = WebClient.create();

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<User> getAllUsers(){
        Flux<User> response = webClient.get().uri("http://127.0.0.1:8080/users").retrieve().bodyToFlux(User.class);
        List<User> users = response.collectList().block();
        return users;


    }

    public User getUserById(String userId){
        Mono<User> response= webClient.get().uri("http://127.0.0.1:8080/users/"+userId).retrieve().bodyToMono(User.class);
        return (User) Objects.requireNonNull(response.block());
    }
}
