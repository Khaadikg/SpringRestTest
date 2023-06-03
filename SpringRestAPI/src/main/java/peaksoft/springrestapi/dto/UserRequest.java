package peaksoft.springrestapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String roleName;
    private String email;
    private Long groupId;
    private Long courseId;
//    private LocalDate localDate;
}
