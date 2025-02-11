package org.controlcenter.vehicle.infrastructure.elasticsearch.repository;

import java.util.List;

import org.controlcenter.vehicle.infrastructure.elasticsearch.document.VehicleInformationDocument;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

import co.elastic.clients.elasticsearch._types.query_dsl.FieldValueFactorModifier;
import co.elastic.clients.elasticsearch._types.query_dsl.FieldValueFactorScoreFunction;
import co.elastic.clients.elasticsearch._types.query_dsl.FunctionScore;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class VehicleSearchQueryRepository {
	private static final String MINIMUM_SHOULD_MATCH_PERCENTAGE = "75%";
	private final ElasticsearchOperations elasticsearchOperations;

	public List<VehicleInformationDocument> findByKeyword(String keyword) {
		NativeQuery query = getKeywordSearchNativeQuery(keyword);

		SearchHits<VehicleInformationDocument> searchHits = elasticsearchOperations.search(query,
			VehicleInformationDocument.class);

		return searchHits.stream().map(SearchHit::getContent).toList();
	}

	private NativeQuery getKeywordSearchNativeQuery(String keyword) {
		NativeQueryBuilder queryBuilder = new NativeQueryBuilder();

		Query multiQuery = QueryBuilders.multiMatch()
			.query(keyword)
			.fields(
				"vehicleNumber_ngram^1",
				"vehicleNumber.ngram^1"
			)
			.minimumShouldMatch(MINIMUM_SHOULD_MATCH_PERCENTAGE)
			.build()._toQuery();

		queryBuilder.withSort(Sort.by(Sort.Order.asc("vehicleNumber")));

		FunctionScore fieldValueFactorScoreFunction = new FieldValueFactorScoreFunction.Builder()
			.field("id")
			.factor(1.2)
			.modifier(FieldValueFactorModifier.None)
			.missing(1.0)
			.build()
			._toFunctionScore();

		Query functionScoreQuery = QueryBuilders.functionScore()
			.functions(List.of(fieldValueFactorScoreFunction))
			.query(multiQuery)
			.build()
			._toQuery();

		return queryBuilder.withQuery(functionScoreQuery)
			.build();
	}
}
