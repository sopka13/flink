package configurations

import com.typesafe.config.Config

case class ProducerConfiguration(brokers: String,
                                 topicName: String,
                                 timeout: Long)

object ProducerConfiguration {
  def apply(config: Config): ProducerConfiguration = ProducerConfiguration(
    brokers =   config.getString("kafka.dst.brokers"),
    topicName = config.getString("kafka.dst.topicName"),
    timeout =   config.getLong  ("kafka.dst.timeout")
  )
}
