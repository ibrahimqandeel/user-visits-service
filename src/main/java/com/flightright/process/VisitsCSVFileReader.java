package com.flightright.process;

import com.flightright.dto.VisitorDto;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Component
@EnableBatchProcessing
public class VisitsCSVFileReader {

    @Value("${file.path}")
    private String filePath;
    private final StepBuilderFactory stepBuilder;
    private final VisitCounterProcessor visitCounterProcessor;
    private final VisitsCounterWriter visitsCounterWriter;

    public VisitsCSVFileReader(StepBuilderFactory stepBuilder, VisitCounterProcessor visitCounterProcessor, VisitsCounterWriter visitsCounterWriter) {
        this.stepBuilder = stepBuilder;
        this.visitCounterProcessor = visitCounterProcessor;
        this.visitsCounterWriter = visitsCounterWriter;
    }

    public Step step() {
        return stepBuilder.get("step-1").<VisitorDto, VisitorDto>chunk(10)
                .reader(reader())
                .processor(visitCounterProcessor)
                .writer(itemWriter())
                .taskExecutor(taskExecutor())
                .build();
    }

    private FlatFileItemReader<VisitorDto> reader() {
        FlatFileItemReader<VisitorDto> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(filePath));
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper());
        return reader;
    }

    private LineMapper<VisitorDto> lineMapper() {
        DefaultLineMapper<VisitorDto> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(true);
        lineTokenizer.setNames("email", "phone", "source");

        BeanWrapperFieldSetMapper<VisitorDto> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(VisitorDto.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    private ItemWriterAdapter itemWriter() {
        ItemWriterAdapter writerAdapter = new ItemWriterAdapter<>();
        writerAdapter.setTargetObject(visitsCounterWriter);
        writerAdapter.setTargetMethod("count");
        return writerAdapter;
    }

    private TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(10);
        return taskExecutor;
    }
}
