package com.rabbitmqshell.shell;

import com.rabbitmqshell.model.ServerPortService;
import com.rabbitmqshell.model.ShellModel;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.net.HttpURLConnection;
import java.net.URL;

@ShellComponent
public class BindingShell {
    @Qualifier("shellModel")
    @Autowired
    ShellModel model;
    @Autowired
    ServerPortService service;

    @SneakyThrows
    @ShellMethod("Declare Exchange! ")
    public void exch(String exch) {
        URL url = new URL("http://localhost:" +
                service.getPort() +
                "/exchange/" + exch);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        model.setExchangeName(exch);
        if (http.getResponseCode() == 200) {
            System.out.println("exchange başarıyla oluşturuldu. ");
        } else
            System.out.println("tekrar deneyiniz. ");
        http.disconnect();

    }

    @SneakyThrows
    @ShellMethod("Declare queue! ")
    public void queue(String queue) {
        URL url = new URL("http://localhost:" +
                service.getPort() +
                "/queue/" +
                queue);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        model.setQueueName(queue);
        if (http.getResponseCode() == 200) {
            System.out.println("queue başarıyla oluşturuldu. ");
        } else
            System.out.println("tekrar deneyiniz. ");
        http.disconnect();

    }


    @SneakyThrows
    @ShellMethod("Binding! ")
    public void bind() {

        if (model.getRoutingKey() != null) {
            URL url = new URL("http://localhost:" +
                    service.getPort() +
                    "/bind/" +
                    model.getRoutingKey());
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            if (http.getResponseCode() == 200) {
                System.out.println("Bind işlemi başarıyla gerçekleşti. ");
            }
            http.disconnect();
        } else
            System.out.println("Lütfen giriş yapınız. ");
    }
}
