package sakuuj.springcourse.sensor.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.ObjectError;

import java.util.List;

public class RestErrorResponse<T extends RestErrorException> {
    @JsonProperty("errors")
    private List<String> errorMessages;

    public RestErrorResponse(T ex) {
        errorMessages = ex.getRestErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .toList();
    }
}
