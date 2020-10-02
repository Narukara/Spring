package club.narukara.spring;

import club.narukara.spring.player.HumanPlayer;
import club.narukara.spring.player.LocalPlayer;
import club.narukara.spring.player.RandomPlayer;

import java.io.IOException;

public class Custom {
    public static void main(String[] args) throws IOException {
        new LocalPlayer("127.0.0.1", 51234, new HumanPlayer()).start();
    }
}
