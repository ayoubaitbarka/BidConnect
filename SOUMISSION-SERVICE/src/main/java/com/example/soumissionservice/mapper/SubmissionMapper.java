package com.example.soumissionservice.mapper;




import com.example.soumissionservice.dto.SubmissionResponse;
import com.example.soumissionservice.entity.Submission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubmissionMapper {

//    @Mapping(target = "id", source = "id")
    SubmissionResponse toResponse(Submission s);

//    @Mapping(target = "id", source = "id")
    Submission toEntity(SubmissionResponse r);

}

