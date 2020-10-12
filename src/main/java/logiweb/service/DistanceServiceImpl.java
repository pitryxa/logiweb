package logiweb.service;

import logiweb.converter.DistanceConverter;
import logiweb.dao.api.DistanceDao;
import logiweb.dto.DistanceDto;
import logiweb.service.api.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DistanceServiceImpl implements DistanceService {

    @Autowired
    private DistanceDao distanceDao;

    @Autowired
    private DistanceConverter distanceConverter;

    @Override
    public List<DistanceDto> getAll() {
        return distanceConverter.toListDto(distanceDao.getAll());
    }

    @Override
    @Transactional
    public void add(DistanceDto distanceDto) {
        distanceDao.create(distanceConverter.toEntity(distanceDto));
    }

    @Override
    @Transactional
    public void delete(DistanceDto distanceDto) {
        distanceDao.delete(distanceConverter.toEntity(distanceDto));
    }

    @Override
    @Transactional
    public void edit(DistanceDto distanceDto) {
        distanceDao.update(distanceConverter.toEntity(distanceDto));
    }

    @Override
    public DistanceDto getById(int id) {
        return distanceConverter.toDto(distanceDao.getById(id));
    }
}
