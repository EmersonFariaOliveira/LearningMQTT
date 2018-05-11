package com.inatel.api;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttApplication {

    public static void main(String[] args) {

        //Configuração para conexão e cominicação com o broker
        String topic        = "MQTT/Examples";
        String content      = "teste";
        int qos             = 2;
        String broker       = "tcp://m13.cloudmqtt.com:18617";
        String clientId     = "JavaSample";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            
            //comunicação MQTT
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            
            //Configurando a conexão com o broker
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName("erarxfqi");
            connOpts.setPassword("tGirIYP7ESyW".toCharArray());
            System.out.println("Connecting to broker: "+broker);
            
            //Conecta com o broker
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            
            //Publicando uma mensagem
            System.out.println("Topic: "+ topic);
            System.out.println("Publishing message: "+content);
            //Configurando o conteudo da mensagem e convertendo em bytes
            MqttMessage message = new MqttMessage();
            message.setQos(qos);
            message.setPayload(content.getBytes());
            //Publicação no topico especifico
            sampleClient.publish(topic, message);
            System.out.println("Message published");
            
            sampleClient.subscribe(topic);
            System.out.println(sampleClient.getTopic(topic));
            
            //Desconecta do broker
            sampleClient.disconnect();
            System.out.println("Disconnected");
            
            System.exit(0);
        
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }
}