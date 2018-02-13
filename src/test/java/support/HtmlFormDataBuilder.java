package support;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;

public class HtmlFormDataBuilder {
	private HttpHeaders headers;
	private MultiValueMap<String, Object> params;

	private HtmlFormDataBuilder(HttpHeaders headers) {
		this.headers = headers;
		this.params = new LinkedMultiValueMap<>();
	}

	public HtmlFormDataBuilder addParameter(String key, Object value) {
		this.params.add(key, value);
		return this;
	}

	public HttpEntity<MultiValueMap<String, Object>> getOrPost() {
		return new HttpEntity<MultiValueMap<String, Object>>(params, headers);
	}

	public HttpEntity<MultiValueMap<String, Object>> put() {
		addParameter("_method", "put");
		return new HttpEntity<MultiValueMap<String, Object>>(params, headers);
	}

	public HttpEntity<MultiValueMap<String, Object>> delete() {
		addParameter("_method", "delete");
		return new HttpEntity<MultiValueMap<String, Object>>(params, headers);
	}

	public static HtmlFormDataBuilder urlEncodedForm() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		return new HtmlFormDataBuilder(headers);
	}
}