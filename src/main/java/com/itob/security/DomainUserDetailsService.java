package com.itob.security;

import com.itob.domain.*;
import com.itob.repository.*;
import com.itob.service.UserUtilityService;
import com.itob.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger LOG = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserCredentialsRepository userCredentialsRepository;
    private final UserUtilityService userUtilityService;
    private final CompanyDeviceTypeCredentialRepository companyDeviceTypeCredentialRepository;
    private final UserTypeCredentialPolicyRepository userTypeCredentialPolicyRepository;

    public DomainUserDetailsService(UserCredentialsRepository userCredentialsRepository,
                                    UserUtilityService userUtilityService,
                                    CompanyDeviceTypeCredentialRepository companyDeviceTypeCredentialRepository,
                                    UserTypeCredentialPolicyRepository userTypeCredentialPolicyRepository) {

        this.userCredentialsRepository=userCredentialsRepository;
        this.userUtilityService = userUtilityService;
        this.companyDeviceTypeCredentialRepository = companyDeviceTypeCredentialRepository;
        this.userTypeCredentialPolicyRepository = userTypeCredentialPolicyRepository;

    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {

        String appId="";
        String idOrgUnit="";
        String lowercaseLoginStr="";

        //fetch app details by appId
//        absCompanyDeviceTypeCredential.fetchCompanyDeviceTypeCredentialByAppId();

        List<UserIdentification> identificationList = userUtilityService
            .fetchUserIdentificationByLoginUserId(login);

        if(!identificationList.isEmpty()
            && identificationList.size()== Constants.INT_ONE
            && identificationList.get(0).getUser().isActive()
            && identificationList.get(0).getUser().getBlackListReason()== Constants.INT_ZERO){

            AbsUser user=identificationList.get(0).getUser();

            idOrgUnit=user.getOrgUnit().getId();
            lowercaseLoginStr=identificationList.get(0).getIdentification();

            Long userId=user.getId();

            ArrayList<Long> credentialTypes=new ArrayList<>();
            credentialTypes.add(Constants.CREDENTIAL_TYPE_PASSWORD);

            Optional<UserCredentials> credentialsOptional=userCredentialsRepository
                .fetchByCredentialTypeAndUserId(credentialTypes, Constants.IS_ACTIVE,
                    userId, Constants.CREDENTIAL_STATUS_OK);

            if(credentialsOptional.isPresent()){

                UserCredentials credentials=credentialsOptional.get();

                Optional<UserTypeCredentialPolicy> credentialPolicyOpt = userTypeCredentialPolicyRepository
                    .fetchPolicyByUserTypeAndCredentialType(user.getUserType().getId(),
                        Constants.CREDENTIAL_TYPE_PASSWORD);

                LOG.info("******Validating Credentials******");
                if(userUtilityService.validateUserCredentialsByPolicies(
                    credentials, credentialPolicyOpt, Constants.FALSE ) ) {

                    String password=credentials.getCredential();

                    List<String> allAuthorities= userUtilityService.fetchUserAuthoritiesByUserId(userId,idOrgUnit);

                    //add all the privileges to the authority list
                    List<GrantedAuthority> grantedAuthorities =allAuthorities.stream()
                        .map(authority -> new SimpleGrantedAuthority(authority))
                        .collect(Collectors.toList());
                    //return the user
                    return new org.springframework.security.core.userdetails.User(lowercaseLoginStr,
                        password,
                        grantedAuthorities);

                }else{
                    credentials.setCountWrongCredential(credentials.getCountWrongCredential().intValue()+1);
                    userCredentialsRepository.save(credentials);//update user credentials

                    LOG.debug("Error : While validating credentials according to polices");
                    throw new UsernameNotFoundException("User "+lowercaseLoginStr+" credentials validation failed");
                }

            }else{
                LOG.debug("Error : Credentials for User "+lowercaseLoginStr+" not found");
                throw new UsernameNotFoundException("User "+lowercaseLoginStr+" credentials not found");
            }

        }else{
            LOG.debug("Error : User "+lowercaseLoginStr+" not found or Inactive");
            throw new UsernameNotFoundException("User "+lowercaseLoginStr+" not found");
        }


        //This was the implementation before customization
        /*LOG.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        Optional<User> userFromDatabase = userRepository.findOneWithAuthoritiesByLogin(lowercaseLogin);
        return userFromDatabase.map(user -> {
            if (!user.getActivated()) {
                throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
            }
            List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
            return new org.springframework.security.core.userdetails.User(lowercaseLogin,
                user.getPassword(),
                grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the " +
        "database"));*/
    }
}
