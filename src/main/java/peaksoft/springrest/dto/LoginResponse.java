package peaksoft.springrest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String jwt;
    private String message;
    private String authorities;
}
