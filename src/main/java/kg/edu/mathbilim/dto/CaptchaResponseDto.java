package kg.edu.mathbilim.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class CaptchaResponseDto {
    private Boolean success;

    @JsonAlias("error-codes")
    private Set<String> errorCodes;
}
