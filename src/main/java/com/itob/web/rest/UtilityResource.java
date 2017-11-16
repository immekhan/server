package com.itob.web.rest;

import com.itob.exception.CustomException;
import com.itob.repository.UserIdentificationRepository;
import com.itob.service.UserUtilityService;
import com.itob.service.UtilityService;
import com.itob.service.dto.SearchCriteriaDTO;
import com.itob.service.dto.ResponseDTO;
import com.itob.util.ErrorConstants;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api/abs/utility")
public class UtilityResource {

    private final Logger LOG = LoggerFactory.getLogger(UtilityResource.class);

    private static final String ENTITY_NAME = "userRegistration";
    private final UserIdentificationRepository absUserIdentificationRepository;
    private final UserUtilityService absUserUtilityService;
    private final UtilityService absUtilityService;

    public UtilityResource(UserUtilityService absUserUtilityService,
                           UserIdentificationRepository absUserIdentificationRepository,
                           UtilityService absUtilityService) {

        this.absUserUtilityService = absUserUtilityService;
        this.absUserIdentificationRepository = absUserIdentificationRepository;
        this.absUtilityService = absUtilityService;
    }

    @GetMapping("/fetchCountries")
    @Timed
    public ResponseDTO fetchCountryList() throws URISyntaxException {
        LOG.debug("fetchCitiesByCountryId");

        ResponseDTO response=new ResponseDTO();
        try {
            response.setStatusCode(ErrorConstants.ERROR_CODE_SUCCESS);
            response.setStatusValue(ErrorConstants.ERROR_CODE_SUCCESS_MSG);
            response.setDataList( absUtilityService.fetchCountriesDTO());

        } catch (Exception e) {
            LOG.debug("ERROR : "+e);
            response.setStatusCode(String.valueOf(ErrorConstants.DEFAULT_EXCEPTION_CODE));
            response.setStatusValue(ErrorConstants.DEFAULT_EXCEPTION_MSG);
        }
        return response;
    }

    @PostMapping("/fetchCitiesByCountryId")
    @Timed
    public ResponseDTO fetchCitiesByCountryId(@Valid @RequestBody SearchCriteriaDTO searchCriteria) throws URISyntaxException {
        LOG.debug("Enter fetchCitiesByCountryId : ");

        ResponseDTO response=new ResponseDTO();
        try {
            response.setStatusCode(ErrorConstants.ERROR_CODE_SUCCESS);
            response.setStatusValue(ErrorConstants.ERROR_CODE_SUCCESS_MSG);
            response.setDataList( absUtilityService.fetchCitiesDTO(searchCriteria.getIdCountry()));

        } catch (Exception e) {
            LOG.debug("ERROR : "+e);
            response.setStatusCode(String.valueOf(ErrorConstants.DEFAULT_EXCEPTION_CODE));
            response.setStatusValue(ErrorConstants.DEFAULT_EXCEPTION_MSG);
        }

        return  response;
    }

    @PostMapping("/fetchByLookUpQuery")
    @Timed
    public ResponseEntity fetchByLookUpQuery(String loopUpId){
//        absUtilityService
        return null;
    }

    @PostMapping("/fetchUserTypesByRoleCategory")
    @Timed
    public ResponseDTO fetchUserTypesByIds(@Valid @RequestBody String roleCategory){

        ResponseDTO response=new ResponseDTO();
        try {
            String idOrgUnit = absUtilityService.fetchLoginUserOrgUnitId();

            response.setStatusCode(ErrorConstants.ERROR_CODE_SUCCESS);
            response.setStatusValue(ErrorConstants.ERROR_CODE_SUCCESS_MSG);
            response.setDataList(absUtilityService
                .fetchUserTypesByRoleCategoryDTO(roleCategory,idOrgUnit));

        } catch (CustomException e) {
            LOG.debug("ERROR : "+e);
            response.setStatusCode(String.valueOf(ErrorConstants.DEFAULT_EXCEPTION_CODE));
            response.setStatusValue(e.getMessage());
        } catch (Exception e){
            LOG.debug("ERROR : "+e);
            response.setStatusCode(String.valueOf(ErrorConstants.DEFAULT_EXCEPTION_CODE));
            response.setStatusValue(ErrorConstants.DEFAULT_EXCEPTION_MSG);
        }
        return response;
    }


}
