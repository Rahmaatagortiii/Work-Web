package com.wellbeignatwork.backend.controller.Collaboration;


import com.sun.mail.iap.Response;

import com.wellbeignatwork.backend.entity.Collaboration.Collaboration;
import com.wellbeignatwork.backend.entity.Collaboration.Publicity;
import com.wellbeignatwork.backend.service.Collaboration.IPublicityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/Publicity")
public class publicitycontroller {
    @Autowired
    IPublicityService publicityService;

    //http://localhost:8081/Wellbeignatwork/Publicity/addPublicity/1
    @PostMapping("/addPublicity/{idOffer}")
    @ResponseBody
    public Publicity addPublicity(@RequestBody Publicity p ,@PathVariable long idOffer){
        return publicityService.addPublicity(p,idOffer);
    }
    //http://localhost:8081/Wellbeignatwork/Publicity/retrievePublicity/1
    @GetMapping("/retrievePublicity/{idPub}")
    @ResponseBody
    public Publicity retrievePublicity(@PathVariable Long idPub){
        return publicityService.retrievePublicity(idPub);
    }


    //http://localhost:8081/Wellbeignatwork/Publicity/datePublicity/idPublicity/starDate/finDate
    @GetMapping("/datePublicity/{idPublicity}/{starDateOf}/{finDateOf}")
    @ResponseBody
    public boolean dateOffer(@PathVariable long idPublicity, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date starDateOf, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date finDateOf){
        return   publicityService.dateOffer(idPublicity,starDateOf,finDateOf);
    }


    //http://localhost:8081/Wellbeignatwork/Publicity/upload-image/1
    @PostMapping("/upload-image/{idPublicity}")
    //@PreAuthorize("hasRole('ADMIN')")
    public Response uploadImageToEvent(@RequestParam MultipartFile[] imgs, @PathVariable Long idPublicity) {
        try {
            for (MultipartFile img : imgs) {
                publicityService.uploadImageToPub(img, idPublicity);
            }
            return new Response("All images have been uploaded successfully!", true);
        } catch (Exception e) {
            return new Response(e.getMessage(), false);
        }
    }
    //http://localhost:8081/Wellbeignatwork/Publicity/upload-banner/1
    @PutMapping("/upload-banner/{idPublicity}")
    //@PreAuthorize("hasRole('ADMIN')")
    public Response uploadBanner(@RequestParam MultipartFile img, @PathVariable Long idPublicity) {
        try {
            publicityService.uploadPubBanner(img, idPublicity);
            return new Response("Event banner has been uploaded successfully!", true);
        } catch (Exception e) {
            return new Response(e.getMessage(), false);
        }
    }

    //http://localhost:8081/Wellbeignatwork/Publicity/delete-image/name
    @DeleteMapping("/delete-image/{name}")
    //@PreAuthorize("hasRole('ADMIN')")
    public Response deleteImage(@PathVariable String name) {
        try {
            publicityService.deleteImage(name);
            return new Response("Image has been deleted successfully!", true);
        } catch (Exception e) {
            return new Response(e.getMessage(), false);
        }
    }
    //http://localhost:8081/Wellbeignatwork/Publicity/retrieve-by-title/title
    @GetMapping("/retrieve-by-title/{title}")
    public List<Publicity> retrieveByTitle(@PathVariable String title) {
        return publicityService.retrieveByTitle(title);
    }
    //http://localhost:8081/Publicity/retrieve-by-date
    @GetMapping("/retrieve-by-date")
    public List<Publicity> retrieveBeforeOfferStartDate() {
        return publicityService.retrieveBeforeOfferStartDate();
    }

    //http://localhost:8081/Wellbeignatwork/Publicity/retrieve-by-localisation/localisation
    @GetMapping("/retrieve-by-localisation/{localisation}")
    public List<Publicity> retrieveByOfferLocalisation(@PathVariable String loc) {
        return publicityService.retrieveByOfferLocalisation(loc);
    }

    //http://localhost:8081/Wellbeignatwork/Publicity/retriveAllPub
    @GetMapping("/retriveAllPub")
    public List<Publicity> retrieveAllPublicitys(){
        return publicityService.retrieveAllPublicitys();
    }
    //http://localhost:8081/Wellbeignatwork/Publicity/uploadImageToPub/1
    @PostMapping("/uploadImageToPub/{idPub}")
    void uploadImageToOffer(@RequestParam("image") MultipartFile img, @PathVariable Long idPub) throws IOException {
        publicityService.uploadImageToPub(img, idPub);
    }
}

