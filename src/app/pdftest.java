package app;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class pdftest{
	public static void main(String[] args) {
		try {
			PdfWriter writer = new PdfWriter("/home/dhoang/Downloads/output.pdf");
			PdfDocument pdf = new PdfDocument(writer);
			Document document = new Document(pdf);
			document.add(new Paragraph("Hello World!"));
			document.close();
			System.out.println("eh!");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
