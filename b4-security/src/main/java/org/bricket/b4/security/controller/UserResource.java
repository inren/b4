package org.bricket.b4.security.controller;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.hateoas.ResourceSupport;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserResource extends ResourceSupport {
    private String email;
}
