package com.itob.web.rest;

import com.itob.domain.UserIdentification;
import com.itob.exception.CustomException;
import com.itob.repository.UserIdentificationRepository;
import com.itob.service.BulkService;
import com.itob.service.UserUtilityService;
import com.itob.service.UtilityService;
import com.itob.service.dto.SearchCriteriaDTO;
import com.itob.service.dto.AbsUserDTO;
import com.itob.util.Constants;
import com.itob.util.ErrorConstants;
import com.itob.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api/abs")
public class AbsUserResource {

    private final Logger LOG = LoggerFactory.getLogger(UserResource.class);

    private static final String ENTITY_NAME = "userRegistration";
    private final UserIdentificationRepository userIdentificationRepository;
    private final UserUtilityService userUtilityService;
    private final UtilityService utilityService;
    @Autowired private BulkService bulkService;

    public AbsUserResource(UserUtilityService userUtilityService,
                           UserIdentificationRepository userIdentificationRepository,
                           UtilityService utilityService) {

        this.userUtilityService = userUtilityService;
        this.userIdentificationRepository = userIdentificationRepository;
        this.utilityService = utilityService;
    }

    @PostMapping("/createUser")
    @Timed
    public ResponseEntity createUser(@Valid @RequestBody AbsUserDTO userDTO) throws URISyntaxException {

        LOG.debug("REST request to save User : {}", userDTO);

        String username= userDTO.getUsername().toLowerCase();

        try {
            String orgUnitId= utilityService.fetchLoginUserOrgUnitId();

            userDTO.setPassword(utilityService.encodePassword(userDTO.getPassword()));
            userDTO.setUsername(userDTO.getUsername().toLowerCase());
            userDTO.setEmail(userDTO.getEmail().toLowerCase());

            //validate request
            createUserValidation(userDTO,orgUnitId);

            //create user
            userUtilityService.createUser(userDTO);

            return ResponseEntity.ok().body(userDTO);

        } catch (CustomException e) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", e.getMessage()))
                .body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", e.getMessage()))
                .body(ErrorConstants.DEFAULT_EXCEPTION_MSG);
        }
    }

    private void createUserValidation(AbsUserDTO userDTO, String idOrgUnit) throws CustomException {

        LOG.info("username : " + userDTO.getUsername());
        LOG.info("orgUnitId : " + idOrgUnit);
        LOG.info("email : " + userDTO.getEmail());

        ArrayList<Long> identificationTypes=new ArrayList<>();
        identificationTypes.add(Constants.IDENTIFICATION_TYPE_USERNAME);

        ArrayList<Character> isActive=new ArrayList<>();
        isActive.add(Constants.IS_ACTIVE);

        List<UserIdentification> userIdentificationList = userUtilityService.
            fetchUserIdentificationByIdentityAndTypes(userDTO.getUsername(),
            identificationTypes,isActive,idOrgUnit);

        if(!userIdentificationList.isEmpty()){
            throw new CustomException(utilityService.fetchExceptionMsgByCode(
                ErrorConstants.ERROR_CODE_USER_ALREADY_EXIST,new Object[]{userDTO.getUsername()}
            ));
        }

        identificationTypes.isEmpty();
        identificationTypes.add(Constants.IDENTIFICATION_TYPE_EMAIL);

        userIdentificationList = userUtilityService.
            fetchUserIdentificationByIdentityAndTypes(userDTO.getEmail(),
                identificationTypes,isActive,idOrgUnit);
        //validate email
        if(!userIdentificationList.isEmpty()){
            throw new CustomException(utilityService.fetchExceptionMsgByCode(
                ErrorConstants.ERROR_CODE_EMAIL_ALREADY_EXIST,new Object[]{userDTO.getEmail()}
            ));
        }
        //todo add validation for password polices
    }

    @PostMapping("/fetchUserRegistrationDropDownList")
    @Timed
    public ResponseEntity fetchUserRegistrationDropDown(@Valid @RequestBody SearchCriteriaDTO searchCriteriaDTO) throws URISyntaxException {


        return null;
    }

}
