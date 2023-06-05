package peaksoft.springrest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GroupRequest {
    private String groupName;
    private String dateStart;
    private String dateFinish;
    private String courseName;
//    private Long courseId;
}
