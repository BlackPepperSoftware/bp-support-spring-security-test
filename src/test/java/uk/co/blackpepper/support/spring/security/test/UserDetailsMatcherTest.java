package uk.co.blackpepper.support.spring.security.test;

import org.hamcrest.Description;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

import static uk.co.blackpepper.support.spring.security.test.UserDetailsMatcher.userDetailsEqualTo;

public class UserDetailsMatcherTest {

	@Test
	public void userDetailsEqualToReturnsMatcher() {
		UserDetails expectedUserDetails = mock(UserDetails.class);
		
		UserDetailsMatcher actual = userDetailsEqualTo(expectedUserDetails);
		
		assertThat(actual.getExpected(), is(expectedUserDetails));
	}
	
	@Test
	public void describeToAppendsDescription() {
		UserDetails expected = mock(UserDetails.class);
		Description description = mock(Description.class);

		userDetailsEqualTo(expected).describeTo(description);

		verify(description).appendValue(expected);
	}

	@Test
	public void matchesSafelyWhenEqualReturnsTrue() {
		UserDetails expected = newUserDetails();
		UserDetails actual = newUserDetails();

		assertThat(userDetailsEqualTo(expected).matchesSafely(actual), is(true));
	}

	@Test
	public void matchesSafelyWhenDifferentAuthoritiesReturnsFalse() {
		UserDetails expected = newUserDetails();
		doReturn(createAuthorityList("x")).when(expected).getAuthorities();
		UserDetails actual = newUserDetails();
		doReturn(createAuthorityList("y")).when(expected).getAuthorities();
	
		assertThat(userDetailsEqualTo(expected).matchesSafely(actual), is(false));
	}

	@Test
	public void matchesSafelyWhenDifferentPasswordReturnsFalse() {
		UserDetails expected = newUserDetails();
		when(expected.getPassword()).thenReturn("x");
		UserDetails actual = newUserDetails();
		when(actual.getPassword()).thenReturn("y");
	
		assertThat(userDetailsEqualTo(expected).matchesSafely(actual), is(false));
	}

	@Test
	public void matchesSafelyWhenDifferentUsernameReturnsFalse() {
		UserDetails expected = newUserDetails();
		when(expected.getUsername()).thenReturn("x");
		UserDetails actual = newUserDetails();
		when(actual.getUsername()).thenReturn("y");

		assertThat(userDetailsEqualTo(expected).matchesSafely(actual), is(false));
	}

	@Test
	public void matchesSafelyWhenDifferentAccountNonExpiredReturnsFalse() {
		UserDetails expected = newUserDetails();
		when(expected.isAccountNonExpired()).thenReturn(false);
		UserDetails actual = newUserDetails();
		when(actual.isAccountNonExpired()).thenReturn(true);

		assertThat(userDetailsEqualTo(expected).matchesSafely(actual), is(false));
	}

	@Test
	public void matchesSafelyWhenDifferentAccountNonLockedReturnsFalse() {
		UserDetails expected = newUserDetails();
		when(expected.isAccountNonLocked()).thenReturn(false);
		UserDetails actual = newUserDetails();
		when(actual.isAccountNonLocked()).thenReturn(true);

		assertThat(userDetailsEqualTo(expected).matchesSafely(actual), is(false));
	}

	@Test
	public void matchesSafelyWhenDifferentCredentialsNonExpiredReturnsFalse() {
		UserDetails expected = newUserDetails();
		when(expected.isCredentialsNonExpired()).thenReturn(false);
		UserDetails actual = newUserDetails();
		when(actual.isCredentialsNonExpired()).thenReturn(true);

		assertThat(userDetailsEqualTo(expected).matchesSafely(actual), is(false));
	}

	@Test
	public void matchesSafelyWhenDifferentEnabledReturnsFalse() {
		UserDetails expected = newUserDetails();
		when(expected.isEnabled()).thenReturn(false);
		UserDetails actual = newUserDetails();
		when(actual.isEnabled()).thenReturn(true);
	
		assertThat(userDetailsEqualTo(expected).matchesSafely(actual), is(false));
	}

	private static UserDetails newUserDetails() {
		UserDetails userDetails = mock(UserDetails.class);
		when(userDetails.getUsername()).thenReturn("_username");
		return userDetails;
	}
}
