package logiweb.service;

import logiweb.converter.DistanceConverter;
import logiweb.dao.api.CityDao;
import logiweb.dao.api.DistanceDao;
import logiweb.dto.DistanceDto;
import logiweb.entity.Distance;
import logiweb.service.api.DistanceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DistanceServiceImpl implements DistanceService {
    private static final Logger logger = Logger.getLogger(DistanceServiceImpl.class);

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
        Distance distance1 = distanceConverter.toEntity(distanceDto);
        distanceDao.create(distance1);

        Distance distance2 = new Distance();
        distance2.setCityFrom(distance1.getCityTo());
        distance2.setCityTo(distance1.getCityFrom());
        distance2.setDistance(distance1.getDistance());
        distanceDao.create(distance2);

        logger.info("Distance is added.");
    }

    @Override
    @Transactional
    public void delete(DistanceDto distanceDto) {
        distanceDao.delete(distanceConverter.toEntity(distanceDto));
        logger.info("Distance is deleted.");
    }

    @Override
    @Transactional
    public void edit(DistanceDto distanceDto) {
        distanceDao.update(distanceConverter.toEntity(distanceDto));
        logger.info("Distance is updeted.");
    }

    @Override
    public DistanceDto getById(int id) {
        return distanceConverter.toDto(distanceDao.getById(id));
    }

    @Override
    public DistanceDto getDistance(String cityFrom, String cityTo) {
        Distance distance = distanceDao.get(cityFrom, cityTo);
        return distance == null ? null : distanceConverter.toDto(distance);
    }
}
