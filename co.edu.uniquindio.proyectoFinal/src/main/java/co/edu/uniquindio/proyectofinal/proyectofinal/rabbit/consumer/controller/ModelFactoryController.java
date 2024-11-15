package co.edu.uniquindio.proyectofinal.proyectofinal.rabbit.consumer.controller;

import co.edu.uniquindio.proyectofinal.proyectofinal.rabbit.consumer.config.RabbitFactory;
import co.edu.uniquindio.proyectofinal.proyectofinal.rabbit.consumer.controller.service.IModelFactoryService;
import co.edu.uniquindio.proyectofinal.proyectofinal.utils.RabbitMessageListener;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.ArrayList;
import java.util.List;

import static co.edu.uniquindio.proyectofinal.proyectofinal.rabbit.consumer.util.Constantes.QUEUE_NUEVA_PUBLICACION;

public class ModelFactoryController implements IModelFactoryService, Runnable {

    private RabbitFactory rabbitFactory;
    private ConnectionFactory connectionFactory;
    private Thread hiloServicioConsumer1;
    private List<RabbitMessageListener> listeners = new ArrayList<>();

    //------------------------------  Singleton ------------------------------------------------
    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public ModelFactoryController() {
        initRabbitConnection();
    }

    private void initRabbitConnection() {
        rabbitFactory = new RabbitFactory();
        connectionFactory = rabbitFactory.getConnectionFactory();
        System.out.println("Conexión a RabbitMQ establecida");
    }

    // Método para registrar listeners
    public void addRabbitMessageListener(RabbitMessageListener listener) {
        listeners.add(listener);
    }

    // Método para notificar a todos los listeners cuando se recibe un mensaje
    private void notifyListeners(String message) {
        for (RabbitMessageListener listener : listeners) {
            listener.onMessageReceived(message);
        }
    }

    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        if (currentThread == hiloServicioConsumer1) {
            consumirMensajes();
        }
    }

    private void consumirMensajes() {
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NUEVA_PUBLICACION, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody());
                System.out.println("Mensaje recibido: " + message);

                // Notifica a todos los listeners registrados
                notifyListeners(message);
            };

            // Consume mensajes de forma continua
            channel.basicConsume(QUEUE_NUEVA_PUBLICACION, true, deliverCallback, consumerTag -> {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void iniciarConsumidor() {
        hiloServicioConsumer1 = new Thread(this);
        hiloServicioConsumer1.start();
    }
}
