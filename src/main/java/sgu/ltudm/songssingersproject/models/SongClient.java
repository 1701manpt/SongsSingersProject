package sgu.ltudm.songssingersproject.models;

import sgu.ltudm.songssingersproject.encryptions.AESEncryption;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class SongClient {
    private final BufferedReader in;
    private final BufferedWriter out;

    public String json;

    public SongClient(BufferedReader in, BufferedWriter out) {
        this.in = in;
        this.out = out;
    }

    public void requestSong(String keyword) throws Exception {
        String data = "song:" + keyword;

        String dataEncode = AESEncryption.encrypt(data, Client.keyAES);

        out.write(dataEncode);
        out.newLine();
        out.flush();

        String songEncode = in.readLine();

        json = AESEncryption.decrypt(songEncode, Client.keyAES);
    }
}
