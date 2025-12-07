package com.example.tenderservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        name = "ErrorResponse",
        description = "Standard error response returned by the API"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {

    @Schema(description = "API path called by client")
    private String apiPath;

    @Schema(description = "HTTP error code")
    private HttpStatus errorCode;

    @Schema(description = "Error message describing the problem")
    private String errorMessage;

    @Schema(description = "Timestamp of the error")
    private LocalDateTime errorTime;
}
