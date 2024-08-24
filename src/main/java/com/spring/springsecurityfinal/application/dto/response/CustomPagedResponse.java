package com.spring.springsecurityfinal.application.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import java.util.List;

@Getter
@Setter
public class CustomPagedResponse<T> {
    private List<EntityModel<T>> content;
    private PagedModel.PageMetadata page;
    public CustomPagedResponse(List<EntityModel<T>> content, PagedModel.PageMetadata page) {
        this.content = content;
        this.page = page;
    }
}