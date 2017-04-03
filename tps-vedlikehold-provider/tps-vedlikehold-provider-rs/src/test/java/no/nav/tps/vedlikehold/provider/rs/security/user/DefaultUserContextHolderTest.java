package no.nav.tps.vedlikehold.provider.rs.security.user;

import static no.nav.tps.vedlikehold.provider.rs.security.user.UserRole.ROLE_READ_Q;
import static no.nav.tps.vedlikehold.provider.rs.security.user.UserRole.ROLE_READ_T;
import static no.nav.tps.vedlikehold.provider.rs.security.user.UserRole.ROLE_WRITE_Q;
import static no.nav.tps.vedlikehold.provider.rs.security.user.UserRole.ROLE_WRITE_T;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.nav.tps.vedlikehold.domain.service.user.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetails;



@RunWith(MockitoJUnitRunner.class)
public class DefaultUserContextHolderTest {

    private static final String USERNAME = "username";
    private static final String DISPLAY_NAME = "displayName";
    private static final List<? extends GrantedAuthority> ROLES = Arrays.asList(ROLE_READ_T, ROLE_WRITE_T, ROLE_READ_Q, ROLE_WRITE_Q);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private LdapUserDetails userDetailsMock;

    @Mock
    private SecurityContext securityContextMock;

    @InjectMocks
    private DefaultUserContextHolder userContextHolder;

    @Mock
    private Authentication authenticationMock;

    @Before
    public void setUp() {
        SecurityContextHolder.setContext(securityContextMock);

        doReturn(authenticationMock).when(securityContextMock).getAuthentication();
        doReturn(userDetailsMock).when(authenticationMock).getPrincipal();

        when(authenticationMock.isAuthenticated()).thenReturn(true);

        when(userDetailsMock.getUsername()).thenReturn(USERNAME);
        doReturn(ROLES).when(userDetailsMock).getAuthorities();
        when(userDetailsMock.getDn()).thenReturn(DISPLAY_NAME);
    }

    @Test
    public void getDisplayNameReturnsDisplayNameFromUserDetails() {
        assertThat(userContextHolder.getDisplayName(), is(DISPLAY_NAME));
    }

    @Test
    public void getDisplayNameThrowsExceptionIfPrincipalIsOfWrongType() {
        Principal principalMock = mock(Principal.class);
        when(authenticationMock.getPrincipal()).thenReturn(principalMock);

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(containsString(principalMock.getClass().toString()));

        userContextHolder.getDisplayName();
    }

    @Test
    public void getUsernameReturnsUsernameFromUserDetails() {
        assertThat(userContextHolder.getUsername(), is(USERNAME));
    }

    @Test
    public void getUsernameThrowsExceptionIfPrincipalIsOfWrongType() {
        Principal principalMock = mock(Principal.class);

        when(authenticationMock.getPrincipal()).thenReturn(principalMock);

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(containsString(principalMock.getClass().toString()));

        userContextHolder.getUsername();
    }

    @Test
    public void getAuthenticationReturnsObjectFromSecurityContext() {
        assertThat(userContextHolder.getAuthentication(), is(authenticationMock));
    }

    @Test
    public void isAuthenticatedReturnsFalseIfAuthenticationIsNull() {
        when(securityContextMock.getAuthentication()).thenReturn(null);

        assertThat(userContextHolder.isAuthenticated(), is(false));
    }

    @Test
    public void isAuthenticatedReturnsFalseIfAuthenticationIsNotNullAndNotAuthenticated() {
        when(authenticationMock.isAuthenticated()).thenReturn(false);

        assertThat(userContextHolder.isAuthenticated(), is(false));
    }

    @Test
    public void isAuthenticatedReturnsTrueIfAuthenticationIsNotNullAndAuthenticated() {
        assertThat(userContextHolder.isAuthenticated(), is(true));
    }

    @Test
    public void getUserReturnsUser() {
        User result = userContextHolder.getUser();

        assertThat(result, is(notNullValue()));
        assertThat(result.getName(), is(DISPLAY_NAME));
        assertThat(result.getUsername(), is(USERNAME));
        assertThat(result.getToken(), is(nullValue()));
        assertThat(result.getRoles(), hasSize(4));
    }

    @Test
    public void getRolesReturnsRolesFromAuthentication() {
        doReturn(ROLES).when(userDetailsMock).getAuthorities();
        assertThat(userContextHolder.getRoles(), containsInAnyOrder((GrantedAuthority) ROLE_READ_T, ROLE_WRITE_T, ROLE_READ_Q, ROLE_WRITE_Q));
    }

    @Test
    public void getRolesThrowsExceptionIfPrincipalIsOfWrongType() {
        Principal principalMock = mock(Principal.class);

        when(authenticationMock.getPrincipal()).thenReturn(principalMock);

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(containsString(principalMock.getClass().toString()));

        userContextHolder.getRoles();
    }

    @Test
    public void logoutLogsOutIfAuthenticated() {
        HttpServletRequest requestMock = mock(HttpServletRequest.class);
        HttpServletResponse responseMock = mock(HttpServletResponse.class);

        userContextHolder.logout(requestMock, responseMock);

        verify(securityContextMock).setAuthentication(null);
    }

    @Test
    public void logoutDoesNothingIfNotAuthenticated() {
        HttpServletRequest requestMock = mock(HttpServletRequest.class);
        HttpServletResponse responseMock = mock(HttpServletResponse.class);

        when(authenticationMock.isAuthenticated()).thenReturn(false);

        userContextHolder.logout(requestMock, responseMock);

        verify(securityContextMock, never()).setAuthentication(null);
    }
}
