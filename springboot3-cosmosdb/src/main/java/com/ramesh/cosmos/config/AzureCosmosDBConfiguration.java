package com.ramesh.cosmos.config;

import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.DirectConnectionConfig;
import com.azure.spring.data.cosmos.config.AbstractCosmosConfiguration;
import com.azure.spring.data.cosmos.config.CosmosConfig;
import com.azure.spring.data.cosmos.core.ResponseDiagnostics;
import com.azure.spring.data.cosmos.core.ResponseDiagnosticsProcessor;
import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;
import com.azure.spring.data.cosmos.repository.config.EnableReactiveCosmosRepositories;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.lang.Nullable;

/**
 *  cosmos db configuration
 */
@Configuration
@EnableConfigurationProperties(AzureCosmosDBProperties.class)
@EnableCosmosRepositories(basePackages = "com.ramesh.cosmos.repository")
@PropertySource("classpath:application.properties")
@Slf4j
public class AzureCosmosDBConfiguration extends AbstractCosmosConfiguration {

  private final AzureCosmosDBProperties properties;

  AzureCosmosDBConfiguration(AzureCosmosDBProperties properties) {
    this.properties = properties;
  }

  /**
   *  Create a cosmos client builder to interact with cosmos DB
   *
   * @return cosmos client {@link CosmosClientBuilder}
   */
  @Bean
  public CosmosClientBuilder cosmosBuilderClient() {
    return new CosmosClientBuilder()
            .endpoint(properties.getUri())
            .key(properties.getKey())
            .directMode(DirectConnectionConfig.getDefaultConfig());
  }

  /**
   *  Create a cosmos configuration
   *
   * @return cosmos configuration {@link CosmosConfig}
   */
  @Override
  @Bean
  public CosmosConfig cosmosConfig() {
    return CosmosConfig.builder()
            .responseDiagnosticsProcessor(new ResponseDiagnosticsProcessorImplementation(properties))
            .enableQueryMetrics(properties.isQueryMetricsEnabled()).build();
  }

  private static class ResponseDiagnosticsProcessorImplementation implements ResponseDiagnosticsProcessor {

    private final AzureCosmosDBProperties properties;

    ResponseDiagnosticsProcessorImplementation(AzureCosmosDBProperties properties) {
      this.properties = properties;
    }

    @Override
    public void processResponseDiagnostics(@Nullable ResponseDiagnostics responseDiagnostics) {
      if (properties.isResponseDiagnosticsEnabled()) {
        log.info("Response Diagnostics {}", responseDiagnostics);
      }
    }
  }

  @Override
  protected String getDatabaseName() {
    return properties.getDatabase();
  }
}
