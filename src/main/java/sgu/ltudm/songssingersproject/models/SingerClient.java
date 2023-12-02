package sgu.ltudm.songssingersproject.models;

import javafx.scene.control.Alert;
import sgu.ltudm.songssingersproject.Helpers.MyAlert;
import sgu.ltudm.songssingersproject.encryptions.AESEncryption;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class SingerClient {
    private final BufferedReader in;
    private final BufferedWriter out;

    public String json;

    public SingerClient(BufferedReader in, BufferedWriter out) throws Exception {
        this.in = in;
        this.out = out;

        if (in == null || out == null) {
            throw new Exception("Đóng kết nối");
        }
    }

    public void requestSinger(String keyword) throws Exception {
        String dataSend = "singer:" + keyword;

        String dataEncode = AESEncryption.encrypt(dataSend, Client.keyAES);

        out.write(dataEncode);
        out.newLine();
        out.flush();

        String singerEncode = in.readLine();

        json = AESEncryption.decrypt(singerEncode, Client.keyAES);
    }
}
