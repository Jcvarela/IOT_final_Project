import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDNonTerminalField;
import org.apache.pdfbox.pdmodel.interactive.form.PDSignatureField;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by jcvar on 4/7/2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        // Load the PDF document created by SimpleForm.java
        PDDocument document = PDDocument.load(new File("output/form.pdf"));

        PDDocumentCatalog docCatalog = document.getDocumentCatalog();
        PDAcroForm acroForm = docCatalog.getAcroForm();
        List<PDField> fields = acroForm.getFields();



        System.out.println(fields.size() + " top-level fields were found on the form");


        for (PDField field : fields)
        {
            try {
                field.setValue("Jorge was here");
                System.out.println("Qualified Name: \t" + field.getFullyQualifiedName());
                System.out.println("type: \t" + field.getFieldType() + "\nValue: \t " + field.getValueAsString());
                System.out.println("\n");
            } catch (Exception e){

            }
        }



        document.save("output/form3.pdf");
        document.close();
    }

}

