package club.narukara.spring.player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import club.narukara.spring.chessboard.Chessboard;

public class OnlinePlayer implements Player {
	Player realPlayer;
	ObjectInputStream objectInputStream;
	ObjectOutputStream objectOutputStream;

	public OnlinePlayer() {
	}

	public void init(int port) throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			Socket socket = serverSocket.accept();
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		}
	}

	@Override
	public int[] decide(Chessboard chessboard, int side) {
		int[] step = new int[2];
		try {
			objectOutputStream.writeObject(chessboard);
			objectOutputStream.flush();
			step[0] = objectInputStream.readInt();
			step[1] = objectInputStream.readInt();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				objectInputStream.close();
				objectOutputStream.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return null;
		}
		return step;
	}

}
