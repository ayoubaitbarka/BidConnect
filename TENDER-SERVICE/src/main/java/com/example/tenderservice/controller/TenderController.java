package com.example.tenderservice.controller;

import com.example.tenderservice.constants.TenderConstants;
import com.example.tenderservice.dto.ErrorResponseDto;
import com.example.tenderservice.dto.TenderRequestDTO;
import com.example.tenderservice.dto.TenderResponseDTO;
import com.example.tenderservice.service.ITenderService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tenders")
@Validated
@RequiredArgsConstructor
@Tag(
        name = "Tender Management APIs",
        description = "REST APIs for creating, updating, publishing, closing, and retrieving tenders in BidConnect"
)
public class TenderController {

    private final ITenderService tenderService;

    // ============================================================
    // CREATE -------------------------------------------------------
    // ============================================================
    @Operation(
            summary = "Create a new tender",
            description = "- Create a new tender by providing the necessary fields inside the request body : \n" +
                    "\n" +
                    "- Pour le champ id de tender il sera gérer coté bdd (auto increment)\n" +
                    "\n" +
                    "- Pour les champs : organisationId, ownerId il seront chargé coté frontend après l'authentification, ainsi des valeurs par defaut dans le formulaire de creation d'une offre \n" +
                    "\n" +
                    "- Pour le champ status il sera mis par defaut a DRAFT lors de la creation d'une offre\n"+
                    "\n" +
                    "- Pour le champ publicationDate il sera mis a la date courant lors de la publication de l'offre\n"+
                    "\n" +
                    "- Pour les champs de suivi (createdAt, createdBy, updatedAt, updatedBy) ils seront gérés automatiquement par Spring Data JPA (auditing)\n" +
                    "\n" +
                    "+ Pour le champ documents, il sera géré via l'upload de fichier :\n" +
                    "\n" +
                    " - Tender-service reçoit : json + fichier\n"+
                    "\n" +
                    " - Tender-service envoie les fichiers à : storage-service (via Feign client)\n" +
                    "\n" +
                    " - Storage-service :\n" +
                    "\n" +
                    "- Stocke dans MinIO\n" +
                    "\n" +
                    "- Retourne : id, URL, path, MIME-type " +
                    "\n" +
                    "- Tender-service : stocke ces info dans la bdd.\n"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = TenderConstants.STATUS_201,
                    description = TenderConstants.MESSAGE_201,
                    content = @Content(schema = @Schema(implementation = TenderResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = TenderConstants.STATUS_500,
                    description = TenderConstants.MESSAGE_500,
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<TenderResponseDTO> createTender(
            @RequestPart("data") String data,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        TenderRequestDTO rawJson =
                mapper.readValue(data, TenderRequestDTO.class);

        TenderResponseDTO response = tenderService.createTender(rawJson, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ============================================================
    // UPDATE -------------------------------------------------------
    // ============================================================
    @Operation(
            summary = "Update an existing tender",
            description = "Update an existing tender using its ID. Only editable fields will be updated."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = TenderConstants.STATUS_200,
                    description = TenderConstants.MESSAGE_200
            ),
            @ApiResponse(
                    responseCode = TenderConstants.STATUS_404,
                    description = TenderConstants.MESSAGE_404,
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<TenderResponseDTO> updateTender(
            @PathVariable Long id,
            @Valid @RequestBody TenderRequestDTO dto
    ) {
        return ResponseEntity.ok(tenderService.updateTender(id, dto));
    }

    // ============================================================
    // DELETE -------------------------------------------------------
    // ============================================================
    @Operation(
            summary = "Delete a tender",
            description = "Delete a tender by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = TenderConstants.STATUS_200,
                    description = TenderConstants.MESSAGE_200
            ),
            @ApiResponse(
                    responseCode = TenderConstants.STATUS_417,
                    description = TenderConstants.MESSAGE_417_DELETE
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTender(@PathVariable Long id) {
        boolean result = tenderService.deleteTender(id);

        if (result) {
            return ResponseEntity.ok(TenderConstants.MESSAGE_200);
        }

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(TenderConstants.MESSAGE_417_DELETE);
    }

    // ============================================================
    // GET BY ID ----------------------------------------------------
    // ============================================================
    @Operation(
            summary = "Get tender details by ID",
            description = "Retrieve the full details of a tender using its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = TenderConstants.STATUS_200,
                    description = TenderConstants.MESSAGE_200
            ),
            @ApiResponse(
                    responseCode = TenderConstants.STATUS_404,
                    description = TenderConstants.MESSAGE_404,
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<TenderResponseDTO> getTenderById(@PathVariable Long id) {
        return ResponseEntity.ok(tenderService.getTenderById(id));
    }

    // ============================================================
    // GET ALL ------------------------------------------------------
    // ============================================================
    @Operation(
            summary = "Get all tenders",
            description = "Retrieve a list of all tenders available in the system."
    )
    @GetMapping
    public ResponseEntity<List<TenderResponseDTO>> getAllTenders() {
        return ResponseEntity.ok(tenderService.getAllTenders());
    }

    // ============================================================
    // GET BY ORGANIZATION ------------------------------------------
    // ============================================================
    @Operation(
            summary = "Get tenders by organization",
            description = "Retrieve all tenders linked to a specific organization."
    )
    @GetMapping("/organization/{orgId}")
    public ResponseEntity<List<TenderResponseDTO>> getByOrganization(@PathVariable Long orgId) {
        return ResponseEntity.ok(tenderService.getTendersByOrganization(orgId));
    }

    // ============================================================
    // GET BY OWNER -------------------------------------------------
    // ============================================================
    @Operation(
            summary = "Get tenders created by a specific user",
            description = "Retrieve all tenders created by a specific user/owner."
    )
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<TenderResponseDTO>> getByOwner(@PathVariable String ownerId) {
        return ResponseEntity.ok(tenderService.getTendersByOwnerUser(ownerId));
    }

    // ============================================================
    // PUBLISH -------------------------------------------------------
    // ============================================================
    @Operation(
            summary = "Publish a tender",
            description = "Set the status of a tender to PUBLISHED."
    )
    @PatchMapping("/{id}/publish")
    public ResponseEntity<TenderResponseDTO> publishTender(@PathVariable Long id) {
        return ResponseEntity.ok(tenderService.publishTender(id));
    }

    // ============================================================
    // CLOSE ---------------------------------------------------------
    // ============================================================
    @Operation(
            summary = "Close a tender",
            description = "Set the status of a tender to CLOSED."
    )
    @PatchMapping("/{id}/close")
    public ResponseEntity<TenderResponseDTO> closeTender(@PathVariable Long id) {
        return ResponseEntity.ok(tenderService.closeTender(id));
    }
}
