package com.itob.service.impl;

import com.itob.domain.DummyForm;
import com.itob.repository.DummyFormRepository;
import com.itob.service.DummyFormService;
import com.itob.service.dto.DummyFormDTO;
import com.itob.service.dto.AbsUserDTO;
import com.itob.service.mapper.DummyFormMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ITOBDummyForm.
 */
@Service
@Transactional
public class DummyFormServiceImpl implements DummyFormService {

    private final Logger log = LoggerFactory.getLogger(DummyFormServiceImpl.class);

    private final DummyFormRepository dummyFormRepository;

    private final DummyFormMapper dummyFormMapper;

    public DummyFormServiceImpl(DummyFormRepository dummyFormRepository, DummyFormMapper dummyFormMapperImpl) {
        this.dummyFormRepository = dummyFormRepository;
        this.dummyFormMapper = dummyFormMapperImpl;
    }

    /**
     * Save a absDummyForm.
     *
     * @param absDummyFormDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DummyFormDTO save(DummyFormDTO absDummyFormDTO) {
        log.debug("Request to save ITOBDummyForm : {}", absDummyFormDTO);
        DummyForm absDummyForm = dummyFormMapper.toEntity(absDummyFormDTO);
        absDummyForm = dummyFormRepository.save(absDummyForm);
        return dummyFormMapper.toDto(absDummyForm);
    }

    /**
     *  Get all the absDummyForms.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DummyFormDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AbsDummyForms");
        return dummyFormRepository.findAll(pageable)
            .map(dummyFormMapper::toDto);
    }

    /**
     *  Get one absDummyForm by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DummyFormDTO findOne(Long id) {
        log.debug("Request to get ITOBDummyForm : {}", id);
        DummyForm absDummyForm = dummyFormRepository.findOne(id);
        return dummyFormMapper.toDto(absDummyForm);
    }

    /**
     *  Delete the  absDummyForm by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ITOBDummyForm : {}", id);
        dummyFormRepository.delete(id);
    }

    @Override
    public void saveCustomFormData(AbsUserDTO userDTO) {

        DummyForm dummyForm= new DummyForm();
        dummyForm.setFirstName(userDTO.getFirstName());
        dummyForm.setLastName(userDTO.getLastName());
        dummyForm.setEmail(userDTO.getEmail());
        dummyForm.setImageFileName(userDTO.getImgFile().getFileName());
        dummyForm.setImageFileContentType(userDTO.getImgFile().getFileType());
        dummyForm.setImageFile(userDTO.getImgFile().getByteData());
        dummyForm.setBinaryFileName(userDTO.getCsvFile().getFileName());
        dummyForm.setBinaryFileContentType(userDTO.getCsvFile().getFileType());
        dummyForm.setBinaryFile(userDTO.getCsvFile().getByteData());
        dummyFormRepository.save(dummyForm);
    }
}
