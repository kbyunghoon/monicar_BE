package org.eventhub.infrastructure.external.util;

import java.util.Map;

import org.eventhub.common.response.BaseResponse;
import org.eventhub.common.response.ErrorCode;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class RestClientService {
	private final RestClient restClient;
	private final ObjectMapper objectMapper;
	private final HeaderUtils headerUtils;

	public BaseResponse postAlarm(
		String endPoint,
		HeaderName... headerNames
	) {
		Map<String, String> headers = headerUtils.additionalHeaders(headerNames);

		return restClient.post()
			.uri(uriBuilder -> uriBuilder.path(endPoint).build())
			.headers(header -> headers.forEach(header::add))
			.exchange((request, response) -> {
				HttpStatusCode statusCode = response.getStatusCode();
				log.info("Response status: {}", statusCode);

				if (statusCode.is2xxSuccessful()) {
					return BaseResponse.success();
				}
				return BaseResponse.fail(ErrorCode.FAIL_RESPONSE_DELIVERED);
				// InputStream responseBody = response.getBody();
				// JsonNode node = objectMapper.readTree(responseBody);
				//
				// boolean isSuccess = node.get("isSuccess").asBoolean();
				// if (!isSuccess) {
				// 	throw new BusinessException(node.get("errorMessage").asText() , ErrorCode.FAIL_RESPONSE_DELIVERED);
				// }
				// return objectMapper.treeToValue(node, BaseResponse.class);
			});
	}
}
