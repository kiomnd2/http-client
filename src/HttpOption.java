import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.HashSet;

public class HttpOption {

    private String method;

    private int timeout;

    private int readTimeout;

    private boolean setDoInput;

    private boolean setDoOutput;

    private String charset;

    private String readCharset;

    private HashMap<String, String> requestProperty = new HashMap<>();

    public HttpOption() {
        this.method = "POST";
        this.timeout = 30000;
        this.readTimeout = 30000;
        this.setDoInput = true;
        this.setDoOutput = true;
        this.charset = "UTF-8";
        this.readCharset = "UTF-8";
    }

    public void addProperty(String key, String value) {
        requestProperty.put(key, value);
    }

    public HttpURLConnection setOption(HttpURLConnection connection) throws ProtocolException {
        connection.setDoInput(this.setDoInput);
        connection.setDoOutput(this.setDoOutput);
        connection.setReadTimeout(this.readTimeout);
        connection.setConnectTimeout(this.timeout);
        connection.setRequestMethod(this.method);

        this.requestProperty.forEach(connection::setRequestProperty);
        return connection;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public void setSetDoInput(boolean setDoInput) {
        this.setDoInput = setDoInput;
    }

    public void setSetDoOutput(boolean setDoOutput) {
        this.setDoOutput = setDoOutput;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setReadCharset(String readCharset) {
        this.readCharset = readCharset;
    }

    public void setRequestProperty(HashMap<String, String> requestProperty) {
        this.requestProperty = requestProperty;
    }
}
