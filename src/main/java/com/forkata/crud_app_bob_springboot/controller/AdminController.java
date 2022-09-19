package com.forkata.crud_app_bob_springboot.controller;


import com.forkata.crud_app_bob_springboot.model.Role;
import com.forkata.crud_app_bob_springboot.model.User;
import com.forkata.crud_app_bob_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService service;


    @Autowired
    public AdminController(UserService service) {
        this.service = service;

    }


    @GetMapping("/users")
    public String showAllUsers(Model model) {
        model.addAttribute("users", service.listUsers());
        return "users";
    }

    @GetMapping("/new")
    public String createUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", Role.values());
        return "addUser";
    }

    @PostMapping
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam Set<Role> roles) {
        user.setRoles(roles);
        service.create(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/user/{id}")
    public String userInfo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", service.userById(id));

        return "user";
    }

    @PatchMapping("/{id}/makeadmin")
    public String makeAdmin(@PathVariable("id") Long id) {
        service.makeAdmin(service.userById(id));
        return "redirect:/admin/users";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", service.userById(id));
        model.addAttribute("roles", Role.values());
        return "editUser";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam Set<Role> roles) {
        user.setRoles(roles);
        service.save(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/admin/users";
    }


}
