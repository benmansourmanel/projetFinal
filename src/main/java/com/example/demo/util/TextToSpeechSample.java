package com.example.demo.util;

import com.github.kilianB.apis.googleTextToSpeech.GLanguage;
import com.github.kilianB.apis.googleTextToSpeech.GoogleTextToSpeech;
import com.github.kilianB.apis.googleTextToSpeech.GoogleTextToSpeechAdapter;
import com.github.kilianB.apis.googleTextToSpeech.GoogleTextToSpeechObserver;

import java.io.File;

public class TextToSpeechSample {
    public static   void textToSpeech(String text, String fileName  ) {

        // 1. Simple TTS Request

        //Path to output mp3 directory
        String outputPath = "C:\\Users\\ksghaier\\Desktop\\demo\\src\\main\\resources\\mp3Files\\";

        //Text to convert to mp3

        //Create directory
        File outputDirectory = new File(outputPath);
        outputDirectory.mkdirs();

        //Convert the text and retrieve an mp3 file
        GoogleTextToSpeech tts = new GoogleTextToSpeech(outputPath);
        File convertedTextMP3 = tts.convertText(text, GLanguage.English_US, fileName);



        // 2. Asnch non blocking TTS

        //Non blocking method. Call event handler in the observer upon finishing
        boolean deleteTemporaryFiles = true;

        GoogleTextToSpeechObserver callback = new GoogleTextToSpeechAdapter() {
            @Override
            public void mergeCompleted(File f,int id) {
                System.out.println("File available for playback: " + f.getAbsolutePath());
            }
        };

        tts.convertTextAsynch(text,GLanguage.German, "FileNameAsynch",deleteTemporaryFiles,callback);



        // 3. Multi language query

        //Request an mp3 file with multiple different pronounciations
        String textMult[] = new String[]{
                "The author",
                "Immanuel Kant",
                "argued that the human mind creates the structure of human experience, that reason is the source of morality."
        };

        GLanguage[] language = new GLanguage[] {
                GLanguage.English_GB,
                GLanguage.German,
                GLanguage.English_GB
        };

        convertedTextMP3 = tts.convertTextMultiLanguage(textMult,language,"FileNameMulti");
    }
}
