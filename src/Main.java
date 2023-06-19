import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    private static final String EXTENSION = ".data";
    public static final String LOCATION = "D:/Idea/Games";

    public static void main(String[] args) throws IOException {

        File gameDirectory = new File(LOCATION);
        GameProgress gameProgress1 = new GameProgress(91, 1, 2, 108.9);
        GameProgress gameProgress2 = new GameProgress(27, 2, 6, 238.1);
        GameProgress gameProgress3 = new GameProgress(74, 4, 14, 579.2);

        saveGame(gameProgress1, "save1.data");
        saveGame(gameProgress2, "save2.data");
        saveGame(gameProgress3, "save3.data");

        zipFiles(LOCATION, "save1.data", "save2.data", "save3.data");

        if (gameDirectory.isDirectory()) {
            for (File file : gameDirectory.listFiles()) {
                if (file.getName().contains(EXTENSION)) {
                    if (file.delete())
                        System.out.println(file + " - удален!");
                    else
                        System.out.println(file + " - удалить не получилось! ");
                }
            }
        }
    }
    private static void saveGame(GameProgress gameProgress1, String saveDate) {
        try (FileOutputStream fos = new FileOutputStream(LOCATION + saveDate);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress1);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void zipFiles(String path, String... savesFile) throws IOException {
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(path + "zip.zip"));
        for (String saveFile : savesFile) {
            FileInputStream fis = new FileInputStream(path + saveFile);
            ZipEntry entry = new ZipEntry(saveFile);
            zos.putNextEntry(entry);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zos.write(buffer);
            zos.closeEntry();
            fis.close();
        }
        zos.close();
    }

}