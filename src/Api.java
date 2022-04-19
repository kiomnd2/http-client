import param.Param;
import param.processor.Processor;
import resultmaker.ResultMaker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Api<T> {

    private final URL url;

    private final HttpOption httpOption;

    private final Processor processor;

    private final ResultMaker<T> resultMaker;


    public Api(URL url, HttpOption httpOption, Processor processor, ResultMaker<T> resultMaker) {
        this.url = url;
        this.httpOption = httpOption;
        this.processor = processor;
        this.resultMaker = resultMaker;
    }


    public T sendApi(Param param) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) this.url.openConnection();
        httpOption.setOption(connection);

        try (OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream(), httpOption.getCharset())){
            String process = processor.process(param);
            osw.write(process);
            osw.flush();
        }

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), httpOption.getReadCharset()))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        return resultMaker.parseResult(sb.toString());
    }
}
