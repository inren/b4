/**
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bricket.b4.security.controller;

import java.security.Principal;

import org.bricket.b4.core.controller.ResourceNotFoundException;
import org.bricket.b4.security.entity.User;
import org.bricket.b4.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/authenticate")
public class AuthenticationController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserResourceAssembler userResourceAssembler;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<UserResource> authenticate(Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        if (user == null) {
            throw new ResourceNotFoundException();
        }
        return new HttpEntity<UserResource>(userResourceAssembler.toResource(user));
    }
}