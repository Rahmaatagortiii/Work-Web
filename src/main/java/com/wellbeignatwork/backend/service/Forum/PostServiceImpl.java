package com.wellbeignatwork.backend.service.Forum;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;

import com.wellbeignatwork.backend.entity.Forum.*;
import com.wellbeignatwork.backend.entity.User.Tags;
import com.wellbeignatwork.backend.entity.User.User;
import com.wellbeignatwork.backend.repository.Forum.CommentRepository;
import com.wellbeignatwork.backend.repository.Forum.PostRepository;

import com.wellbeignatwork.backend.exceptions.Forum.PostException;
import com.wellbeignatwork.backend.repository.Forum.ReactionRepository;
import com.wellbeignatwork.backend.repository.User.UserRepository;
import com.wellbeignatwork.backend.service.UserService.MailService;
import com.wellbeignatwork.backend.util.BadWordFilter;
import com.wellbeignatwork.backend.util.FirebaseStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.lowagie.text.FontFactory;


import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    private UserRepository userRepository;
    private CommentRepository commentRepository;
    private ReactionRepository reactionRepository;
    private final FirebaseStorage firebaseStorage;
    private MailService mailService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           MailService mailService,
                           UserRepository userRepository,
                           CommentRepository commentRepository,
                           ReactionRepository reactionRepository,
                           FirebaseStorage firebaseStorage){

        this.postRepository=postRepository;
        this.userRepository=userRepository;
        this.commentRepository=commentRepository;
        this.reactionRepository=reactionRepository;
        this.firebaseStorage=firebaseStorage;
        this.mailService=mailService;
    }
    public List<String> getAllSubjects(){
        List<String> resultat=new ArrayList<>();
        for(Post p:postRepository.findAll()){
            resultat.add(p.getSubject());
        }
        return resultat;
    }
    public List<Post> getPostsforUser(Long iduser){


        int n;
        List<Post> list=new ArrayList<>();

        User u=userRepository.findById(iduser).orElse(null);
        for(Post p:postRepository.findAll()){
            List<Tags> tags=new ArrayList<>(p.getTags());
            n=tags.size()-1;
            while(n<0){
                if(tags.containsAll(u.getUserTags())){
                    list.add(p);
                    tags.remove(n);
                    n--;
                }

            }

        }
        return list;

    }
    @Override
    public Post createpost(Post post,MultipartFile file) throws IOException {
        String fileData = firebaseStorage.uploadFile(file);
        Post isSubjectExist=null;
        for(Post p: postRepository.findAll()){
            if(similarity(p.getSubject(),post.getSubject())>0.6){
                isSubjectExist=p;
            }
        }
        if(isSubjectExist!=null){

            return isSubjectExist;
        }
        else{
            post.setFile(fileData);
            post.setContent(BadWordFilter.getCensoredText(post.getContent()));
            post.setCreatedAt(LocalDateTime.now());
            return postRepository.save(post);
        }

    }
    @Override
    public Post addPost(Post post,Long idUser){

        Post isSubjectExist=null;
        for(Post p: postRepository.findAll()){
            if(similarity(p.getSubject(),post.getSubject())>0.6){
                isSubjectExist=p;
            }
        }
        if(isSubjectExist!=null){

            return isSubjectExist;
        }
        else{

            post.setContent(BadWordFilter.getCensoredText(post.getContent()));
            post.setCreatedAt(LocalDateTime.now());
            postRepository.save(post);

            return assignUserToPost(idUser,post.getId());
        }
    }

    @Override
    public Collection<Post> getAll() {
        return (Collection<Post>) postRepository.findAll();
    }

    @Override
    public Collection<Post> getAllPaginated(Integer pageNumber) {
        Integer index = pageNumber - 1;
        Page<Post> posts = this.postRepository.findAll(PageRequest.of(index, 20));
        return posts.stream().collect(Collectors.toList());

    }
    //@PreAuthorize("hasRole('USER')")
    @Override
    public Post updatepost(Post post) {
        postRepository.findById(post.getId()).orElseThrow(
                () -> new PostException("Can't update. Post not found!")
        );
        post.setModifiedAt(LocalDateTime.now());
        return this.postRepository.save(post);
    }
    //@PreAuthorize("hasRole('USER')")
    @Override
    public void deletepost(int id) {
       postRepository.delete(postRepository.findById(id).orElse(null));
    }


    @Override
    public Post assignUserToPost(Long id_user,int id_post){
        Post p=postRepository.findById(id_post).orElse(null);
        User u=userRepository.findById(id_user).orElse(null);
        u.getPosts().add(p);
        p.setUser(u);

        postRepository.save(p);
        return p;
    }
    @Override
    public List<Post> groupByPreference(Long idUser)
    {
        User u=userRepository.findById(idUser).orElse(null);
        List<Tags> userTags=new ArrayList<>(u.getUserTags());
        Map<Post,Integer> prefMap=new HashMap<>();

        for(Post p:postRepository.findAll()){
            List<Tags> postTags=new ArrayList<>(p.getTags());
            List<Tags> commonTags=new ArrayList<>(postTags);
            commonTags.retainAll(userTags);

            prefMap.put(p,commonTags.size());
        }
        LinkedHashMap<Post, Integer> reverseSortedMap = new LinkedHashMap<>();

        prefMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
        return new ArrayList<Post>(reverseSortedMap.keySet());


    }
    public List<Double> postInteraction(List<Post> posts){
        List<Double> result=new ArrayList<>();
        for(Post p:posts){
            result.add((double)commentRepository.NbrCommentByPost(p)+(double) reactionRepository.NbrReactionByPost(p));

        }
        return result;
    }
    public List<Post> sortPostByInteraction(List<Post> posts){
        HashMap<Post,Double> postDoubleHashMap=new HashMap<>();
        List<Double> interactions=postInteraction(posts);

        for(int i=0;i<interactions.size();i++){
            postDoubleHashMap.put(posts.get(i),interactions.get(i));
        }
        LinkedHashMap<Post, Double> reverseSortedMap = new LinkedHashMap<>();

        postDoubleHashMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
        return new ArrayList<Post>(reverseSortedMap.keySet());

    }
    public HashMap<String, List> analyzepostInteractionForSignals(List<Double> data, Double threshold, Double influence) {
        int lag=2;

        SummaryStatistics stats = new SummaryStatistics();
        List<Integer> signals = new ArrayList<Integer>(Collections.nCopies(data.size(), 0));
        List<Double> filteredData = new ArrayList<Double>(data);

        List<Double> avgFilter = new ArrayList<Double>(Collections.nCopies(data.size(), 0.0d));

        List<Double> stdFilter = new ArrayList<Double>(Collections.nCopies(data.size(), 0.0d));
        System.out.println(data);
        for (int i = 0; i < lag; i++) {
            stats.addValue(data.get(i));
        }

        avgFilter.set(lag - 1, stats.getMean());

        stdFilter.set(lag - 1, Math.sqrt(stats.getPopulationVariance()));
        stats.clear();

        for (int i = lag; i < data.size(); i++) {

            if (Math.abs((data.get(i) - avgFilter.get(i - 1))) > threshold * stdFilter.get(i - 1)) {
                if (data.get(i) > avgFilter.get(i - 1)) {
                    signals.set(i, 1);
                } else {
                    signals.set(i, -1);
                }
                filteredData.set(i, (influence * data.get(i)) + ((1 - influence) * filteredData.get(i - 1)));
            } else {
                signals.set(i, 0);
                filteredData.set(i, data.get(i));
            }

            for (int j = i - lag; j < i; j++) {
                stats.addValue(filteredData.get(j));
            }
            avgFilter.set(i, stats.getMean());
            stdFilter.set(i, Math.sqrt(stats.getPopulationVariance()));
            stats.clear();
        }

        HashMap<String, List> returnMap = new HashMap<String, List>();
        returnMap.put("signals", signals);
        returnMap.put("filteredData", filteredData);
        returnMap.put("avgFilter", avgFilter);
        returnMap.put("stdFilter", stdFilter);

        return returnMap;

    }
    @Override
    public List<Post> getTrendingPost(){

        DecimalFormat df = new DecimalFormat("#0.000");
        List<Post> allPosts=new ArrayList<>();
        postRepository.findAll().forEach(allPosts::add);
        ArrayList<Double> data =new ArrayList<Double>(postInteraction(allPosts));


        double threshold = 5;
        double influence = 0;
        List<Integer> signalsList=new ArrayList<>();
        Map<Post,Integer> sortTrendPost=new HashMap<>();



            HashMap<String, List> resultsMap = analyzepostInteractionForSignals(data, threshold, influence);

            signalsList = resultsMap.get("signals");
            for(int i=0;i<allPosts.size();i++){
                sortTrendPost.put(allPosts.get(i),signalsList.get(i));
            }


        System.out.println("**************************************************************************************************************************");
        for (int i : signalsList) {
            System.out.print(df.format(i) + "\t");
        }
        System.out.println();


        System.out.println();
        for (int i = 0; i < signalsList.size(); i++) {
            if (signalsList.get(i) != 0) {
                System.out.println("Point " + i + " gave signal " + signalsList.get(i));
            }
        }
        System.out.println();
        LinkedHashMap<Post, Integer> reverseSortedMap = new LinkedHashMap<>();

        sortTrendPost.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
        //System.out.println(reverseSortedMap);
        List<Post> peakpost=new ArrayList<>();
        List<Post> avgpost=new ArrayList<>();
        List<Post> poorPost=new ArrayList<>();
        for(Map.Entry<Post, Integer> entry:reverseSortedMap.entrySet()) {
            if(entry.getValue()==1){
                peakpost.add(entry.getKey());
            }
            else if(entry.getValue()==0){
                avgpost.add(entry.getKey());
            }
            else{
                poorPost.add(entry.getKey());
            }

        }

        peakpost=sortPostByInteraction(peakpost);
        avgpost=sortPostByInteraction(avgpost);
        poorPost=sortPostByInteraction(poorPost);
        List<Post> sortPostByPeaks=new ArrayList<>();
        sortPostByPeaks.addAll(peakpost);
        sortPostByPeaks.addAll(avgpost);
        sortPostByPeaks.addAll(poorPost);
        return sortPostByPeaks;

    }
    public double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) {
            longer = s2; shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) { return 1.0; }

        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

    }


    public int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }
    public String filterBadWords(String string) {
        System.out.println(string);
        String filteredContent = BadWordFilter.getCensoredText(string);
        System.out.println(filteredContent);
        return string;
    }

    public void downloadArticle(int idPost, HttpServletResponse response) throws IOException, DocumentException {

        Post post=postRepository.findById(idPost).orElse(null);
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER);
        font.setSize(60);
        font.setColor(Color.RED);

        Paragraph p = new Paragraph(post.getSubject());
        p.setAlignment(Paragraph.ALIGN_CENTER);
        p.setFont(font);


        Font font1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(14);
        font.setColor(Color.BLACK);

        Paragraph p1=new Paragraph(post.getContent(),font1);
        p1.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(p);
        document.add(p1);

        document.close();
    }

    @Scheduled(cron = "*/30 * * * * *")
    public void greatingMostPopularPost(){
        List<Post> posts=new ArrayList<>();
        postRepository.findAll().forEach(posts::add);
        List<Post> sort=sortPostByInteraction(posts);
        if(sort.size()>=1){
            User user=sort.get(0).getUser();

            mailService.sendMail(user.getEmail(),"Congrat on hiting the most popular post",user.getDisplayName()+" your post has reached so many people keep the good work",false);

        }

    }



}
