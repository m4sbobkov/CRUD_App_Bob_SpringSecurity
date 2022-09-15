package com.forkata.crud_app_bob_springboot.controller;


import com.forkata.crud_app_bob_springboot.model.User;
import com.forkata.crud_app_bob_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AdminController {
    private final UserService service;
    private final PasswordEncoder passwordEncoder;



    @Autowired
    public AdminController(UserService service, PasswordEncoder passwordEncoder) {
        this.service = service;

        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String homePage(){
        return "home";
    }

    @GetMapping("/admin/users")
    public String showAllUsers(Model model) {
        model.addAttribute("users", service.listUsers());
        return "users";
    }

    @GetMapping("/admin/new")
    public String createUser(@ModelAttribute("user") User user) {
        return "addUser";
    }

    @PostMapping
    public String addUser (@ModelAttribute("user") User user){
        String pass = passwordEncoder.encode(user.getPassword());
        user.setPassword(pass);
        service.create(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/user/{id}")
    public String userInfo(@PathVariable("id") Long id, Model model){
        model.addAttribute("user", service.userById(id));

        return "user";
    }

    @PatchMapping("/admin/{id}/makeadmin")
    public String makeAdmin(@PathVariable("id") Long id){
        service.makeAdmin(service.userById(id));
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/{id}/edit")
    public String editUser(@PathVariable("id") Long id, Model model){
        model.addAttribute("user", service.userById(id));
        return "editUser";
    }
    @PatchMapping("/admin/{id}")
    public String updateUser(@ModelAttribute("user") User user){
        service.save(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/admin/delete/{id}")
    public String deleteUser (@PathVariable("id")Long id){
        service.delete(id);
        return "redirect:/admin/users";
    }







}
