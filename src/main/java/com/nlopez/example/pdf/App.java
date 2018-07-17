package com.nlopez.example.pdf;

import java.io.*;
import java.util.*;
import java.util.List;

import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws com.lowagie.text.DocumentException, IOException, DocumentException {
		// String current = new java.io.File(".").getCanonicalPath();

		final File itextpdffile = File.createTempFile("itextpdf", ".pdf");
		final File itextrendererfile = File.createTempFile("itextrendererfile", ".pdf");
		final File pdffile = File.createTempFile("pdffile", ".pdf");

		FileOutputStream fos0 = new FileOutputStream(pdffile);
		FileOutputStream fos1 = new FileOutputStream(itextpdffile);
		FileOutputStream fos2 = new FileOutputStream(itextrendererfile);

		// Build itextpdf file

		Document document = new Document(PageSize.A6);
		PdfWriter.getInstance(document, fos1);
		document.open();
		document.add(new Paragraph("Hello World!"));
		document.close();
		
		fos1.close();

		// Build itextrenderer file
		ITextRenderer renderer = new ITextRenderer();
		String processedHtml = "<html><head></head><body><h1>Hello World!</h1></body></html>";
		renderer.setDocumentFromString(processedHtml);
		renderer.layout();
		renderer.createPDF(fos2, false);
		renderer.finishPDF();

		fos2.close();

		// Build final pdf file
		Document doc = new Document();
		PdfWriter w = PdfWriter.getInstance(doc, fos0);

		doc.open();
		PdfContentByte cb = w.getDirectContent();

		List<InputStream> list = new ArrayList<InputStream>();

		list.add(new FileInputStream(itextpdffile));
		list.add(new FileInputStream(itextrendererfile));

		for (InputStream in : list) {
			PdfReader reader = new PdfReader(in);
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				doc.newPage();
				// import the page from source pdf
				PdfImportedPage page = w.getImportedPage(reader, i);
				// add the page to the destination pdf
				cb.addTemplate(page, 0, 0);
			}
		}

		fos0.flush();
		doc.close();
		fos0.close();

		System.out.println(pdffile.getAbsolutePath());
	}

}
