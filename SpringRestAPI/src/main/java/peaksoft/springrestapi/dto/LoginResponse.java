package peaksoft.springrestapi.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class LoginResponse {
    private String jwt;
    private String message;
    private String authorities;
}
