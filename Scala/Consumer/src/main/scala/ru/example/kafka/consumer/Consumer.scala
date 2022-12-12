package ru.example.kafka.consumer

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization._
import java.time.Duration
import java.util.Properties
import scala.jdk.CollectionConverters.IterableHasAsJava

object Consumer {
  def main(args: Array[String]): Unit = {
    // Параметры
    val servers = "localhost:29092"
    val topic   = "test"
    val group   = "g1"

    // Создаём Consumer и подписываемся на тему
    val props = new Properties()
    props.put("bootstrap.servers", servers)
    props.put("group.id", group)
    props.put("auto.offset.reset", "earliest")
    props.put("enable.auto.commit", false)
    val consumer = new KafkaConsumer(props, new IntegerDeserializer, new StringDeserializer)
    consumer.subscribe(List(topic).asJavaCollection)

    // Читаем тему
    try {
      while (true) {
        consumer
          .poll(Duration.ofSeconds(1))
          .forEach { msg => println(s"${msg.partition}\t${msg.offset}\t${msg.key}\t${msg.value}") }
      }
    } catch {
      case e: Exception =>
        println(e.getLocalizedMessage)
        sys.exit(-1)
    } finally {
      consumer.close()
    }

    sys.exit(0)
  }
}
