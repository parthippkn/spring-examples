package com.ppkn.core.config;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.ppkn.core.dto.UserDto;
import com.ppkn.core.service.UserProfileService;

@Configuration
@EnableBatchProcessing
public class CsvToDbJobConfig {

	private static Logger logger = LoggerFactory.getLogger(CsvToDbJobConfig.class);

	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;

	@Autowired
	private AppConfigReader appConfigReader;

	@Autowired
	private UserProfileService userProfileService;

	@Bean(name = "csvToDb")
	public Job job(@Qualifier("csvFileToDatabaseStep") Step step1) {

		return jobs.get("csvToDb").incrementer(new RunIdIncrementer()).flow(step1).end().build();
	}

	@Bean(name = "csvFileToDatabaseStep")
	public Step step1(@Qualifier("csvItemReader") ItemReader<UserDto> itemReader,
			@Qualifier("csvItemProcessor") ItemProcessor<UserDto, UserDto> itemProcessor,
			@Qualifier("csvItemWriter") ItemWriter<UserDto> itemWriter) {

		return steps.get("csvFileToDatabaseStep").<UserDto, UserDto>chunk(10).reader(itemReader)
				.processor(itemProcessor).writer(itemWriter).listener(new ItemReadListener<UserDto>() {

					public void beforeRead() {
						logger.debug("Before Reading..");
					}

					public void afterRead(UserDto item) {
						logger.debug("After Reading an item : " + item);
					}

					public void onReadError(Exception ex) {
						logger.error("PLEASE CHECK!! Error while reading an item : " + ex.getMessage(), ex);

					}

				}).build();
	}

	@Bean(name = "csvItemReader")
	public ItemReader<UserDto> csvItemReader() {

		try {
			FlatFileItemReader<UserDto> reader = new FlatFileItemReader<UserDto>();
			reader.setLinesToSkip(1);
			reader.setResource(new FileSystemResource(new File(appConfigReader.getCsvFilePath())));
			reader.setLineMapper(createLineMapper());

			return reader;
		} catch (Exception ex) {
			throw new IllegalStateException("PLEASE CHECK!! Error while reading file : " + ex.getMessage(), ex);
		}

	}

	@Bean(name = "csvItemProcessor")
	public ItemProcessor<UserDto, UserDto> csvItemProcessor() {

		return new ItemProcessor<UserDto, UserDto>() {
			public UserDto process(UserDto item) throws Exception {
				logger.debug("ItemProcessor : " + item.toString());
				return item;
			}
		};
	}

	@Bean(name = "csvItemWriter")
	public ItemWriter<UserDto> csvItemWriter() {

		return new ItemWriter<UserDto>() {
			public void write(List<? extends UserDto> items) throws Exception {
				logger.debug("ItemWriter : " + items.toString());
				userProfileService.saveUsers(items);
			}
		};
	}

	private LineMapper<UserDto> createLineMapper() {

		DefaultLineMapper<UserDto> lineMapper = new DefaultLineMapper<UserDto>();

		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setDelimiter(appConfigReader.getCsvFileDelim());
		tokenizer.setNames(appConfigReader.getCsvFileHeader().split(","));
		lineMapper.setLineTokenizer(tokenizer);

		BeanWrapperFieldSetMapper<UserDto> fieldSetMapper = new BeanWrapperFieldSetMapper<UserDto>();
		fieldSetMapper.setTargetType(UserDto.class);

		lineMapper.setFieldSetMapper(fieldSetMapper);
		return lineMapper;

	}
}
