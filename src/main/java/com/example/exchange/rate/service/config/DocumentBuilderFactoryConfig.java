package com.example.exchange.rate.service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

@Configuration
@Slf4j
public class DocumentBuilderFactoryConfig {
    @Bean
    public DocumentBuilderFactory getDocumentBuilderFactory() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {
            documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        } catch (ParserConfigurationException e) {
            log.warn("DOCUMENT_BUILDER_FACTORY config failed: " + e.getMessage());
        }

        return documentBuilderFactory;
    }
}
