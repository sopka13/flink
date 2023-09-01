import com.typesafe.config.ConfigFactory
import configurations.ConsumerConfiguration
import org.apache.flink.api.common.serialization.{SimpleStringEncoder, SimpleStringSchema}
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.api.scala.{AggregateDataSet, createTypeInformation}
import org.apache.flink.core.fs.Path
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer, FlinkKafkaProducer}


object WordCounter {
  def main(args: Array[String]): Unit = {
    // Set up the execution environment
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    implicit val typeInfo = TypeInformation.of(classOf[String])

    // Input stream
    val kafkaConsumer: FlinkKafkaConsumer[String] = KafkaConsumer.consumer
    val stringInputStream: DataStream[String] = env.addSource(kafkaConsumer)
    val stringInputStreamSecond = env.readTextFile("/home/sopka13/Temp/test_text_file_src")
//    val allStreams = stringInputStream.union(stringInputStreamSecond)

    // Outputs
    val kafkaProducer: FlinkKafkaProducer[String] = KafkaProducer.producer
    val stringOutputStreamSecond = StreamingFileSink.forRowFormat(new Path("/home/sopka13/Temp/test_text_file_dst"), new SimpleStringEncoder[String]("UTF-8")).build()


    // Handler and sink
    // Sink to Kafka
    allStreams
      .map(str => str.toUpperCase)
      .addSink(kafkaProducer)
      .name("kafka_dst")

    // Sink to file
    allStreams
      .map(str => str.toLowerCase)
      .addSink(stringOutputStreamSecond)
      .name("file_dst")

    // Sink to STDOUT
    allStreams.print()

    env.execute("My_first_job")
  }

  // Start Kafka                          # -     # +
  // Start Kafka producer to Kafka.src    # src   # +
  // Start Kafka consumer from kafka.src  # src   # +
  // Start Kafka consumer from kafka.dst  # dst   # +
  // Start Flink Job                      # dst   #
  // Start Tail from dst file             # dst
}
