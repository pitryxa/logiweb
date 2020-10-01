package logiweb.service;

import logiweb.converter.DriverConverter;
import logiweb.dao.api.DriverDao;
import logiweb.dto.DriverDto;
import logiweb.service.api.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverDao driverDao;

    @Autowired
    private DriverConverter driverConverter;

    @Override
    public List<DriverDto> getAll() {
        return driverConverter.toListDto(driverDao.getAllSorted());
    }

    @Override
    @Transactional
    public void add(DriverDto driverDto) {
        driverDao.create(driverConverter.toEntity(driverDto));
    }

    @Override
    @Transactional
    public void delete(DriverDto driverDto) {
        driverDao.delete(driverConverter.toEntity(driverDto));
    }

    @Override
    @Transactional
    public void edit(DriverDto driverDto) {
        driverDao.update(driverConverter.toEntity(driverDto));
    }

    @Override
    public DriverDto getById(int id) {
        return driverConverter.toDto(driverDao.getById(id));
    }

    @Override
    public LocalDateTime getTimeLastChangeStatusById(int id) {
        return null;
    }
}
