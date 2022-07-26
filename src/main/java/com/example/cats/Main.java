package com.example.cats;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class Main {
    public static final String REMOTE_SERVICE_URI = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static final ObjectMapper mapper = new ObjectMapper();


    public static void main(String[] args) throws IOException {

           CloseableHttpClient httpClient = HttpClientBuilder.create()
                   .setDefaultRequestConfig(RequestConfig.custom()
                           .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                           .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                           .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                           .build())
                   .build();

        HttpGet request = new HttpGet(REMOTE_SERVICE_URI);
        CloseableHttpResponse response = httpClient.execute(request);
//        Arrays.stream(response.getAllHeaders())
//                .forEach(System.out::println);

        List<Cats> cats = mapper.readValue(response.getEntity().getContent(),
                new TypeReference<>() {});
                    cats.stream().filter(value -> value.getUpvotes() != -1 && value.getUpvotes() > 0)
                            .forEach(System.out::println); //(value != null) не работает из-за примитива int
                                                     // поискав в интернет решил сравнивать с -1


        //String body = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);  System.out.println(body);
    }
}
