package edu.utn.UTNPhones.controllers.domain;

import edu.utn.UTNPhones.domain.Province;
import edu.utn.UTNPhones.exceptions.EmptyListException;
import edu.utn.UTNPhones.exceptions.ParamException;
import edu.utn.UTNPhones.services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;

    public void create(Province province) throws ParamException {
        if(province.getProvinceName() == null) throw new ParamException("Province name cannot be null");
        this.provinceService.create(province);
    }

    public List<Province> allProvinces() throws EmptyListException {
        return this.provinceService.getAll();
    }

}
