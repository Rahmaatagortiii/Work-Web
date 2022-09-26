package com.wellbeignatwork.backend.entity.Evaluation;
import java.io.*;
import java.util.Arrays;
import java.util.List;

import com.wellbeignatwork.backend.entity.Evaluation.Answer;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
public class CsvHelper {
    public static ByteArrayInputStream ToCSV(List<Answer> answers) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);
        )
        {
            for (Answer answer : answers) {
                List<? extends Serializable> data = Arrays.asList(
                        String.valueOf(answer.getIdAnswer()),
                        answer.getContent(),
                        answer.getCreatedAt(),
                        answer.getSentiment(),
                        answer.getQuestionId()
                );


                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
