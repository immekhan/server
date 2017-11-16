package com.itob.jobs.batch;

import com.itob.domain.DummyForm;
import com.itob.service.dto.DummyFormDetailDTO;
import com.itob.service.mapper.DummyFormMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;

public class AbsDummyFormItemWriter implements ItemWriter<DummyForm> {

    private static final Logger LOG = LoggerFactory.getLogger(AbsDummyFormItemWriter.class);

    @Autowired private DummyFormDetailDTO dummyFormDetailDTO;
    @Autowired private DummyFormMapper absDummyFormMapper;

    //
    @Override
    public void write(List<? extends DummyForm> dummyForms) throws Exception {
        //can add extra knowledge
        dummyForms.forEach(t -> {
                LOG.trace("Adding new dummyForm {}", t.toString());

                String key = "Key"+ new Random().nextInt();
                dummyFormDetailDTO.put(key,
                    absDummyFormMapper.toDto(t));
        });
    }

}
