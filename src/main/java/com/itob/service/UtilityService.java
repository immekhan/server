package com.itob.service;

import com.itob.domain.*;
import com.itob.exception.CustomException;
import com.itob.repository.*;
import com.itob.security.SecurityUtils;
import com.itob.service.dto.CityDTO;
import com.itob.service.dto.CountryDTO;
import com.itob.service.dto.UserTypeDTO;
import com.itob.util.Constants;
import com.itob.util.ErrorConstants;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UtilityService {

    private final Logger LOG = LoggerFactory.getLogger(UtilityService.class);

    private final UserTypeRepository userTypeRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final PasswordEncoder passwordEncoder;
    private final ErrorCodeRepository errorCodeRepository;
    private final OrgUnitRepository orgUnitRepository;
    private final CompanyDeviceTypeRepository companyDeviceTypeRepository;
    private final CompanyRepository companyRepository;
    private final CompanyDeviceTypeCredentialRepository companyDeviceTypeCredentialRepository;

    @Autowired
    private RestTemplate restTemplate;

    public UtilityService(UserTypeRepository userTypeRepository,
                          CityRepository cityRepository,
                          CountryRepository countryRepository,
                          ErrorCodeRepository errorCodeRepository,
                          PasswordEncoder passwordEncoder,
                          OrgUnitRepository orgUnitRepository,
                          CompanyDeviceTypeRepository companyDeviceTypeRepository,
                          CompanyRepository companyRepository,
                          CompanyDeviceTypeCredentialRepository companyDeviceTypeCredentialRepository
    ) {

        this.userTypeRepository = userTypeRepository;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.errorCodeRepository = errorCodeRepository;
        this.passwordEncoder = passwordEncoder;
        this.orgUnitRepository = orgUnitRepository;
        this.companyDeviceTypeRepository = companyDeviceTypeRepository;
        this.companyRepository = companyRepository;
        this.companyDeviceTypeCredentialRepository = companyDeviceTypeCredentialRepository;
    }

    /**
     * Fetch userType by category [ROLE] this search also uses the wild card like 'ROLE%'
     * @param roleCategory
     * @param idOrgUnit
     * @return
     */

    public List<UserTypeDTO> fetchUserTypesByRoleCategoryDTO(String roleCategory, String idOrgUnit){

        //fetch user type by id from repo
        //convert it to DTO
        //return the DTO
        List<UserType> userTypesEntity= userTypeRepository.findAllByUserTypesCategory(
            Constants.WILD_CARD_PERCENT+roleCategory+
                Constants.WILD_CARD_PERCENT,idOrgUnit
        ).get();

        return userTypesEntity.stream().map(userType -> new UserTypeDTO(userType))
            .collect(Collectors.toList());
    }

    /**
     * Either fetch the AbsUserType by userTypeId and convert it OR only convert the AbsUserType if present
     * @param userTypeId
     * @return
     */
    public UserTypeDTO fetchUserTypeDTO(Long userTypeId, UserType userType){

        //fetch user type by id from repo
        //convert it to DTO
        //return the DTO
        userType=userType!=null?userType: userTypeRepository.findOne(userTypeId);

        UserTypeDTO userTypeDTO=null;
        if(userType!=null){
            userTypeDTO=new UserTypeDTO(userType);
        }

        return userTypeDTO;
    }

    /**
     * Fetch userTypes base by id
     * @param userTypesId
     * @param userTypes
     * @return
     */
    public List<UserTypeDTO> fetchUserTypesDTO(List<Long> userTypesId, List<UserType> userTypes){

        //fetch user type by id from repo
        //convert it to DTO
        //return the DTO
        userTypes=userTypes!=null?userTypes: userTypeRepository.findAllByUserTypes(userTypesId).get();

        List<UserTypeDTO> userTypesDTO=userTypes.stream()
            .map(userType -> new UserTypeDTO(userType))
            .collect(Collectors.toList());

        return userTypesDTO;
    }

    /**
     * Fetch countries and convert it to DTO
     * @return
     */
    public List<CountryDTO> fetchCountriesDTO(){

        //fetch all the countries and convert them to DTO then return
        List<Country> countries= countryRepository.findAll();
        List<CountryDTO> countriesDTO = countries.stream()
                .map(country -> new CountryDTO(country))
                .collect(Collectors.toList());

        return countriesDTO;
    }

    /**
     * Fetch cities and convert it to DTO
     * @return
     */
    public List<CityDTO> fetchCitiesDTO(Long idCountry){

        //fetch all the cities and convert them
        List<City> cities= cityRepository.fetchByCountryId(idCountry).get();
        List<CityDTO> citiesDTO=cities.stream()
            .map(city -> new CityDTO(city))
            .collect(Collectors.toList());

        return citiesDTO;
    }

    /**
     * Returns the encoded password
     * @param password
     * @return
     */
    public String encodePassword(String password){
        return passwordEncoder.encode(password);
    }

    /**
     * Returns and verify the logged in user orgUnit
     * @return
     * @throws CustomException
     */
    public String fetchLoginUserOrgUnitId() throws CustomException {

        String strIdApp = SecurityUtils.getCurrentUserAppId();

        if(strIdApp!=null && !strIdApp.isEmpty()) {

            Long idApp=Long.parseLong(strIdApp);

            CompanyDeviceType companyDeviceType =
                companyDeviceTypeRepository.findOne(idApp);

            String idOrgUnit = companyDeviceType.getIdOrgUnit();

            if (idOrgUnit != null && !idOrgUnit.isEmpty()) {
                if (orgUnitRepository.findOne(idOrgUnit) != null) {
                    return idOrgUnit;
                }

            }
            LOG.debug(fetchExceptionMsgByCode(ErrorConstants
                .ERROR_CODE_ID_ORG_UNIT_INVALID, new Object[]{}));
        }
        LOG.debug("Logged In App Id not found");
        throw new CustomException(ErrorConstants.ERROR_CODE_BAD_REQUEST_MSG);
    }

    /**
     * Returns the iterator of List,Map, Array , Map etc. returned from Rest Api calling having JSON response
     * @param url
     * @param typeReference
     * @return
     */
    public MappingIterator<?> callRestApi(String url, TypeReference<?> typeReference){

        MappingIterator<?> iterator=null;
        try {
            ObjectMapper mapper = new ObjectMapper();
//            Use this function in case of json List,Map, collection etc
            String response = restTemplate.getForObject(url, String.class);

            JsonParser jsonParser = new JsonFactory().createParser(response);

            iterator=mapper.readValues(jsonParser, typeReference);

        } catch (Exception e) {
            LOG.debug("Error : "+e);
        }

        return iterator;
    }

    public void socketClient(String host, int port) {
        try{
            Socket socketClient= new Socket(host,port);
            System.out.println("Client: "+"Connection Established");

            BufferedReader reader =
                new BufferedReader(new InputStreamReader(socketClient.getInputStream()));

            BufferedWriter writer=
                new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
            String serverMsg;
            writer.write("8\r\n");
            writer.write("10\r\n");
            writer.flush();
            while((serverMsg = reader.readLine()) != null){
                LOG.debug("Client: " + serverMsg);
            }

        }catch(Exception e){
            LOG.debug("Error : ",e);
        }
    }

    public CompanyDeviceType fetchActiveCompanyDeviceType(Long idApp){

        CompanyDeviceType companyDeviceType =
            companyDeviceTypeRepository.findOne(idApp);

        if(companyDeviceType!=null){
            LOG.info(companyDeviceType.toString());

            if(fetchActiveCompany(companyDeviceType.getIdCompany()) !=null){
                return companyDeviceType;
            }
            return null;
        }
        LOG.info("Company Device Type not found. App Id is invalid");
        return null;
    }

    public Company fetchActiveCompany(String idCompany){

        Company company = companyRepository.findOne(idCompany);

        if(company!=null && company.isActive()){
            LOG.info(company.toString());
            return company;
        }
        LOG.info("Company not found. Company Id is invalid or company is inactive");
        return null;
    }

    public CompanyDeviceTypeCredential fetchActiveCompanyDeviceCredential(Long idApp){

        CompanyDeviceType companyDeviceType = fetchActiveCompanyDeviceType(idApp);

        if(companyDeviceType!=null){

            CompanyDeviceTypeCredential companyDeviceTypeCredential =
                companyDeviceTypeCredentialRepository.findOne(idApp);

            if(companyDeviceTypeCredential!=null){

                LOG.info(companyDeviceTypeCredential.toString());

                //verify is the client is expired or not
                if(companyDeviceTypeCredential.getDateExpiry()!=null){
                    companyDeviceTypeCredential.getDateExpiry().isAfter(Instant.now());
                    LOG.info("Client Device Type is expired");
                    return null;
                }
                return companyDeviceTypeCredential;
            }
        }
        return null;
    }

    /**
     * Fetch Error By Code
     * @param code
     * @param params
     * @return
     * @throws EntityNotFoundException
     */
    public String fetchExceptionMsgByCode(int code, Object[] params) throws EntityNotFoundException {

        ErrorCode errorCode= errorCodeRepository.findOne(new Integer(code).longValue());

        if(errorCode!=null){

            String errorMsg=errorCode.getInfo();
            if(params!=null&&params.length>0) {
                MessageFormat mf = new MessageFormat(errorMsg);
                errorMsg=mf.format(params);
            }
            return errorMsg;
        }else{
            LOG.debug("Error Code : "+ code+ " not found ");
            throw new EntityNotFoundException("Error Code not found: " + code);
        }

    }

    /**
     * This Method is for bulk file. to throw error at some specific line
     * @param code
     * @param params
     * @param lineNumber
     * @return
     * @throws EntityNotFoundException
     */
    public String fetchErrorWithLineNumber(int code, Object[] params, int lineNumber) throws EntityNotFoundException{
        String error="";
        error = "Error at line number : " + lineNumber +" ,"+
            fetchExceptionMsgByCode(code, params);
        return error;
    }
}
