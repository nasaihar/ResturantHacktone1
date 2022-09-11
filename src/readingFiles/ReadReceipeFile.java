package readingFiles;

import entities.Ingredient;
import entities.Receipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadReceipeFile {

    public static List<Receipe> readReceipeFile(String filePath, List<Ingredient> ingredientList) throws IOException {
        List<String> fileList=ReadFile.readInputFile(filePath);
        List<Receipe> receipeList=new ArrayList<>();
        Map<Ingredient,Double> com=new HashMap<>();
        for (String s:fileList) {
            String splitLine[] =s.split(" ");
            for(int i=2;i<splitLine.length;i=i+2)
            {
                String ingName=splitLine[i];
                double qnt=Double.parseDouble(splitLine[i+1]);
                for (int j=0;j<ingredientList.size();j++)
                {
                    if(ingredientList.get(j).getName().equals(ingName))
                    {
                      com.put(ingredientList.get(j),qnt);
                    }
                }
            }
            receipeList.add(new Receipe(splitLine[0],com,Double.parseDouble(splitLine[1])));
        }
        System.out.println(" No of receipes "+receipeList.size());
        return receipeList;
    }
}
