import com.typesafe.config.ConfigFactory
import configurations.ProducerConfiguration
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer

object KafkaProducer {
  private val config = ProducerConfiguration(ConfigFactory.load("dev.conf"))

  def producer = new FlinkKafkaProducer(config.brokers, config.topicName, new SimpleStringSchema())
}
