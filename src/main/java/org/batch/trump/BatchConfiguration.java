package org.batch.trump;

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import org.batch.trump.model.reader.OrganisationModel;
import org.batch.trump.model.reader.PersonModel;
import org.batch.trump.model.reader.PersonOrgModel;
import org.batch.trump.processor.OrganisationItemProcessor;
import org.batch.trump.processor.PersonItemProcessor;
import org.batch.trump.processor.PersonOrgItemProcessor;
import org.batch.trump.writer.OrganisationOrientItemWriter;
import org.batch.trump.writer.OrientItemWriter;
import org.batch.trump.writer.PersonOrgItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<PersonModel> personReader() {
        FlatFileItemReader<PersonModel> reader = new FlatFileItemReader<PersonModel>();
        reader.setResource(new ClassPathResource("person-person-connections.csv"));
        reader.setLineMapper(new DefaultLineMapper<PersonModel>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "firstPerson", "secondPerson","relationShip","source" });

            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<PersonModel>() {{
                setTargetType(PersonModel.class);
            }});
        }});
        return reader;
    }

    @Bean
    public FlatFileItemReader<OrganisationModel> organisationReader() {
        FlatFileItemReader<OrganisationModel> reader = new FlatFileItemReader<OrganisationModel>();
        reader.setResource(new ClassPathResource("org-org-connections.csv"));
        reader.setLineMapper(new DefaultLineMapper<OrganisationModel>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "firstOrganisation", "secondeOrganisation","relationShip","source" });

            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<OrganisationModel>() {{
                setTargetType(OrganisationModel.class);
            }});
        }});
        return reader;
    }


    @Bean
    public FlatFileItemReader<PersonOrgModel> organisationPersonReader() {
        FlatFileItemReader<PersonOrgModel> reader = new FlatFileItemReader<PersonOrgModel>();
        reader.setResource(new ClassPathResource("person-org-connections.csv"));
        reader.setLineMapper(new DefaultLineMapper<PersonOrgModel>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "organisation", "person","relationShip","source" });

            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<PersonOrgModel>() {{
                setTargetType(PersonOrgModel.class);
            }});
        }});
        return reader;
    }


    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean
    public OrganisationItemProcessor orgaProcessor() {
        return new OrganisationItemProcessor();
    }

    @Bean
    public PersonOrgItemProcessor personOrgProcessor() {
        return new PersonOrgItemProcessor();
    }

    @Bean ItemWriter orgaItemWriter(){
        return new OrganisationOrientItemWriter();
    }
    @Bean
    public ItemWriter orientdbWriter(){
        return new OrientItemWriter();
    }

    @Bean
    public ItemWriter personOrgWriter(){
        return new PersonOrgItemWriter();
    }

    @Bean
    public Job importTrumpJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importTrumpJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(stepPerson()).next(stepOrganisation())
                .next(stepPersonOrganisation())
                .end()
                .build();
    }

    @Bean
    public Step stepPerson() {
        return stepBuilderFactory.get("stepPerson")
                .<PersonModel, PersonModel> chunk(1000)
                .reader(personReader())
                .processor(processor())
                .writer(orientdbWriter())
                .build();
    }


    @Bean
    public Step stepOrganisation() {
        return stepBuilderFactory.get("stepOrganisation")
                .<OrganisationModel, OrganisationModel> chunk(1000)
                .reader(organisationReader())
                .processor(orgaProcessor())
                .writer(orgaItemWriter())
                .build();
    }

    @Bean
    public Step stepPersonOrganisation() {
        return stepBuilderFactory.get("stepPersonOrganisation")
                .<PersonOrgModel, PersonOrgModel> chunk(1000)
                .reader(organisationPersonReader())
                .processor(personOrgProcessor())
                .writer(personOrgWriter())
                .build();
    }

    @Bean
    OrientGraphFactory orientGraphFactory(){
        return new  OrientGraphFactory("remote:localhost/trump");
    }
    // end::jobstep[]
}
