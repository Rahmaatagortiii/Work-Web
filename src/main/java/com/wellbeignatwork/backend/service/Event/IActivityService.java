package com.wellbeignatwork.backend.service.Event;

import com.google.zxing.WriterException;
import com.lowagie.text.DocumentException;
import com.wellbeignatwork.backend.entity.User.Departement;
import com.wellbeignatwork.backend.entity.Event.*;
import com.wellbeignatwork.backend.entity.User.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface IActivityService {
    public void addEvent(Event e, MultipartFile file) throws IOException ;
    public void addEvent(Event e);
    public void deleteEvent(Event e);
    public Event updateEvent(Event e);
    public List<Event> getAllEvents();
    public void addUser(User u);
    public void assignUserToEvent (Long idUser, Long idEvent);
    public void export(HttpServletResponse response, Long idEvent, Long idUser, String text, int width, int height, String filePath) throws DocumentException, IOException, WriterException;
    public void getNbrOfParticipant();
    public double calculDistance (String a, String b);
    public List<Event> sortedByDistance();
    public List<Event> sortedByFrais();
    public double getRevenueByEvent(Long idEvent);
    public List<Event> showEventsByUser (Long idUser);
    public void cadeauEvent() ;
    public void reductionEvent (Long idEvent, Long idUser, int familyNumber);
    public Object getEventWeather(Long eventId);
    public void inviteUser (Long idUser,Long idEvent);
    public void acceptInvitation (Long idEvent , Long idUser);
    public Event popularEvent();
    public void assignPointToUser (Long idUser, Long idEvent);
    public List<Event> filtreByDepartement(Departement departement);


     public void addSubscription(Subscription s);
     public void deleteSubscription(Subscription s);
     public void updateSubscription(Subscription s);
     public List<Subscription> getAllSubscriptions();
     public void assignUserToSubscription (Long idUser, Long idSubscription);

    public void addAndAssignFeedBack(FeedBack feedBack, Long idEvent, Long idUser) ;
    public void deleteFeedBack(FeedBack feedBack);
    public void updateFeedBack(FeedBack feedBack);
    Float getAverageRateEvent(Long idEvent);
    public void findMostPopularTag();
    public List<Integer> EventSatisfaction(Long idEvent);

}
