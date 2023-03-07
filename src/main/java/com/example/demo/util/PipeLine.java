package com.example.demo.util;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

public class PipeLine {
    private  static Properties properties;
    private static  String propertiesNames="tokenize, ssplit, pos, lemma, ner";
    private static StanfordCoreNLP stanfordCoreNLP;

    public PipeLine() {
    }
    static {
        properties=new Properties();
        properties.setProperty("annotators",propertiesNames);
    }
    public static  StanfordCoreNLP getPipeline() {
        if (stanfordCoreNLP == null)
            stanfordCoreNLP = new StanfordCoreNLP();

        return stanfordCoreNLP;
    }
}
