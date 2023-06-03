package peaksoft.springrest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
//    private String password;
    private String roleName;
    private LocalDate localDate;
}
