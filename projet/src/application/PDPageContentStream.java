package application;

import java.io.File;
import java.io.IOException;

import application.JavaFXApplication1.Student;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PDPageContentStream {
	FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save Summary");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
    File outputFile = fileChooser.showSaveDialog(studentTable.getScene().getWindow());

    if (outputFile != null) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            PDType0Font font;
            font = PDType0Font.load(document, new File("path/to/Calibri.ttf"));
            contentStream.setFont(font, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 700);
            contentStream.showText("Student Summary");
            contentStream.newLine();
            contentStream.newLine();
            contentStream.setFont(font, 10);

            for (Student student : students) {
                contentStream.showText("ID: " + student.getId());
                contentStream.newLine();
                contentStream.showText("Name: " + student.getName());
                contentStream.newLine();
                contentStream.showText("Email: " + student.getEmail());
                contentStream.newLine();
                contentStream.showText("Father Name: " + student.getFatherName());
                contentStream.newLine();
                contentStream.showText("CNIC: " + student.getCnic());
                contentStream.newLine();
                contentStream.showText("Address: " + student.getAddress());
                contentStream.newLine();
                contentStream.showText("Contact No: " + student.getContactNo());
                contentStream.newLine();
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close();
            document.save(outputFile);
            document.close();

            showAlert("Success", "Summary saved successfully.");
        } catch (IOException e) {
            showAlert("Error", "An error occurred while saving the summary.");
        }
    }
}


}
