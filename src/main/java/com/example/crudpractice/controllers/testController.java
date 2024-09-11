package com.example.crudpractice.controllers;

import com.example.crudpractice.services.AdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class testController {
    private final AdService adService;

}
