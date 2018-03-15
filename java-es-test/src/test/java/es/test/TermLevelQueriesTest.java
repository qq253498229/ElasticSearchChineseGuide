package es.test;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Slf4j
public class TermLevelQueriesTest extends BaseTest {


  @Before
  public void setUp() throws IOException {
    //初始化测试数据
    bulkProcessor.add(new IndexRequest("test", "test", "1")
            .source(jsonBuilder().startObject().field("price", 5).endObject()));
    bulkProcessor.add(new IndexRequest("test", "test", "2")
            .source(jsonBuilder().startObject().field("price", 10).endObject()));
    bulkProcessor.add(new IndexRequest("test", "test", "3")
            .source(jsonBuilder().startObject().field("price", 20).endObject()));
    bulkProcessor.add(new IndexRequest("test", "test", "4")
            .source(jsonBuilder().startObject().field("price", 30).endObject()));
    bulkProcessor.add(new IndexRequest("test", "test", "5")
            .source(jsonBuilder().startObject().field("price", 40).endObject()));
    bulkProcessor.flush();
    bulkProcessor.close();
    client.admin().indices().prepareRefresh().get();

  }

  @After
  public void tearDown() {
    //删除测试数据
    DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
            .filter(QueryBuilders.matchAllQuery())
            .source("test")
            .get();
  }

  /**
   * range query
   */
  @Test
  public void rangeQuery() {
    RangeQueryBuilder price = QueryBuilders.rangeQuery("price")
            .from(5)
            .to(10)
            .includeLower(true)
            .includeUpper(false);
    log.info(price.toString());
    SearchResponse searchResponse = client.prepareSearch().setQuery(price).get();
    log.info(searchResponse.toString());
    Assert.assertEquals(1, searchResponse.getHits().getTotalHits());
    Assert.assertEquals(5, searchResponse.getHits().getHits()[0].getSource().get("price"));

  }

  /**
   * simple range query
   */
  @Test
  public void simpleRangeQuery() {
    RangeQueryBuilder price = QueryBuilders.rangeQuery("price")
            .gte(10)
            .lt(20);
    log.info(price.toString());
    SearchResponse searchResponse = client.prepareSearch().setQuery(price).get();
    log.info(searchResponse.toString());
    Assert.assertEquals(1, searchResponse.getHits().getTotalHits());
    Assert.assertEquals(10, searchResponse.getHits().getHits()[0].getSource().get("price"));
  }
}
