package com.ppkn.core.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ppkn.core.dto.UserDto;

@Configuration
@EnableBatchProcessing
public class ExcelToDbJobConfig {

	@Autowired
	private JobBuilderFactory job;

	@Autowired
	private StepBuilderFactory steps;

	@Bean(name = "excelJob")
	public Job createExcelJob(@Qualifier("excelStep") Step step) {

		return job.get("excelJob").incrementer(new RunIdIncrementer()).flow(step).end().build();
	}

	@Bean(name = "excelStep")
	public Step createExcelStep(@Qualifier("excelReader") ItemReader<UserDto> reader,
			@Qualifier("excelProcessor") ItemProcessor<UserDto, UserDto> processor,
			@Qualifier("excelWriter") ItemWriter<UserDto> writer) {

		return steps.get("excelStep").<UserDto, UserDto>chunk(10).reader(reader).processor(processor).writer(writer)
				.build();
	}

	@Bean(name = "excelReader")
	public ItemReader<UserDto> createExcelReader() {

		PoiItemReader
		return null;
	}
}
