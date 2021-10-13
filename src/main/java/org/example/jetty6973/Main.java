package org.example.jetty6973;

import ch.qos.logback.access.jetty.RequestLogImpl;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.component.LifeCycle;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8180);

        RequestLogImpl requestLog = new CustomRequestLog();
        requestLog.setFileName(Main.class.getClassLoader().getResource("logback-access.xml").getFile());

        server.setRequestLog(requestLog);

        final byte[] request = ("POST . HTTP/1.1\n"
                + "Content-Type: application/x-www-form-urlencoded\n"
                + "\n"
                + "\n"
                + "").getBytes(StandardCharsets.UTF_8);

        server.start();


        Socket socket = new Socket("localhost", 8180);
        OutputStream outputStream = socket.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.write(request);
        dataOutputStream.flush();
        dataOutputStream.close();

        socket.close();
    }

    private static class CustomRequestLog extends RequestLogImpl implements LifeCycle {
        private CustomRequestLog() {
        }
    }
}
