package kg.edu.mathbilim.service.impl.reference.types;

import kg.edu.mathbilim.dto.reference.types.UserTypeDto;
import kg.edu.mathbilim.exception.nsee.UserTypeNotFoundException;
import kg.edu.mathbilim.mapper.reference.types.UserTypeMapper;
import kg.edu.mathbilim.model.reference.types.UserType;
import kg.edu.mathbilim.repository.reference.types.UserTypeRepository;
import kg.edu.mathbilim.service.interfaces.reference.types.UserTypeService;
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
