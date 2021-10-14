package org.example.jetty6973;

import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class HttpListener implements HttpChannel.Listener
{
    public static final Logger LOG = Log.getLogger(HttpListener.class);

    @Override
    public void onDispatchFailure(Request request, Throwable failure)
    {
        LOG.warn("onDispatchFailure(" + request + ")", failure);
    }

    @Override
    public void onRequestFailure(Request request, Throwable failure)
    {
        LOG.warn("onRequestFailure(" + request + ")", failure);
    }

    @Override
    public void onResponseFailure(Request request, Throwable failure)
    {
        LOG.warn("onResponseFailure(" + request + ", " + request.getResponse() + ")", failure);
    }
}
