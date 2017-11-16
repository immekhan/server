package com.itob.jobs.batch;

import com.itob.service.dto.DummyFormDTO;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

public class AbsDummyFormItemReader extends FlatFileItemReader<DummyFormDTO> {

    public AbsDummyFormItemReader() {

        //Set input file
        this.setResource(new ClassPathResource("/batch/spring-batch-data-sample.csv"));
        //Skip the file header line
//        this.setLinesToSkip(1);
        //Line is mapped to item (FxMarketEvent) using setLineMapper(LineMapper)
        this.setLineMapper(new DefaultLineMapper<DummyFormDTO>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {{
                        setNames(new String[] { "firstName", "lastName","email","imageFile","imageFileContentType","binaryFile","binaryFileContentType","clobTextField" });

                }});
                setFieldSetMapper(new BeanWrapperFieldSetMapper<DummyFormDTO>() {{
                        setTargetType(DummyFormDTO.class);
                }});
            }
        });
    }
}
