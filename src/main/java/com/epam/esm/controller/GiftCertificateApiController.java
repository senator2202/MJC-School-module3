package com.epam.esm.controller;

import com.epam.esm.controller.error_handler.ErrorCode;
import com.epam.esm.controller.exception.GiftEntityNotFoundException;
import com.epam.esm.controller.exception.WrongParameterFormatException;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftEntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/certificates")
public class GiftCertificateApiController {

    private GiftCertificateService service;

    @Autowired
    public void setService(GiftCertificateService service) {
        this.service = service;
    }

    @GetMapping
    public List<EntityModel<GiftCertificateDTO>> findAll(@RequestParam(required = false) String name,
                                                         @RequestParam(required = false) String description,
                                                         @RequestParam(value = "tags", required = false) String tagNames,
                                                         @RequestParam(value = "sort", required = false) String sortType,
                                                         @RequestParam(required = false) String direction,
                                                         @RequestParam(required = false) Integer limit,
                                                         @RequestParam(required = false) Integer offset) {
        if (!GiftEntityValidator.
                correctOptionalParameters(name, description, tagNames, sortType, direction, limit, offset)) {
            throw new WrongParameterFormatException("Wrong optional parameters", ErrorCode.WRONG_OPTIONAL_PARAMETERS);
        }
        List<GiftCertificateDTO> giftCertificates =
                service.findAll(name, description, tagNames, sortType, direction, limit, offset);
        return giftCertificates.stream()
                .map(this::addSelfLink)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id:^[1-9]\\d{0,18}$}")
    public EntityModel<GiftCertificateDTO> findById(@PathVariable long id) {
        GiftCertificateDTO giftCertificate = service.findById(id)
                .orElseThrow(() ->
                        new GiftEntityNotFoundException("Certificate not found", ErrorCode.GIFT_CERTIFICATE_NOT_FOUND));
        return addSelfLink(giftCertificate);
    }

    @PostMapping
    public EntityModel<GiftCertificateDTO> create(@RequestBody GiftCertificateDTO certificate) {
        if (!GiftEntityValidator.correctGiftCertificate(certificate)) {
            throw new WrongParameterFormatException("Wrong certificate parameters",
                    ErrorCode.CERTIFICATE_WRONG_PARAMETERS);
        }
        GiftCertificateDTO created = service.add(certificate);
        return addSelfLink(created);
    }

    @PutMapping("/{id:^[1-9]\\d{0,18}$}")
    public EntityModel<GiftCertificateDTO> update(@RequestBody GiftCertificateDTO certificate, @PathVariable long id) {
        if (!GiftEntityValidator.correctOptionalCertificateName(certificate.getName()) ||
                !GiftEntityValidator.correctOptionalDescription(certificate.getDescription()) ||
                !GiftEntityValidator.correctOptionalPrice(certificate.getPrice()) ||
                !GiftEntityValidator.correctOptionalDuration(certificate.getDuration()) ||
                !GiftEntityValidator.correctTags(certificate.getTags())) {
            throw new WrongParameterFormatException("Wrong certificate parameters",
                    ErrorCode.CERTIFICATE_WRONG_PARAMETERS);
        }
        certificate.setId(id);
        GiftCertificateDTO updated = service.update(certificate)
                .orElseThrow(() ->
                        new GiftEntityNotFoundException("Certificate not found", ErrorCode.GIFT_CERTIFICATE_NOT_FOUND));
        return addSelfLink(updated);
    }

    @DeleteMapping("/{id:^[1-9]\\d{0,18}$}")
    public EntityModel<DeleteResult> delete(@PathVariable long id) {
        boolean result = service.delete(id);
        return EntityModel.of(new DeleteResult(result));
    }

    @PutMapping("/{id}/field")
    public EntityModel<GiftCertificateDTO> updateField(@PathVariable long id,
                                                       @RequestBody UpdatingField field) {
        UpdatingField.FieldName fieldName = field.getFieldName();
        String fieldValue = field.getFieldValue();
        if (!GiftEntityValidator.correctUpdateFieldParameters(fieldName, fieldValue)) {
            throw new WrongParameterFormatException("Wrong update field parameters",
                    ErrorCode.UPDATE_PARAMETERS_WRONG_FORMAT);
        }
        GiftCertificateDTO updated = service.updateField(id, field).orElseThrow(
                () -> new GiftEntityNotFoundException("Certificate not found",
                        ErrorCode.GIFT_CERTIFICATE_NOT_FOUND)
        );
        return addSelfLink(updated);
    }

    private EntityModel<GiftCertificateDTO> addSelfLink(GiftCertificateDTO certificate) {
        certificate.setTags(
                certificate.getTags().stream().map(TagApiController::addSelfLink).collect(Collectors.toList())
        );
        return EntityModel
                .of(certificate)
                .add(linkTo(methodOn(GiftCertificateApiController.class).findById(certificate.getId())).withSelfRel());
    }

    static void addSelfLink(EntityModel<GiftCertificateDTO> entityModel) {
        Long id = entityModel.getContent().getId();
        entityModel.add(linkTo(methodOn(GiftCertificateApiController.class).findById(id)).withSelfRel());
    }
}
