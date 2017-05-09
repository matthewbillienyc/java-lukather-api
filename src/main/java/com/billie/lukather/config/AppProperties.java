package com.billie.lukather.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * Created by matthew on 5/9/17.
 */
@ConfigurationProperties(prefix = "app-properties")
public class AppProperties implements Serializable { }

