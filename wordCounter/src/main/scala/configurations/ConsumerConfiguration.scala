package configurations

import com.typesafe.config.Config

case class ConsumerConfiguration(brokers: String,
                                 groupId: String,
                                 topicName: String,
                                 timeout: Long)

object ConsumerConfiguration {
  def apply(config: Config): ConsumerConfiguration = ConsumerConfiguration(
    brokers =   config.getString("kafka.src.brokers"),
    groupId =   config.getString("kafka.src.groupId"),
    topicName = config.getString("kafka.src.topicName"),
    timeout =   config.getLong  ("kafka.src.timeout")
  )
}
