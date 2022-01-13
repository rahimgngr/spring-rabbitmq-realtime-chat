package com.rabbitmqshell.shell;

import com.rabbitmqshell.model.ShellModel;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;


@ShellComponent
public class UserShell implements PromptProvider {

    @Qualifier("shellModel")
    @Autowired
    ShellModel shellModel;

    @ShellMethod("kayıt ol")
    @SneakyThrows
    public void signup(@NonNull String userName, @NonNull String userPass, @NonNull String role) {
        URL url = new URL("http://localhost:8081/users/");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/json");

        String data = "{\"userName\":\"" +
                userName +
                "\",\"userPass\":\"" +
                userPass +
                "\",\"role\":\"" +
                role +
                "\"}";

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        if (!Objects.equals(userName, "null") && !Objects.equals(userPass, "null") && !Objects.equals(role, "null")) {
            stream.write(out);
        } else
            System.out.println("Değerler null olamaz. ");


        if (http.getContentLength() == -1) {
            System.out.println("Kullanıcı başarıyla oluşturuldu. ");
        } else
            System.out.println("Hata tekrar deneyin. ");
        http.disconnect();
    }

    @ShellMethod("Giriş yap")
    @SneakyThrows
    public void signin(String userName, String userPass) {
        URL url = new URL("http://localhost:8080/login");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        String data = "username=" +
                userName +
                "&password=" +
                userPass;

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);

        if (http.getResponseCode() == 500) {
            System.out.println("Başarıyla giriş yapıldı. ");
        } else {
            System.out.println("Giriş yapılamadı, tekrar deneyin. ");
        }
        shellModel.setRoutingKey(userName);
        http.disconnect();
    }

    @Override
    public AttributedString getPrompt() {
        return new AttributedString("rabbitmq>", AttributedStyle.BOLD.foreground(AttributedStyle.WHITE));
    }
}
