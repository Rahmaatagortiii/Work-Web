package com.wellbeignatwork.backend.controller.Event;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.WriterException;
import com.wellbeignatwork.backend.entity.User.User;

import com.lowagie.text.DocumentException;
import com.wellbeignatwork.backend.entity.User.Departement;
import com.wellbeignatwork.backend.entity.Event.*;
import com.wellbeignatwork.backend.service.Event.ActivityServiceImp;
import com.wellbeignatwork.backend.service.Event.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/event")
public class ActitvityController {

    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/QRCode.png";
    @Autowired
    private IActivityService activityService;

    @PostMapping("/AddEvent")
    @ResponseBody
    public void addEvent (@RequestParam("file") MultipartFile file, @RequestParam("event")String event)throws IOException{
        Event e = new ObjectMapper().readValue(event, Event.class);
        activityService.addEvent(e,file);

    }
    @PostMapping("/AddE")
    @ResponseBody
    public void addEvent (@RequestBody Event event){
        activityService.addEvent(event);
    }

    @DeleteMapping("/removeE/{event-id}")
    @ResponseBody
    public void deleteEvent(@PathVariable("event-id")Event e){
        activityService.deleteEvent(e);
    }

    @PutMapping("/modifyE")
    @ResponseBody
    public Event updateEvent( @RequestBody Event e){
       return activityService.updateEvent(e);
    }

    @GetMapping("/getAllEvents")
    @ResponseBody
    public List<Event> getAllEvents(){
        return activityService.getAllEvents();
    }

    @GetMapping("/assign-user-to-event/{id_user}/{id_event}")
    @ResponseBody
    public void assignUserToEvent (@PathVariable("id_user") Long idUser ,
                                   @PathVariable("id_event") Long idEvent,
                                   HttpServletResponse response
                                   )throws DocumentException, IOException, WriterException {
        String codeText=idUser+"-"+idEvent;
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=ticket" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        activityService.assignUserToEvent(idUser,idEvent);
        activityService.export(response,idEvent,idUser,codeText,100,100,QR_CODE_IMAGE_PATH);
        ResponseEntity.status(HttpStatus.OK).body(ActivityServiceImp.getQRCodeImage(codeText, 100, 100));
        }
    @GetMapping("/nbr")
    @ResponseBody
    public void getNbrOfParticipant() {
         activityService.getNbrOfParticipant();

    }
    @GetMapping("/distance/{a}/{b}")
    @ResponseBody
    public double calculDistance (@PathVariable("a") String a ,
                                  @PathVariable("b") String b ){
        return activityService.calculDistance(a,b);
    }

    @GetMapping("/sortByDistance")
    @ResponseBody
    public List<Event> sortedByDistance (){
        return activityService.sortedByDistance();
    }

  /*  @GetMapping("/tags")
    @ResponseBody
    public List<ITagsCount> tags(){
        return activityService.tags();
    }*/

    @GetMapping("/sortByFrais")
    @ResponseBody
    public List<Event> sortedByFrais(){
        return activityService.sortedByFrais();
    }
   @GetMapping("/rev/{id-event}")
   @ResponseBody
    public double getRevenueByEvent(
            @PathVariable("id-event") Long idEvent){
            return activityService.getRevenueByEvent(idEvent);
    }
    @GetMapping("/compare/{id-user}")
    @ResponseBody
    public List<Event> showEventsByUser(@PathVariable("id-user") Long idUser){
        return activityService.showEventsByUser(idUser);
    }
    @GetMapping("/gift")
    @ResponseBody
    public void cadeauEvent (){
        activityService.cadeauEvent();
    }
    @GetMapping("/reduction/{id-user}/{id-event}/{familymemb}")
    @ResponseBody
    public void reductionEvent ( @PathVariable("id-event") Long idEvent,
                                 @PathVariable("id-user") Long idUser,
                                 @PathVariable("familymemb")int familyNumber){
        activityService.reductionEvent(idEvent,idUser,familyNumber);

    }

    @GetMapping("/weather")
    @ResponseBody
    public Object getEventWeather(@RequestParam Long eventId) {
        return activityService.getEventWeather(eventId);
    }

    @GetMapping("/popularEvent")
    @ResponseBody
    public ResponseEntity<Event> popularEvent(){
         return new ResponseEntity<>(activityService.popularEvent(), HttpStatus.OK);
    }
    @GetMapping("/invite/{idUser}/{idEvent}")
    @ResponseBody
    public void inviteUser (@PathVariable Long idUser, @PathVariable Long idEvent){
        activityService.inviteUser(idUser,idEvent);
    }
    @GetMapping("/accept/{idUser}/{idEvent}")
    @ResponseBody
    public void acceptInvitation (@PathVariable Long idEvent , @PathVariable Long idUser){
        activityService.acceptInvitation(idEvent,idUser);
    }

    @GetMapping("/assign-point/{id-u}/{id-event}")
    @ResponseBody

    public void assignPointToUser(
                                  @PathVariable("id-event") Long idEvent,
                                  @PathVariable("id-u") Long idUser)
    {
        activityService.assignPointToUser(idUser,idEvent);
    }


    @PostMapping("/AddU")
    @ResponseBody
    public void addUser (@RequestBody User u){
        activityService.addUser(u);
    }
    @PostMapping("/AddS")
    @ResponseBody
    public void addSubscription(@RequestBody Subscription s){
        activityService.addSubscription(s);
    }
    @DeleteMapping("/removeS/{sub-id}")
    @ResponseBody
    public void deleteSubscription(@PathVariable("sub-id")Subscription s){
        activityService.deleteSubscription(s);
    }
    @GetMapping("/getAllSub")
    @ResponseBody
    public List<Subscription> getAllSubscriptions() {
        return activityService.getAllSubscriptions();
    }
    @GetMapping("/assign-user-to-sub/{id_user}/{id_sub}")
    @ResponseBody
    public void assignUserToSubscription(@PathVariable("id_user") Long idUser, @PathVariable("id_sub") Long idSubscription){
        activityService.assignUserToSubscription(idUser,idSubscription);
    }

    @PutMapping("/modifyS")
    @ResponseBody
    public void updateSubscription(@RequestBody Subscription s){
        activityService.updateSubscription(s);
    }

    @GetMapping("/filtreByDepartement/{dep}")
    @ResponseBody
    public List<Event> filtreByDepartement(@PathVariable("dep") Departement departement){
        return activityService.filtreByDepartement(departement);
    }

    @PostMapping("/AddF/{idEvent}/{idUser}")
    @ResponseBody
    public void addFeedBack (@RequestBody FeedBack feedBack, @PathVariable Long idEvent, @PathVariable Long idUser){
        activityService.addAndAssignFeedBack(feedBack,idEvent,idUser);
    }

    @DeleteMapping("/removeF/{feedback-id}")
    @ResponseBody
    public void deleteFeedBack(@PathVariable("feedback-id")FeedBack feedBack){
        activityService.deleteFeedBack(feedBack);
    }

    @PutMapping("/modifyF")
    @ResponseBody
    public void updateFeedBack( @RequestBody FeedBack feedBack){
         activityService.updateFeedBack(feedBack);
    }
    @GetMapping("/AverageRateEvent/{idEvent}")
    @ResponseBody
    public Float getAverageRateEvent( @PathVariable  Long idEvent){
        return activityService.getAverageRateEvent(idEvent);
    }
    @GetMapping("/findMostPopularTag")
    @ResponseBody
    public void findMostPopularTag(){
        activityService.findMostPopularTag();
    }
    @GetMapping("/EventSatisfaction/{idEvent}")
    public List<Integer> PostSatisfaction(@PathVariable Long idEvent){
        return activityService.EventSatisfaction(idEvent);
    }

}
