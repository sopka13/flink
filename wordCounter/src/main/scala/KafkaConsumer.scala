import com.typesafe.config.ConfigFactory
import configurations.ConsumerConfiguration
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.connectors.kafka._
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.flink.api.scala._

import java.util.Properties

object KafkaConsumer {

  private val config = ConsumerConfiguration(ConfigFactory.load("dev.conf"))
  private val property: Properties = {
    val prop = new Properties()
    prop.put(ConsumerConfig.GROUP_ID_CONFIG, config.groupId)
    prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.brokers)
    prop.put("enable.auto.commit", "true")
    prop.put("auto.commit.interval.ms", "1000")
    prop
  }

  def consumer: FlinkKafkaConsumer[String] = new FlinkKafkaConsumer[String](config.topicName, new SimpleStringSchema, property)
}