package com.itob.security;

import com.itob.util.Constants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.LinkedHashMap;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    public static String getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = null;

        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }
        return userName;
    }

    /**
     * fetch login user orgUnit id
     * @return
     */
    public static String fetchDetailsFromSecurityContext(String key){

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        String value=null;

        if (authentication != null) {

            LinkedHashMap authDetails = (LinkedHashMap)((OAuth2Authentication) authentication).getUserAuthentication()
                .getDetails();
            value= (String)authDetails.get(key);
        }

        return value;
    }

    /**
     * Fetch the logged In orgUnit
     * @return
     */
    public static String getCurrentUserAppId(){

        String loginUserId=fetchDetailsFromSecurityContext(Constants.AUTH_KEY_USERNAME);
        String data[]=loginUserId.split(":");
        String idApp=data[2].trim();

        return idApp;
    }
    /**
     * Fetch the logged In app client id
     * @return
     */
    public static String getCurrentClientId(){
        //fetch the login client Id
        return fetchDetailsFromSecurityContext(Constants.AUTH_KEY_CLIENT_ID);
    }

    /**
     * Fetch the logged In app client secret
     * @return
     */
    public static String getCurrentClientSecret(){
        //fetch the login client secret
        return fetchDetailsFromSecurityContext(Constants.AUTH_KEY_CLIENT_SECRET);
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise
     */
    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities().stream()
                .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(AuthoritiesConstants.ANONYMOUS));
        }
        return false;
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * The name of this method comes from the isUserInRole() method in the Servlet API
     *
     * @param authority the authority to check
     * @return true if the current user has the authority, false otherwise
     */
    public static boolean isCurrentUserInRole(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority));
        }
        return false;
    }
}
