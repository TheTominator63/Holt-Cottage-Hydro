/*
   DOCO:  
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class testParse2
{
    public static void main(String []args)
    {
        System.out.println("Hello world!");

        int size = 0;
        int recnum=0;
        try
        {
            BinaryFileInteraction binaryFile1 = new BinaryFileInteraction("dat/US4CN22M.000");

            System.out.println(binaryFile1.getRecordLength(0));

            File s  = new File("dat/US4CN22M.000");


            while (binaryFile1.getFileData().size() != binaryFile1.getLengthTraversed())
            {
                try
                {
                    int x = binaryFile1.getLengthTraversed();
                    int y = binaryFile1.getRecordLength(x);
                    binaryFile1.getRecordData(y);
                }
                catch (StringIndexOutOfBoundsException e)
                {
                    System.out.println("It's all finished");
                }
                System.out.println(recnum + " Read characters");
                recnum++;
            }

            int sz = 0;
            for (aRecord a : binaryFile1.getAllrecords())
            {
                sz += a.getReclen(); // sz = sz + a.getReclen();
                System.out.println(a);
            }
            System.out.println("total size=" + sz);
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
