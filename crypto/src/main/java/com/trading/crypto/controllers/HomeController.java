package com.trading.crypto.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

	@GetMapping
	public String home() {
		return "Welcome to our Trading Platform";
	}

	@GetMapping("/api")
	public String secure() {
		return "welcome to treading platform secure";
	}
}
