package com.epam.esm.controller;

import com.epam.esm.controller.error_handler.ProjectError;
import com.epam.esm.controller.exception.ExceptionProvider;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftEntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * RestController for GiftCertificate entity
 */
@RestController
@RequestMapping("api/certificates")
public class GiftCertificateApiController {

    private GiftCertificateService service;
    private ExceptionProvider exceptionProvider;

    /**
     * Method adds HATEOAS link to GiftCertificateDTO entity
     *
     * @param certificate the certificate
     * @return the gift certificate dto
     */
    static GiftCertificateDTO addSelfLink(GiftCertificateDTO certificate) {
        if (certificate.getTags() != null) {
            certificate.setTags(
                    certificate.getTags().stream().map(TagApiController::addLinks).collect(Collectors.toList())
            );
        }
        return certificate
                .add(linkTo(methodOn(GiftCertificateApiController.class).findById(certificate.getId())).withSelfRel())
                .add(linkTo(GiftCertificateApiController.class)
                        .withRel(HateoasData.POST)
                        .withName(HateoasData.ADD_CERTIFICATE))
                .add(linkTo(methodOn(GiftCertificateApiController.class)
                        .findById(certificate.getId()))
                        .withRel(HateoasData.PATCH)
                        .withName(HateoasData.UPDATE_CERTIFICATE_FIELDS))
                .add(linkTo(methodOn(GiftCertificateApiController.class)
                        .findById(certificate.getId()))
                        .withRel(HateoasData.DELETE)
                        .withName(HateoasData.DELETE_CERTIFICATE));
    }

    /**
     * Sets service.
     *
     * @param service the service
     */
    @Autowired
    public void setService(GiftCertificateService service) {
        this.service = service;
    }

    /**
     * Sets exception provider.
     *
     * @param exceptionProvider the exception provider
     */
    @Autowired
    public void setExceptionProvider(ExceptionProvider exceptionProvider) {
        this.exceptionProvider = exceptionProvider;
    }

    /**
     * Find all certificates, satisfying optional find parameters, sort parameters, limit and offset.
     *
     * @param name        the name
     * @param description the description
     * @param tagNames    the tag names
     * @param sortType    the sort type (price, duration, create-date, last-update-date)
     * @param direction   the direction (desc, asc)
     * @param limit       the limit
     * @param offset      the offset
     * @return the list
     */
    @GetMapping
    public List<GiftCertificateDTO> findAll(@RequestParam(required = false) String name,
                                            @RequestParam(required = false) String description,
                                            @RequestParam(value = "tags", required = false) String tagNames,
                                            @RequestParam(value = "sort", required = false) String sortType,
                                            @RequestParam(required = false) String direction,
                                            @RequestParam(required = false) Integer limit,
                                            @RequestParam(required = false) Integer offset) {
        if (!GiftEntityValidator.
                correctOptionalParameters(name, description, tagNames, sortType, direction, limit, offset)) {
            throw exceptionProvider.wrongParameterFormatException(ProjectError.WRONG_OPTIONAL_PARAMETERS);
        }
        List<GiftCertificateDTO> giftCertificates =
                service.findAll(name, description, tagNames, sortType, direction, limit, offset);
        return giftCertificates.stream()
                .map(GiftCertificateApiController::addSelfLink)
                .collect(Collectors.toList());
    }

    /**
     * Find certificate by id, return gift certificate dto.
     *
     * @param id the id
     * @return the gift certificate dto
     */
    @GetMapping("/{id:^[1-9]\\d{0,18}$}")
    public GiftCertificateDTO findById(@PathVariable long id) {
        GiftCertificateDTO giftCertificate = service.findById(id)
                .orElseThrow(
                        () -> exceptionProvider.giftEntityNotFoundException(ProjectError.GIFT_CERTIFICATE_NOT_FOUND)
                );
        return addSelfLink(giftCertificate);
    }

    /**
     * Create gift certificate, return created dto.
     *
     * @param certificate the certificate
     * @return the gift certificate dto
     */
    @PostMapping
    public GiftCertificateDTO create(@RequestBody GiftCertificateDTO certificate) {
        if (!GiftEntityValidator.correctGiftCertificate(certificate)) {
            throw exceptionProvider.wrongParameterFormatException(ProjectError.CERTIFICATE_WRONG_PARAMETERS);
        }
        GiftCertificateDTO created = service.add(certificate);
        return addSelfLink(created);
    }

    /**
     * Update gift certificate, return updated dto.
     *
     * @param certificate the certificate
     * @param id          the id
     * @return the gift certificate dto
     */
    @PatchMapping("/{id:^[1-9]\\d{0,18}$}")
    public GiftCertificateDTO update(@RequestBody GiftCertificateDTO certificate, @PathVariable long id) {
        if (!GiftEntityValidator.correctGiftCertificateOptional(certificate)) {
            throw exceptionProvider.wrongParameterFormatException(ProjectError.CERTIFICATE_WRONG_PARAMETERS);
        }
        certificate.setId(id);
        GiftCertificateDTO updated = service.update(certificate)
                .orElseThrow(() -> exceptionProvider.giftEntityNotFoundException(ProjectError.GIFT_CERTIFICATE_NOT_FOUND));
        return addSelfLink(updated);
    }

    /**
     * Delete gift certificate, return result of deleting.
     *
     * @param id the id
     * @return the delete result
     */
    @DeleteMapping("/{id:^[1-9]\\d{0,18}$}")
    public DeleteResult delete(@PathVariable long id) {
        boolean result = service.delete(id);
        return new DeleteResult(result);
    }
}
