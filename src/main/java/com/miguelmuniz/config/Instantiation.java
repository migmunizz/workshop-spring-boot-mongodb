package com.miguelmuniz.config;

import com.miguelmuniz.domain.Post;
import com.miguelmuniz.domain.User;
import com.miguelmuniz.dto.AuthorDTO;
import com.miguelmuniz.dto.CommentDTO;
import com.miguelmuniz.repository.PostRepository;
import com.miguelmuniz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

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

        Post post1 = new Post(null, Instant.parse("2018-03-21T00:00:00Z"),"Partiu Viagem", "Vou viajar para São paulo",new AuthorDTO(maria));
        Post post2 = new Post(null, Instant.parse("2018-04-23T00:00:00Z").plusSeconds(60),"Bom dia", "Acordei Feliz Hoje",new AuthorDTO(maria));

        CommentDTO c1 = new CommentDTO("Boa viagem mano",Instant.parse("2018-03-22T00:00:00Z"),new AuthorDTO(alex));
        CommentDTO c2 = new CommentDTO("Aproveite",Instant.parse("2018-04-10T00:00:00Z"),new AuthorDTO(bob));
        CommentDTO c3 = new CommentDTO("Tenha um otimo dia",Instant.parse("2018-05-15T00:00:00Z"),new AuthorDTO(alex));

        post1.getComments().addAll(Arrays.asList(c1,c2));
        post2.getComments().add(c3);

        postRepository.saveAll(Arrays.asList(post1,post2));

        maria.getPosts().addAll(Arrays.asList(post1,post2));
        userRepository.save(maria);


    }
}
