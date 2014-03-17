package com.demo.file;

import java.io.IOException;
import java.net.URLDecoder;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

public class EncodeFilter implements Filter
{

    public EncodeFilter()
    {
        config = null;
        encode = null;
    }

    public void init(FilterConfig config)
        throws ServletException
    {
        this.config = config;
        encode = config.getInitParameter("DEFAULT_URI_ENCODE");
        if(encode == null)
            encode = "UTF-8";
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
        throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest)req;
        String uri = request.getRequestURI();
        String ch = URLDecoder.decode(uri, encode);
        if(uri.equals(ch))
        {
            chain.doFilter(req, res);
            return;
        } else
        {
            ch = ch.substring(request.getContextPath().length());
            config.getServletContext().getRequestDispatcher(ch).forward(req, res);
            return;
        }
    }

    public void destroy()
    {
        config = null;
    }

    public static final String DEFAULT_URI_ENCODE = "UTF-8";
    private FilterConfig config;
    private String encode;
}