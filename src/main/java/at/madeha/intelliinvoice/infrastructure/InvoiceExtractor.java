package at.madeha.intelliinvoice.infrastructure;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.ImageUrl;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface InvoiceExtractor {

    //    @SystemMessage("You extract structured data from invoice images.")
    //Claud can return direct java object insted of having JSON and then convert it ot java
    @SystemMessage("Extract structured data. Return ONLY valid JSON matching the provided structure")
    @UserMessage("Extract invoice number, date, vendor name, and total amount from this invoice.")
        //@Imageurl so the llm can recognize this  a url for an image and not a text
    String extract(@ImageUrl String imageUrl); // return an object
    /*Langchain4j and quarkus will generate and implement the method ı do now have to write it
    ready to use

     */
}