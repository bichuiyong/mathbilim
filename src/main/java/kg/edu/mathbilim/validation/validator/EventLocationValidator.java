package kg.edu.mathbilim.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.edu.mathbilim.dto.event.EventDto;
import kg.edu.mathbilim.validation.annotation.ValidEventLocation;

public class EventLocationValidator implements ConstraintValidator<ValidEventLocation, EventDto> {

    @Override
    public void initialize(ValidEventLocation constraintAnnotation) {
    }

    @Override
    public boolean isValid(EventDto eventDto, ConstraintValidatorContext context) {
        if (eventDto == null) {
            return true;
        }

        Boolean isOffline = eventDto.getIsOffline();
        String address = eventDto.getAddress();
        String url = eventDto.getUrl();

        if (isOffline == null) {
            return true;
        }

        if (isOffline) {
            if (address == null || address.trim().isEmpty()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Для офлайн мероприятия необходимо указать адрес")
                        .addPropertyNode("address")
                        .addConstraintViolation();
                return false;
            }
        } else {
            if (url == null || url.trim().isEmpty()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Для онлайн мероприятия необходимо указать ссылку")
                        .addPropertyNode("url")
                        .addConstraintViolation();
                return false;
            }

            try {
                new java.net.URL(url);
            } catch (java.net.MalformedURLException e) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Указанная ссылка имеет неверный формат")
                        .addPropertyNode("url")
                        .addConstraintViolation();
                return false;
            }
        }

        return true;
    }
}