import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ISOIEC8211
{
    public static void main(String[] args)
    {
        System.out.println("Hello world!");
//        BinaryFileInteraction binaryFile1 = new BinaryFileInteraction("US4CN22M.000");

        try
        {
            BinaryFileInteraction binaryFile1 = new BinaryFileInteraction("US4CN22M.000");
            System.out.println(binaryFile1.getRecordLength(0));


            while (binaryFile1.getFileData().size() != binaryFile1.getLengthTraversed())
            {
                try {
                    binaryFile1.getRecordData(binaryFile1.getRecordLength(binaryFile1.getLengthTraversed()));
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("It's all finished");
                }
                //Test Statement below
                //System.out.println(binaryFile1.getLengthTraversed());
            }
            System.out.println(binaryFile1.getAllrecords());



//        for (int i = 0; i < binaryFile1.getRecords().size(); i++)
//        {
//            System.out.println(binaryFile1.getRecords().get(i));
//        }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }



    }
}