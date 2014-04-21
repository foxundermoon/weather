package quweijia.weather.core;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
 public class JsonTest{
    public final static void main(String[] args) throws Exception {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            httpclient.addRequestInterceptor(new HttpRequestInterceptor() {
                public void process(
                        final HttpRequest request,
                        final HttpContext context) throws HttpException, IOException {
                    if (!request.containsHeader("Accept-Encoding")) {
                        request.addHeader("Accept", "application/json");
                    }
                }
 
            });
 
//            httpclient.addResponseInterceptor(new HttpResponseInterceptor() {
//
//                public void process(
//                        final HttpResponse response,
//                        final HttpContext context) throws HttpException, IOException {
//                    HttpEntity entity = response.getEntity();
//                    Header ceheader = entity.getContentEncoding();
//                    if (ceheader != null) {
//                        HeaderElement[] codecs = ceheader.getElements();
//                        for (int i = 0; i < codecs.length; i++) {
//                            if (codecs[i].getName().equalsIgnoreCase("gzip")) {
//                                response.setEntity(
//                                        new GzipDecompressingEntity(response.getEntity()));
//                                return;
//                            }
//                        }
//                    }
//                }
//
//            });
             HttpGet httpget = new HttpGet("http://your_url");
            // Execute HTTP request
            System.out.println("executing request " + httpget.getURI());
            HttpResponse response = httpclient.execute(httpget); 
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
//            System.out.println(response.getLastHeader("Content-Encoding"));
//            System.out.println(response.getLastHeader("Content-Length"));
            System.out.println("----------------------------------------");
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String content = EntityUtils.toString(entity);
                System.out.println(content);
                System.out.println("----------------------------------------");
//                System.out.println("Uncompressed size: "+content.length());
            }
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
    } 
//    static class GzipDecompressingEntity extends HttpEntityWrapper {
//
//        public GzipDecompressingEntity(final HttpEntity entity) {
//            super(entity);
//        }
//
//        @Override
//        public InputStream getContent()
//            throws IOException, IllegalStateException {
//
//            // the wrapped entity's getContent() decides about repeatability
//            InputStream wrappedin = wrappedEntity.getContent();
//
//            return new GZIPInputStream(wrappedin);
//        }
//
//        @Override
//        public long getContentLength() {
//            // length of ungzipped content is not known
//            return -1;
//        }
//
//    }
}

