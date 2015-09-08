/*
 * Copyright 2014 Black Pepper Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.co.blackpepper.support.spring.security.test;

import java.util.Objects;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.springframework.security.core.userdetails.UserDetails;

import static com.google.common.base.Preconditions.checkNotNull;

public class UserDetailsMatcher extends TypeSafeMatcher<UserDetails> {

	private final UserDetails expected;

	protected UserDetailsMatcher(UserDetails expected) {
		this.expected = checkNotNull(expected, "expected");
	}
	
	public static UserDetailsMatcher userDetailsEqualTo(UserDetails expected) {
		return new UserDetailsMatcher(expected);
	}
	
	@Override
	public void describeTo(Description description) {
		description.appendValue(expected);
	}

	@Override
	protected boolean matchesSafely(UserDetails actual) {
		return expected.getAuthorities().equals(actual.getAuthorities())
			&& Objects.equals(expected.getPassword(), actual.getPassword())
			&& expected.getUsername().equals(actual.getUsername())
			&& expected.isAccountNonExpired() == actual.isAccountNonExpired()
			&& expected.isAccountNonLocked() == actual.isAccountNonLocked()
			&& expected.isCredentialsNonExpired() == actual.isCredentialsNonExpired()
			&& expected.isEnabled() == actual.isEnabled();
	}

	public UserDetails getExpected() {
		return expected;
	}
}
