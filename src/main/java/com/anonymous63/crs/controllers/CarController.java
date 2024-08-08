package com.anonymous63.crs.controllers;

import com.anonymous63.crs.dtos.CarDto;
import com.anonymous63.crs.dtos.UserDto;
import com.anonymous63.crs.models.Car;
import com.anonymous63.crs.payloads.responses.APIResponse;
import com.anonymous63.crs.payloads.responses.PageableResponse;
import com.anonymous63.crs.services.CarService;
import com.anonymous63.crs.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/car")
    public ResponseEntity<APIResponse<CarDto>> createCar(@RequestBody CarDto carDTO) {
        CarDto saveCar = this.carService.save(carDTO);
        APIResponse<CarDto> response = APIResponse.<CarDto>builder()
                .status(true)
                .message("Car created successfully")
                .results(saveCar)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PostMapping("/car/{id}")
    public ResponseEntity<APIResponse<CarDto>> getCar(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/cars")
    public ResponseEntity<APIResponse<PageableResponse<CarDto>>> findAllCars(
            @RequestParam(value = "page", defaultValue = Constants.PAGE_NUMBER, required = false) int page,
            @RequestParam(value = "size", defaultValue = Constants.PAGE_SIZE, required = false) int size,
            @RequestParam(value = "sortBy", defaultValue = Constants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = Constants.SORT_DIR, required = false) String sortDir
    ) {
        PageableResponse<CarDto> pageableResponse = this.carService.findAll(page, size, sortBy, sortDir);
        APIResponse<PageableResponse<CarDto>> response = APIResponse.<PageableResponse<CarDto>>builder()
                .status(true)
                .message("Users fetched successfully")
                .results(pageableResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
