package com.wellbeignatwork.backend.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.wellbeignatwork.backend.entity.Collaboration.Offer;

import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PdfAllOffre {

            public static ByteArrayInputStream OfferPDFReport(List<Offer> offres) throws IOException {
            Document document=new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                PdfWriter.getInstance(document,out);
                document.open();
                //add text to pdf file
                Font font= FontFactory.getFont(FontFactory.COURIER,12,BaseColor.LIGHT_GRAY);
                Paragraph para = new Paragraph("Offer List ",font);
                para.setAlignment(Element.ALIGN_CENTER);
                document.add(para);
                document.add(Chunk.NEWLINE);
                PdfPTable table=new PdfPTable(9);
                //make the columns
                Stream.of("IdOffer","Title","Descrption","StarDate","EndDate","NPlaces","promotion","pourcentage","localisation").forEach(headerTitle -> {
                    PdfPCell header = new PdfPCell();
                    Font headfont= FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                    header.setBackgroundColor(BaseColor.YELLOW);
                    header.setBorderWidth(12);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(headerTitle,headfont));
                    table.addCell(header);


                });

                for (Offer offre : offres) {

                    //id Offer
                    PdfPCell IdOffer = new PdfPCell(new Phrase((offre.getIdOffer())));
                    IdOffer.setPaddingLeft(1);
                    IdOffer.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    IdOffer.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(IdOffer);
                    //title
                    PdfPCell Title = new PdfPCell(new Phrase(offre.getTitle()));
                    Title.setPaddingLeft(1);
                    Title.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    Title.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(Title);

                    PdfPCell Descrption = new PdfPCell(new Phrase(offre.getDescrption()));
                    Descrption.setPaddingLeft(1);
                    Descrption.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    Descrption.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(Descrption);

                    PdfPCell start = new PdfPCell(new Phrase(offre.getStarDateOf().toString()));
                    start.setPaddingLeft(1);
                    start.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    start.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(start);

                    PdfPCell endDate = new PdfPCell(new Phrase(offre.getStarDateOf().toString()));
                    endDate.setPaddingLeft(1);
                    endDate.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    endDate.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(endDate);

                    PdfPCell NPlaces = new PdfPCell(new Phrase(String.valueOf(offre.getNplaces())));
                    NPlaces.setPaddingLeft(1);
                    NPlaces.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    NPlaces.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(NPlaces);

                    PdfPCell promotion = new PdfPCell(new Phrase(String.valueOf(offre.getPromotion())));
                    promotion.setPaddingLeft(1);
                    promotion.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    promotion.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(promotion);

                    PdfPCell pourcentage = new PdfPCell(new Phrase(String.valueOf(offre.getPercentage())));
                    pourcentage.setPaddingLeft(1);
                    pourcentage.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    pourcentage.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(pourcentage);

                    PdfPCell localisation = new PdfPCell(new Phrase(offre.getLocalisation()));
                    localisation.setPaddingLeft(1);
                    localisation.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    localisation.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(localisation);

                }
                // Creating an ImageData object
                String url = "./src/main/resources/Image/QRCode.png";
                Image image = Image.getInstance(url);
                document.add(image);


                document.add(table);
                document.close();

            } catch ( DocumentException | MalformedURLException e) {
                e.printStackTrace();
            }
            return new ByteArrayInputStream(out.toByteArray());
        }

}
