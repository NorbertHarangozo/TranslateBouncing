package hu.maszeksolutions.translatebouncing;

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.language_translator.v3.model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.MessageFormat;
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

    private String translate(String text, String source, String target)
    {
        if (source.equals(target))
            return text;

        TranslateOptions translateOptions = new TranslateOptions.Builder()
                .addText(text)
                .source(source)
                .target(target)
                .build();
        TranslationResult result = service.translate(translateOptions).execute().getResult();
        List<Translation> translations = result.getTranslations();

        return translations.get(0).getTranslation();
    }

    private String pickRandomLanguage(String sourceLanguage, List<String> languages)
    {
        Random rnd = new Random();
        String targetLanguage = sourceLanguage;

        while (targetLanguage.equals(sourceLanguage))
        {
            targetLanguage = languages.get(rnd.nextInt(languages.size()));
        }

        return targetLanguage;
    }

    public String translateBouncing(String text, String sourceLanguage, int numberOfTranslations)
    {
        List<String> languages = getSupportedLanguages();

        if (!languages.contains(sourceLanguage))
        {
            System.out.println("The source language that you've entered is invalid. Pick one of the following:");
            return languages.toString();
        }

        System.out.println(text);

        String translatedText = text;
        String source = sourceLanguage;
        String target = null;

        for (int i = 0; i < numberOfTranslations; i++)
        {
            // We have to translate the text English first, due to API limitations.
            translatedText = translate(translatedText, source, "en");

            target = pickRandomLanguage(source, languages);
            translatedText = translate(translatedText, source, target);

            String message = MessageFormat.format("[{0}] {1}", target, translatedText);
            System.out.println(message);
        }

        // Translating to English one more time...
        source = target;
        translatedText = translate(translatedText, source, "en");
        // ...and then back to the original language.
        translatedText = translate(translatedText, "en", sourceLanguage);

        return translatedText;
    }

    private List<String> getSupportedLanguages()
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
