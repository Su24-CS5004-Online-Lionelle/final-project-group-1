
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;

public class ApiUtil {
    /** The base URL for the Dog API (v2). */
    private static final String API_BASE_URL = "https://dogapi.dog/api/v2";

    /** The HttpClient instance used to send HTTP requests. */
    private final HttpClient httpClient;

    /**
     * Constructs a new ApiUtil.
     */
    public ApiUtil() {
        this.httpClient = HttpClient.newBuilder().build();
    }

    /**
     * Fetches a list of dog breeds from the Dog API, including detailed information about each breed.
     *
     * @return A JSON string containing the list of dog breeds with detailed information.
     * @throws IOException If an I/O error occurs when sending or receiving.
     * @throws InterruptedException If the operation is interrupted.
     */
    public String getBreeds() throws IOException, InterruptedException {
        String endpoint = API_BASE_URL + "/breeds";
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(endpoint)).GET().build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static void main(String[] args) {
        ApiUtil test = new ApiUtil();
        try {
            String breeds = test.getBreeds();
            System.out.println("Breeds: " + breeds);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}