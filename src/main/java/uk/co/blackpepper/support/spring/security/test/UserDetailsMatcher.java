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
