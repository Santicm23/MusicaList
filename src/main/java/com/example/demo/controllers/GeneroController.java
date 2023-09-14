package com.example.demo.controllers;

import com.example.demo.model.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeneroController {

    @Autowired
    private GeneroRepository generoRepository;

}
