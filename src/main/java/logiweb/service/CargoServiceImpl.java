package logiweb.service;

import logiweb.converter.CargoConverter;
import logiweb.dao.CargoDao;
import logiweb.dto.CargoDTO;
import logiweb.entity.Cargo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
public class CargoServiceImpl implements CargoService{

    private CargoDao cargoDao;

    @Autowired
    public void setCargoDao(CargoDao cargoDao) {
        this.cargoDao = cargoDao;
    }

    @Override
    @Transactional
    public List<CargoDTO> allCargo() {
        return createCargoDtoListFromCargoList(cargoDao.getAll());
    }

    @Override
    @Transactional
    public void add(CargoDTO cargoDTO) throws UnsupportedEncodingException {
        convertCharset(cargoDTO);
        cargoDao.create(CargoConverter.toEntity(cargoDTO));
        System.out.println(cargoDTO.getName());
    }

    @Override
    @Transactional
    public void delete(CargoDTO cargoDTO) {
        cargoDao.delete(CargoConverter.toEntity(cargoDTO));
    }

    @Override
    @Transactional
    public void edit(CargoDTO cargoDTO) throws UnsupportedEncodingException {
        convertCharset(cargoDTO);
        cargoDao.update(CargoConverter.toEntity(cargoDTO));
    }

    @Override
    @Transactional
    public CargoDTO getById(int id) {
        return CargoConverter.toDto(cargoDao.getById(id));
    }

    public static List<CargoDTO> createCargoDtoListFromCargoList(List<Cargo> cargoList) {
        List<CargoDTO> cargoDTOList = new ArrayList<>();

        for (Cargo cargo : cargoList) {
            cargoDTOList.add(CargoConverter.toDto(cargo));
        }

        return cargoDTOList;
    }

    public static void convertCharset(CargoDTO cargoDTO) throws UnsupportedEncodingException {
        cargoDTO.setName(new String(cargoDTO.getName().getBytes("ISO-8859-1"), Charset.forName("UTF-8")));
    }
}
