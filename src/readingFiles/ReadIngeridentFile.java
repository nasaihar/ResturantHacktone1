package readingFiles;

import entities.Ingredient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadIngeridentFile
{
    public static List<Ingredient> readIngridentlist(String filePath) throws IOException {
        List<String> inglist=ReadFile.readInputFile(filePath);
        List<Ingredient> ingredientList=new ArrayList<>();
        for (String s:inglist) {
            String [] splitLine=s.split(" ");
            ingredientList.add(new Ingredient(splitLine[0],Double.parseDouble(splitLine[1]),Double.parseDouble(splitLine[2])));

        }
        System.out.println("Number of Ing "+ingredientList.size());
        return ingredientList;
    }
}
