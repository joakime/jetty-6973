package org.example.jetty6973;

import ch.qos.logback.access.jetty.RequestLogImpl;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.util.component.LifeCycle;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws Exception {
        int port = 8180;
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        connector.addBean(new HttpListener());
        server.addConnector(connector);

        RequestLogImpl requestLog = new CustomRequestLog();
        requestLog.setFileName(Main.class.getClassLoader().getResource("logback-access.xml").getFile());

        server.setRequestLog(requestLog);

        final byte[] request = ("POST . HTTP/1.1\n"
                + "Content-Type: application/x-www-form-urlencoded\n"
                + "\n"
                + "\n"
                + "").getBytes(StandardCharsets.UTF_8);

        server.start();


        Socket socket = new Socket("localhost", port);
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
