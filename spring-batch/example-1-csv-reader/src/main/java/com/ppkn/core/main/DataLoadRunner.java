package com.ppkn.core.main;

import java.util.UUID;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.ppkn.core.config.ServiceConfig;

public class DataLoadRunner {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ServiceConfig.class);

		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("csvToDb");
		System.out.println("Starting the batch job : " + job);
		try {
			// if we want to run same job for multiple times then create UUID as
			// follows and convert as job params
			final JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
			jobParametersBuilder.addString("instance_id", UUID.randomUUID().toString(), true);

			JobExecution jobExecution = jobLauncher.run(job, jobParametersBuilder.toJobParameters());
			System.out.println(
					"JobExecution : " + jobExecution.getStatus() + "; Exist Status : " + jobExecution.getExitStatus());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
