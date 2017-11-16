package com.itob.web.rest;

import com.itob.service.BulkService;
import com.itob.service.DummyFormService;
import com.itob.service.UtilityService;
import com.itob.service.dto.DummyFormDTO;
import com.itob.service.dto.AbsUserDTO;
import com.itob.service.dto.FileDTO;
import com.itob.service.dto.ResponseDTO;
import com.itob.util.Constants;
import com.itob.util.ErrorConstants;
import com.itob.web.rest.util.HeaderUtil;
import com.itob.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ITOBDummyForm.
 */
@RestController
@RequestMapping("/api")
public class DummyFormResource {

    private final Logger LOG = LoggerFactory.getLogger(DummyFormResource.class);

    private static final String ENTITY_NAME = "absDummyForm";

    private final DummyFormService absDummyFormService;
    @Autowired private BulkService absBulkService;
    @Autowired private UtilityService absUtilityService;

    public DummyFormResource(DummyFormService absDummyFormService) {
        this.absDummyFormService = absDummyFormService;
    }

    /**
     * POST  /abs-dummy-form : Create a new absDummyForm.
     *
     * @param absDummyFormDTO the absDummyFormDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new absDummyFormDTO, or with status 400 (Bad Request) if the absDummyForm has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/abs-dummy-form")
    @Timed
    public ResponseEntity<DummyFormDTO> createAbsDummyForm(@Valid @RequestBody DummyFormDTO absDummyFormDTO) throws URISyntaxException {
        LOG.debug("REST request to save ITOBDummyForm : {}", absDummyFormDTO);
        if (absDummyFormDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new absDummyForm cannot already have an ID")).body(null);
        }
        DummyFormDTO result = absDummyFormService.save(absDummyFormDTO);
        return ResponseEntity.created(new URI("/api/abs-dummy-form/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /abs-dummy-form : Updates an existing absDummyForm.
     *
     * @param absDummyFormDTO the absDummyFormDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated absDummyFormDTO,
     * or with status 400 (Bad Request) if the absDummyFormDTO is not valid,
     * or with status 500 (Internal Server Error) if the absDummyFormDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/abs-dummy-form")
    @Timed
    public ResponseEntity<DummyFormDTO> updateAbsDummyForm(@Valid @RequestBody DummyFormDTO absDummyFormDTO) throws URISyntaxException {
        LOG.debug("REST request to update ITOBDummyForm : {}", absDummyFormDTO);
        if (absDummyFormDTO.getId() == null) {
            return createAbsDummyForm(absDummyFormDTO);
        }
        DummyFormDTO result = absDummyFormService.save(absDummyFormDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, absDummyFormDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /abs-dummy-form : get all the absDummyForm.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of absDummyForm in body
     */
    @GetMapping("/abs-dummy-form")
    @Timed
    public ResponseEntity<List<DummyFormDTO>> getAllAbsDummyForm(@ApiParam Pageable pageable) {
        LOG.debug("REST request to get a page of ITOBDummyForm");
        Page<DummyFormDTO> page = absDummyFormService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/abs-dummy-form");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /abs-dummy-form/:id : get the "id" absDummyForm.
     *
     * @param id the id of the absDummyFormDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the absDummyFormDTO, or with status 404 (Not Found)
     */
    @GetMapping("/abs-dummy-form/{id}")
    @Timed
    public ResponseEntity<DummyFormDTO> getAbsDummyForm(@PathVariable Long id) {
        LOG.debug("REST request to get ITOBDummyForm : {}", id);
        DummyFormDTO absDummyFormDTO = absDummyFormService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(absDummyFormDTO));
    }

    /**
     * DELETE  /abs-dummy-form/:id : delete the "id" absDummyForm.
     *
     * @param id the id of the absDummyFormDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/abs-dummy-form/{id}")
    @Timed
    public ResponseEntity<Void> deleteAbsDummyForm(@PathVariable Long id) {
        LOG.debug("REST request to delete ITOBDummyForm : {}", id);
        absDummyFormService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/abs-dummy-form-custom")
    @Timed
    public ResponseDTO createCustomDummyForm(@Valid @RequestBody AbsUserDTO userDTO) throws URISyntaxException {

        //todo always verify the request first then process it
        ResponseDTO response=new ResponseDTO();
//        LOG.debug("REST request to save User : {}", userDTO);
        try {


            /*
            todo this is the code to convert png file if u want to use different formats GIF,PNG,JPG in writer
            InputStream in = new ByteArrayInputStream(userDTO.getImgFile());
            BufferedImage bImageFromConvert = ImageIO.read(in);
            File outputFile = new File("d:\\saved.png");
            ImageIO.write(bImageFromConvert, "png", outputFile);
            */

            String orgUnitId= absUtilityService.fetchLoginUserOrgUnitId();
            List<String> errorReport=new ArrayList<>();
//            /*todo uncomment for proper file checking
//            List<String> errorReport=absBulkService
//                .checkFileAndGenerateErrorReport(userDTO.getCsvFile().getByteData(), orgUnitId);

            BulkService.uploadFileAtServer(Constants.SFTP_FILE_UPLOAD_PATH, userDTO.getCsvFile().getFileName(),
                userDTO.getCsvFile().getByteData(), Constants.SFTP_SERVER_IP, Constants.SFTP_USER_PASSWORD
            , Constants.SFTP_USER_NAME);

            absDummyFormService.saveCustomFormData(userDTO);
            if(!errorReport.isEmpty()) {
                response.setStatusCode(String.valueOf(ErrorConstants.DEFAULT_EXCEPTION_CODE));
                response.setStatusValue(ErrorConstants.DEFAULT_EXCEPTION_MSG);
                response.setDataArray(errorReport.toArray());
            }else {
                response.setStatusCode(ErrorConstants.ERROR_CODE_SUCCESS);
                response.setStatusValue(ErrorConstants.ERROR_CODE_SUCCESS_MSG);
            }
        } catch (Exception e) {
            LOG.debug("ERROR : "+e);
            response.setStatusCode(String.valueOf(ErrorConstants.DEFAULT_EXCEPTION_CODE));
            response.setStatusValue(ErrorConstants.DEFAULT_EXCEPTION_MSG);
        }
        return response;
    }

    /**
     * GET  /abs-dummy-form/:id : get the "id" absDummyForm.
     *
     * @param id the id of the absDummyFormDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the absDummyFormDTO, or with status 404 (Not Found)
     */
    @GetMapping("/abs-dummy-form-custom/{id}")
    @Timed
    public ResponseDTO fetchCustomAbsDummyForm(@PathVariable Long id) {

        LOG.debug("REST request to get ITOBDummyForm : {}", id);
        DummyFormDTO absDummyFormDTO = absDummyFormService.findOne(id);
        AbsUserDTO userDTO=new AbsUserDTO();

        userDTO.setId(id);
        userDTO.setUsername("dummyusername");
        userDTO.setFirstName(absDummyFormDTO.getFirstName());
        userDTO.setLastName(absDummyFormDTO.getLastName());
        userDTO.setEmail(absDummyFormDTO.getEmail());

        FileDTO imgFile= new FileDTO();
        imgFile.setFileType(absDummyFormDTO.getImageFileContentType());
        imgFile.setByteData(absDummyFormDTO.getImageFile());

        FileDTO csvFile= new FileDTO();
        csvFile.setFileType(absDummyFormDTO.getBinaryFileContentType());
        csvFile.setByteData(absDummyFormDTO.getBinaryFile());

        userDTO.setImgFile(imgFile);
        userDTO.setCsvFile(csvFile);
        userDTO.setMiddleName("Dummy Middle Name");
        userDTO.setDisplayName("Dummy Display Name");
        userDTO.setUserType(1l);
        userDTO.setIdCountry(1l);
        userDTO.setIdCity(1l);

        ResponseDTO response=new ResponseDTO();
        response.setStatusCode(ErrorConstants.ERROR_CODE_SUCCESS);
        response.setStatusValue(ErrorConstants.ERROR_CODE_SUCCESS_MSG);
        response.setData(userDTO);

        return response;
    }

    @PostMapping("/abs-dummy-form-custom/update")
    @Timed
    public ResponseDTO updateDummyForm(@Valid @RequestBody AbsUserDTO userDTO) throws URISyntaxException {

        ResponseDTO response=new ResponseDTO();
        response.setStatusCode(ErrorConstants.ERROR_CODE_SUCCESS);
        response.setStatusValue(ErrorConstants.ERROR_CODE_SUCCESS_MSG);

        return response;
    }

    @PostMapping("/abs-dummy-form-custom/fetchFileFromSftp")
    @Timed
    public ResponseDTO fetchFileFromSftp(@Valid @RequestBody String fileName){
        ResponseDTO response=new ResponseDTO();

        response.setData(BulkService.readFileFromServer(fileName));

        response.setStatusCode(ErrorConstants.ERROR_CODE_SUCCESS);
        response.setStatusValue(ErrorConstants.ERROR_CODE_SUCCESS_MSG);

        return response;
    }
}
