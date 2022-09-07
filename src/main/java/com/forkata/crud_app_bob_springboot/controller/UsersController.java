package com.forkata.crud_app_bob_springboot.controller;


import com.forkata.crud_app_bob_springboot.model.User;
import com.forkata.crud_app_bob_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class UsersController {
    private final UserService service;

    @Autowired
    public UsersController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String showAllUsers(Model model) {
        model.addAttribute("users", service.listUsers());
        return "users";
    }

    @GetMapping("/new")
    public String createUser(@ModelAttribute("user") User user) {
        return "addUser";
    }

    @PostMapping
    public String addUser (@ModelAttribute("user") User user){
        service.save(user);
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") Long id, Model model){
        model.addAttribute("user", service.userById(id));
        return "editUser";
    }
    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user){
        service.save(user);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteUser (@PathVariable("id")Long id){
        service.delete(id);
        return "redirect:/";
    }






}
