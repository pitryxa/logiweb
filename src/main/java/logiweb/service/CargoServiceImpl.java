package logiweb.service;

import logiweb.converter.CargoConverter;
import logiweb.dao.api.CargoDao;
import logiweb.dto.CargoDto;
import logiweb.entity.Cargo;
import logiweb.service.api.CargoService;
import logiweb.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
public class CargoServiceImpl implements CargoService {

    @Autowired
    private CargoDao cargoDao;

    @Autowired
    private CargoConverter cargoConverter;

    @Override
    @Transactional
    public List<CargoDto> getAll() {
        return this.createCargoDtoListFromCargoList(cargoDao.getAllSorted());
    }

    @Override
    @Transactional
    public void add(CargoDto cargoDTO) {
        cargoDao.create(cargoConverter.toEntity(cargoDTO));
    }

    @Override
    @Transactional
    public void delete(CargoDto cargoDTO) {
        cargoDao.delete(cargoConverter.toEntity(cargoDTO));
    }

    @Override
    @Transactional
    public void edit(CargoDto cargoDTO) {
        cargoDao.update(cargoConverter.toEntity(cargoDTO));
    }

    @Override
    @Transactional
    public CargoDto getById(int id) {
        return cargoConverter.toDto(cargoDao.getById(id));
    }

    public List<CargoDto> createCargoDtoListFromCargoList(List<Cargo> cargoList) {
        List<CargoDto> cargoDtoList = new ArrayList<>();

        for (Cargo cargo : cargoList) {
            cargoDtoList.add(cargoConverter.toDto(cargo));
        }

        return cargoDtoList;
    }
}
