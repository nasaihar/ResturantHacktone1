package readingFiles;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {

    public static List<String> readInputFile(String filePath) throws IOException {
        File f=new File(filePath);
        FileReader fr=new FileReader(f);
        List<String> inputFile= new ArrayList<>();
        BufferedReader br=new BufferedReader(fr);
        String st;
        while ((st=br.readLine())!= null)
        {
            inputFile.add(st);
        }
        System.out.println("No of lines read "+inputFile.size());
        return inputFile;
    }
}
