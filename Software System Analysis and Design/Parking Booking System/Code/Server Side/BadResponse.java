public class BadResponse extends Response {
    
    private Response res;
    
    public BadResponse(Response r) {
        this.res = r;
    }
    
    public String message();
    
    private void badSend();
}