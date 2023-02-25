import java.io.FileNotFoundException;
import java.io.IOException;

public class ISOIEC8211 {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        String filename = "dat/US4CN22M.000";
        try {
            BinaryFileInteraction binaryFile1 = new BinaryFileInteraction(filename);
            while (binaryFile1.getFileData().length() != binaryFile1.getLengthTraversed()) {
                try {
                    binaryFile1.getRecordsFromString(binaryFile1.getRecordLength(binaryFile1.getLengthTraversed()));
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("It's all finished");
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}