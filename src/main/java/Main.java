import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHeaders;


import java.io.IOException;
import java.util.List;


public class Main {

    public static ObjectMapper oMapper = new ObjectMapper();

    public static void main(String[] args) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");
            httpGet.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
            try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {

                List<Cat> cats = oMapper.readValue(
                        response1.getEntity().getContent(),
                        new TypeReference<>() {}
                );
                List<Cat> sortedCats = cats.stream()
                        .filter(x->x.getUpvotes()!=null)
                        .filter(x->x.getUpvotes()>0)
                        .toList();
                sortedCats.forEach(System.out::println);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
