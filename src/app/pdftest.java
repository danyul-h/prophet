package app;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class pdftest{
	public static void main(String[] args) {
		try {
			String home = System.getProperty("user.home");
			PdfWriter writer = new PdfWriter(home+"/Downloads/output.pdf");
			PdfDocument pdf = new PdfDocument(writer);
			Document document = new Document(pdf);
			document.add(new Paragraph("Hello World!"));
			document.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
