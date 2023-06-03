package peaksoft.springrest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserResponseView {
    List<UserResponse> userResponses;
}
