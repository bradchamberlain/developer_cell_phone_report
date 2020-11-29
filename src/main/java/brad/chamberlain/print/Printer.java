package brad.chamberlain.print;
/**
 * I've never implemented a local print job.  So I found this from:
 * https://www.rgagnon.com/javadetails/java-print-a-text-file-with-javax.print-api.html
 *
 * Shamelessly copied and refactored just a little bit.
 */

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import java.io.*;

public class Printer {

    private static final PrintRequestAttributeSet PRINT_REQUEST_ATTRIBUTE_SET = new HashPrintRequestAttributeSet();
    static
    {
        PRINT_REQUEST_ATTRIBUTE_SET.add(new Copies(1));
        PRINT_REQUEST_ATTRIBUTE_SET.add(OrientationRequested.LANDSCAPE);
    }

    public static void print(String fileToPrint) throws PrintException, IOException
    {
        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
        FileInputStream fileInputStream = new FileInputStream(new File(fileToPrint));
        Doc doc = new SimpleDoc(fileInputStream, DocFlavor.INPUT_STREAM.AUTOSENSE, null);

        DocPrintJob printJob = defaultPrintService.createPrintJob();
        PrintJobWatcher printJobWatcher = new PrintJobWatcher(printJob);
        printJob.print(doc, PRINT_REQUEST_ATTRIBUTE_SET);
        printJobWatcher.waitForDone();
        fileInputStream.close();
    }
}

class PrintJobWatcher
{
    boolean done = false;

    PrintJobWatcher(DocPrintJob job)
    {
        job.addPrintJobListener(new PrintJobAdapter()
        {
            public void printJobCanceled(PrintJobEvent pje)
            {
                allDone();
            }
            public void printJobCompleted(PrintJobEvent pje)
            {
                allDone();
            }
            public void printJobFailed(PrintJobEvent pje)
            {
                allDone();
            }
            public void printJobNoMoreEvents(PrintJobEvent pje)
            {
                allDone();
            }
            void allDone()
            {
                synchronized (PrintJobWatcher.this)
                {
                    done = true;
                    System.out.println("Printing done ...");
                    PrintJobWatcher.this.notify();
                }
            }
        });
    }
    public synchronized void waitForDone()
    {
        try
        {
            while (!done)
            {
                wait();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}