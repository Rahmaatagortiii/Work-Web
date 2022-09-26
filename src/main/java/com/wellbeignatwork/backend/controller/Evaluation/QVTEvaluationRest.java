package com.wellbeignatwork.backend.controller.Evaluation;

import com.wellbeignatwork.backend.entity.Evaluation.Answer;
import com.wellbeignatwork.backend.entity.Evaluation.Question;
import com.wellbeignatwork.backend.service.Evaluation.IntQVTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wellbeignatwork.backend.entity.User.User;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/QVT")
public class QVTEvaluationRest {

  @Autowired
    private IntQVTService MyQVTService;

    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/QrCode/QRCode.png";

    @PostMapping("/addUser")
    public User adduser(@RequestBody User u)
    { return MyQVTService.addUser(u); }

  @GetMapping("/Survey")
  public List<Question> SendAllSurveys()
  {
    return MyQVTService.SendSurvey();

  }
    @PostMapping("/PutYourAnswerAndGetYourAdvice")
    public void UserAnswer(@RequestBody List<Answer>  answer)
    {  MyQVTService.UserAnswer(answer); }
     @GetMapping("/Stastic")
     public String nbreSentiment()
    { return MyQVTService.nbreSentiment();}

  @GetMapping("/download")
  public ResponseEntity<InputStreamResource> getFile() {
    String filename = "FeedBack.csv";
    InputStreamResource file = new InputStreamResource(MyQVTService.load());

    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
            .contentType(MediaType.parseMediaType("application/csv"))
            .body(file);
  }

/*
    @GetMapping(value = "generateRandomCODEPROMO")
     public void RandomCode ()
    {
        MyQVTService.generateRandomCODEPROMO();
    }

    @GetMapping(value = "/genrateAndDownloadQRCode/{width}/{height}")
    public void download(

            @PathVariable("width") Integer width,
            @PathVariable("height") Integer height)
            throws Exception {
        QRCodeGenerator.generateQRCodeImage(MyQVTService.generateRandomCODEPROMO() ,width, height, QR_CODE_IMAGE_PATH);
    }

    @GetMapping(value = "/genrateQRCode/{width}/{height}")
    public ResponseEntity<byte[]> generateQRCode(

            @PathVariable("width") Integer width,
            @PathVariable("height") Integer height)
            throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(QRCodeGenerator.getQRCodeImage(MyQVTService.generateRandomCODEPROMO(), width, height));
    }


 */

















}
