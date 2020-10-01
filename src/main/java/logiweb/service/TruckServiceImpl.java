package logiweb.service;

import logiweb.converter.TruckConverter;
import logiweb.dao.api.TruckDao;
import logiweb.dto.TruckDto;
import logiweb.service.api.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TruckServiceImpl implements TruckService {

    @Autowired
    private TruckDao truckDao;

    @Autowired
    private TruckConverter truckConverter;

    @Override
    public List<TruckDto> getAll() {
        return truckConverter.toListDto(truckDao.getAllSorted());
    }

    @Override
    @Transactional
    public void add(TruckDto truckDto) {
        truckDao.create(truckConverter.toEntity(truckDto));
    }

    @Override
    @Transactional
    public void delete(TruckDto truckDto) {
        truckDao.delete(truckConverter.toEntity(truckDto));
    }

    @Override
    @Transactional
    public void edit(TruckDto truckDto) {
        truckDao.update(truckConverter.toEntity(truckDto));
    }

    @Override
    public TruckDto getById(int id) {
        return truckConverter.toDto(truckDao.getById(id));
    }
}
