package com.epam.esm.controller;

import com.epam.esm.controller.error_handler.ErrorCode;
import com.epam.esm.controller.exception.GiftEntityNotFoundException;
import com.epam.esm.controller.exception.WrongParameterFormatException;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftEntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/certificates")
public class GiftCertificateApiController {

    private GiftCertificateService service;

    @Autowired
    @Qualifier("giftCertificateServiceImpl")
    public void setService(GiftCertificateService service) {
        this.service = service;
    }

    @GetMapping
    public HttpEntity<List<GiftCertificateDTO>> findAll(@RequestParam(required = false) Integer limit,
                                                        @RequestParam(required = false) Integer offset) {
        List<GiftCertificateDTO> giftCertificates = service.findAll(limit, offset);
        return addLinks(giftCertificates);
    }

    @GetMapping("/{id:^[1-9]\\d{0,18}$}")
    public HttpEntity<GiftCertificateDTO> findById(@PathVariable long id) {
        GiftCertificateDTO giftCertificate = service.findById(id)
                .orElseThrow(() ->
                        new GiftEntityNotFoundException("Certificate not found", ErrorCode.GIFT_CERTIFICATE_NOT_FOUND));
        addLink(giftCertificate);
        return new ResponseEntity<>(giftCertificate, HttpStatus.OK);
    }

    @PostMapping
    public HttpEntity<GiftCertificateDTO> create(@RequestBody GiftCertificateDTO certificate) {
        GiftCertificateDTO created = service.add(certificate);
        created.add(linkTo(methodOn(GiftCertificateApiController.class).create(certificate)).withRel(HateoasRel.POST));
        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @PutMapping("/{id:^[1-9]\\d{0,18}$}")
    public HttpEntity<GiftCertificateDTO> update(@RequestBody GiftCertificateDTO certificate, @PathVariable long id) {
        certificate.setId(id);
        GiftCertificateDTO updated = service.update(certificate)
                .orElseThrow(() ->
                        new GiftEntityNotFoundException("Certificate not found", ErrorCode.GIFT_CERTIFICATE_NOT_FOUND));
        updated.add(linkTo(GiftCertificateApiController.class).slash(id).withRel(HateoasRel.PUT));
        updated.getTags().forEach(t -> t.add(linkTo(methodOn(TagApiController.class).findById(t.getId())).withSelfRel()));
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id:^[1-9]\\d{0,18}$}")
    public EntityModel<Map<String, Boolean>> delete(@PathVariable long id) {
        Map<String, Boolean> map = new HashMap<>();
        map.put(HateoasRel.RESULT, service.delete(id));
        EntityModel<Map<String, Boolean>> entityModel = EntityModel.of(map);
        entityModel.add(linkTo(GiftCertificateApiController.class).slash(id).withRel(HateoasRel.DELETE));
        return entityModel;
    }

    @GetMapping("/find/tag/{tagName}")
    public HttpEntity<List<GiftCertificateDTO>> findByTagName(@PathVariable String tagName,
                                                              @RequestParam(value = "sort", required = false) String sortType,
                                                              @RequestParam(value = "direction", required = false) String direction) {
        if (!GiftEntityValidator.correctTagName(tagName)) {
            throw new WrongParameterFormatException("Wrong tag name format", ErrorCode.NAME_WRONG_FORMAT);
        }
        List<GiftCertificateDTO> giftCertificates = service.findByTagName(tagName, sortType, direction);
        return addLinks(giftCertificates);
    }

    @GetMapping("/find/name/{name}")
    public HttpEntity<List<GiftCertificateDTO>> findByName(@PathVariable String name,
                                                           @RequestParam(value = "sort", required = false) String sortType,
                                                           @RequestParam(value = "direction", required = false) String direction) {
        if (!GiftEntityValidator.correctTagName(name)) {
            throw new WrongParameterFormatException("Wrong certificate name format", ErrorCode.NAME_WRONG_FORMAT);
        }
        List<GiftCertificateDTO> giftCertificates = service.findByName(name, sortType, direction);
        return addLinks(giftCertificates);
    }

    @GetMapping("/find/description/{description}")
    public HttpEntity<List<GiftCertificateDTO>> findByDescription(@PathVariable String description,
                                                                  @RequestParam(value = "sort", required = false) String sortType,
                                                                  @RequestParam(value = "direction", required = false) String direction) {
        if (!GiftEntityValidator.correctCertificateDescription(description)) {
            throw new WrongParameterFormatException("Wrong certificate name format", ErrorCode.DESCRIPTION_WRONG_FORMAT);
        }
        List<GiftCertificateDTO> giftCertificates = service.findByDescription(description, sortType, direction);
        return addLinks(giftCertificates);
    }

    @PutMapping("/{id}/updateField")
    public HttpEntity<GiftCertificateDTO> updateField(@PathVariable long id,
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
        addLink(updated);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping("/find/tags")
    public HttpEntity<List<GiftCertificateDTO>> findByTags(@RequestParam String tagNames,
                                                           @RequestParam(value = "sort", required = false) String sortType,
                                                           @RequestParam(value = "direction", required = false) String direction) {
        if (!GiftEntityValidator.correctTagNames(tagNames)) {
            throw new WrongParameterFormatException("Wrong tag names format", ErrorCode.NAME_WRONG_FORMAT);
        }
        return addLinks(service.findByTagNames(tagNames, sortType, direction));
    }

    private HttpEntity<List<GiftCertificateDTO>> addLinks(List<GiftCertificateDTO> giftCertificates) {
        giftCertificates.forEach(this::addLink);
        return new ResponseEntity<>(giftCertificates, HttpStatus.OK);
    }

    private void addLink(GiftCertificateDTO giftCertificate) {
        giftCertificate.add(linkTo(methodOn(GiftCertificateApiController.class).findById(giftCertificate.getId()))
                .withSelfRel());
        giftCertificate.getTags().forEach(t -> t.add(linkTo(methodOn(TagApiController.class).findById(t.getId()))
                .withSelfRel()));
    }
}
