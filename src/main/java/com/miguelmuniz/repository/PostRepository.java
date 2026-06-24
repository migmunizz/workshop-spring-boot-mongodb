package com.miguelmuniz.repository;

import com.miguelmuniz.domain.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post,String> {

    Page<Post> findByTitleContainingIgnoreCase(String text,Pageable pageable);

    @Query("{ 'title': { $regex: ?0, $options: 'i' } }")
    Page<Post> findByTitle(String text,Pageable pageable);

    @Query("{ $and: [ {date: {$gte: ?1} }, {date: {$lte: ?2} }, { $or: [ { 'title': { $regex: ?0, $options: 'i' } }, { 'body': { $regex: ?0, $options: 'i' } }, { 'comments.text': { $regex: ?0, $options: 'i' } } ] } ] }")
    Page<Post> fullSearch(String text, Instant minDate, Instant maxDate, Pageable pageable);
}
