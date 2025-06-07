package kg.edu.mathbilim.service.interfaces.reference.status;

import kg.edu.mathbilim.dto.reference.status.TestStatusDto;

import java.util.List;

public interface TestStatusService {
    List<TestStatusDto> getAllStatuses();
}
