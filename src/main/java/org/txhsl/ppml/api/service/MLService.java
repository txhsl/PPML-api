package org.txhsl.ppml.api.service;

import jsat.classifiers.ClassificationDataSet;
import jsat.classifiers.ClassificationModelEvaluation;
import jsat.classifiers.bayesian.MultinomialNaiveBayes;
import jsat.classifiers.bayesian.NaiveBayes;
import jsat.io.CSV;
import jsat.parameters.RandomSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
public class MLService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MLService.class);

    public MLService() {

    }

    public void testNaiveBayes() throws IOException {
        Resource resource = new ClassPathResource("spambase.csv");
        Set<Integer> featureIndex = new TreeSet<>();
        for(int i = 0; i < 56; i++) {
            featureIndex.add(i);
        }
        ClassificationDataSet dataset = CSV.readC(57, new FileReader(resource.getFile()), 0, featureIndex);

        List<ClassificationDataSet> splits = dataset.randomSplit(0.75, 0.25);
        ClassificationDataSet train = splits.get(0), test = splits.get(1);

        NaiveBayes model = new NaiveBayes();
        RandomSearch search = new RandomSearch(model, 3);
        search.autoAddParameters(train);
        search.train(train);

        ClassificationModelEvaluation cme = new ClassificationModelEvaluation(search.getTrainedClassifier() ,train);
        cme.evaluateTestSet(test);

        LOGGER.info("Tuned Error rate(NaiveBayes): " + cme.getErrorRate());
    }

    public void testMultinomialNaiveBayes() throws IOException {
        Resource resource = new ClassPathResource("spambase.csv");
        Set<Integer> featureIndex = new TreeSet<>();
        for(int i = 0; i < 56; i++) {
            featureIndex.add(i);
        }
        ClassificationDataSet dataset = CSV.readC(57, new FileReader(resource.getFile()), 0, featureIndex);

        List<ClassificationDataSet> splits = dataset.randomSplit(0.75, 0.25);
        ClassificationDataSet train = splits.get(0), test = splits.get(1);

        MultinomialNaiveBayes model = new MultinomialNaiveBayes();
        RandomSearch search = new RandomSearch(model, 3);
        search.autoAddParameters(train);
        search.train(train);

        ClassificationModelEvaluation cme = new ClassificationModelEvaluation(search.getTrainedClassifier() ,train);
        cme.evaluateTestSet(test);

        LOGGER.info("Tuned Error rate(Multinomial Naive Bayes): " + cme.getErrorRate());
    }
}
