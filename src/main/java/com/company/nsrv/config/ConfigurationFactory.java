package com.company.nsrv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Properties;

@Component
public class ConfigurationFactory {

    @Bean
    public IConfiguration getConfiguration() throws IOException {
        Properties properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("version.properties");
        if (in == null) {
            return new Configuration();
        }

        properties.load(in);
        return new Configuration(properties);
    }
}
