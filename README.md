# Jeeves Server

Simple Spaghetti Spring boot RESTful Webservice to Handle a Merge queue in Hipchat.

As basically as I can put it, we have a global Queue of Strings in memory, and we can add / remove or query Strings and send a message back to Hipchat.

## Request JSON 
```javascript
{
    event: 'room_message',
    item: {
        message: {
            date: '2015-01-20T22:45:06.662545+00:00',
            from: {
                id: 1661743,
                mention_name: 'Blinky',
                name: 'Blinky the Three Eyed Fish'
            },
            id: '00a3eb7f-fac5-496a-8d64-a9050c712ca1',
            mentions: [],
            message: '/weather',
            type: 'message'
        },
        room: {
            id: 1147567,
            name: 'The Weather Channel'
        }
    },
    webhook_id: 578829
}
```

## Response JSON
```javascript
{
    "color": "blue",
    "message": "Formatted HTML message. Note that, to use emoticons, we have to use img tags with HipChat's emoticon 16x16 image addresses",
    "notify": false,
    "message_format": "html"
}
```

## Deployment
There is no CI in place. If you compile a new version of Jeeves Server, send me the generated JAR so I can upload it to AWS.

## To be developed
* Change the ul tag for an ol tag 
* Add a simple way of Randomizing Phrases / Messages for Success, failure, and stuff
* Figure a way to add extra handling depending on the executed message, instead of using different Integration bots in the hipchat room (I think I'm missing a parameter in the incoming JSON)
* Unspaghettize the GSON parse of the request json.
