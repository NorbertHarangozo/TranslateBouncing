package hu.maszeksolutions.translatebouncing;

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.language_translator.v3.model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Translator
{
    Authenticator authenticator;
    LanguageTranslator service;

    public Translator(String apikey, String serviceUrl)
    {
        this.authenticator = new IamAuthenticator(apikey);
        this.service = new LanguageTranslator("2018-05-01", authenticator);
        this.service.setServiceUrl(serviceUrl);
    }

    public String translate(String text, String source, String target)
    {
        TranslateOptions translateOptions = new TranslateOptions.Builder()
                .addText(text)
                .source(source)
                .target(target)
                .build();
        TranslationResult result = service.translate(translateOptions).execute().getResult();
        List<Translation> translations = result.getTranslations();

        return translations.get(0).getTranslation();
    }

    public String translateBouncing(String text, String sourceLanguage, int numberOfTranslations, List<String> languages)
    {
        if (!languages.contains(sourceLanguage))
        {
            System.out.println("The source language that you've entered is invalid. Pick one of the following:");
            return languages.toString();
        }

        String result = text;

        String source = sourceLanguage;
        String target = "";
        Random rnd = new Random();

        System.out.println(result);

        for (int i = 0; i < numberOfTranslations; i++)
        {
            if (!source.equals("en"))
            {
                target = "en";
                result = translate(result, source, target);
                source = target;
            }

            do {
                target = languages.get(rnd.nextInt(languages.size()));
            } while (target.equals(source));

            result = translate(result, source, target);
            System.out.println("[" + target + "]" + result);
        }

        source = target;

        if (!target.equals("en"))
        {
            target = "en";
            result = translate(result, source, target);
            source = target;
        }

        if (!source.equals(sourceLanguage))
        {
            result = translate(result, source, sourceLanguage);
        }

        return result;
    }

    public List<String> getSupportedLanguages()
    {
        Languages languages = service.listLanguages().execute().getResult();

        JSONObject json = new JSONObject(languages);
        JSONArray array = json.getJSONArray("languages");

        List<String> supportedLanguages = new ArrayList<>();

        for (int i = 0; i < array.length(); i++)
        {
            JSONObject obj = array.getJSONObject(i);

            if (obj.getBoolean("supportedAsSource") && obj.getBoolean("supportedAsTarget") && !obj.getString("language").equals("ca") && !obj.getString("language").equals("eu"))
            {
                supportedLanguages.add(obj.getString("language"));
            }
        }

        return supportedLanguages;
    }
}
