package com.wellbeignatwork.backend.controller.Collaboration;


import com.itextpdf.text.DocumentException;

import com.stripe.exception.StripeException;
import com.wellbeignatwork.backend.entity.Collaboration.Payment;
import com.wellbeignatwork.backend.entity.Collaboration.Reservation;
import com.wellbeignatwork.backend.entity.User.User;
import com.wellbeignatwork.backend.repository.User.UserRepository;
import com.wellbeignatwork.backend.service.Collaboration.IReservationService;
import com.wellbeignatwork.backend.service.Collaboration.ISendEmailService;
import com.wellbeignatwork.backend.util.PDFGeneratorService;
import com.wellbeignatwork.backend.util.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequestMapping("/Reservation")
@RestController
public class ReservationController {

    @Autowired
    IReservationService reservationService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    StripeService stripeService;
    @Autowired
    private ISendEmailService iServiceEmail;

    private final PDFGeneratorService pdfGeneratorService;

    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/image/QRCode.png";

    public ReservationController(PDFGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }


    @GetMapping("/pdf/generate")
    public void generatePDF(HttpServletResponse response, String p1 , String p2 , String qrcode) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        this.pdfGeneratorService.exportFor(response,p1,p2,qrcode);
    }
    //http://localhost:8081/Wellbeignatwork/Reservation/addResevation/1/1
    //local date time 2022-03-24T12:59:11.332
    @PostMapping("/addResevation/{idOffer}/{idUser}")
    @ResponseBody
    public void addResevation(@RequestBody Reservation r, @PathVariable long idUser, @PathVariable long idOffer, HttpServletResponse response) throws MessagingException, DocumentException, IOException, com.lowagie.text.DocumentException {

        User u = userRepository.findById(idUser).orElse(null);
       /* byte[] image = new byte[0];
        try {

            // Generate and Return Qr Code in Byte Array
            image = QRCodeGenerator.getQRCodeImage(u.getEmail(),250,250);

            QRCodeGenerator.generateQRCodeImage(u.getEmail(),250,250,QR_CODE_IMAGE_PATH);

            // Generate and Save Qr Code Image in static/image folder

        } catch (WriterException | IOException e) {

            e.printStackTrace();
        }
        // Convert Byte Array into Base64 Encode String
        String qrcode = Base64.getEncoder().encodeToString(image);
        // log.info(qrcode);
        generatePDF(response,u.getEmail(),u.getEmail(),qrcode);
        iServiceEmail.sendSimpleEmail("mahdi.homrani@esprit.tn","  add Resevation " ," add succesful ... ");*/

        reservationService.reservation(idUser,idOffer,r);
    }

    //http://localhost:8081/Wellbeignatwork/Reservation/calculTotal/1/1
    @GetMapping("/calculTotal/{idOffer}/{idReservation}")
    @ResponseBody
    public float calculTotal( @PathVariable long idReservation, @PathVariable long idOffer){
        return reservationService.prixTotale(idReservation,idOffer);
    }

    //http://localhost:8081/Wellbeignatwork/Reservation/stripe/1/1
    @PostMapping("/stripe/{idUser}/{idReservation}")
    @ResponseBody
    public Payment index(@PathVariable long idUser , @PathVariable long idReservation , @RequestBody Payment p ) throws StripeException {
        return stripeService.payment(idUser,idReservation,p);
    }

    //http://localhost:8081/Wellbeignatwork/Reservation/stripe/email/token/1/1/1
    @PostMapping("/stripe/{email}/{token}/{idUser}/{idReservation}/{idOffer}")
    @ResponseBody
    public Reservation createCharge(@PathVariable String email,@PathVariable String token,@PathVariable Long idUser,@PathVariable Long idReservation,@PathVariable Long idOffer) throws StripeException, MessagingException {
        return stripeService.createCharge(email,token,idUser,idReservation,idOffer);
    }
}
