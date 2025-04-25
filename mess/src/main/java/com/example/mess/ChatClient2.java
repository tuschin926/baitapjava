package com.example.mess;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ChatClient2 extends Application {

    private TextArea messageArea;
    private TextField inputField;
    private TextField ipField;
    private TextField portField;
    private Button connectButton;
    private Button sendButton;

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    private Thread receiveThread;

    @Override
    public void start(Stage primaryStage) {
        messageArea = new TextArea();
        messageArea.setEditable(false);

        inputField = new TextField();
        inputField.setPromptText("Nhập tin nhắn...");

        ipField = new TextField();
        ipField.setPromptText("Nhập IP server");

        portField = new TextField();
        portField.setPromptText("Nhập Port server");

        connectButton = new Button("Kết nối");
        sendButton = new Button("Gửi");
        sendButton.setDisable(true);

        connectButton.setOnAction(e -> connectToServer());
        sendButton.setOnAction(e -> sendMessage());

        HBox connectionBox = new HBox(10, ipField, portField, connectButton);
        HBox inputBox = new HBox(10, inputField, sendButton);

        VBox root = new VBox(10, messageArea, connectionBox, inputBox);
        root.setPrefSize(500, 400);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Chat Client 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void connectToServer() {
        String ip = ipField.getText().trim();
        int port;

        try {
            port = Integer.parseInt(portField.getText().trim());
        } catch (NumberFormatException e) {
            showError("Port phải là số!");
            return;
        }

        try {
            socket = new Socket(ip, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            sendButton.setDisable(false);
            connectButton.setDisable(true);
            ipField.setDisable(true);
            portField.setDisable(true);

            messageArea.appendText("Đã kết nối tới server.\n");

            receiveThread = new Thread(this::receiveMessages);
            receiveThread.setDaemon(true);
            receiveThread.start();
        } catch (IOException e) {
            showError("Không thể kết nối tới server: " + e.getMessage());
        }
    }

    private void sendMessage() {
        String message = inputField.getText().trim();
        if (message.isEmpty()) {
            return;
        }

        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
            inputField.clear();
        } catch (IOException e) {
            showError("Lỗi gửi tin nhắn: " + e.getMessage());
        }
    }

    private void receiveMessages() {
        String message;
        try {
            while ((message = reader.readLine()) != null) {
                String finalMessage = message;
                Platform.runLater(() -> messageArea.appendText("Server: " + finalMessage + "\n"));
            }
        } catch (IOException e) {
            Platform.runLater(() -> showError("Mất kết nối với server."));
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void stop() throws Exception {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
        if (receiveThread != null && receiveThread.isAlive()) {
            receiveThread.interrupt();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}