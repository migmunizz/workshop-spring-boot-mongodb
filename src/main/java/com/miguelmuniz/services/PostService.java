package com.miguelmuniz.services;

import com.miguelmuniz.domain.Post;
import com.miguelmuniz.domain.User;
import com.miguelmuniz.dto.UserDTO;
import com.miguelmuniz.repository.PostRepository;
import com.miguelmuniz.repository.UserRepository;
import com.miguelmuniz.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository repo;

    public List<Post> findAll(){
        return repo.findAll();
    }

    public Post findById(String id) {
        Optional<Post> post = repo.findById(id);
        return post.orElseThrow(() ->
                new ObjectNotFoundException("Object not found"));
    }


}
