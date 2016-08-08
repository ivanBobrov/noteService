package com.company.nsrv.config;


import com.company.nsrv.serial.ConfigurationSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = ConfigurationSerializer.class)
public interface IConfiguration {

    String getVersion();

    String getBuildTime();

}
