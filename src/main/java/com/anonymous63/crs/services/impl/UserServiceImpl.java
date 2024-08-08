package com.anonymous63.crs.services.impl;

import com.anonymous63.crs.dtos.UserDto;
import com.anonymous63.crs.exceptions.DuplicateResourceException;
import com.anonymous63.crs.exceptions.ResourceNotFoundException;
import com.anonymous63.crs.models.Role;
import com.anonymous63.crs.models.User;
import com.anonymous63.crs.models.embeddables.Address;
import com.anonymous63.crs.payloads.responses.PageableResponse;
import com.anonymous63.crs.repositories.RoleRepo;
import com.anonymous63.crs.repositories.UserRepo;
import com.anonymous63.crs.services.UserService;
import com.anonymous63.crs.utils.Constants;
import com.anonymous63.crs.utils.CustomeDtoNameResolver;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final RoleRepo roleRepo;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, ModelMapper modelMapper, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.roleRepo = roleRepo;
    }

    @Override
    public UserDto save(UserDto userDTO) {
        if (this.userRepo.findById(userDTO.getId()).isPresent()) {
            throw new DuplicateResourceException(CustomeDtoNameResolver.resolve(UserDto.class), "id", userDTO.getId());
        }
        User user = this.modelMapper.map(userDTO, User.class);
        User saveUser = this.userRepo.save(user);
        return this.modelMapper.map(saveUser, UserDto.class);
    }

    @Override
    public UserDto update(UserDto userDTO) {
        User user = this.userRepo.findById(userDTO.getId()).orElseThrow(() -> new ResourceNotFoundException(CustomeDtoNameResolver.resolve(UserDto.class), "id", userDTO.getId()));
        this.modelMapper.map(userDTO, User.class);
        User saveUser = this.userRepo.save(user);
        return this.modelMapper.map(saveUser, UserDto.class);
    }

    @Override
    public void delete(Long id) {
        User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(CustomeDtoNameResolver.resolve(UserDto.class), "id", id));
        this.userRepo.delete(user);
    }

    @Override
    public UserDto findById(Long id) {
        User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(CustomeDtoNameResolver.resolve(UserDto.class), "id", id));
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public PageableResponse<UserDto> findAll(Integer page, Integer size, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<User> userPage = this.userRepo.findAll(pageable);
        List<UserDto> userDTOS = userPage.getContent().stream().map(user -> this.modelMapper.map(user, UserDto.class))
                .toList();
        return PageableResponse.<UserDto>builder()
                .entities(userDTOS)
                .totalPages(userPage.getTotalPages())
                .totalElements(userPage.getTotalElements())
                .currentPage(userPage.getNumber())
                .itemsPerPage(userPage.getSize())
                .isEmpty(userPage.isEmpty())
                .hasNext(userPage.hasNext())
                .hasPrevious(userPage.hasPrevious())
                .isFirst(userPage.isFirst())
                .isLast(userPage.isLast())
                .build();
    }

    @Override
    public List<UserDto> disable(List<Long> ids) {
        List<User> users = this.userRepo.findAllById(ids);
        users.forEach(user -> {
            if (ids.contains(user.getId())) {
                user.setEnabled(false);
            }
        });
        List<User> disabledUsers = this.userRepo.saveAll(users);
        return disabledUsers.stream().map(user -> this.modelMapper.map(user, UserDto.class)).toList();
    }

    @Override
    public List<UserDto> enable(List<Long> ids) {
        List<User> users = this.userRepo.findAllById(ids);
        users.forEach(user -> {
            if (ids.contains(user.getId())) {
                user.setEnabled(true);
            }
        });
        List<User> enabledUsers = this.userRepo.saveAll(users);
        return enabledUsers.stream().map(user -> this.modelMapper.map(user, UserDto.class)).toList();
    }

    @Override
    public String getEntityName() {
        return User.class.getSimpleName();
    }

    @Override
    public UserDto register(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        this.userRepo.findByUsername(user.getUsername()).ifPresent(u -> {
            throw new IllegalArgumentException("Username already exists");
        });
        // Set the address
        Address address = Address.builder()
                .street(userDto.getAddress().getStreet())
                .city(userDto.getAddress().getCity())
                .state(userDto.getAddress().getState())
                .country(userDto.getAddress().getCountry())
                .pinCode(userDto.getAddress().getPinCode())
                .build();
        user.setAddress(address);

        String password = userDto.getPassword();
        user.setPassword(this.passwordEncoder.encode(password));

        Role role = this.roleRepo.findById(Constants.ROLE_USER)
                .orElseThrow(() -> new ResourceNotFoundException(Role.class.getSimpleName(), "id", Constants.ROLE_USER));
        user.getRoles().add(role);
        User newUser = this.userRepo.save(user);

        return this.modelMapper.map(newUser, UserDto.class);
    }
}
