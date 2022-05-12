package com.spbstu.StudMess.controller;

import com.spbstu.StudMess.controller.annotation.ApiV1;
import com.spbstu.StudMess.dto.request.GroupRequest;
import com.spbstu.StudMess.dto.response.GroupResponse;
import com.spbstu.StudMess.model.GroupEntity;
import com.spbstu.StudMess.service.GroupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Group")
@ApiV1
@RestController
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/groups")
    @ResponseStatus(HttpStatus.CREATED)
    public GroupResponse create(@NonNull @Valid @RequestBody GroupRequest request) {
        return toResponse(groupService.create(request.getName()));
    }

    @GetMapping("/groups")
    @ResponseStatus(HttpStatus.OK)
    public Page<GroupResponse> findAll(@RequestParam(name = "nameContains", required = false) String groupNameContains,
                                       @ParameterObject Pageable pageable) {
        return groupService.findAll(groupNameContains, pageable)
                .map(this::toResponse);
    }

    @GetMapping("/groups/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    public GroupResponse find(@NonNull @PathVariable Long groupId) {
        return toResponse(groupService.findById(groupId));
    }

    @DeleteMapping("/groups/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@NonNull @PathVariable Long groupId) {
        groupService.delete(groupId);
    }

    @PutMapping("/groups/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    public GroupResponse update(@NonNull @PathVariable Long groupId,
                              @NonNull @Valid @RequestBody GroupRequest request) {
        return toResponse(groupService.update(groupId, request.getName()));
    }

    @NonNull
    public GroupResponse toResponse(@NonNull GroupEntity groupEntity) {
        return new GroupResponse(groupEntity.getId(), groupEntity.getName());
    }
}
