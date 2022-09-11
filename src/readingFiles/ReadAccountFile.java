package readingFiles;

import java.io.IOException;
import java.util.List;

public class ReadAccountFile {

    public static double readAccountFile(String filePath) throws IOException {
        List<String> listString=ReadFile.readInputFile(filePath);
        Double intialAmount=Double.parseDouble(listString.get(0));
        return intialAmount;
    }
}
