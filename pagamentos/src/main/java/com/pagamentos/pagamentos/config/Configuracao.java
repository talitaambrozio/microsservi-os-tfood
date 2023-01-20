package com.pagamentos.pagamentos.config;

import javax.sql.DataSource;

import org.modelmapper.ModelMapper;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configuracao {

	
	@Bean
	public ModelMapper obterModelMapper() {
		return new ModelMapper();
	}
	
	@Bean
    public DataSource getDataSource() {
        return DataSourceBuilder.create()
          .driverClassName("com.mysql.cj.jdbc.Driver")
          .url("jdbc:mysql://localhost:3306/pagamentos?createDatabaseIfNotExist=True")
          .username("root")
          .password("root")
          .build();	
    }
}
