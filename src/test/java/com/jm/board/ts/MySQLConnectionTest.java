package com.jm.board.ts;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootTest
@ActiveProfiles("local")
public class MySQLConnectionTest {

    @Value("${spring.config.activate.on-profile}")
    private String profile;

/*
    @Value("${spring.datasource.url}")
    private String url;*/

    @Test
    public void testConnection() throws Exception{

        System.out.println("profile = " + profile);
      //  System.out.println("url = " + url);
    }
}
