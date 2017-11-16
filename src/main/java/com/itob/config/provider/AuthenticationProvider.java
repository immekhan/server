package com.itob.config.provider;

import com.itob.domain.CompanyDeviceTypeCredential;
import com.itob.domain.UserCredentials;
import com.itob.domain.UserIdentification;
import com.itob.domain.UserTypeCredentialPolicy;
import com.itob.repository.UserCredentialsRepository;
import com.itob.repository.UserTypeCredentialPolicyRepository;
import com.itob.service.UserUtilityService;
import com.itob.service.UtilityService;
import com.itob.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final Logger LOG = LoggerFactory.getLogger(AuthenticationProvider.class);

    @Autowired
    protected UserDetailsService userDetailsService;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    private UserUtilityService userUtilityService;
    @Autowired
    private UserTypeCredentialPolicyRepository userTypeCredentialPolicyRepository;
    @Autowired
    private UtilityService utilityService;
    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    public AuthenticationProvider(){
        super();
    }

    @Transactional
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {

        String rawPass=(String)authentication.getCredentials();

        //1. fetch user by identification
        List<UserIdentification> identificationList = userUtilityService
            .fetchUserIdentificationByLoginUserId((String)authentication.getPrincipal());

        //2. fetch credential policies by user type and credential type
        Optional<UserTypeCredentialPolicy> credentialPolicyOpt = userTypeCredentialPolicyRepository
            .fetchPolicyByUserTypeAndCredentialType(identificationList.get(0).getUser().getUserType().getId(),
            Constants.CREDENTIAL_TYPE_PASSWORD);

        //3. fetch credential object to update according to the checks
        ArrayList<Long> credentialTypes=new ArrayList<>();
        credentialTypes.add(Constants.CREDENTIAL_TYPE_PASSWORD);

        Optional<UserCredentials> credentialsOptional = userCredentialsRepository
            .fetchByCredentialTypeAndUserId(credentialTypes, Constants.IS_ACTIVE,
                identificationList.get(0).getUser().getId(), Constants.CREDENTIAL_STATUS_OK);

        UserCredentials userCredentials=credentialsOptional.get();

        //check if the policy is present the check accordingly as some of the checks are already being verified
        if(!credentialPolicyOpt.isPresent()
            || !rawPass.matches(credentialPolicyOpt.get().getCredentialPolicy().getRegex())
            || !passwordEncoder.matches(rawPass,userDetails.getPassword())){

            LOG.debug("\n\n\n**Error**: Password matches the regex policy -> '"
                + !rawPass.matches(credentialPolicyOpt.get().getCredentialPolicy().getRegex())
                +"'\n**OR**   : Invalid Password\n\n");


            userCredentials.setCountWrongCredential(
                userCredentials.getCountWrongCredential().intValue()+ Constants.INT_ONE);
            userCredentials.setIdUpdatedBy(identificationList.get(0).getUser().getId());
            userCredentials.setDateUpdated(Instant.now());
            userCredentialsRepository.save(userCredentials);//update user credentials

            throw new BadCredentialsException("Password could not be validated.");

        }else{

            LOG.info("***Credential verifies successfully***");
            userCredentials.setCountWrongCredential(Constants.INT_ZERO);
            userCredentials.setDateLastUsed(Instant.now());
            userCredentials.setIdUpdatedBy(identificationList.get(0).getUser().getId());
//            userCredentials.setDateUpdated(Instant.now());
            userCredentialsRepository.save(userCredentials);//update user credentials
        }

    }

    @Override
    protected UserDetails retrieveUser(String username,
                                       UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {

        String data[]=username.split(":");

        if (data.length>3) {

            String idApp = data[2].trim();

            CompanyDeviceTypeCredential companyDeviceTypeCredential =
                utilityService.fetchActiveCompanyDeviceCredential(Long.parseLong(idApp));

            if(companyDeviceTypeCredential!=null
                && fetchFromContextFromUserAuthToken(authentication, Constants.AUTH_KEY_CLIENT_ID)
                    .equals(companyDeviceTypeCredential.getIdClient())
                && fetchFromContextFromUserAuthToken(authentication, Constants.AUTH_KEY_CLIENT_SECRET)
                .equals(companyDeviceTypeCredential.getClientCredentials())) {


                final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (userDetails != null) {
                    return userDetails;
                }

            }else {
                LOG.debug("Error : Client Id and Secret not found or is invalid");
            }
        }
        LOG.debug("Error : Invalid username");
        throw new UsernameNotFoundException(
            MessageFormat.format("User {0} could not be loaded", username));
    }

    private String fetchFromContextFromUserAuthToken(UsernamePasswordAuthenticationToken authentication, String key){
        String value=null;

        if (authentication != null) {

            LinkedHashMap authDetails =
                (LinkedHashMap)((UsernamePasswordAuthenticationToken) authentication).getDetails();
            value= (String)authDetails.get(key);
        }

        return value;
    }
}
