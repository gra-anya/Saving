package ru.evgrafova;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void saveGame(String path, GameProgress gameProgress) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path))) {
            objectOutputStream.writeObject(gameProgress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zipFiles(String zipPath, List<String> filesList) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath))) {
            int count = 0;
            for (String file : filesList) {
                count++;
                FileInputStream fis = new FileInputStream(file);
                ZipEntry entry = new ZipEntry("packed_GameProgress" + count + ".dat");
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
                new File(file).delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {

        List<GameProgress> progresses = Arrays.asList(
                new GameProgress(10, 2, 2, 12),
                new GameProgress(9, 4, 5, 23),
                new GameProgress(7, 10, 13, 122));
        List<String> filesList = new ArrayList<>();

        int count = 0;
        for (GameProgress gameProgress : progresses) {
            count++;
            String fileName = "C:\\Java\\Games\\savegames\\gameProgress" + count + ".dat";
            saveGame(fileName, gameProgress);
            filesList.add(fileName);
        }
        zipFiles("C:\\Java\\Games\\savegames\\zip.zip", filesList);

    }
}
