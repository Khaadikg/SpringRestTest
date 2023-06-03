package peaksoft.springrestapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupRequest {
    private String groupName;
    private String dateStart;
    private String dateFinish;
    private String courseName;
//    private Long courseId;
}
