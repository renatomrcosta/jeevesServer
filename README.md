# Jeeves Server

Simple Spaghetti Spring boot RESTful Webservice to Handle a Merge queue in Hipchat.

As basically as I can put it, we have a global Queue of Strings in memory, and we can add / remove or query Strings and send a message back to Hipchat.

TODO - Write here a sample of the input JSON and the expected Message result.

# Deployment
There is no CI in place. If you compile a new version of Jeeves Server, send me the generated JAR so I can upload it to AWS.

## To be developed
* Change the ul tag for an ol tag
* Add back a check for /dequeue in order to have only the actual user in line dequeued.
* Add a /Kick handler to boot queue stragglers 
* Add a simple way of Randomizing Phrases / Messages for Success, failure, and stuff
* Figure a way to add extra handling depending on the executed message, instead of using different Integration bots in the hipchat room (I think I'm missing a parameter in the incoming JSON)
* Unspaghettize the GSON parse of the request json.