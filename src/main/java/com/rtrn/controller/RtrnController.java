package com.rtrn.controller;

import com.rtrn.model.Item;
import com.rtrn.model.User;
import com.rtrn.service.RtrnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class RtrnController {
    @Autowired
    private RtrnService service;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam(defaultValue="false") boolean premium) {
        service.register(name, email, password, premium);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("items", user != null ? service.getUserItems(user) : null);
        return "dashboard";
    }

    @PostMapping("/items")
    public String addItem(@AuthenticationPrincipal User user,
                          @RequestParam String name,
                          @RequestParam String description,
                          @RequestParam String photoUrl,
                          @RequestParam String contactInfo) {
        if (user == null) return "redirect:/";
        service.addItem(user, name, description, photoUrl, contactInfo);
        return "redirect:/dashboard";
    }

    @GetMapping("/item/{id}")
    public String viewItem(@PathVariable Long id, Model model) {
        Item item = service.getItem(id);
        model.addAttribute("item", item);
        return "item";
    }

    @GetMapping("/item/{id}/qr")
    @ResponseBody
    public byte[] qr(@PathVariable Long id) throws IOException {
        try {
            String base64 = service.generateQrCode(id);
            return java.util.Base64.getDecoder().decode(base64);
        } catch (Exception e) {
            return new byte[0];
        }
    }
}
