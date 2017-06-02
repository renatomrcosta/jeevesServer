package br.com.xunfos.jeeves;


import br.com.xunfos.jeeves.DTO.ResponseDTO;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.Queue;

@RestController
public class QueueController {
    private final Queue<String> mergeQueue = new LinkedList<>();

    @RequestMapping(value = "/banana", method = RequestMethod.POST)
    public ResponseDTO banana(@RequestBody String jsonRequest) {
        final String user = getUsername(jsonRequest);
        final ResponseDTO response = new ResponseDTO();
        response.setMessage(String.format("%ss wants Banana! <img src=\"%s\"/>", user, "http://uploads.neatorama.com/images/posts/265/68/68265/1388798520-0.jpg"));
        return response;
    }

    @RequestMapping(value = "/dance", method = RequestMethod.POST)
    public ResponseDTO dance(@RequestBody String jsonRequest) {
        final ResponseDTO response = new ResponseDTO();
        response.setMessage("https://www.youtube.com/watch?v=uKjbQ0m43b8");
        return response;
    }

    @RequestMapping(value = "/queue", method = RequestMethod.POST)
    public ResponseDTO queueUser(@RequestBody String jsonRequest) {
        final String user = getUsername(jsonRequest);
        return queue(user);
    }

    @RequestMapping(value = "/dequeue", method = RequestMethod.POST)
    public ResponseDTO dequeue(@RequestBody String jsonRequest) {
        final ResponseDTO response = new ResponseDTO();
        final String user = getUsername(jsonRequest);
        final StringBuilder sb = new StringBuilder();
        mergeQueue.poll();
        sb.append(String.format("<p>%s has merged successfully!</p>", user));
        if(mergeQueue.size() > 0){
            sb.append(String.format("<p>It's %s's turn!</p>", mergeQueue.peek()));
        }
        //sb.append(String.format("<p>Good job %s! Here, take this <img src=\"%s\">!</p>", user, "https://dujrsrsgsd3nh.cloudfront.net/img/emoticons/620675/money-1486545160.gif"));
        //sb.append(printQueue());
        response.setMessage(sb.toString());
        return response;
    }

    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public ResponseDTO ststus() {
        final ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(printQueue());
        return responseDTO;
    }

    @RequestMapping(value = "/donce", method = RequestMethod.POST)
    public ResponseDTO donce() {
        final ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Really Kevin? donce again? This isn't a Blizzard game");
        return responseDTO;
    }

    private ResponseDTO queue(final String user) {
        final ResponseDTO response = new ResponseDTO();
        final StringBuilder sb = new StringBuilder();

        if (mergeQueue.size() == 0) {
            sb.append(String.format("<p>Hey %s, you are the first in line! You can go right ahead! <img src=\"%s\" /></p>", user, "https://dujrsrsgsd3nh.cloudfront.net/img/emoticons/620675/rthumbsup-1486545158.gif"));
        } else {
            sb.append(String.format("<p>Alright alright alright %s! Sit tight, and wait a bit until your turn <img src=\"%s\" /></p>", user, "https://dujrsrsgsd3nh.cloudfront.net/img/emoticons/620675/popcorn-1476892268.gif"));
        }
        mergeQueue.add(user);

        //sb.append(printQueue());
        response.setMessage(sb.toString());
        return response;
    }

    private String printQueue() {
        final StringBuilder sb = new StringBuilder();
        sb.append("<p>The queue currently looks like this: </p>");
        if (mergeQueue.size() > 0) {
            sb.append("<ol>");
            mergeQueue
                    .forEach(user -> {
                        sb.append("<li>");
                        sb.append(user);
                        sb.append("</li>");
                    });
            sb.append("</ol>");
        } else {
            sb.append(String.format("<p>The merge queue is empty! <img src=\"%s\" /></p>", "https://dujrsrsgsd3nh.cloudfront.net/img/emoticons/620675/yayyyyy-1486545159.gif"));
        }

        return sb.toString();
    }

    private String getUsername(final String jsonRequest) {
        //Spagghettio!!!
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(jsonRequest));
        reader.setLenient(true);
        JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
        return ((JsonObject) jsonElement).get("item").getAsJsonObject().get("message").getAsJsonObject().get("from").getAsJsonObject().get("name").getAsString();
    }

}
