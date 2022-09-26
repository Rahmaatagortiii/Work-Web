package com.wellbeignatwork.backend.util;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.wellbeignatwork.backend.entity.Collaboration.Offer;



public class OfferPDFExporter {
    private List<Offer> listOffers;

    public OfferPDFExporter(List<Offer> listOffers) {
        this.listOffers = listOffers;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        cell.setBorderWidth(1);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Id Offer", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Title", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("localisation", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("StarDate", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("EndDate", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Offer offer : listOffers) {
            table.addCell(String.valueOf(offer.getIdOffer()));
            table.addCell(offer.getTitle());
            table.addCell(offer.getLocalisation());
            table.addCell(offer.getStarDateOf().toString());
            table.addCell(offer.getEndDateOf().toString());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("List of Offer", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {2f, 2f, 2f, 2.5f, 2.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}