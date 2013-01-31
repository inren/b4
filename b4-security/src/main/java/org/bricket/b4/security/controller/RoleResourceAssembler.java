package org.bricket.b4.security.controller;

import org.bricket.b4.security.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class RoleResourceAssembler extends ResourceAssemblerSupport<Role, RoleResource> {

    @Autowired
    ModelMapper modelmapper;

    public RoleResourceAssembler() {
        super(RoleController.class, RoleResource.class);
    }

    @Override
    public RoleResource toResource(Role role) {
        RoleResource resource = instantiateResource(role);
        modelmapper.map(role, resource);
        return resource;
    }

}
