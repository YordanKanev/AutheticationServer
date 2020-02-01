package bg.sofia.uni.fmi.mjt.authentication.model.web.response;

public interface ResponseFactory {

    static Response error(String message) {
        return new ResponseImpl(false, message);
    }

    static Response success(String message) {
        return new ResponseImpl(true, message);
    }
}
