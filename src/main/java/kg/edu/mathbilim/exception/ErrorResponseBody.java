package kg.edu.mathbilim.exception;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseBody {
    private String title;
    private Map<String, List<String>> response;
}
