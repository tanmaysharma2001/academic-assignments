public class Server {
    
    public Server() {

    }

    public Stream listen(int port);

    public void parse(Stream s, QuestionType qt, Object data);

    public void response(QuestionType qt, Object data);
}