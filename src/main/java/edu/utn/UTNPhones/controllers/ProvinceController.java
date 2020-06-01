package edu.utn.UTNPhones.controllers;

import edu.utn.UTNPhones.domain.Province;
import edu.utn.UTNPhones.exceptions.EmptyListException;
import edu.utn.UTNPhones.exceptions.ParamException;
import edu.utn.UTNPhones.services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/provinces")
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;

    @PostMapping("/")
    public void create(@RequestBody Province province) throws ParamException {
        if(province.getProvinceName() == null) throw new ParamException("Province name cannot be null");
        this.provinceService.create(province);
    }

    @GetMapping("/")
    public List<Province> allProvinces() throws EmptyListException {
        return this.provinceService.getAll();
    }

}
