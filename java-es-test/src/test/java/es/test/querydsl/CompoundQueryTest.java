package es.test.querydsl;

import es.test.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.ConstantScoreQueryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.constantScoreQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

@Slf4j
public class CompoundQueryTest extends BaseTest {


  @Before
  public void setUp() throws IOException {
    //初始化测试数据
    bulkProcessor.add(new IndexRequest("test", "test").source(jsonBuilder().startObject().field("name", "王百万").endObject()));
    bulkProcessor.add(new IndexRequest("test", "test").source(jsonBuilder().startObject().field("name", "王大力").endObject()));
    bulkProcessor.add(new IndexRequest("test", "test").source(jsonBuilder().startObject().field("name", "赵海洋").endObject()));
    bulkProcessor.add(new IndexRequest("test", "test").source(jsonBuilder().startObject().field("name", "丁建国").endObject()));
    bulkProcessor.flush();
    bulkProcessor.close();
    client.admin().indices().prepareRefresh().get();

  }

  /*@After
  public void tearDown() {
    //删除测试数据
    DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
            .filter(QueryBuilders.matchAllQuery())
            .source("test")
            .get();
  }*/

  @Test
  public void constantScore() {
    ConstantScoreQueryBuilder builder =
            constantScoreQuery(
                    termQuery("name", "王"))
                    .boost(2.0f);
    log.info(builder.toString());
    SearchResponse searchResponse = client.prepareSearch().setQuery(builder).get();
    log.info(searchResponse.toString());
    Assert.assertEquals(1, searchResponse.getHits().getTotalHits());
  }
}
