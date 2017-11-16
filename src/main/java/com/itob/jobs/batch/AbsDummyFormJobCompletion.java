package com.itob.jobs.batch;

import com.itob.domain.DummyForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AbsDummyFormJobCompletion extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(AbsDummyFormJobCompletion.class);

    @Autowired private JdbcTemplate jdbcTemplate;

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            RowMapper rowMapper= (rs, row) ->
                new DummyForm(rs.getString(1), rs.getString(2));
//            List<ITOBDummyForm> results = jdbcTemplate.query("SELECT first_name, last_name FROM abs_dummy_form", rowMapper);
            /*List<ITOBDummyForm> results = jdbcTemplate.query("SELECT first_name, last_name FROM abs_dummy_form", new RowMapper<ITOBDummyForm>() {
                @Override
                public ITOBDummyForm mapRow(ResultSet rs, int row) throws SQLException {
                    return new ITOBDummyForm(rs.getString(1), rs.getString(2));
                }
            });*/

//            for (ITOBDummyForm dummyForm : results) {
//                log.info("Found <" + dummyForm + "> in the database.");
//            }

        }
    }

    /*@Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.trace("Loading the results into file");
            Path path = Paths.get("prices.csv");
            try (BufferedWriter fileWriter = Files.newBufferedWriter(path)) {
                fileWriter.write(HEADER);
                fileWriter.newLine();
                for (StockPriceDetails pd : fxMarketPricesStore.values()) {
                    fileWriter.write(new StringBuilder().append(pd.getStock())
                        .append(LINE_DILM).append(pd.getOpen())
                        .append(LINE_DILM).append(pd.getClose())
                        .append(LINE_DILM).append(pd.getLow())
                        .append(LINE_DILM).append(pd.getHigh()).toString());
                    fileWriter.newLine();
                }
            } catch (Exception e) {
                log.error("Fetal error: error occurred while writing {} file", path.getFileName());
            }
        }
    }*/
}
