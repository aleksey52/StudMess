package com.spbstu.StudMess.controller;

import com.spbstu.StudMess.controller.annotation.ApiV1;
import com.spbstu.StudMess.dto.request.SubjectRequest;
import com.spbstu.StudMess.dto.response.SubjectResponse;
import com.spbstu.StudMess.model.SubjectEntity;
import com.spbstu.StudMess.service.SubjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Subject")
@ApiV1
@RestController
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping("/subjects")
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectResponse create(@NonNull @Valid @RequestBody SubjectRequest request) {
        return toResponse(subjectService.create(request.getName()));
    }

    @GetMapping("/subjects")
    @ResponseStatus(HttpStatus.OK)
    public Page<SubjectResponse> findAll(@RequestParam(name = "nameContains", required = false) String subjectNameContains,
                                       @ParameterObject Pageable pageable) {
        return subjectService.findAll(subjectNameContains, pageable)
                .map(this::toResponse);
    }

    @GetMapping("/subjects/{subjectId}")
    @ResponseStatus(HttpStatus.OK)
    public SubjectResponse find(@NonNull @PathVariable Long subjectId) {
        return toResponse(subjectService.findById(subjectId));
    }

    @DeleteMapping("/subjects/{subjectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@NonNull @PathVariable Long subjectId) {
        subjectService.delete(subjectId);
    }

    @PutMapping("/subjects/{subjectId}")
    @ResponseStatus(HttpStatus.OK)
    public SubjectResponse update(@NonNull @PathVariable Long subjectId,
                                @NonNull @Valid @RequestBody SubjectRequest request) {
        return toResponse(subjectService.update(subjectId, request.getName()));
    }

    @NonNull
    public SubjectResponse toResponse(@NonNull SubjectEntity subjectEntity) {
        return new SubjectResponse(subjectEntity.getId(), subjectEntity.getName());
    }
}
