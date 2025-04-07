package com.example.demo;

import com.example.demo.Errors.UserNotFoundException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

}
