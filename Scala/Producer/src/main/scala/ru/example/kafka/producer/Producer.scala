package ru.example.kafka.producer

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization._
import java.util.Properties

object Producer {
  def main(args: Array[String]): Unit = {
    // Параметры
    val servers = "localhost:9092"
    val topic   = "test"

    // Создаём Producer
    val props = new Properties()
    props.put("bootstrap.servers", servers)
    val producer = new KafkaProducer(props, new IntegerSerializer, new StringSerializer)

    // Генерируем записи
    try {
      (1 to 1000).foreach { i =>
        producer.send(new ProducerRecord(topic, i, s"Message $i"))
      }
    } finally {
      producer.close()
    }

    sys.exit(0)
  }
}
