package com.example.demo.controllers;

import com.example.demo.model.CancionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CancionController {

    @Autowired
    private CancionRepository cancionRepository;
}
