package com.rtrn.controller;

import com.rtrn.model.Item;
import com.rtrn.model.User;
import com.rtrn.service.RtrnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class RtrnController {
    @Autowired
    private RtrnService service;

    private User currentUser; // simplistic session substitute

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("user", currentUser);
        return "index";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String email, @RequestParam(defaultValue="false") boolean premium, Model model) {
        currentUser = service.register(name, email, premium);
        model.addAttribute("user", currentUser);
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("user", currentUser);
        model.addAttribute("items", currentUser != null ? service.getUserItems(currentUser) : null);
        return "dashboard";
    }

    @PostMapping("/items")
    public String addItem(@RequestParam String name,
                          @RequestParam String description,
                          @RequestParam String photoUrl,
                          @RequestParam String contactInfo,
                          Model model) {
        if (currentUser == null) return "redirect:/";
        service.addItem(currentUser, name, description, photoUrl, contactInfo);
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
