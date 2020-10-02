package club.narukara.spring.player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import club.narukara.spring.chessboard.Chessboard;

public class OnlinePlayer implements Player {
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;

    public OnlinePlayer(int port) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket socket = serverSocket.accept();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            recycle();
            throw e;
        }
    }

    @Override
    public int[] decide(Chessboard chessboard, int side) {
        int[] step = new int[2];
        try {
            objectOutputStream.writeObject(chessboard);
            objectOutputStream.writeInt(side);
            objectOutputStream.flush();
            objectOutputStream.reset();
            step[0] = objectInputStream.readInt();
            step[1] = objectInputStream.readInt();
        } catch (IOException e) {
            e.printStackTrace();
            recycle();
            return new RandomPlayer().decide(chessboard, side);
        }
        return step;
    }

    @Override
    public void announce(Chessboard chessboard, int side) {
        try {
            objectOutputStream.writeObject(chessboard);
            objectOutputStream.writeInt(side);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            recycle();
        }
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
    }
}
