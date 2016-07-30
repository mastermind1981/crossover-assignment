package com.dev.frontend.utils;

import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.dev.frontend.exception.handlers.CustomErrorHandler;

public class RestTemplateUtility {

	private static RestTemplate restTemplate = null;

	static {

		int timeout = 5000;
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(timeout);
		restTemplate = new RestTemplate(clientHttpRequestFactory);
		restTemplate.setErrorHandler(new CustomErrorHandler());
	}

	public static RestTemplate getRestTemplate() {
		return restTemplate;
	}
	
	public static HttpClient getHttpClient() {
		RequestConfig config = RequestConfig.custom().setConnectTimeout(50 * 1000)
				.setConnectionRequestTimeout(50 * 1000).setSocketTimeout(50 * 1000).build();
		CredentialsProvider provider = new BasicCredentialsProvider();
		HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider)
				.setDefaultRequestConfig(config).build();
		return httpClient;
	}

}
