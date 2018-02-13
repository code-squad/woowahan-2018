package support;

import com.woowahan.woowahan2018.domain.User;
import com.woowahan.woowahan2018.domain.UserRepository;
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
    private static final String DEFAULT_LOGIN_USER = "saram4030";

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private UserRepository userRepository;

    public TestRestTemplate template() {
        return template;
    }

    public TestRestTemplate basicAuthTemplate() {
        return basicAuthTemplate(defaultUser());
    }

    public TestRestTemplate basicAuthTemplate(User loginUser) {
        return template.withBasicAuth(loginUser.getEmail(), loginUser.getPassword());
    }

    protected User defaultUser() {
        return findByEmail(DEFAULT_LOGIN_USER);
    }

    protected User findByEmail(String email) {
        return userRepository.findByEmail(email).get();
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

    protected <T> T getResource(String location, Class<T> responseType, User loginUser) {
        return basicAuthTemplate(loginUser).getForObject(location, responseType);
    }
}