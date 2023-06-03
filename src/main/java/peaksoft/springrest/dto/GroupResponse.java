package peaksoft.springrest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupResponse {
    private Long id;
    private String groupName;
    private String dateStart;
    private String dateFinish;
    private String courseName;
}
