package com.epam.esm.controller;

import com.epam.esm.controller.error_handler.ErrorCode;
import com.epam.esm.controller.exception.GiftEntityNotFoundException;
import com.epam.esm.controller.exception.WrongParameterFormatException;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("api/tags")
public class TagApiController {

    private TagService service;

    @Autowired
    public void setService(TagService service) {
        this.service = service;
    }

    @GetMapping
    public HttpEntity<List<TagDTO>> findAll(@RequestParam(required = false) Integer limit,
                                            @RequestParam(required = false) Integer offset) {
        List<TagDTO> tags = service.findAll(limit, offset);
        tags.forEach(t -> t.add(linkTo(TagApiController.class).slash(t.getId()).withSelfRel()));
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public HttpEntity<TagDTO> findById(@PathVariable long id) {
        TagDTO tag = service.findById(id).orElseThrow(() ->
                new GiftEntityNotFoundException("Tag not found", ErrorCode.TAG_NOT_FOUND));
        tag.add(linkTo(TagApiController.class).slash(tag.getId()).withSelfRel());
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @PostMapping
    public HttpEntity<TagDTO> create(@RequestBody TagDTO tag) {
        if (tag.getName() == null) {
            throw new WrongParameterFormatException("Tag name could not be null", ErrorCode.TAG_NAME_IS_NULL);
        }
        TagDTO created = service.add(tag);
        created.add(linkTo(TagApiController.class).withRel(HateoasRel.POST));
        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public HttpEntity<TagDTO> update(@RequestBody TagDTO tag, @PathVariable long id) {
        tag.setId(id);
        TagDTO updated = service.update(tag).orElseThrow(() ->
                new GiftEntityNotFoundException("Tag not found", ErrorCode.TAG_NOT_FOUND));
        updated.add(linkTo(TagApiController.class).slash(id).withRel(HateoasRel.POST));
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public EntityModel<Map<String, Boolean>> delete(@PathVariable int id) {
        Map<String, Boolean> map = new HashMap<>();
        map.put(HateoasRel.RESULT, service.delete(id));
        EntityModel<Map<String, Boolean>> entityModel = EntityModel.of(map);
        entityModel.add(linkTo(TagApiController.class).slash(id).withRel(HateoasRel.DELETE));
        return entityModel;
    }
}
