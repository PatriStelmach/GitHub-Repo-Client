package com.example.demo;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockRequestDispatcher;
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

	public static MockWebServer mockWebServer;

	@BeforeEach
	void setUp() throws IOException {
		mockWebServer = new MockWebServer();
		mockWebServer.start();
	}

	@AfterEach
	void tearDown() throws IOException {
		mockWebServer.shutdown();
	}

	@Test
	void getUser() throws UserNotFoundException
	{
		MockWebServer mockWebServer = new MockWebServer();
		MockResponse mockResponse = new MockResponse()
				.addHeader("CONTENT_TYPE", "application/json; charset=utf-8" )
				.setBody("{ \"name\": \"John\" }");
	}

}
