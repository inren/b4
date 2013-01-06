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
package org.bricket.b4.security.service;

import org.bricket.b4.core.service.B4Service;

public interface UserService extends B4Service {
    /**
     * Enumeration of all default users
     * 
     * @author Henning Teek
     */
    public enum Users {
	USER("user@localhost", "user"), ADMIN("admin@localhost", "admin");

	private final String email;
	private final String password;

	Users(String email, String password) {
	    this.email = email;
	    this.password = password;
	}

	public String getEmail() {
	    return email;
	}

	public String getPassword() {
	    return password;
	}
    }
}
