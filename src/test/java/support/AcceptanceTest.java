package support;

import com.woowahan.woowahan2018.dto.UserDto;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {
    private static final String DEFAULT_LOGIN_USER_EMAIL = "javajigi@a.com";
    private static final String DEFAULT_LOGIN_USER_PASSWORD = "12345asdfg!@";

    @Autowired
    private TestRestTemplate template;

    public TestRestTemplate template() {
        return template;
    }

    public TestRestTemplate basicAuthTemplate() {
        return template.withBasicAuth(DEFAULT_LOGIN_USER_EMAIL, DEFAULT_LOGIN_USER_PASSWORD);
    }

    public TestRestTemplate basicAuthTemplate(UserDto loginUser) {
        return template.withBasicAuth(loginUser.getEmail(), loginUser.getPassword());
    }

    protected String createResource(String path, Object bodyPayload, TestRestTemplate template) {
        ResponseEntity<String> response = template.postForEntity(path, bodyPayload, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        return response.getHeaders().getLocation().getPath();
    }

    protected String createResourceNoAuth(String path, Object bodyPayload) {
        return createResource(path, bodyPayload, template());
    }

    protected String createResourceBasicAuth(String path, Object bodyPayload) {
        return createResource(path, bodyPayload, basicAuthTemplate());
    }

    protected <T> T getResource(String location, Class<T> responseType) {
        return basicAuthTemplate().getForObject(location, responseType);
    }

    protected <T> T getResource(String location, Class<T> responseType, UserDto loginUser) {
        return basicAuthTemplate(loginUser).getForObject(location, responseType);
    }
}