package com.example.demo;

import com.example.demo.Config.CreateJWT;
import com.example.demo.Config.GitAuth;
import com.example.demo.Config.PKC1Decode;
import com.example.demo.Services.GitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
@SpringBootTest
class EnvironmentalVariablesTests
{

	@Test
	void contextLoads() {
	}

	//testing invalid GitHub App Id, jwt and accesstoken
	@Test
	public void testEstAppId() throws Exception
	{
		//given
		Long newId = 12345L;
		String jwt = "wrong_jwt";
		String token = "wrong_token";

		//when
		try (MockedStatic<CreateJWT> createJWTMock = mockStatic(CreateJWT.class);
			 MockedStatic<GitAuth> gitAuthMock = mockStatic(GitAuth.class))
		{

			createJWTMock.when(CreateJWT::generateJWTFromPKCS1File).thenReturn(jwt);
			gitAuthMock.when(() -> GitAuth.getInstallToken(jwt)).thenReturn(token);

			Long result = GitService.estAppId(newId);

			//then
			assertEquals(newId, GitService.getAppId());
			assertNull(result);

			//verify number of interactions
			createJWTMock.verify(CreateJWT::generateJWTFromPKCS1File, times(2));
			gitAuthMock.verify(() -> GitAuth.getInstallToken(jwt), times(1));
		}
	}

	//testing invalid key path
	@Test
	public void testDefinePath() throws Exception
	{
		//given
		String newPath = "/wrong_path/key.pem";
		String decoded = "wrong_decoded";

		//when
		try (MockedStatic<PKC1Decode> pkcs1DecodeMock = mockStatic(PKC1Decode.class))
		{

			pkcs1DecodeMock.when(PKC1Decode::decode).thenReturn(decoded);

			String result = GitService.definePath(newPath);
			//then
			assertEquals(newPath, GitService.getPath());
			assertEquals(decoded, result);

			//verify number of interactions
			pkcs1DecodeMock.verify(PKC1Decode::decode, times(1));
		}
	}


}
