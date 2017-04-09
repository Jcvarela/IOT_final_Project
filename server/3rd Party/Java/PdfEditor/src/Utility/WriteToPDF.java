package Utility;

import Utility.JSON.simple.JSONObject;
import Utility.JSON.simple.parser.JSONParser;
import Utility.JSON.simple.parser.ParseException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by jcvar on 4/8/2017.
 */


public class WriteToPDF {

    public static void fillPDF(String inputPath, String json)throws IOException, ParseException {
        fillPDF(inputPath,inputPath,json);
    }


    /**Function to update pdf file depending on the json
     *
     * @param inputPath     file path that's going to be updated
     * @param outputPath    path  for saving file
     * @param json          contains a map between fields Qualified Name with the new value
     * @throws IOException      Problem opening the pdf file
     * @throws ParseException   Problem reading the json String
     */
    public static void fillPDF(String inputPath, String outputPath, String json) throws IOException, ParseException {

        File file = new File(inputPath);
        //load document
        PDDocument document = PDDocument.load(file);

        List<PDField> fields = getFields(document);
        JSONObject jsonObj = getJsonObject(json);

        updateFields(fields,jsonObj);

        document.save(outputPath);
        document.close();

    }

    private static void updateFields(List<PDField> fields, JSONObject json){

        for(PDField field: fields){
            String id = field.getFullyQualifiedName();

            String tempJson = (String)json.get(id);

            updateField(field,tempJson);
        }
    }

    private static void updateField(PDField field, String value){
        if(value == null){
            return;
        }
        try {
            //separated to add different test cases depending on the input
            
            if (field instanceof PDCheckBox) {
                field.setValue(value); //Yes
            } else if (field instanceof PDComboBox) {
                field.setValue(value);
            } else if (field instanceof PDListBox) {
                field.setValue(value);
            } else if (field instanceof PDRadioButton) {
                field.setValue(value);
            } else if (field instanceof PDTextField) {
                field.setValue(value);
            }else {
                field.setValue(value);
            }

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private static JSONObject getJsonObject(String json) throws ParseException {
        JSONParser parser = new JSONParser();

        JSONObject jsonObj = (JSONObject) parser.parse(json);

        return jsonObj;
    }

    private static List<PDField> getFields(PDDocument document){

        PDDocumentCatalog documentCatalog = document.getDocumentCatalog();
        PDAcroForm acroForm = documentCatalog.getAcroForm();
        List<PDField> fields = acroForm.getFields();

        return fields;
    }

}
