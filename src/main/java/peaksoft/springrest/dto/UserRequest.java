package peaksoft.springrest.dto;

import lombok.Getter;
import lombok.Setter;

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
