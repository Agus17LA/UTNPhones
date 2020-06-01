package edu.utn.UTNPhones.services;

import edu.utn.UTNPhones.domain.Province;
import edu.utn.UTNPhones.exceptions.NotExistException;
import edu.utn.UTNPhones.repositories.IProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProvinceService {
    @Autowired
    private IProvinceRepository provinceRepository;

    public Province create(Province province) throws DataAccessException {
        return this.provinceRepository.save(province);
    }

    public List<Province> getAll() throws DataAccessException{
        return this.provinceRepository.findAll();
    }

    public void update(Province province) throws DataAccessException,NotExistException {
        Province provinceToUpdate = this.provinceRepository.getOne(province.getId());
        if(provinceToUpdate!=null){
            provinceToUpdate.setProvinceName(province.getProvinceName());
            this.provinceRepository.save(provinceToUpdate);
        }else{
            throw new NotExistException("Province not exists");
        }
    }

    public void delete(Province province) throws DataAccessException, NotExistException {
        if(province != null) this.provinceRepository.delete(province);
        else throw new NotExistException("Province not exists");
    }

}
