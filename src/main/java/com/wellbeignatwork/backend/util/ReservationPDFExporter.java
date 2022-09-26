package com.wellbeignatwork.backend.util;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.wellbeignatwork.backend.entity.Collaboration.Reservation;


public class ReservationPDFExporter {
    private List<Reservation> listReservations;

    public ReservationPDFExporter(List<Reservation> listReservations) {
        this.listReservations = listReservations;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        cell.setBorderWidth(1);
        cell.setPadding(6);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("USER ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Id Offer", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Id Reservation", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Start Date ", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("End Date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Nombre Palce", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table) {
        for (Reservation reservation : listReservations) {
            table.addCell(String.valueOf(reservation.getUserRes().getId()));
            table.addCell(String.valueOf(reservation.getOffersRes().getIdOffer()));
            table.addCell(String.valueOf(reservation.getIdReservation()));
            table.addCell(reservation.getStartDateRes().toString());
            table.addCell(reservation.getEndDateRes().toString());
            table.addCell(String.valueOf(reservation.getNmPalce()));
        }
    }

    public String export(HttpServletResponse response, String p1, String p2, String qrcode) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("List of Reservation", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{2f, 2f, 2f, 2.5f, 2.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

        return null;
    }
}
