package net.thumbtack.school.tasks1_10.file;

import net.thumbtack.school.tasks1_10.colors.Color;
import net.thumbtack.school.tasks1_10.colors.ColorException;
import net.thumbtack.school.tasks1_10.figures.v3.Rectangle;
import net.thumbtack.school.tasks1_10.ttschool.Trainee;
import com.google.gson.Gson;
import net.thumbtack.school.tasks1_10.ttschool.TrainingException;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileService {

    public static void writeByteArrayToBinaryFile(File file, byte[] array) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(array);
        }
    }

    public static void writeByteArrayToBinaryFile(String fileName, byte[] array) throws IOException {
        writeByteArrayToBinaryFile(new File(fileName), array);
    }

    public static byte[] readByteArrayFromBinaryFile(File file) throws IOException {
        byte[] array;
        try (FileInputStream fis = new FileInputStream(file)) {
            array = new byte[fis.available()];
            fis.read(array);
        }
        return array;
    }

    public static byte[] readByteArrayFromBinaryFile(String fileName) throws IOException {
        return readByteArrayFromBinaryFile(new File(fileName));
    }

    public static byte[] writeAndReadByteArrayUsingByteStream(byte[] array) throws IOException {
        byte[] array_return = new byte[array.length / 2];
        byte[] array_read = new byte[array.length];
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            baos.write(array);
        }

        try (ByteArrayInputStream bais = new ByteArrayInputStream(array)) {
            bais.read(array_read);
            int j = 0;
            for (int i = 0; i < array.length; i += 2) {
                array_return[j] = array_read[i];
                j++;
            }
        }
        return array_return;
    }

    public static void writeByteArrayToBinaryFileBuffered(File file, byte[] array) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            bos.write(array);
        }
    }

    public static void writeByteArrayToBinaryFileBuffered(String fileName, byte[] array) throws IOException {
        writeByteArrayToBinaryFileBuffered(new File(fileName), array);
    }

    public static byte[] readByteArrayFromBinaryFileBuffered(File file) throws IOException {
        byte[] array = null;
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            array = new byte[bis.available()];
            bis.read(array);
        }
        return array;
    }

    public static byte[] readByteArrayFromBinaryFileBuffered(String fileName) throws IOException {
        return readByteArrayFromBinaryFileBuffered(new File(fileName));
    }

    public static void writeRectangleToBinaryFile(File file, Rectangle rect) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
            dos.writeInt(rect.getTopLeft().getX());
            dos.writeInt(rect.getTopLeft().getY());
            dos.writeInt(rect.getBottomRight().getX());
            dos.writeInt(rect.getBottomRight().getY());
        }
    }

    public static Rectangle readRectangleFromBinaryFile(File file) throws IOException {
        Rectangle rect = null;
        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            rect = new Rectangle(dis.readInt(), dis.readInt(), dis.readInt(), dis.readInt(), Color.RED);
        } catch (ColorException e) {
            e.printStackTrace();
        }
        return rect;
    }

    public static void writeRectangleArrayToBinaryFile(File file, Rectangle[] rects) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
            for (Rectangle rect : rects) {
                dos.writeInt(rect.getTopLeft().getX());
                dos.writeInt(rect.getTopLeft().getY());
                dos.writeInt(rect.getBottomRight().getX());
                dos.writeInt(rect.getBottomRight().getY());
            }
        }
    }

    public static Rectangle[] readRectangleArrayFromBinaryFileReverse(File file) throws IOException {
        Rectangle[] rects = null;
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            rects = new Rectangle[(int) file.length() / 16];
            for (int i = 0; i < rects.length; i++) {
                raf.seek((rects.length - 1 - i) * 16);
                rects[i] = new Rectangle(raf.readInt(), raf.readInt(), raf.readInt(), raf.readInt(), Color.RED);
            }
        } catch (IOException | ColorException e) {
            e.printStackTrace();
        }
        return rects;
    }

    public static void writeRectangleToTextFileOneLine(File file, Rectangle rect) throws IOException {
        try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file))) {
            osw.write(rect.getTopLeft().getX() + " " + rect.getTopLeft().getY() + " "
                    + rect.getBottomRight().getX() + " " + rect.getBottomRight().getY());
        }
    }

    public static Rectangle readRectangleFromTextFileOneLine(File file) throws IOException {
        Rectangle rect = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String[] str = br.readLine().split(" ");
            rect = new Rectangle(Integer.parseInt(str[0]), Integer.parseInt(str[1]), Integer.parseInt(str[2]), Integer.parseInt(str[3]), Color.RED);
        } catch (ColorException e) {
            e.printStackTrace();
        }
        return rect;
    }

    public static void writeRectangleToTextFileFourLines(File file, Rectangle rect) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
            dos.writeInt(rect.getTopLeft().getX());
            dos.writeChars("\n");
            dos.writeInt(rect.getTopLeft().getY());
            dos.writeChars("\n");
            dos.writeInt(rect.getBottomRight().getX());
            dos.writeChars("\n");
            dos.writeInt(rect.getBottomRight().getY());
            dos.writeChars("\n");
        }
    }

    public static Rectangle readRectangleFromTextFileFourLines(File file) throws IOException {
        Rectangle rect = null;
        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            int xLeft = dis.readInt();
            dis.skip(2);
            int yTop = dis.readInt();
            dis.skip(2);
            int xRight = dis.readInt();
            dis.skip(2);
            int yBottom = dis.readInt();
            dis.skip(2);
            rect = new Rectangle(xLeft, yTop, xRight, yBottom, Color.RED);
        } catch (ColorException e) {
            e.printStackTrace();
        }
        return rect;
    }

    public static void writeTraineeToTextFileOneLine(File file, Trainee trainee) throws IOException {
        try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file))) {
            osw.write(trainee.getFullName() + " " + trainee.getRating());
        }
    }

    public static Trainee readTraineeFromTextFileOneLine(File file) throws IOException {
        Trainee trainee = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String[] str = br.readLine().split(" ");
            trainee = new Trainee(str[0], str[1], Integer.parseInt(str[2]));
        } catch (TrainingException e) {
            e.printStackTrace();
        }
        return trainee;
    }

    public static void writeTraineeToTextFileThreeLines(File file, Trainee trainee) throws IOException {
        try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file))) {
            osw.write(trainee.getFirstName() + "\n" + trainee.getLastName() + "\n" + trainee.getRating() + "\n");
        }
    }

    public static Trainee readTraineeFromTextFileThreeLines(File file) throws IOException {
        Trainee trainee = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            trainee = new Trainee(br.readLine(), br.readLine(), Integer.parseInt(br.readLine()));
        } catch (TrainingException e) {
            e.printStackTrace();
        }
        return trainee;
    }

    public static void serializeTraineeToBinaryFile(File file, Trainee trainee) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(trainee);
        }
    }

    public static Trainee deserializeTraineeFromBinaryFile(File file) throws IOException {
        Trainee trainee = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            trainee = (Trainee) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return trainee;
    }

    public static String serializeTraineeToJsonString(Trainee trainee) {
        Gson gson = new Gson();
        return gson.toJson(trainee);
    }

    public static Trainee deserializeTraineeFromJsonString(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Trainee.class);
    }

    public static void serializeTraineeToJsonFile(File file, Trainee trainee) throws IOException {
        Gson gson = new Gson();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            gson.toJson(trainee, bw);
        }
    }

    public static Trainee deserializeTraineeFromJsonFile(File file) throws IOException {
        Gson gson = new Gson();
        Trainee trainee;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            trainee = gson.fromJson(br, Trainee.class);
        }
        return trainee;
    }
}
