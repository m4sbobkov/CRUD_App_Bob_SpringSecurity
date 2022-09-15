package com.forkata.crud_app_bob_springboot.service;


import com.forkata.crud_app_bob_springboot.model.Role;
import com.forkata.crud_app_bob_springboot.model.User;
import com.forkata.crud_app_bob_springboot.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public List<Role> getRoles(User user) {
        return user.getRoles();
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
    public void create(User user) {

        user.addRole(new Role("ROLE_USER"));
        repository.save(user);

    }

    @Override
    public void makeAdmin(User user) {
        user.addRole(new Role("ROLE_ADMIN"));
        repository.save(user);
    }


    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
