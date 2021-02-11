package net.thumbtack.school.threads;

import java.io.*;

class Message {
    private final String emailAddress;
    private final String sender;
    private final String subject;
    private final String body;

    public Message(String emailAddress, String sender, String subject, String body) {
        this.emailAddress = emailAddress;
        this.sender = sender;
        this.subject = subject;
        this.body = body;
    }

    @Override
    public String toString() {
        return "Message{" +
                "emailAddress='" + emailAddress + '\'' +
                ", sender='" + sender + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                "}\n";
    }
}

class Transport {
    private String emailAddress;
    private final String sender;
    private final String subject;
    private final String body;

    public Transport() {
        sender = "sender@ya.ru";
        subject = "Тема сообщения";
        body = "Текст сообщения";
    }

    public void sendToAll() {
        try (BufferedReader r = new BufferedReader(new FileReader("/home/ekaterina/emailAddresses"))) {
            emailAddress = r.readLine();
            while (emailAddress != null) {
                send(new Message(emailAddress, sender, subject, body));
                emailAddress = r.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(Message message) {
        Runnable getTask = () -> {
            System.out.println("Thread " + Thread.currentThread().getId() + " START send " + message);
            try (FileWriter fr = new FileWriter(new File("/home/ekaterina/messages"), true)) {
                fr.write(message.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Thread " + Thread.currentThread().getId() + " END send " + message);
        };
        new Thread(getTask).start();
    }
}

public class Task14 {
    public static void main(String[] args) {
        Transport transport = new Transport();
        transport.sendToAll();
    }
}
