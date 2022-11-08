package com.forkata.crud_app_bob_springboot.service;


import com.forkata.crud_app_bob_springboot.model.User;
import com.forkata.crud_app_bob_springboot.repositories.RolesRepository;
import com.forkata.crud_app_bob_springboot.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RolesRepository rolesRepository;


    @Autowired
    public UserServiceImpl(UsersRepository repository, PasswordEncoder passwordEncoder, RolesRepository rolesRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
    }

    @Override

    public List<User> listUsers() {
        return repository.findAll();
    }

    @Override

    public User userById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public void save(User user) {
        repository.save(user);
    }

    @Override
    public void create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRoles().isEmpty()) {
            user.addRole(rolesRepository.getReferenceById(2));
        }
        repository.save(user);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }


}
