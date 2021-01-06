package com.epam.esm.controller;

import com.epam.esm.controller.exception.GiftEntityNotFoundException;
import com.epam.esm.controller.exception.WrongParameterFormatException;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftEntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/certificates")
public class GiftCertificateApiController {

    private GiftCertificateService service;

    @Autowired
    public void setService(GiftCertificateService service) {
        this.service = service;
    }

    @GetMapping
    public List<GiftCertificate> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id:^[1-9]\\d{0,18}$}")
    public GiftCertificate findById(@PathVariable long id) {
        return service.findById(id)
                .orElseThrow(() ->
                        new GiftEntityNotFoundException("Certificate not found", ErrorCode.GIFT_CERTIFICATE_NOT_FOUND));
    }

    @PostMapping
    public GiftCertificate create(@RequestBody GiftCertificate certificate) {
        return service.add(certificate);
    }

    @PutMapping("/{id:^[1-9]\\d{0,18}$}")
    public GiftCertificate update(@RequestBody GiftCertificate certificate, @PathVariable long id) {
        certificate.setId(id);
        return service.update(certificate)
                .orElseThrow(() ->
                        new GiftEntityNotFoundException("Certificate not found", ErrorCode.GIFT_CERTIFICATE_NOT_FOUND));
    }

    @DeleteMapping("/{id:^[1-9]\\d{0,18}$}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

    @GetMapping("/find/tag{tagName}")
    public List<GiftCertificate> findByTagName(@PathVariable String tagName,
                                               @RequestParam(value = "sort", required = false) String sortType,
                                               @RequestParam(value = "direction", required = false) String direction) {
        if (!GiftEntityValidator.correctTagName(tagName)) {
            throw new WrongParameterFormatException("Wrong tag name format", ErrorCode.NAME_WRONG_FORMAT);
        }
        return service.findByTagName(tagName, sortType, direction);
    }

    @GetMapping("/find/name/{name}")
    public List<GiftCertificate> findByName(@PathVariable String name,
                                            @RequestParam(value = "sort", required = false) String sortType,
                                            @RequestParam(value = "direction", required = false) String direction) {
        if (!GiftEntityValidator.correctTagName(name)) {
            throw new WrongParameterFormatException("Wrong certificate name format", ErrorCode.NAME_WRONG_FORMAT);
        }
        return service.findByName(name, sortType, direction);
    }

    @GetMapping("/find/description/{description}")
    public List<GiftCertificate> findByDescription(@PathVariable String description,
                                                   @RequestParam(value = "sort", required = false) String sortType,
                                                   @RequestParam(value = "direction", required = false) String direction) {
        if (!GiftEntityValidator.correctCertificateDescription(description)) {
            throw new WrongParameterFormatException("Wrong certificate name format", ErrorCode.DESCRIPTION_WRONG_FORMAT);
        }
        return service.findByDescription(description, sortType, direction);
    }

    @PutMapping("/update/{id}")
    public GiftCertificate updateField(@PathVariable long id,
                                       @RequestBody UpdatableField field) {
        String fieldName = field.getFieldName();
        String fieldValue = field.getFieldValue();
        if (!GiftEntityValidator.correctUpdateFieldParameters(fieldName, fieldValue)) {
            throw new WrongParameterFormatException("Wrong update field parameters",
                    ErrorCode.UPDATE_PARAMETERS_WRONG_FORMAT);
        }
        switch (fieldName) {
            case "name":
                return service.updateName(id, fieldValue).orElseThrow(
                        () -> new GiftEntityNotFoundException("Certificate not found",
                                ErrorCode.GIFT_CERTIFICATE_NOT_FOUND)
                );
            case "description":
                return service.updateDescription(id, fieldValue).orElseThrow(
                        () -> new GiftEntityNotFoundException("Certificate not found",
                                ErrorCode.GIFT_CERTIFICATE_NOT_FOUND)
                );
            case "price":
                return service.updatePrice(id, fieldValue).orElseThrow(
                        () -> new GiftEntityNotFoundException("Certificate not found",
                                ErrorCode.GIFT_CERTIFICATE_NOT_FOUND)
                );
            case "duration":
                return service.updateDuration(id, fieldValue).orElseThrow(
                        () -> new GiftEntityNotFoundException("Certificate not found",
                                ErrorCode.GIFT_CERTIFICATE_NOT_FOUND)
                );
            default:
                throw new GiftEntityNotFoundException("Certificate not found", ErrorCode.GIFT_CERTIFICATE_NOT_FOUND);
        }
    }

    @GetMapping("/find/tags")
    public List<GiftCertificate> findByTags(@RequestParam String tagNames,
                                            @RequestParam(value = "sort", required = false) String sortType,
                                            @RequestParam(value = "direction", required = false) String direction) {
        if (!GiftEntityValidator.correctTagNames(tagNames)) {
            throw new WrongParameterFormatException("Wrong tag names format", ErrorCode.NAME_WRONG_FORMAT);
        }
        return service.findByTagNames(tagNames, sortType, direction);
    }
}
