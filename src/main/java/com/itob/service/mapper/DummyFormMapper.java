package com.itob.service.mapper;

import com.itob.domain.DummyForm;

import com.itob.service.dto.DummyFormDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity ITOBDummyForm and its DTO AbsDummyFormDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DummyFormMapper extends EntityMapper <DummyFormDTO, DummyForm> {


    default DummyForm fromId(Long id) {
        if (id == null) {
            return null;
        }
        DummyForm absDummyFormBlob = new DummyForm();
        absDummyFormBlob.setId(id);
        return absDummyFormBlob;
    }
}
