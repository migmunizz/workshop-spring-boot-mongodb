package com.miguelmuniz.resources;

import com.miguelmuniz.domain.Post;
import com.miguelmuniz.domain.User;
import com.miguelmuniz.dto.UserDTO;
import com.miguelmuniz.resources.util.URL;
import com.miguelmuniz.services.PostService;
import com.miguelmuniz.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {


    @Autowired
    private PostService service;

    @GetMapping
    public ResponseEntity<Page<Post>> findAll(Pageable pageable){
        Page<Post> page = service.findAll(pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Post> findById(@PathVariable String id){
        Post obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/titlesearch")
    public ResponseEntity<Page<Post>> findByTitle(@RequestParam(value = "text",defaultValue = "") String text, Pageable pageable){
        text = URL.decodeParam(text);
        Page<Post> page = service.findByTitle(text,pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/fullsearch")
    public ResponseEntity<Page<Post>> fullSearch(
            @RequestParam(value = "text", defaultValue = "") String text,
            @RequestParam(value = "minDate", defaultValue = "") String minDate,
            @RequestParam(value = "maxDate", defaultValue = "") String maxDate, Pageable pageable) {

        text = URL.decodeParam(text);

        Instant min = URL.convertDate(minDate, Instant.EPOCH);

        Instant max = URL.convertDate(
                maxDate,
                Instant.now().atZone(ZoneId.systemDefault()).plusDays(1).toInstant()
        );

        Page<Post> page = service.fullSearch(text, min, max,pageable);

        return ResponseEntity.ok().body(page);
    }





}
