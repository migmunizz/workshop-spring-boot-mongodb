package com.miguelmuniz.services;

import com.miguelmuniz.domain.User;
import com.miguelmuniz.dto.UserDTO;
import com.miguelmuniz.repository.UserRepository;
import com.miguelmuniz.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public Page<UserDTO> findAll(Pageable pageable) {
        Page<User> page = repo.findAll(pageable);
        return page.map(UserDTO::new);
    }

    public User findById(String id) {
        Optional<User> user = repo.findById(id);
        return user.orElseThrow(() ->
                new ObjectNotFoundException("Object not found"));
    }

    public User insert(User obj){
        return repo.insert(obj);
    }

    public void delete(String id){
        findById(id);
        repo.deleteById(id);
    }

    public User update(User obj){
        User newObj = findById(obj.getId());
        updateData(newObj,obj);
        return repo.save(newObj);
    }

    private void updateData(User newObj, User obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
    }

    public User fromDTO(UserDTO objDTO){
        return new User(objDTO.id(),objDTO.name(),objDTO.email());
    }


}
