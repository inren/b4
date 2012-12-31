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
package org.bricket.b4.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author Henning Teek
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "Domain_User", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User extends DomainObject {
    @Column(nullable = false)
    private String email;
    private String password;

    public final String getEmail() {
	return email;
    }

    public final void setEmail(String email) {
	this.email = email;
    }

    public final String getPassword() {
	return password;
    }

    public final void setPassword(String password) {
	this.password = password;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result
		+ ((password == null) ? 0 : password.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (!super.equals(obj)) {
	    return false;
	}
	if (!(obj instanceof User)) {
	    return false;
	}
	User other = (User) obj;
	if (email == null) {
	    if (other.email != null) {
		return false;
	    }
	} else if (!email.equals(other.email)) {
	    return false;
	}
	if (password == null) {
	    if (other.password != null) {
		return false;
	    }
	} else if (!password.equals(other.password)) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return String.format("User [email=%s, password=%s, getId()=%s]", email,
		password, getId());
    }
}
