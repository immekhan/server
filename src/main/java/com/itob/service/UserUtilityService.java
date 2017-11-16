package com.itob.service;

import com.itob.domain.*;
import com.itob.repository.*;
import com.itob.security.SecurityUtils;
import com.itob.service.dto.AbsUserDTO;
import com.itob.service.dto.UserDTO;
import com.itob.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserUtilityService {

    private final Logger LOG = LoggerFactory.getLogger(UserUtilityService.class);

    private final UserAddressRepository userAddressRepository;
    private final UserTypeRepository userTypeRepository;
    private final AbsUserRepository userRepository;
    private final UserIdentificationRepository userIdentificationRepository;
    private final UserRolesRepository userRolesRepository;
    private final RolePrivilegeRepository rolePrivilegeRepository;
    private final UserPrivilegesRepository userPrivilegesRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private final CompanyDeviceTypeRepository companyDeviceTypeRepository;

    public UserUtilityService(UserAddressRepository userAddressRepository,
                              UserTypeRepository userTypeRepository,
                              AbsUserRepository userRepository,
                              UserIdentificationRepository userIdentificationRepository,
                              UserRolesRepository userRolesRepository,
                              RolePrivilegeRepository rolePrivilegeRepository,
                              UserPrivilegesRepository userPrivilegesRepository,
                              UserCredentialsRepository userCredentialsRepository,
                              CompanyDeviceTypeRepository companyDeviceTypeRepository) {

        this.userAddressRepository = userAddressRepository;
        this.userTypeRepository = userTypeRepository;
        this.userRepository = userRepository;
        this.userIdentificationRepository = userIdentificationRepository;
        this.userRolesRepository = userRolesRepository;
        this.rolePrivilegeRepository = rolePrivilegeRepository;
        this.userPrivilegesRepository = userPrivilegesRepository;
        this.userCredentialsRepository = userCredentialsRepository;
        this.companyDeviceTypeRepository = companyDeviceTypeRepository;
    }

    /**
     * @Note This service is only for the login --> loginId = :idOrgUnit:00:admin
     * @param loginId
     * @return
     */
    public List<UserIdentification> fetchUserIdentificationByLoginUserId(String loginId){

        String data[]=loginId.split(":");
        String idApp=data[2].trim();
        String username=data[3].trim();

        LOG.debug("Authenticating {}", username);

        String lowercaseLoginStr=username.toLowerCase(Locale.ENGLISH);

        ArrayList<Long> identificationTypes=new ArrayList<>();
        identificationTypes.add(Constants.IDENTIFICATION_TYPE_USERNAME);

        ArrayList<Character> isActive=new ArrayList<>();
        isActive.add(Constants.IS_ACTIVE);

        CompanyDeviceType companyDeviceType =
            companyDeviceTypeRepository.findOne(Long.parseLong(idApp));

        String idOrgUnit=companyDeviceType.getIdOrgUnit();
        List<UserIdentification> identificationList=
            fetchUserIdentificationByIdentityAndTypes(lowercaseLoginStr,identificationTypes
                ,isActive, idOrgUnit);

        return identificationList;
    }

    public List<UserIdentification> fetchUserIdentificationByIdentityAndTypes(String identity,
                                                                              ArrayList<Long> identityTypes,
                                                                              ArrayList<Character> isActive,
                                                                              String orgUnitId){

        LOG.debug("identity {}", identity);


        //fetch identification by identity
        //fetch credentials for user
        //fetch all the privileges and assign to authorities
        Optional<List<UserIdentification>> identificationOptional = userIdentificationRepository
            .fetchByIdentityType(identityTypes,isActive,orgUnitId,identity);


        return identificationOptional.get();

    }

    @Transactional(readOnly = true)
    public UserDTO fetchLoggedInUserWithAuthorities(){

        String username= SecurityUtils.getCurrentUserLogin();
        String strIdApp = SecurityUtils.getCurrentUserAppId();

        Long idApp = Long.parseLong(strIdApp);

        CompanyDeviceType companyDeviceType =
            companyDeviceTypeRepository.findOne(idApp);

        String orgUnitId = companyDeviceType.getIdOrgUnit();

        LOG.info("username : " + username);
        LOG.info("orgUnitId : " + orgUnitId);

        ArrayList<Long> identificationTypes=new ArrayList<>();
        identificationTypes.add(Constants.IDENTIFICATION_TYPE_USERNAME);

        ArrayList<Character> isActive=new ArrayList<>();
        isActive.add(Constants.IS_ACTIVE);

        List<UserIdentification> identification=
            fetchUserIdentificationByIdentityAndTypes(username, identificationTypes,isActive,orgUnitId);

        LOG.info("No of Identifications found by username : " + identification.size());
        if(!identification.isEmpty() && identification.size()== Constants.INT_ONE){

            AbsUser user=identification.get(0).getUser();

            identificationTypes.clear();
            identificationTypes.add(Constants.IDENTIFICATION_TYPE_EMAIL);

            Optional<List<UserIdentification>> emailIdentificationOptional = userIdentificationRepository
                .fetchByUserIdIdentityType(identificationTypes,isActive,orgUnitId,user.getId());

            LOG.info("No of Identifications found by email type : " + emailIdentificationOptional.get().size());
            String email="";
            if(!emailIdentificationOptional.get().isEmpty() && emailIdentificationOptional.get().size()== Constants.INT_ONE){
                email=emailIdentificationOptional.get().get(0).getIdentification();
            }

            List<String> authorities=fetchUserAuthoritiesByUserId(user.getId(),orgUnitId);
            LOG.info("No of authorities found for user : " + authorities.size());

            UserDTO userDTO=new UserDTO(user.getId(), identification.get(0).getIdentification(),
                user.getFirstName(), user.getLastName(),email,
                user.isActive(), null, Constants.CODE_LANG_ENGLISH,
                user.getIdCreatedBy(), user.getDateCreation(), user.getIdUpdatedBy(), user.getDateUpdated(),
                new HashSet<String>(authorities));

            LOG.info("Returning userDTO");
            return userDTO;
        }
        return null;
    }

    public List<String> fetchUserAuthoritiesByUserId(Long userId,String orgUnitId){

        //fetch user roles
        Optional<List<String>> userRolesOptional= userRolesRepository
            .fetchByUserId(userId,orgUnitId);
        List<String> roleList = userRolesOptional.get();

        LOG.info("No. of roles found for user Id : " + roleList.size());

        //fetch privileges for each role
        Optional<List<String>> rolePrivilegesOptional = rolePrivilegeRepository
            .findPrivilegesByRoles(roleList);
        List<String> rolePrivilegesList=rolePrivilegesOptional.get();

        LOG.info("No. of privileges found all roles : " + rolePrivilegesList.size());

        //fetch privileges for user id
        Optional<List<String>> userPrivilegesOptional= userPrivilegesRepository
            .fetchByUserId(userId,orgUnitId);
        List<String> userPrivilegesList=userPrivilegesOptional.get();

        LOG.info("No. of privileges found for user Id : " + userPrivilegesList.size());


        List<String> allAuthorities=new ArrayList<>();

        allAuthorities.addAll(roleList);
        allAuthorities.addAll(rolePrivilegesList);
        allAuthorities.addAll(userPrivilegesList);

        LOG.info("Returning authority List");
        //remove the duplication of privileges
       return allAuthorities.stream().distinct().collect(Collectors.toList());
    }

    public void createUser(AbsUserDTO userDTO) {
        LOG.debug("User Registration started...");
        // Step No: 1
        saveUser(userDTO);
        // Step No: 2
        saveUserIdentification(userDTO, Constants.IDENTIFICATION_TYPE_USERNAME, userDTO.getUsername());
        // Step No: 3
        saveUserIdentification(userDTO, Constants.IDENTIFICATION_TYPE_EMAIL, userDTO.getEmail());
        // Step No: 4
        saveUserAddress(userDTO);
        // Step No: 5
        saveUserCredentials(userDTO);
        // Step No: 6
        saveUserRole(userDTO);
        LOG.debug("User Registration completed...");
    }

    public void saveUser(AbsUserDTO userDTO) {
        LOG.debug("Saving User Registration started...");
        AbsUser user = new AbsUser();
        user.setDisplayName(userDTO.getDisplayName());

        OrgUnit orgUnit = new OrgUnit();
        orgUnit.setId(userDTO.getIdOrgUnit());
        user.setOrgUnit(orgUnit);

        user.setBlackListReason(Constants.BLACKLIST_REASON_ACTIVE);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setActive(userDTO.isActive());

        UserType userType = new UserType();
        userType.setId(Constants.USER_TYPES_ADMINISTRATOR);
        user.setUserType(userType);

        user.setIdCreatedBy(userDTO.getCreatedBy());
        user.setDateCreation(Instant.now());

        userRepository.save(user);

        userDTO.setId(user.getId());
    }

    public void saveUserIdentification(AbsUserDTO userDTO, long identityType, String identityValue) {
        UserIdentification userIdentification = new UserIdentification();

        AbsUser user = new AbsUser();
        user.setId(userDTO.getId());
        userIdentification.setUser(user);

        userIdentification.setIdentificationType(identityType);
        userIdentification.setIdentification(identityValue);
        userIdentification.setDbActive(userDTO.isActive() ? new Character('Y') : new Character('N'));

        OrgUnit orgUnit = new OrgUnit();
        orgUnit.setId(userDTO.getIdOrgUnit());
        userIdentification.setOrgUnit(orgUnit);

        userIdentification.setIdCreatedBy(userDTO.getCreatedBy());
        userIdentification.setDateCreation(Instant.now());

        userIdentificationRepository.save(userIdentification);
    }

    public void saveUserAddress(AbsUserDTO userDTO) {
        UserAddress userAddress = new UserAddress();
        AbsUser user = new AbsUser();
        user.setId(userDTO.getId());
        userAddress.setUser(user);

        userAddress.setAddressType(Constants.ADDRESS_TYPE_POSTAL_AND_DELIVERY);
        userAddress.setAddressStatus(Constants.ADDRESS_VALID);
        userAddress.setCity(userDTO.getIdCity());
        userAddress.setCountry(userDTO.getIdCountry());
        userAddress.setPhone(userDTO.getPhone());
        userAddress.setFax(userDTO.getFax());
        userAddress.setAddress(userDTO.getAddress());
        userAddress.setIdCreatedBy(userDTO.getCreatedBy());
        userAddress.setDateCreation(Instant.now());

        userAddressRepository.save(userAddress);

    }

    public void saveUserCredentials(AbsUserDTO userDTO) {
        UserCredentials userCredentials = new UserCredentials();
        AbsUser user = new AbsUser();
        user.setId(userDTO.getId());
        userCredentials.setIdUser(user.getId());
        userCredentials.setCredentialType(Constants.CREDENTIAL_TYPE_PASSWORD);
        userCredentials.setCredentialStatus(Constants.CREDENTIAL_STATUS_OK);
        userCredentials.setDbActive(userDTO.isActive() ? new Character('Y') : new Character('N'));
        userCredentials.setCredential(userDTO.getPassword());
        userCredentials.setCountWrongCredential(Constants.COUNT_WRONG_CREDENTIALS);
        userCredentials.setIdCreatedBy(userDTO.getCreatedBy());
        userCredentials.setDateCreation(Instant.now());

        userCredentialsRepository.save(userCredentials);
    }

    public void saveUserRole(AbsUserDTO userDTO) {
        UserRoles userRoles = new UserRoles();
        userRoles.setIdUser(userDTO.getId());
        userRoles.setIdOrgUnit(userDTO.getIdOrgUnit());
        userRoles.setIdRole(Constants.ROLE_ADMIN);
        userRoles.setIdCreatedBy(userDTO.getCreatedBy());
        userRoles.setDateCreation(Instant.now());

        userRolesRepository.save(userRoles);
    }

    public boolean validateUserCredentialsByPolicies(UserCredentials userCredentials,
                                                     CredentialPolicy credentialPolicy){

        if(userCredentials.getCredential().length()<credentialPolicy.getMinLength()){
            LOG.debug("Error : credential min length policy failed");
            return false;
        }

        if(userCredentials.getCredential().length()>credentialPolicy.getMaxLength()){
            LOG.debug("Error : credential max length policy failed");
            return false;
        }
        return true;
    }

    public boolean validateUserCredentialsByPolicies(UserCredentials userCredentials,
                                                     Optional<UserTypeCredentialPolicy> userTypeCredentialPolicyOpt,
                                                     boolean isSavingCredential){

        if((userCredentials.getDateBlockUntil()==null
            || (userCredentials.getDateBlockUntil()!=null
            && !userCredentials.getDateBlockUntil().isAfter(Instant.now())))
            && userTypeCredentialPolicyOpt.isPresent()) {

            UserTypeCredentialPolicy userTypeCredentialPolicy=userTypeCredentialPolicyOpt.get();

            CredentialPolicy credentialPolicy=userTypeCredentialPolicy.getCredentialPolicy();

            if(isSavingCredential){
                validateUserCredentialsByPolicies(userCredentials,credentialPolicy);
            }

            long daysPasswordCreated = ChronoUnit.DAYS.between(userCredentials.getDateCreation(),Instant.now());

            if(credentialPolicy.getCountOfDaysPasswordTimeout()!=null
                && daysPasswordCreated>credentialPolicy.getCountOfDaysPasswordTimeout()){

                LOG.debug("Error : CountOfDaysPasswordTimeout policy failed");
                return false;
            }

            if(userCredentials.getDateLastUsed()!=null) {

                long daysPasswordUnused = ChronoUnit.DAYS.between(userCredentials.getDateLastUsed(), Instant.now());

                if (credentialPolicy.getNoOfDaysUnusedTimeout() != null
                    && daysPasswordUnused > credentialPolicy.getNoOfDaysUnusedTimeout()) {

                    LOG.debug("Error : NoOfDaysUnusedTimeout policy failed");
                    return false;
                }
            }
            //todo check if these policies needed to be check for saving or only validation
            //todo INT_BLOCK_SIZE policy needs to implement if needed
            //todo INT_BLOCK_THRESHOLD policy needs to be implemented
            //todo INT_PASSWORD_RETENTION policy needs to be implemented
            //todo INT_MIN_BLOCK_ACCOUNT policy needs to be implemented
            //todo INT_DAYS_TEMP_TIMEOUT policy needs to be implemented

        }else{
            LOG.debug("Error : User Credentials are block.");
            LOG.debug("****************OR*****************");
            LOG.debug("Error : credential policy not Found");
            //todo throw exception
            return false;
        }
        return true;
    }
}
