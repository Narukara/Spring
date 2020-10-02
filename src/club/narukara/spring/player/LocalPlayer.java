package club.narukara.spring.player;

import club.narukara.spring.chessboard.Chessboard;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class LocalPlayer {
    private final Player player;
    private final Socket socket;
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;

    public LocalPlayer(String ip, int port, Player player) throws IOException {
        this.player = player;
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port));
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            recycle();
            throw e;
        }
    }

    public void start() {
        new Thread(() -> {
            try {
                while (true) {
                    Chessboard chessboard = (Chessboard) objectInputStream.readObject();
                    int side = objectInputStream.readInt();
                    if (chessboard.getWinner() != 0) { // an announce
                        player.announce(chessboard, side);
                        break;
                    }
                    int[] step = player.decide(chessboard, side);
                    objectOutputStream.writeInt(step[0]);
                    objectOutputStream.writeInt(step[1]);
                    objectOutputStream.flush();
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("End or Abort");
            } finally {
                recycle();
            }
        }).start();
    }

    public void recycle() {
        try {
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (objectInputStream != null) {
                objectInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
