package es.test;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 提取client和bulkProcessor到公共类，避免每个测试类都要初始化客户端。
 * 因为每个类所测试的数据不一定相同，所以测试数据需要在单独的测试类中初始化。
 */
class BaseTest {

  Client client;
  BulkProcessor bulkProcessor;

  BaseTest() {
    //初始化客户端
    Settings settings = Settings.builder()
            .put("cluster.name", "elasticsearch").build();
    TransportClient client = null;
    try {
      client =
              //默认集群名就是elasticsearch，所以也可以传入默认参数Settings.EMPTY
              new PreBuiltTransportClient(settings)
                      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    this.client = client;

    assert client != null;
    this.bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
      public void beforeBulk(long l, BulkRequest bulkRequest) {
        //批处理之前会经过这里
      }

      public void afterBulk(long l, BulkRequest bulkRequest, BulkResponse bulkResponse) {
        //批处理之后会经过这里
      }

      public void afterBulk(long l, BulkRequest bulkRequest, Throwable throwable) {
        //批处理出错会经过这里
      }
    })
            //由于单元测试不支持多线程操作，所以需要设置并发数为1
            .setConcurrentRequests(0)
            .build();
  }

}
