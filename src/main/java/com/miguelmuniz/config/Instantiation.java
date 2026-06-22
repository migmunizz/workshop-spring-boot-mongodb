package com.miguelmuniz.config;

import com.miguelmuniz.domain.Post;
import com.miguelmuniz.domain.User;
import com.miguelmuniz.dto.AuthorDTO;
import com.miguelmuniz.repository.PostRepository;
import com.miguelmuniz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        userRepository.deleteAll();
        postRepository.deleteAll();

        User maria = new User(null, "Maria Brown", "maria@gmail.com");
        User alex = new User(null, "Alex Green", "alex@gmail.com");
        User bob = new User(null, "Bob Grey", "bob@gmail.com");

        userRepository.saveAll(Arrays.asList(maria, alex, bob));

        Post post1 = new Post(null, Instant.now(),"Partiu Viagem", "Vou viajar para São paulo",new AuthorDTO(maria));
        Post post2 = new Post(null, Instant.now().plusSeconds(60),"Bom dia", "Acordei Feliz Hoje",new AuthorDTO(maria));


        postRepository.saveAll(Arrays.asList(post1,post2));


    }
}
