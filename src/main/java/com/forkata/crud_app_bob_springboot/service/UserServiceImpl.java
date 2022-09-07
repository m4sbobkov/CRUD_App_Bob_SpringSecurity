package com.forkata.crud_app_bob_springboot.service;


import com.forkata.crud_app_bob_springboot.model.User;
import com.forkata.crud_app_bob_springboot.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository repository;

    @Autowired
    public UserServiceImpl(UsersRepository repository) {
        this.repository = repository;
    }


    @Override

    public List<User> listUsers() {
        return repository.findAll();
    }

    @Override

    public User userById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void save(User user) {
        repository.save(user);
    }


    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
