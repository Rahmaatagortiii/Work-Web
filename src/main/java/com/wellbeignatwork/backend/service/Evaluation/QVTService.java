package com.wellbeignatwork.backend.service.Evaluation;


import com.wellbeignatwork.backend.entity.Evaluation.*;
import com.wellbeignatwork.backend.entity.User.User;
import com.wellbeignatwork.backend.repository.Evaluation.AnswerRepo;
import com.wellbeignatwork.backend.repository.Evaluation.IntAdviceRepo;
import com.wellbeignatwork.backend.repository.Evaluation.QuestionRepo;
import com.wellbeignatwork.backend.repository.Evaluation.SurveyRepo;
import com.wellbeignatwork.backend.repository.User.UserRepository;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class QVTService implements IntQVTService {

    @Autowired
    private UserRepository MyUserRepo;
    @Autowired
    private IntAdviceRepo MyAdviceRepo;
    @Autowired
    private AnswerRepo MyAnswerRepo ;
    @Autowired
    private SurveyRepo MysurveyRepo;
    @Autowired
    private QuestionRepo questionsRepo;
    @Override
    public User addUser(User u) {

        u.setBadge(Badge.None);
        return MyUserRepo.save(u);
    }
    public QVTService() throws IOException {
    }

    private static List<Survey> surveys = new ArrayList<>();

    @Override
    public List<Question> SendSurvey() {
        Question question1 = new Question(
                "you feel physicaly safe in job ?");
        Question question2 = new Question(
                "your job provides good health benefits ?");
        Question question3 = new Question(
                "your salary job does well with your familly needs ?");
        Question question4 = new Question(
                "Any suggestions ?");


        List<Question> questions = new ArrayList<>(Arrays.asList(question1,
                question2, question3, question4));

        Survey survey = new Survey();
        survey.setTitle("quality of life at work");
        survey.setDescription("Description of the Survey");
        MysurveyRepo.save(survey);
        question1.setSurvey(survey);
        question2.setSurvey(survey);
        question3.setSurvey(survey);
        question4.setSurvey(survey);
        questionsRepo.saveAll(questions);
        MysurveyRepo.save(survey);

        return  questionsRepo.findQuestionsBySurvey(survey);
    }













    /*
    @Override
    public void AddAndAffectSurveyUser( )
    {
        List<User> u=new ArrayList<>();
        for(User user:MyUserRepo.findAll())
        {
            if(user.getRole().equals(s.getRole()))
            {

                user.getSurveys().add(s);
                if(s.getUsers()==null){
                    u.add(user);
                    s.setUsers(u);
                }
                else{
                    s.getUsers().add(user);
                }
                MyUserRepo.save(user);
                MySurveyRepo.save(s);
            }
        }
    }


     */


    @Override
    public String UserAnswer(List<Answer> answer)
    {
        String Res="";

        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
        for (Answer answer1 : answer) {
            String text = answer1.getContent();
            CoreDocument coreDocument = new CoreDocument(text);
            stanfordCoreNLP.annotate(coreDocument);
            List<CoreSentence> sentences = coreDocument.sentences();
            System.out.println(sentences);
            for (CoreSentence sentence : sentences) {
                String sentiment = sentence.sentiment();
                System.out.println(sentiment + "\t" + sentence);
                if (!sentiment.contentEquals("Very negative")) {
                    answer1.setSentiment(Sentiment.valueOf(sentiment));
                } else {
                    answer1.setSentiment(Sentiment.Very_negative);
                }
                MyAnswerRepo.save(answer1);
            }
        }
        System.out.println(MyAnswerRepo.getLastAnswer(PageRequest.of(0, 4)));
        int nbVeryBad = 0;
        int nbPositive = 0;
        int nbNeutral = 0;
        int nbBad = 0;
        for (Answer a : MyAnswerRepo.getLastAnswer(PageRequest.of(0, 4))) {
            if (a.getSentiment().equals(Sentiment.Very_negative)) {
                nbVeryBad++;
            } else if (a.getSentiment().equals(Sentiment.Negative)) {
                nbBad++;
            } else if (a.getSentiment().equals(Sentiment.Neutral)) {
                nbNeutral++;
            } else {
                nbPositive++;
            }
        }
        System.out.println(nbBad);
        System.out.println(nbNeutral);
        System.out.println(nbPositive);
        System.out.println(nbVeryBad);
        if (nbPositive == 4) {
            System.out.println
                    ("Exellent ! Your Work Life Is Very Positive,i wish you much success in your carreer");
            return Res="Exellent ! Your Work Life Is Very Positive,i wish you much success in your carreer";
        } else if(((nbBad==2 )&&(nbNeutral==1)&&(nbVeryBad==1))|(nbBad)==4|(nbVeryBad)==4)
        {
            //  System.out.println("Your Survey Is Positive :),You Have Some Issues Don't Wory We Will Fix That ");
            System.out.println("Very Negative ! Thank You For Your FeedBack We Will Take This In Hand ");
            return Res="Very Negative ! Thank You For Your FeedBack We Will Take This In Hand ";
        }
      /*  else if(nbVeryBad==5 | (nbBad==5)| nbVeryBad==5 && (nbBad==5)){
            System.out.println("Very Negative ! Thank You For Your FeedBack We Will Take This In Hand ");
        }

       */

        else
            System.out.println("Neutral ! Thank You For Your FeedBack Enjoy Your Time ");
        return Res="Neutral ! Thank You For Your FeedBack Enjoy Your Time ";


    }






    @Override
    public String nbreSentiment(){
        String Res="";

        return Res="Total Sentiment Positive = "+MyAnswerRepo.nbreByStatus(Sentiment.Positive)+" "
                +"Total Sentiment Negative = "+MyAnswerRepo.nbreByStatus(Sentiment.Negative)+" "+
                "Total Sentiment Neutral = "+MyAnswerRepo.nbreByStatus(Sentiment.Neutral)+" "+
                "Total Sentiment Neutral = "+MyAnswerRepo.nbreByStatus(Sentiment.Neutral)+" "+
                "Total Sentiment Very negative = "+MyAnswerRepo.nbreByStatus(Sentiment.Very_negative);
    }
    public ByteArrayInputStream load() {
        List<Answer> answers = (List<Answer>) MyAnswerRepo.findAll();

        ByteArrayInputStream in = CsvHelper.ToCSV(answers);
        return in;
    }

}







