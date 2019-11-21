package ru.geekbrains.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

class ClientHandler {
    private DataOutputStream out;
    private DataInputStream in;
    private String nick;

    private List<String> blackList;

    String getNick() {
        return nick;
    }

    private final static Logger LOGGER = Logger.getLogger(ClientHandler.class.getName());


    ClientHandler(Server server, Socket socket) throws IOException {
        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.blackList = new ArrayList<>();
            Handler handler = new FileHandler("chatLog.log", true);
            handler.setLevel(Level.ALL);
            handler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return record.getLevel() + "\t" + record.getMessage() + "\t" + record.getMillis();
                }
            });
            LOGGER.addHandler(handler);
            new Thread(() -> {
                try {
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/auth")) { // /auth login72 pass72
                            String[] tokens = str.split(" ");
                            String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                            if (newNick != null) {
                                if (!server.isNickBusy(newNick)) {
                                    sendMsg("/authok");
                                    nick = newNick;
                                    server.subscribe(this);
                                    LOGGER.info("Пользователь с ником " + nick + " подключен");
                                    break;
                                } else {
                                    sendMsg("Учетная запись уже используется");
                                    LOGGER.info("Попытка входа под учетной записью уже авторизованного пользователя");
                                }
                            } else {
                                sendMsg("Неверный логин/пароль");
                                LOGGER.warning("Пользователь ввел неверные данные при авторизации");
                            }
                        }
                    }

                    blackList = AuthService.LoadBlackList(nick);

                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/")) {
                            if (str.equals("/end")) {
                                out.writeUTF("/serverclosed");
                                LOGGER.info("Ползователь " + nick + " отключился");
                                break;
                            }
                            if (str.startsWith("/w ")) {
                                String[] tokens = str.split(" ", 3);
                                server.sendPersonalMsg(this, tokens[1], tokens[2]);
                                LOGGER.info("Пользователь с ником " + nick + " отправил сообщение пользователю " + tokens[1]);
                            }
                            if (str.startsWith("/blacklist")) {
                                String[] tokens = str.split(" ");
                                if (AuthService.addBlackList(nick, tokens[1])) {
                                    blackList.add(tokens[1]);
                                    sendMsg("Вы добавили пользователя " + tokens[1] + " в черный список");
                                    LOGGER.info("Пользователь " + nick + " добавил пользователя " + tokens[1] + " в черный список");
                                } else {
                                    sendMsg("Ошибка добавления в черный список. \nДанного пользователя нет в БД или вы пытаетесь добавить сами себя.");
                                }
                            }
                        } else {
                            server.broadcastMsg(this, nick + ": " + str);
                        }
                        System.out.println("Client: " + str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    server.unsubscribe(this);
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean checkBlackList(String nick) {
        return blackList.contains(nick);
    }

    void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
