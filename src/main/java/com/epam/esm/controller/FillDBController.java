package com.epam.esm.controller;

import com.epam.esm.model.dao.impl.FillDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fill")
public class FillDBController {

    @Autowired
    private FillDao fillDao;

    @PostMapping
    Boolean fill() {
        fillDao.fill();
        return true;
    }
}
