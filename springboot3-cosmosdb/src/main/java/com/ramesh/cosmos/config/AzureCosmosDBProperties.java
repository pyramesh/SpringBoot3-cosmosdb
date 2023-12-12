package com.ramesh.cosmos.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *  Holds cosmos db configuration
 */
@ConfigurationProperties(prefix = "azure.cosmos")
@Getter
@Setter
public class AzureCosmosDBProperties {

    private String uri;
    private String key;
    private String secondaryKey;
    private String database;
    private boolean queryMetricsEnabled;
    private boolean responseDiagnosticsEnabled;

}
