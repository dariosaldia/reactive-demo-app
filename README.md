# Reactive CRUD demo with reactor

This is a simple reactive crud demo application to practice with reactor

## Usage
First, you need to have installed mongodb in your pc. Once you're done with it you can start the application by running:

```bash
mvn spring-boot:run
```

The application will startup in http://localhost:8080. The default profile is dev.

### Server Side Events (SSE):

Explanation from https://golb.hplar.ch/2017/03/Server-Sent-Events-with-Spring.html
> A popular choice for sending real-time data from the server to a web application is WebSocket. WebSocket opens a bidirectional connection between client and server. Both parties can send and receive messages. In scenarios where the application only needs one-way communication from the server to the client, a simpler alternative exists Server-Sent Events (SSE). It's a HTML5 standard and utilizes HTTP as the transport protocol, the protocol only supports text messages, and it's unidirectional, only the server can send messages to the client.

We will see SSE in action performing a GET to obtain students

GET students accepts 3 parameters: sort, page and size.
* sort format is FIELD_TO_SORT_BY:DIRECTION. If not passed, then no sorting is applied. FIELD_TO_SORT_BY for now accepts 'gpa' and 'email'. DIRECTION can take asc or desc. If a different field than the previous two is passed, or if a valid field is passed but no DIRECTION or empty is passed, an error will be thrown.
* page takes a number. If page is empty or null, then defaults to zero.
* size takes a number too and defaults to 10 if not specified in the URL.

**Each value returned will have a delay of 500ms for demonstration purposes**

#### Using non-sse controller

Enter the following URL in the browser http://localhost:8080/students

![json blocking sse](https://github.com/dariosaldia/resources/blob/master/blocking.gif?raw=true)

This is the traditional way. The server waits until all the elements are ready and only after that, it sends the response back to the client. 

#### Using sse controller

Enter the following URL in the browser http://localhost:8080/students-sse

![sse](https://github.com/dariosaldia/resources/blob/master/sse.gif?raw=true)

Now we're producing using the text/event-stream media type which later is read by the client. This way we enabled sse on the server side and as soon as each element is available, the server sends it to the client instead of waiting for all the elements to be ready.

```java
@RestController
@RequestMapping(path = "/students-sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
@Slf4j
public class SSEStudentController {
  
  private static final int DELAY_PER_ITEM_MS = 500;

    @Autowired
    private StudentService studentService;
    
    @GetMapping
    public Flux<Student> getStudentsByParams(
        @RequestParam(name = "sort", required = false) String sort, 
        @RequestParam(name = "page", required = false) Integer page, 
        @RequestParam(name = "size", required = false) Integer size) {
      log.info("Sort param is {}", sort);
      log.info("Page param is {}", page);
      log.info("Size param is {}", size);
      
      List<String> sortBy = Optional.ofNullable(sort)
          .map(sortParam -> Arrays.asList(sortParam.split(",")))
          .orElseGet(() -> Arrays.asList());
      
      Optional<Integer> pageOpt = Optional.ofNullable(page);
      Integer pageNum = null;
      if (pageOpt.isPresent()) {
        pageNum = pageOpt.get();
      }
      else {
        log.info("Page number is empty or null. Defaulting to {}", pageNum);
        pageNum = 0;
      }
      
      Optional<Integer> sizeOpt = Optional.ofNullable(size);
      Integer sizeNum = null;
      if (sizeOpt.isPresent()) {
        sizeNum = sizeOpt.get();
      }
      else {
        log.info("Size number is empty or null. Defaulting to {}", sizeNum);
        sizeNum = 10;
      }
}
```
