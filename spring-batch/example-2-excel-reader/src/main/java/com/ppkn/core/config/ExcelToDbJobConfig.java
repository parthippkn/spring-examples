package com.ppkn.core.config;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.mapping.BeanWrapperRowMapper;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.ppkn.core.dto.UserDto;
import com.ppkn.core.service.UserProfileService;

@Configuration
@EnableBatchProcessing
public class ExcelToDbJobConfig {

	private static Logger logger = LoggerFactory.getLogger(ExcelToDbJobConfig.class);

	@Autowired
	private JobBuilderFactory job;

	@Autowired
	private StepBuilderFactory steps;

	@Autowired
	private AppConfigReader appConfigReader;

	@Autowired
	private UserProfileService userProfileService;

	@Bean(name = "excelJob")
	public Job createExcelJob(@Qualifier("excelStep") Step step) {

		return job.get("excelJob").incrementer(new RunIdIncrementer()).flow(step).end()
				.build();
	}

	@Bean(name = "excelStep")
	public Step createExcelStep(@Qualifier("excelReader") ItemReader<UserDto> reader,
			@Qualifier("excelProcessor") ItemProcessor<UserDto, UserDto> processor,
			@Qualifier("excelWriter") ItemWriter<UserDto> writer) {

		return steps.get("excelStep").<UserDto, UserDto>chunk(10).reader(reader)
				.processor(processor).writer(writer).build();
	}

	@Bean(name = "excelReader")
	public PoiItemReader<UserDto> excelReader() {

		PoiItemReader<UserDto> reader = new PoiItemReader<UserDto>();
		reader.setLinesToSkip(1);
		reader.setResource(
				new FileSystemResource(new File(appConfigReader.getExcelFilePath())));
		reader.setRowMapper(excelRowMapper());
		return reader;
	}

	@Bean
	public RowMapper<UserDto> excelRowMapper() {

		BeanWrapperRowMapper<UserDto> rowMapper = new BeanWrapperRowMapper<>();
		rowMapper.setTargetType(UserDto.class);
		return rowMapper;
	}

	@Bean(name = "excelProcessor")
	public ItemProcessor<UserDto, UserDto> excelItemProcessor() {

		return new ItemProcessor<UserDto, UserDto>() {
			public UserDto process(UserDto item) throws Exception {
				logger.debug("ItemProcessor : " + item.toString());
				return item;
			}
		};
	}

	@Bean(name = "excelWriter")
	public ItemWriter<UserDto> excelItemWriter() {

		return new ItemWriter<UserDto>() {
			public void write(List<? extends UserDto> items) throws Exception {
				logger.debug("ItemWriter : " + items.toString());
				userProfileService.saveUsers(items);
			}
		};
	}

}
