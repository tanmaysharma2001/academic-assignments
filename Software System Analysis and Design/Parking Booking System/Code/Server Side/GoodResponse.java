public class GoodResponse extends Response {
    private Response res;
    public GoodResponse(Response r) {
        this.res = r;
    }
    public String message();
    private void goodSend();
}