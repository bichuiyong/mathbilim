package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.dto.UserTypeDto;
import kg.edu.mathbilim.exception.UserTypeNotFoundException;
import kg.edu.mathbilim.mapper.UserTypeMapper;
import kg.edu.mathbilim.model.UserType;
import kg.edu.mathbilim.repository.UserTypeRepository;
import kg.edu.mathbilim.service.interfaces.UserTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTypeServiceImpl implements UserTypeService {
    private final UserTypeRepository userTypeRepository;
    private final UserTypeMapper userTypeMapper;

    @Override
    public List<UserTypeDto> getAllTypes() {
        return userTypeRepository.findAll()
                .stream()
                .map(userTypeMapper::toDto)
                .toList();
    }

    @Override
    public UserType getById(Integer id) {
        return userTypeRepository.findById(id)
                .orElseThrow(UserTypeNotFoundException::new);
    }
}
