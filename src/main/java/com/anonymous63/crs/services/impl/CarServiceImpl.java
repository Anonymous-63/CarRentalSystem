package com.anonymous63.crs.services.impl;

import com.anonymous63.crs.dtos.CarDto;
import com.anonymous63.crs.models.Car;
import com.anonymous63.crs.payloads.responses.PageableResponse;
import com.anonymous63.crs.repositories.CarRepo;
import com.anonymous63.crs.services.CarService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepo carRepo;
    private final ModelMapper modelMapper;

    public CarServiceImpl(CarRepo carRepo, ModelMapper modelMapper) {
        this.carRepo = carRepo;
        this.modelMapper = modelMapper;
    }


    @Override
    public CarDto save(CarDto carDto) {
        if (this.carRepo.findById(carDto.getId()).isPresent()) {
            throw new IllegalArgumentException("Car already exists with id: " + carDto.getId());
        }
        Car car = this.modelMapper.map(carDto, Car.class);
        Car saveCar = this.carRepo.save(car);
        return this.modelMapper.map(saveCar, CarDto.class);
    }

    @Override
    public CarDto update(CarDto carDto) {
        return null;
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Not implemented yet");

    }

    @Override
    public CarDto findById(Long id) {
        return null;
    }

    @Override
    public PageableResponse<CarDto> findAll(Integer page, Integer size, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public List<CarDto> disable(List<Long> ids) {
        return List.of();
    }

    @Override
    public List<CarDto> enable(List<Long> ids) {
        return List.of();
    }

    @Override
    public String getEntityName() {
        return "";
    }
}
