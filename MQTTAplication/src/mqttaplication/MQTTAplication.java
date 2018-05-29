/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mqttaplication;

import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author Emerson
 */
public class MQTTAplication {

    /**
     * @param args the command line arguments
     * @throws org.eclipse.paho.client.mqttv3.MqttException
     */
    public static void main(String[] args) throws MqttException {

        String broker = "tcp://192.168.0.103:1883";
        String clientId = "clientid";
        String password = "passwd";
        String userName = "admins";

        String topic1 = "metrics/exchange1";
        String topic2 = "metrics/exchange2";
        String menssage = "Ola Teste de Publicacao";

        ClientMQTT cliente = new ClientMQTT(broker, clientId, userName, password);
        cliente.BrokerConnection();
        
        cliente.getSampleClient().subscribe(topic1);
        cliente.getSampleClient().subscribe(topic2);
        System.out.println("Subscribed on topic: "+topic1);
        System.out.println("Subscribed on topic: "+topic2);
        System.out.println("---------------------------------------------");
        //cliente.TopicPublish(topic, menssage);

        //cliente.BrokerDisconnection();

    }

}
