package com.wellbeignatwork.backend.service.Event;


import com.github.prominence.openweathermap.api.model.Coordinate;
import com.github.prominence.openweathermap.api.model.onecall.current.CurrentWeatherData;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.wellbeignatwork.backend.entity.User.Departement;
import com.wellbeignatwork.backend.entity.Event.*;
import com.wellbeignatwork.backend.entity.Event.Event;
import com.wellbeignatwork.backend.entity.User.Tags;
import com.wellbeignatwork.backend.entity.User.User;
import com.wellbeignatwork.backend.exceptions.Event.BadRequestException;
import com.wellbeignatwork.backend.exceptions.Evaluation.ResourceNotFoundException;
import com.wellbeignatwork.backend.payload.PushNotificationRequest;
import com.wellbeignatwork.backend.repository.User.UserRepository;
import com.wellbeignatwork.backend.service.NotificationService.PushNotificationService;
import com.wellbeignatwork.backend.service.UserService.MailService;
import com.wellbeignatwork.backend.util.FirebaseStorage;
import com.wellbeignatwork.backend.util.WeatherService;

import com.wellbeignatwork.backend.repository.Event.EventRepository;
import com.wellbeignatwork.backend.repository.Event.FeedBackRep;
import com.wellbeignatwork.backend.repository.Event.SubscriptionRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.web.multipart.MultipartFile;

import static javax.mail.Transport.send;

@RestController
public class ActivityServiceImp implements IActivityService {
    private StanfordCoreNLP pipeline;
    EventRepository eventRepository;
    UserRepository userRepo;
    SubscriptionRepository subscriptionRepository;
    private final PushNotificationService notificationService;
    private final MailService mailService;
    private final FirebaseStorage firebaseStorage;

    @PostConstruct
    public void init() {
        pipeline = new StanfordCoreNLP("MyPropFile.properties");
    }

    @Autowired
    FeedBackRep feedBackRep;
    private JavaMailSender javaMailSender;
    @Autowired
    WeatherService weatherService;

    @Autowired
    public ActivityServiceImp(EventRepository eventRepository, UserRepository userRepo,
                              SubscriptionRepository subscriptionRepository,
                              JavaMailSender javaMailSender,
                              PushNotificationService notificationService,
                              MailService mailService,
                              FirebaseStorage firebaseStorage) {
        this.eventRepository = eventRepository;
        this.userRepo = userRepo;
        this.subscriptionRepository = subscriptionRepository;
        this.javaMailSender = javaMailSender;
        this.notificationService = notificationService;
        this.mailService = mailService;
        this.firebaseStorage=firebaseStorage;
    }


    @Override
    public void addEvent(Event e, MultipartFile file) throws IOException {
        String fileData = firebaseStorage.uploadFile(file);
        e.setImage(fileData);
        eventRepository.save(e);
    }

    @Override
    public void deleteEvent(Event e) {
        if(e.getUsers()==null){
            eventRepository.delete(e);
        }
        else{
            for(User u:e.getUsers()){
                u.getEvents().remove(e);
                userRepo.save(u);
            }
            e.setUsers(null);
            eventRepository.save(e);
            eventRepository.delete(e);
        }


    }

    @Override
    public Event updateEvent(Event e) {
        return eventRepository.save(e);
    }

    @Override
    public List<Event> getAllEvents() {
        return (List<Event>) eventRepository.findAll();
    }

    @Override
    public void assignUserToEvent(Long idUser, Long idEvent) {

        User user = userRepo.findById(idUser).orElse(null);
        Event event = eventRepository.findById(idEvent).orElse(null);
        if(event.getStartDate().isBefore(LocalDateTime.now()) ){
            throw new BadRequestException("event already started");
        }
        if (event.getUsers().size() - 1 >= event.getNbrMaxParticipant()) {
            throw new BadRequestException("event is full");
        }
        else {
            user.getEvents().add(event);
            event.setRevenue(event.getRevenue() + event.getFrais());
            userRepo.save(user);
        }
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Event Name", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Event Locatisation", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Fees", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("User Name", font));
        table.addCell(cell);


    }

    private void writeTableData(PdfPTable table, Long idEvent, Long idUser) {
        User user = userRepo.findById(idUser).orElse(null);
        Event event = eventRepository.findById(idEvent).orElse(null);
        table.addCell(event.getEventName());
        table.addCell(event.getEventLocalisation());
        table.addCell(String.valueOf(event.getFrais()));
        table.addCell(user.getFirstName() + " " + user.getLastName());

    }

    public void export(HttpServletResponse response, Long idEvent,
                       Long idUser, String text,
                       int width, int height, String filePath)
            throws DocumentException, IOException, WriterException {
        Event e=eventRepository.findById(idEvent).orElse(null);
        User u=userRepo.findById(idUser).orElse(null);
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER);
        font.setSize(60);
        font.setColor(Color.RED);

        Paragraph p = new Paragraph("Ticket");
        p.setAlignment(Paragraph.ALIGN_CENTER);
        p.setFont(font);


        Font font1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(14);
        font.setColor(Color.BLACK);
        Paragraph p1=new Paragraph("Event Name :"+e.getEventName(),font1);
        p1.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph p2=new Paragraph("Event Locatisation :"+e.getEventLocalisation(),font1);
        p2.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph p3=new Paragraph("Fees :"+e.getFrais(),font1);
        p3.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph p4=new Paragraph("User Name :"+u.getDisplayName(),font1);
        p4.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(p);
        document.add(p1);
        document.add(p2);
        document.add(p3);
        document.add(p4);
/*
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f, 3.5f, 3.0f, 1.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table, idEvent, idUser);

        document.add(table);

 */
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        System.out.println(path);
        Image image = Image.getInstance(path.toString());
        document.add(image);
        document.close();

    }

    public static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }


    public String getlatitudelieu(String adresse) {
        String map = adresse;
        String[] arrOfStr = map.split(",");
        String Latitude = arrOfStr[0];

        return Latitude;
    }

    public String getlongitudelieu(String adresse) {
        String map = adresse;
        String[] arrOfStr = map.split(",");
        String Longitude = arrOfStr[1];

        return Longitude;
    }

    public double convertRad(double input) {
        return (Math.PI * input) / 180;
    }

    @Override
    public double calculDistance(String a, String b) {

        Coordinate ac=weatherService.getCoordinateByCityName(a);
        Coordinate bc=weatherService.getCoordinateByCityName(b);


        double lata = convertRad(ac.getLatitude());
        double latb = convertRad(bc.getLatitude());
        double longa = convertRad(ac.getLongitude());
        double longb = convertRad(bc.getLongitude());
        double rayon = 6378137;
        double distance = Math.acos((Math.sin(lata) * Math.sin(latb))
                + Math.cos(lata) * Math.cos(latb) * (Math.cos(longb - longa)));
        return distance * rayon / 1000;


    }

    public List<Event> sortedByDistance() {
        Map<Event, Double> envdis = new HashMap<>();
        for (Event e : eventRepository.findAll()) {
            envdis.put(e, calculDistance("ariana", e.getEventLocalisation()));
        }
        List<Map.Entry<Event, Double>> list = new ArrayList<>(envdis.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<Event, Double> result = new LinkedHashMap<>();
        for (Map.Entry<Event, Double> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        List<Event> dis = new ArrayList<>();
        for (Map.Entry<Event, Double> entry : list) {
            dis.add(entry.getKey());
        }
        return dis;
    }

    @Override
    public List<Event> sortedByFrais() {
        List<Event> events = new ArrayList<>();
        eventRepository.findAll().forEach(events::add);

        return events.stream().sorted(Comparator.comparing(Event::getFrais))
                .collect(Collectors.toList());
    }

    //  @Override
    //  public List<ITagsCount> tags() {
//        return eventRepository.tags();
    // }

    @Override
    public double getRevenueByEvent(Long idEvent) {

        Event event = eventRepository.findById(idEvent).orElse(null);

        return event.getRevenue();
    }

    @Override
    public List<Event> showEventsByUser(Long idUser) {
        User u = userRepo.findById(idUser).orElse(null);
        List<Tags> tags = new ArrayList<>(u.getUserTags());
        Map<Event, Integer> preferenceEvent = new HashMap<>();
        for (Event event : eventRepository.findAll()) {
            List<Tags> eventTag = new ArrayList<>(event.getEventTags());
            List<Tags> communTag = new ArrayList<>(tags);
            //n3abiw les tags li yabdew kifkif (eventTag et tags)
            communTag.retainAll(event.getEventTags());
            preferenceEvent.put(event, communTag.size());
        }
        //e1 old value e2 new value
        preferenceEvent = preferenceEvent.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));

        List<Event> result = new ArrayList<>(preferenceEvent.keySet());

        for (int i = 0, j = result.size() - 1; i < j; i++) {
            result.add(i, result.remove(j));
        }
        return result;


    }


    public Event randomEventByTags(List<Tags> tags) {
        Event result = new Event();
        Map<Event, Integer> preferenceEvent = new HashMap<>();
        for (Event event : eventRepository.findAll()) {
            List<Tags> eventTag = new ArrayList<>(event.getEventTags());
            List<Tags> communTag = new ArrayList<>(tags);
            //n3abiw les tags li yabdew kifkif (eventTag et tags)
            communTag.retainAll(event.getEventTags());
            preferenceEvent.put(event, communTag.size());
        }
        int maxValue = Collections.max(preferenceEvent.values());
        for (Map.Entry<Event, Integer> entry : preferenceEvent.entrySet()) {
            if (entry.getValue() == maxValue) {
                result = entry.getKey();
            }
        }
        return result;
    }

    public void cadeauEvent() {

        List<User> users = new ArrayList<>();
        userRepo.findAll().forEach(users::add);
        User max = users.stream().max(Comparator.comparing(User::getPointFidelite)).get();
        List<Tags> userTags = new ArrayList<>(max.getUserTags());
        Event eventGift = randomEventByTags(userTags);

        max.getEvents().add(eventGift);
        userRepo.save(max);
    }

    @Override
    public void reductionEvent(Long idEvent, Long idUser, int familyNumber) {
        double x = 0;
        User u = userRepo.findById(idUser).orElse(null);
        Event event = eventRepository.findById(idEvent).orElse(null);
        if (event.getFrais() > 20) {
            x = (((event.getFrais() * 8) / 10) * (familyNumber + 1));

            u.getEvents().add(event);
            event.setRevenue(event.getRevenue() + x);
            userRepo.save(u);
        }
    }

    @Transactional
    @Scheduled(cron = "*/30 * * * * *")
    public String reminderEvent() {
        String a = "Mail Send Successfully";

        List<Event> events = eventRepository.reminder
                (LocalDateTime.now(), LocalDateTime.now().plus(1, ChronoUnit.DAYS));
        if (!events.isEmpty()) {
            for (Event e : events) {
                for (User u : e.getUsers()) {
                    System.out.println("vous avez un evenement : " + u.getFirstName());
                    mailService.sendMail(u.getEmail(),"event","you have an event",false);
/*
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setFrom("wellbeingatworkevent@gmail.com");
                    message.setTo("nourhene.maaouia@esprit.tn");
                    message.setSubject("Test Subject");
                    message.setText("Test Body ");

                    javaMailSender.send(message);
*/


                }
            }
        }
        return a;
    }

    @Override

    public Object getEventWeather(Long eventId) {
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event == null) {
            throw new ResourceNotFoundException("Event is not exist");
        }
        //check if the event is already started
        if (LocalDate.now().isAfter(event.getStartDate().toLocalDate())) {
            throw new BadRequestException("No need to fetch weather of an event already started or finished");
        }
        CurrentWeatherData currentWeatherData = weatherService.getWeatherData(event.getEventLocalisation());
        LocalDate nextWeek = LocalDate.now().plusDays(7);
        System.out.println(nextWeek);
        LocalDate eventStartDate = event.getStartDate().toLocalDate();
        if (nextWeek.isBefore(eventStartDate) || nextWeek.isEqual(eventStartDate)) {
            return currentWeatherData.getCurrent();
        } else {
            int idx = 7 - Period.between(eventStartDate, nextWeek).getDays();
            return currentWeatherData.getDailyList().get(idx);
        }
    }

    @Override
    public Event popularEvent() {
        Event eventMax = eventRepository.findAll().iterator().next();
        System.out.println(eventMax);
        //  Comparator<Event> particComparator = (e1,e2) -> e1.getUsers().size().compareTo(e2.getUsers().size());
        //  List<Event> result=getAllEvents().stream().sorted(Comparator.comparing(event -> ))
        for (Event event : eventRepository.findAll()) {
            if (event.getUsers().size() > eventMax.getUsers().size()) {
                System.out.println("slt");
                eventMax = event;
            }
        }
        return eventMax;
    }

    @Override
    public void assignPointToUser(Long idUser, Long idEvent) {
        User u = userRepo.findById(idUser).orElse(null);
        Event event = eventRepository.findById(idEvent).orElse(null);
        if (event.getEventTags().contains(Tags.SPORT)) {
            u.setPointFidelite(u.getPointFidelite() + 3);
        }
        userRepo.save(u);

    }

    @Override
    public List<Event> filtreByDepartement(Departement departement) {
        Event e1 = new Event();
        int i = 0;
        int size = IterableUtils.size(eventRepository.findAll());
        List<Integer> list = new ArrayList<Integer>(Collections.nCopies(size, 0));
        Map<Event,Integer> filterbydep=new HashMap<>();
        for (Event e : eventRepository.findAll()) {
            for (User u : e.getUsers()) {
                if (u.getDepartement().equals(departement)) {

                    int a = list.get(i);
                    list.set(i, a += 1);
                }
            }
            i++;
        }
        int j=0;
        for(Event e:eventRepository.findAll()){
            if(list.get(j)!=0){

                filterbydep.put(e,list.get(j));
            }

            j++;

        }
        LinkedHashMap<Event, Integer> reverseSortedMap = new LinkedHashMap<>();

        filterbydep.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
        System.out.println(reverseSortedMap);

        return new ArrayList<Event>(reverseSortedMap.keySet());
    }

    public boolean compareTags(Set<Tags> eventTags, Set<Tags> userTags) {
        return eventTags.containsAll(userTags);
    }
    /*
     @Override
    public Integer getRevenueByEvent(Long idEvent , Date startDate , Date endDate) {
      int revenue=0;
      Event event = eventRepository.findById(idEvent).orElse(null);
     if(event.getEventDate().after(startDate) && event.getEventDate().before(endDate)){
          revenue = (event.getFrais()*getNbrOfParticipant(idEvent));
     }
        return revenue ;
    }
     */

    @Override
    public void getNbrOfParticipant() {
        List<Event> events = new ArrayList<>();
        eventRepository.findAll().forEach(events::add);
        List<Integer> nbParticipantByEvent = new ArrayList<>(Collections.nCopies(events.size(), 0));


        for (int i = 0; i < events.size(); i++) {


            nbParticipantByEvent.set(i, events.get(i).getUsers().size());

        }


        System.out.println(nbParticipantByEvent);
    }

    @Override

    public void inviteUser(Long idUser, Long idEvent) {
        //send notification to the specific user ( user , event )
/*

        //OnMessage (notification )  if notification.get subject event invitation => notitifaction.getTopic(userId,EventID
        // this.httpp.port (/accept invitaion))
        //bouton ignore

        Event event = eventRepository.findById(idEvent).orElseThrow(() -> {
            throw new ResourceNotFoundException("user with this id : " + idEvent + " not found");
        });
        //true event
        userRepo.findById(idUser)
                .map(user1 -> {
                            if (event.getUsers().contains(user1)) {
                                throw new BadRequestException("You can't invite someone who already participate");
                            }

                            if (event.getStartDate().isBefore(LocalDateTime.now())) {
                                throw new BadRequestException("You can't invite someone to an event already started or finished");
                            }

                            //prep data
                            String redirectionLink = "http://localhost:8081/Wellbeignatwork/event/accept/" + idUser + "/" + idEvent;
                            Map<String, String> data = new HashMap<>();
                            data.put("redirectionLink", redirectionLink);
                            //prep request
                            PushNotificationRequest request = new PushNotificationRequest();
                            request.setTitle("Event Invitation");
                            request.setMessage("you have been invited to Event : " + event.getEventName());
                            request.setToken(user1.getFireBaseToken());
                            request.setData(data);

                            //mail sent
                            mailService.sendMail(user1.getEmail(), "Event Invitation", "you have been invited to Event" + event.getEventName() + "\n" + "click this link to accept the invitation" + "\n" + "http://localhost:8081/Wellbeignatwork/event/accept/" + idUser + "/" + idEvent, false);
                            //notification sent
                            try {
                                notificationService.sendMessageToTokenWithExtraData(request);
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                            return user1;
                        }
                )
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("user with this id : " + idUser + " not found");
                });




 */
    }

    @Override

    public void acceptInvitation(Long idEvent, Long idUser) {
        PushNotificationRequest request = new PushNotificationRequest();
        request.setTitle("Event Invitation");
        // route to to execute the AssignUserToEvent method

        User user = userRepo.findById(idUser)
                .map(user1 ->{
                    request.setToken(user1.getFireBaseToken());
                    return user1;
                })
                .orElseThrow(()->new ResourceNotFoundException("user with id : "+idUser+" doesnt exist"));

        //System.out.println(event);
        eventRepository.findById(idEvent).map((event1 -> {
            if (event1.getUsers().size() == event1.getNbrMaxParticipant()) {

                //prep request
                request.setMessage("sorry our event is already full");
                //send decline notification
                notificationService.sendPushNotificationToToken(request);

            }

            if (event1.getUsers().contains(user)) {
                //senda notification to tell the user that he already joined the Event
                //prepare the request
                request.setMessage("you are subscribed to this event ");
                //send decline notification
                notificationService.sendPushNotificationToToken(request);
            }
            else {
                // all things good
                assignUserToEvent(idUser, idEvent);
                //acceptence notification
                //prepare the request
                request.setMessage(" you have been succesfully joined our event : " + event1.getEventName());
                notificationService.sendPushNotificationToToken(request);
            }


            return event1;
        })).orElseThrow(() -> new ResourceNotFoundException("event  with id : " + idEvent + " not found"));


    }

   /*
    @Override
    public void addEventToFavory(Long idUser, Event e) {
        User u = userRepo.findById(idUser).orElse(null);
        e.getUsersFavorites().add(idUser);
        eventRepository.save(e);

    }*/
 /*  public void feedbackEvent(Long userId, Long eventId, FeedBack feedback) {
       User usersEvents = userRepo.findById(userId).orElse(null);
       Event event = eventRepository.findById(eventId).orElse(null);
       if (usersEvents == null) {
           throw new BadRequestException("Cannot give feedback for an event without participation");
       }
       LocalDateTime eventEndDate = usersEvents.getEvents().getEnd();
       LocalDateTime now = LocalDateTime.now();
       if (!usersEvents.ge()) {
           throw new BadRequestException("Cannot give feedback for an event without participation");
       }
       if (!eventEndDate.isBefore(now)) {
           throw new BadRequestException("Cannot give feedback for an event before its finish");
       }
       usersEvents.setFeedback(feedback.getContent());
       usersEvents.se(feedback.getRate());
       userRepo.save(usersEvents);
   }*/

    @Override
    public void addUser(User u) {
        u.setCreatedDate(LocalDateTime.now());
        userRepo.save(u);
    }
    @Override
    public void addEvent(Event e){
        eventRepository.save(e);
    }

    @Override
    public void addSubscription(Subscription s) {
        subscriptionRepository.save(s);
    }

    @Override
    public void deleteSubscription(Subscription s) {
        subscriptionRepository.delete(s);
    }

    @Override
    public void updateSubscription(Subscription s) {
        subscriptionRepository.save(s);
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        return (List<Subscription>) subscriptionRepository.findAll();
    }

    @Override
    public void assignUserToSubscription(Long idUser, Long idSubscription) {
        User user = userRepo.findById(idUser).orElse(null);
        Subscription subscription = subscriptionRepository.findById(idSubscription).orElse(null);
        subscription.setUser(user);
        subscriptionRepository.save(subscription);
    }

    @Override
    public void addAndAssignFeedBack(FeedBack feedBack, Long idEvent, Long idUser) {
        Event event = eventRepository.findById(idEvent).orElse(null);
        User user = userRepo.findById(idUser).orElse(null);
        if (!event.getUsers().contains(user)) {

            throw new BadRequestException("You can't decline an invitation where you are not invited");
        }
        if (event.getEndDate().isAfter(LocalDateTime.now())) {

            throw new BadRequestException("You can't decline an invitation where you are not invited");
        }
        feedBack.setIdUser(idUser);
        List<FeedBack> feedBacks = new ArrayList<>();
        feedBacks.add(feedBack);
        if (event.getFeedBacks() == null) {
            event.setFeedBacks(feedBacks);
        } else {
            event.getFeedBacks().add(feedBack);
        }

        feedBack.setEvent(event);
        feedBackRep.save(feedBack);
    }

    @Override
    public void deleteFeedBack(FeedBack feedBack) {
        feedBackRep.delete(feedBack);
    }

    @Override
    public void updateFeedBack(FeedBack feedBack) {
        feedBackRep.save(feedBack);
    }

    @Override
    public Float getAverageRateEvent(Long idEvent) {
        Event event = eventRepository.findById(idEvent).orElse(null);
        return feedBackRep.getAverageRateEvent(event);
    }

    public void findMostPopularTag() {
        List<Tags> tags = new ArrayList<Tags>(Arrays.asList(Tags.values()));
        List<Integer> nbParticipantByTag = new ArrayList<>(Collections.nCopies(tags.size(), 0));
        int x = 0;
        for (Event e : eventRepository.findAll()) {
            for (int i = 0; i < tags.size(); i++) {
                if (e.getEventTags().contains(tags.get(i))) {

                    nbParticipantByTag.set(i, nbParticipantByTag.get(i) + e.getUsers().size());

                }
            }

        }
        System.out.println(tags);
        System.out.println(nbParticipantByTag);
    }

    @Override
    public List<Integer> EventSatisfaction(Long idEvent) {
        Event event = eventRepository.findById(idEvent).orElse(null);
        List<Integer> result = new ArrayList<>();
        if (event.getFeedBacks() == null) {
            return null;
        } else {
            for (FeedBack f : event.getFeedBacks()) {
                result.add(findSentiment(f.getContent()));
            }
            return result;
        }

    }

    public int findSentiment(String content) {
        int mainSentiment = 0;
        String cleancontent = cleancontent(content);
        if (cleancontent != null && cleancontent.length() > 0) {
            int longest = 0;
            Annotation annotation = pipeline.process(cleancontent);
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partText = sentence.toString();
                if (partText.length() > longest) {
                    mainSentiment = sentiment;
                    longest = partText.length();
                }
            }
        }
        return mainSentiment;
    }

    private String cleancontent(String commentContent) {
        commentContent = commentContent.toLowerCase();
        commentContent = commentContent.replaceAll("(@[A-Za-z0-9_]+)|([^0-9A-Za-z \\t])|(\\w+:\\/\\/\\S+)", " ");
        System.out.println(commentContent);
        return commentContent;
    }


}
