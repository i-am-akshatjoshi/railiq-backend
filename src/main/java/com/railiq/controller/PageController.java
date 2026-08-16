package com.railiq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
	
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/results")
    public String results() {
        return "results";
    }

    @GetMapping("/pnr-status")
    public String pnrStatus() {
        return "pnr-status";
    }

    @GetMapping("/help")
    public String help() {
        return "help";
    }

    @GetMapping("/booking")
    public String booking() {
        return "booking";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    // --- New: Phase 2 ---
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }
}