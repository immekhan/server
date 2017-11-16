package com.itob.jobs.batch;

import com.itob.domain.DummyForm;
import com.itob.service.dto.DummyFormDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class AbsDummyFormItemProcessor implements ItemProcessor<DummyFormDTO, DummyForm> {

    private static final Logger log = LoggerFactory.getLogger(AbsDummyFormItemProcessor.class);

    @Override
    public DummyForm process(final DummyFormDTO dummyFormDTO) throws Exception {

        DummyForm dummyForm=new DummyForm();
        dummyForm.setFirstName(dummyFormDTO.getFirstName().toLowerCase());
        dummyForm.setLastName(dummyFormDTO.getLastName().toLowerCase());
        dummyForm.setImageFileContentType("plain/text");
        dummyForm.setImageFile("Batch Image".getBytes());
        dummyForm.setBinaryFileContentType("plain/text");
        dummyForm.setBinaryFile("Batch Binary File".getBytes());

//        log.info("Converting (" + dummyForm + ") into (" + transformedPerson + ")");

        return dummyForm;
    }

}
