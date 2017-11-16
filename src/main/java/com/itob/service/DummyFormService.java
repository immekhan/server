package com.itob.service;

import com.itob.service.dto.DummyFormDTO;
import com.itob.service.dto.AbsUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AbsDummyFormBlob.
 */
public interface DummyFormService {

    /**
     * Save a absDummyFormBlob.
     *
     * @param absDummyFormBlobDTO the entity to save
     * @return the persisted entity
     */
    DummyFormDTO save(DummyFormDTO absDummyFormBlobDTO);

    /**
     *  Get all the absDummyFormBlobs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DummyFormDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" absDummyFormBlob.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DummyFormDTO findOne(Long id);

    /**
     *  Delete the "id" absDummyFormBlob.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    void saveCustomFormData(AbsUserDTO userDTO);

}
