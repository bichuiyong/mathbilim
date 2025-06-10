package kg.edu.mathbilim.service.impl.reference;

import kg.edu.mathbilim.exception.UserTypeNotFoundException;
import kg.edu.mathbilim.model.reference.UserType;
import kg.edu.mathbilim.repository.UserTypeRepository;
import kg.edu.mathbilim.service.interfaces.reference.UserTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserTypeServiceImpl implements UserTypeService {
    private final UserTypeRepository userTypeRepository;

    @Override
    public List<UserType> getAll() {
        return userTypeRepository.findAll();
    }

    @Override
    public UserType findById(Long id) {
        return userTypeRepository.findById(id)
                .orElseThrow(() -> new UserTypeNotFoundException("UserType not found with id: " + id));
    }
}
