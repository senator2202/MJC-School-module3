package com.epam.esm.controller;

import com.epam.esm.controller.error_handler.ErrorCode;
import com.epam.esm.controller.exception.GiftEntityNotFoundException;
import com.epam.esm.controller.exception.WrongParameterFormatException;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.GiftEntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * RestController for Tag entity.
 */
@RestController
@RequestMapping("api/tags")
public class TagApiController {

    private TagService service;

    /**
     * Method adds HATEOAS link to TagDTO entity
     */
    static TagDTO addSelfLink(TagDTO tag) {
        return tag.add(linkTo(TagApiController.class).slash(tag.getId()).withSelfRel());
    }

    @Autowired
    public void setService(TagService service) {
        this.service = service;
    }

    @GetMapping
    public List<TagDTO> findAll(@RequestParam(required = false) Integer limit,
                                @RequestParam(required = false) Integer offset) {
        List<TagDTO> tags = service.findAll(limit, offset);
        return tags.stream()
                .map(TagApiController::addSelfLink)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id:^[1-9]\\d{0,18}$}")
    public TagDTO findById(@PathVariable long id) {
        TagDTO tag = service.findById(id).orElseThrow(() ->
                new GiftEntityNotFoundException("Tag not found", ErrorCode.TAG_NOT_FOUND));
        return addSelfLink(tag);
    }

    @PostMapping
    public TagDTO create(@RequestBody TagDTO tag) {
        if (!GiftEntityValidator.correctTag(tag)) {
            throw new WrongParameterFormatException("Tag parameters are wrong!", ErrorCode.TAG_WRONG_PARAMETERS);
        }
        return addSelfLink(service.add(tag));
    }

    @PutMapping("/{id:^[1-9]\\d{0,18}$}")
    public TagDTO update(@RequestBody TagDTO tag, @PathVariable long id) {
        if (!GiftEntityValidator.correctTag(tag)) {
            throw new WrongParameterFormatException("Tag parameters are wrong!", ErrorCode.TAG_WRONG_PARAMETERS);
        }
        tag.setId(id);
        TagDTO updated = service.update(tag).orElseThrow(() ->
                new GiftEntityNotFoundException("Tag not found", ErrorCode.TAG_NOT_FOUND));
        return addSelfLink(updated);
    }

    @DeleteMapping("/{id:^[1-9]\\d{0,18}$}")
    public EntityModel<DeleteResult> delete(@PathVariable int id) {
        boolean result = service.delete(id);
        return EntityModel.of(new DeleteResult(result));
    }
}
