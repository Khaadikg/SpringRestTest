package peaksoft.springrest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.springrest.dto.CourseResponseView;
import peaksoft.springrest.dto.GroupResponseView;
import peaksoft.springrest.service.GroupService;
import peaksoft.springrest.dto.GroupRequest;
import peaksoft.springrest.dto.GroupResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/groups")
@PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
@Tag(name = "Group controller", description = "Using CRUD upon groups")
public class GroupController {
    private final GroupService groupService;
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    @Operation(summary = "Get all groups", description = "Uses by ADMIN and INSTRUCTOR")
    public GroupResponseView getAll(@RequestParam(name = "text", required = false) String text,
                                    @RequestParam int page,
                                    @RequestParam int size){
        return groupService.searchAndPagination(text, page, size);
    }
    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    @Operation(summary = "Get group by id", description = "Uses by ADMIN and INSTRUCTOR")
    public GroupResponse getGroup(@PathVariable("id") Long id) {
        return groupService.getGroupById(id);
    }
    @PostMapping
    @Operation(summary = "Adding group")
    public GroupResponse save(@RequestBody GroupRequest groupRequest) {
        return groupService.saveGroup(groupRequest);
    }
    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    @Operation(summary = "Add group by id", description = "Uses by ADMIN and INSTRUCTOR")
    public GroupResponse update(@PathVariable("id")Long id, @RequestBody GroupRequest groupRequest) {
        return groupService.updateGroup(id, groupRequest);
    }
    @DeleteMapping("{id}")
    @Operation(summary = "Deleting group", description = "Only for ADMIN")
    public String delete(@PathVariable("id") Long id) {
        return groupService.deleteGroup(id);
    }
}
