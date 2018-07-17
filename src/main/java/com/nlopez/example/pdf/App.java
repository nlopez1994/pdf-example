package com.nlopez.example.pdf;

import java.io.*;
import java.util.*;
import java.util.List;

import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws com.lowagie.text.DocumentException, IOException, DocumentException {
        // String current = new java.io.File(".").getCanonicalPath();

        File example = new File("/Users/nahuellopez/Desktop/example.pdf");

        if(!example.exists()) {
            example.createNewFile();
        }

        final File itextpdffile = File.createTempFile("itextpdf", ".pdf");
        final File itextrendererfile = File.createTempFile("itextrendererfile", ".pdf");
        final File pdffile = File.createTempFile("pdffile", ".pdf");

        FileOutputStream fos0 = new FileOutputStream(pdffile);
        FileOutputStream fos1 = new FileOutputStream(example);//new FileOutputStream(itextpdffile);
        FileOutputStream fos2 = new FileOutputStream(itextrendererfile);

        // Build itextpdf file

//		Document document = new Document();
//		PdfWriter.getInstance(document, fos1);
//		document.open();
//
//		document.add(new Paragraph("Hello World!"));

        App.HeaderTable event = new App.HeaderTable();
        // step 1
        Document document = new Document(PageSize.A4, 36, 36, 20 + event.getTableHeight(), 36);
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, fos1);
        writer.setPageEvent(event);
        // step 3
        document.open();
        // step 4
        for(int i = 0; i < 50; i++) document.add(new Paragraph("Hello World!"));

        document.newPage();
        document.add(new Paragraph("Hello World!"));
        document.newPage();
        document.add(new Paragraph("Hello World!"));

        fos1.flush();
        document.close();
        fos1.close();

        // Build itextrenderer file
		/*ITextRenderer renderer = new ITextRenderer();
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
*/
        System.out.println(pdffile.getAbsolutePath());
    }

    static class HeaderTable extends PdfPageEventHelper {
        protected PdfPTable table;
        protected float tableHeight;

        public HeaderTable() {
            table = new PdfPTable(1);
            table.setTotalWidth(523);
            table.setLockedWidth(true);
            table.addCell("Header row 1");
            table.addCell("Header row 2");
            table.addCell("Header row 3");
            tableHeight = table.getTotalHeight();
        }

        public float getTableHeight() {
            return tableHeight;
        }

        public void onEndPage(PdfWriter writer, Document document) {
            table.writeSelectedRows(0, -1,
                    document.left(),
                    document.top() + ((document.topMargin() + tableHeight) / 2),
                    writer.getDirectContent());
        }
    }

}
