package br.com.xunfos.jeeves;


import br.com.xunfos.jeeves.dto.RequestDTO;
import br.com.xunfos.jeeves.dto.ResponseDTO;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Controller of the merging queue, please don´t lose it!
 */
@RestController
public class QueueController {
    private final Map<String, Queue> queueCollection = new HashMap<>();

    /**
     * Emergency nutrition for when you are doing overtime and is out of food.
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/banana", method = RequestMethod.POST)
    public ResponseDTO banana(@RequestBody String jsonRequest) {
        final RequestDTO request = readRequest(jsonRequest);
        return ResponseDTO.builder()
                .message(String.format("%ss wants Banana! <img src=\"%s\"/>", request.getUsername(),
                        "http://uploads.neatorama.com/images/posts/265/68/68265/1388798520-0.jpg"))
                .build();
    }

    /**
     * Programmers just wanna have fun!!!
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/dance", method = RequestMethod.POST)
    public ResponseDTO dance(@RequestBody String jsonRequest) {
        return ResponseDTO.builder()
                .message("https://www.youtube.com/watch?v=uKjbQ0m43b8")
                .build();
    }

    /**
     * Add user do merging queue.
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/queue", method = RequestMethod.POST)
    public ResponseDTO queueUser(@RequestBody String jsonRequest) {
        return queue(readRequest(jsonRequest));
    }

    /**
     * Remove current merging user from the queue or complain about spamming the chat.
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/dequeue", method = RequestMethod.POST)
    public ResponseDTO dequeue(@RequestBody String jsonRequest) {
        final StringBuilder sb = new StringBuilder();
        final Queue mergeQueue = getQueue(readRequest(jsonRequest));
        final String mergingUser = (String) mergeQueue.poll();
        if (mergingUser == null) {
            sb.append(String.format("<p>There is no merging in process, %s STOP SPAMMING!!!!</p>", readRequest(jsonRequest).getUsername()));
        } else {
            sb.append(String.format("<p>%s has merged successfully!</p>", mergingUser));
        }
        if (mergeQueue.size() > 0) {
            sb.append(String.format("<p>It's %s's turn!</p>", mergeQueue.peek()));
        }
        //sb.append(String.format("<p>Good job %s! Here, take this <img src=\"%s\">!</p>", user, "https://dujrsrsgsd3nh.cloudfront.net/img/emoticons/620675/money-1486545160.gif"));
        //sb.append(printQueue());
        return ResponseDTO.builder()
                .message(sb.toString())
                .build();
    }

    /**
     * Show the current status of the queue.
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public ResponseDTO status(@RequestBody String jsonRequest) {
        return ResponseDTO.builder()
                .message(printQueue(readRequest(jsonRequest)))
                .build();
    }

    /**
     * Kick the user that´s taking too long to finish his merging.
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/kick", method = RequestMethod.POST)
    public ResponseDTO kickMergingUser(@RequestBody String jsonRequest) {
        final StringBuilder sb = new StringBuilder();
        final Queue mergeQueue = getQueue(readRequest(jsonRequest));
        final String mergingUser = (String) mergeQueue.poll();
        if(mergingUser == null){
            sb.append(String.format("<p>%s stop trying to brake jeeves! </p>", readRequest(jsonRequest).getUsername()));
        }else{
            sb.append(String.format("<p>%s you were too slow, you had your change, now you´re being kicked out!! <img src=\"%s\" /></p>", mergingUser,
                    "http://metropolitanafm.com.br/wp-content/uploads/2015/09/30-Frases-de-Chaves-e-Chapolin-e-fotos-5.gif"));
        }
        return ResponseDTO.builder()
                .message(sb.toString())
                .build();
    }

    /**
     * When you fucked-up your typing, Kevin takes the blame.
     *
     * @return
     */
    @RequestMapping(value = "/donce", method = RequestMethod.POST)
    public ResponseDTO donce() {
        return ResponseDTO.builder().message("Really Kevin? donce again? This isn't a Blizzard game").build();
    }

    private ResponseDTO queue(final RequestDTO request) {
        final StringBuilder sb = new StringBuilder();
        final Queue mergeQueue = getQueue(request);

        if (mergeQueue.size() == 0) {
            sb.append(String.format("<p>Hey %s, you are the first in line! You can go right ahead! <img src=\"%s\" /></p>", request.getUsername(),
                    "https://dujrsrsgsd3nh.cloudfront.net/img/emoticons/620675/rthumbsup-1486545158.gif"));
        } else {
            sb.append(String.format("<p>Alright alright alright %s! Sit tight, and wait a bit until your turn <img src=\"%s\" /></p>", request.getUsername(),
                    "https://dujrsrsgsd3nh.cloudfront.net/img/emoticons/620675/popcorn-1476892268.gif"));
        }
        mergeQueue.add(request.getUsername());

        return ResponseDTO.builder().message(sb.toString()).build();
    }

    private String printQueue(final RequestDTO request) {
        final StringBuilder sb = new StringBuilder();
        sb.append("<p>The queue currently looks like this: </p>");
        final Queue mergeQueue = getQueue(request);
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

    //Spagghettio!!!
    private RequestDTO readRequest(final String jsonRequest) {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(jsonRequest));
        reader.setLenient(true);
        JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
        return RequestDTO.builder()
                .username(((JsonObject) jsonElement).get("item").getAsJsonObject().get("message").getAsJsonObject().get("from").getAsJsonObject().get("name").getAsString())
                .room(((JsonObject) jsonElement).get("item").getAsJsonObject().get("room").getAsJsonObject().get("name").getAsString())
                .build();
    }

    //Dirty spaghetti to make a queue per room for rMerge
    private Queue getQueue(RequestDTO request) {
        if (!queueCollection.containsKey(request.getRoom())) {
            queueCollection.put(request.getRoom(), new LinkedList<String>());
        }
        return queueCollection.get(request.getRoom());
    }
}
