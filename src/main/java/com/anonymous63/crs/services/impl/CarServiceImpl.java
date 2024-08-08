package com.anonymous63.crs.services.impl;

import com.anonymous63.crs.dtos.CarDto;
import com.anonymous63.crs.exceptions.DuplicateResourceException;
import com.anonymous63.crs.models.Car;
import com.anonymous63.crs.payloads.responses.PageableResponse;
import com.anonymous63.crs.repositories.CarRepo;
import com.anonymous63.crs.services.CarService;
import com.anonymous63.crs.utils.CustomeDtoNameResolver;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
            throw new DuplicateResourceException(CustomeDtoNameResolver.resolve(CarDto.class), "id", carDto.getId());
        }
        Car car = this.modelMapper.map(carDto, Car.class);
        Car saveCar = this.carRepo.save(car);
        return this.modelMapper.map(saveCar, CarDto.class);
    }

    @Override
    public CarDto update(CarDto carDto) {
        Car car = this.carRepo.findById(carDto.getId()).orElseThrow(() -> new DuplicateResourceException(CustomeDtoNameResolver.resolve(CarDto.class), "id", carDto.getId()));
        this.modelMapper.map(carDto, Car.class);
        Car saveCar = this.carRepo.save(car);
        return this.modelMapper.map(saveCar, CarDto.class);
    }

    @Override
    public void delete(Long id) {
        Car car = this.carRepo.findById(id).orElseThrow(() -> new DuplicateResourceException(CustomeDtoNameResolver.resolve(CarDto.class), "id", id));
        this.carRepo.delete(car);

    }

    @Override
    public CarDto findById(Long id) {
        Car car = this.carRepo.findById(id).orElseThrow(() -> new DuplicateResourceException(CustomeDtoNameResolver.resolve(CarDto.class), "id", id));
        return this.modelMapper.map(car, CarDto.class);
    }

    @Override
    public PageableResponse<CarDto> findAll(Integer page, Integer size, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<Car> carPage = this.carRepo.findAll(pageable);
        List<CarDto> carDtos = carPage.getContent().stream().map(car -> this.modelMapper.map(car, CarDto.class))
                .toList();
        return PageableResponse.<CarDto>builder()
                .entities(carDtos)
                .totalPages(carPage.getTotalPages())
                .totalElements(carPage.getTotalElements())
                .currentPage(carPage.getNumber())
                .itemsPerPage(carPage.getSize())
                .isEmpty(carPage.isEmpty())
                .hasNext(carPage.hasNext())
                .hasPrevious(carPage.hasPrevious())
                .isFirst(carPage.isFirst())
                .isLast(carPage.isLast())
                .build();
    }

    @Override
    public List<CarDto> disable(List<Long> ids) {
        List<Car> cars = this.carRepo.findAllById(ids);
        cars.forEach(car -> {
            if (ids.contains(car.getId())) {
                car.setEnabled(false);
            }
        });
        List<Car> disabledUsers = this.carRepo.saveAll(cars);
        return disabledUsers.stream().map(car -> this.modelMapper.map(car, CarDto.class)).toList();
    }

    @Override
    public List<CarDto> enable(List<Long> ids) {
        List<Car> cars = this.carRepo.findAllById(ids);
        cars.forEach(car -> {
            if (ids.contains(car.getId())) {
                car.setEnabled(true);
            }
        });
        List<Car> enabledUsers = this.carRepo.saveAll(cars);
        return enabledUsers.stream().map(car -> this.modelMapper.map(car, CarDto.class)).toList();
    }

    @Override
    public String getEntityName() {
        return Car.class.getSimpleName();
    }
}
