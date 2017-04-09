import Utility.JSON.simple.parser.ParseException;
import Utility.WriteToPDF;

import java.io.IOException;

/**
 * Created by jcvar on 4/8/2017.
 */
public class Main {
    public static void main(String[] args) throws IOException, ParseException {

        if(args.length < 2){
            System.err.println("Invalid parameters");
            return;
        }

        int parameters = Integer.parseInt(args[0]);

        String inputPath;
        String outPath;
        int jsonStartPos;

        if(parameters == 3){
            inputPath = args[1];
            outPath = args[2];
            jsonStartPos = 3;
        }
        else {
            inputPath = args[1];
            outPath = args[1];
            jsonStartPos = 2;
        }


        String json = "";
        for(int i = jsonStartPos; i < args.length; i++){
            json += args[i];
        }

        WriteToPDF.fillPDF(inputPath,outPath,json);

//        WriteToPDF.fillPDF("C:\\Users\\jcvar\\Desktop\\repo\\IOT_final_Project\\server\\resources\\form.pdf",
//                "C:\\Users\\jcvar\\Desktop\\repo\\IOT_final_Project\\server\\resources\\form2.pdf",
//                "{\"Full name\":\"Jorge\"," +
//                        "\"DOB\":\"08/17/93\"}");
    }



}
