package com.wellbeignatwork.backend.controller.Collaboration;

import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;

import com.wellbeignatwork.backend.entity.Collaboration.Offer;
import com.wellbeignatwork.backend.service.Collaboration.IOfferService;
import com.wellbeignatwork.backend.service.Collaboration.ISendEmailService;
import com.wellbeignatwork.backend.service.Evaluation.QRCodeGenerator;
import com.wellbeignatwork.backend.util.OfferPDFExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/Offer")
public class OfferController {

    @Autowired
    IOfferService offerService;

    @Autowired
    private ISendEmailService iSendEmailService;


    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/Image/QRCode.png";


    //http://localhost:8081/Wellbeignatwork/Offer/addOffer/1
    @PostMapping("/addOffer/{idCollaboration}")
    @ResponseBody
    public Offer addOffer(@RequestBody Offer o, @PathVariable long idCollaboration, HttpServletResponse response) throws DocumentException, IOException{
        byte[] image = new byte[0];
        try {

            // Generate and Return Qr Code in Byte Array
            image = QRCodeGenerator.getQRCodeImage(o.getTitle(),250,250);

            QRCodeGenerator.generateQRCodeImage(o.getLocalisation(),250,250,QR_CODE_IMAGE_PATH);

            // Generate and Save Qr Code Image in static/image folder

        } catch (IOException e) {

            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
        // Convert Byte Array into Base64 Encode String
        String qrcode = Base64.getEncoder().encodeToString(image);
        // log.info(qrcode);

        return offerService.addOffer(o,idCollaboration);
    }

    //http://localhost:8081/Wellbeignatwork/Offer/deleteOffer/id
    @DeleteMapping("/deleteOffer/{id}")
    @ResponseBody
    public void deleteOffer(@PathVariable Long id){
        offerService.deleteOffer(id);
    }

    //http://localhost:8081/Wellbeignatwork/Offer/updateOffer/1
    @PutMapping("/updateOffer")
    @ResponseBody
    public Offer updateOffer(@RequestBody Offer o){
        return offerService.updateOffer(o);
    }

    //http://localhost:8081/Wellbeignatwork/Offer/retrieveAllOffers
    @GetMapping("/retrieveAllOffers")
    @ResponseBody
    public List<Offer> retrieveAllOffers(){
        return offerService.retrieveAllOffers();
    }

    //http://localhost:8081/Wellbeignatwork/Offer/retrieveOffer/1
    @GetMapping("/retrieveOffer/{id}")
    @ResponseBody
    public Offer retrieveOffer(@PathVariable Long id){
        return offerService.retrieveOffer(id);
    }

    //http://localhost:8081/Wellbeignatwork/Offer/calculPromotion/id
    @PostMapping("/calculPromotion/{idOffer}")
    @ResponseBody
    public float calculPromotion (@PathVariable long idOffer){
       return offerService.calculProm(idOffer);
    }

    //http://localhost:8081/Wellbeignatwork/Offer/exportOffer/pdf
    @GetMapping("/exportOffer/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException, com.lowagie.text.DocumentException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Offer> listOffers = offerService.listAll();

        OfferPDFExporter exporter = new OfferPDFExporter(listOffers);
        exporter.export(response);

    }

    //http://localhost:8081/Wellbeignatwork/Offer/weather?idOffer=1
    @GetMapping("/weather")
   // @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Object getOfferWeather(@RequestParam Long idOffer) {
        return offerService.getOfferWeather(idOffer);
    }

    //http://localhost:8081/Wellbeignatwork/Offer/uploadImageToOffer/1
    @PostMapping("/uploadImageToOffer/{idOffer}")
    void uploadImageToOffer(@RequestParam("image") MultipartFile img, @PathVariable Long idOffer) throws IOException {
        offerService.uploadImageToOffer(img, idOffer);
    }
}
