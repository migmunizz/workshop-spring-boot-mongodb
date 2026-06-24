package com.miguelmuniz.services;

import com.miguelmuniz.domain.Post;
import com.miguelmuniz.domain.User;
import com.miguelmuniz.dto.UserDTO;
import com.miguelmuniz.repository.PostRepository;
import com.miguelmuniz.repository.UserRepository;
import com.miguelmuniz.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository repo;

    public Page<Post> findAll(Pageable pageable){
        return repo.findAll(pageable);
    }

    public Post findById(String id) {
        Optional<Post> post = repo.findById(id);
        return post.orElseThrow(() ->
                new ObjectNotFoundException("Object not found"));
    }

    public Page<Post> findByTitle(String text,Pageable pageable){
        return repo.findByTitle(text,pageable);
    }

    public Page<Post> fullSearch(String text, Instant minDate, Instant maxDate,Pageable pageable){
        return repo.fullSearch(text,minDate,maxDate,pageable);


    }

}
