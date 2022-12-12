package ke.or.explorersanddevelopers.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ke.or.explorersanddevelopers.lms.exception.ErrorDetails;
import ke.or.explorersanddevelopers.lms.model.dto.CertificateDto;
import ke.or.explorersanddevelopers.lms.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Saturday, 15/10/2022
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/certificates")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
        }),
        @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
        }),
        @ApiResponse(responseCode = "403", description = "FORBIDDEN", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
        }),
        @ApiResponse(responseCode = "404", description = "RESOURCE NOT FOUND", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
        }),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
        }),
})
@Tag(name = "Certificate Controller", description = "A Controller to manage Certificate operations")
public class CertificateController {
    private final CertificateService certificateService;

    @GetMapping("/{certificateId}")
    @Operation(summary = "Get a certificate by id")
    @ApiResponse(responseCode = "200", description = "The certificate was retrieved successfully")
    public ResponseEntity<CertificateDto> getCertificateById(@PathVariable BigDecimal certificateId) {
        CertificateDto certificateById = certificateService.getCertificateById(certificateId);
        return ResponseEntity.ok(addHateoasLinks(certificateById));
    }

    @GetMapping
    @Operation(summary = "Get a list of certificates.")
    @ApiResponse(responseCode = "200", description = "The certificates were retrieved successfully")
    public ResponseEntity<CollectionModel<CertificateDto>> getListOfCertificates(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        List<CertificateDto> response = new ArrayList<>();
        certificateService.getListOfCertificates(PageRequest.of(pageNo, pageSize)).forEach(certificateDto -> response.add(addHateoasLinks(certificateDto)));
        CollectionModel<CertificateDto> collectionModel = CollectionModel.of(response);

        return ResponseEntity.ok(collectionModel);
    }

    private CertificateDto addHateoasLinks(CertificateDto savedCertificate) {
        savedCertificate.add(linkTo(methodOn(CertificateController.class).getCertificateById(savedCertificate.getCertificateId())).withSelfRel());
        savedCertificate.add(linkTo(methodOn(CertificateController.class).getListOfCertificates(0, 10)).withRel(IanaLinkRelations.COLLECTION));
        return savedCertificate;
    }
}
