package com.example.exchange.rate.service.service.impl;

import com.example.exchange.rate.service.exceptions.ExternalServiceException;
import com.example.exchange.rate.service.modal.BaseExchangeRates;
import com.example.exchange.rate.service.service.ExchangeRateSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import static com.example.exchange.rate.service.Constants.PRECISION;

@Service
@Slf4j
public class ECBExchangeRateSourceService implements ExchangeRateSourceService {
    private static final String ECB_URL = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
    private static final String CUBE_ELEMENT_TAG_NAME = "Cube";
    private static final String DATE_ATTRIBUTE_NAME = "time";
    private static final String CURRENCY_ATTRIBUTE_NAME = "currency";
    private static final String RATE_ATTRIBUTE_NAME = "rate";

    private DocumentBuilderFactory documentBuilderFactory;

    @Autowired
    public void setDocumentBuilderFactory(DocumentBuilderFactory documentBuilderFactory) {
        this.documentBuilderFactory = documentBuilderFactory;
    }

    @Override
    @Cacheable("BaseExchangeRates")
    public BaseExchangeRates getBaseExchangeRates() {
        try {
            log.info("Fetching ECB exchange rates");

            Map<String, BigDecimal> exchangeRatesMap = new HashMap<>();
            String date = null;

            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            Document ecbExchangeRatesXML = builder.parse(ECB_URL);
            ecbExchangeRatesXML.getDocumentElement().normalize();

            NodeList nodeList = ecbExchangeRatesXML.getElementsByTagName(CUBE_ELEMENT_TAG_NAME);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;

                    if (elem.hasAttribute(CURRENCY_ATTRIBUTE_NAME) && elem.hasAttribute(RATE_ATTRIBUTE_NAME)) {
                        String currencyIdentifier = elem.getAttribute(CURRENCY_ATTRIBUTE_NAME);
                        BigDecimal currencyRate = new BigDecimal(elem.getAttribute(RATE_ATTRIBUTE_NAME));
                        currencyRate = currencyRate.setScale(PRECISION, RoundingMode.HALF_UP);

                        exchangeRatesMap.put(currencyIdentifier, currencyRate);
                    } else if (elem.hasAttribute(DATE_ATTRIBUTE_NAME)) {
                        date = elem.getAttribute(DATE_ATTRIBUTE_NAME);
                    }
                }
            }

            if (exchangeRatesMap.isEmpty()) {
                throw new ExternalServiceException(ECBExchangeRateSourceService.class.getSimpleName(), "No exchange rates found");
            }

            BaseExchangeRates baseExchangeRates = new BaseExchangeRates(exchangeRatesMap, date);
            log.info("Successfully Fetched exchange rates: " + baseExchangeRates);
            return baseExchangeRates;
        } catch (IOException | SAXException | ParserConfigurationException e) {
            throw new ExternalServiceException(ECBExchangeRateSourceService.class.getName(), e.getMessage());
        }
    }
}
