package com.fish.learn.demo.mq.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Logger;

import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class ProducerTest {

    private static Logger logger = Logger.getLogger(ProducerTest.class);


    protected static Properties props = new Properties();
    //protected static String kafka_server = "118.122.82.52:9092";
    protected static String kafka_server = "192.168.24.249:9092";
    protected static String topic = "topicName";

    static {
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka_server);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class);
    }

    public static void main(String[] args) throws Exception {
        try{

        }catch (Exception e){
            logger.error(e);
        }
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);

        String data = "bbbbbb";
        send(producer,data);

        data="aaaaaa";
        send(producer,data);

        TimeUnit.SECONDS.sleep(30);
        producer.close();
    }

    public static void send(KafkaProducer<String, String> producer, String v){
        producer.send(new ProducerRecord<String, String>(topic, v), new Callback() {

            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e == null) {
                    System.out.println("offset=" + recordMetadata.offset() + ",partition=" + recordMetadata.partition());
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}