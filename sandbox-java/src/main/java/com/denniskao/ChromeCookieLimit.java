package com.denniskao;

import com.linecorp.armeria.common.*;
import com.linecorp.armeria.server.Server;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import io.netty.handler.codec.http.cookie.ServerCookieEncoder;

public class ChromeCookieLimit {

    public static void main(String[] args) throws InterruptedException {
        Server.builder()
                .service("/", (ctx, req) -> {
                    var cookieA = new DefaultCookie("a", "a".repeat(10));
                    cookieA.setDomain("localhost");
                    cookieA.setMaxAge(100000);
                    cookieA.setHttpOnly(true);
                    cookieA.setSecure(true);
                    cookieA.setPath("/");
                    var cookieB = new DefaultCookie("b", "b".repeat(4897));
                    cookieB.setDomain("localhost");
                    cookieB.setMaxAge(100000);
                    cookieB.setHttpOnly(true);
                    cookieB.setSecure(true);
                    cookieB.setPath("/");
                    var cookieC = new DefaultCookie("c", "c".repeat(10));
                    cookieC.setDomain("localhost");
                    cookieC.setMaxAge(100000);
                    cookieC.setHttpOnly(true);
                    cookieC.setSecure(true);
                    cookieC.setPath("/");
                    return HttpResponse.builder()
                            .ok()
                            .header(HttpHeaderNames.SET_COOKIE, ServerCookieEncoder.STRICT.encode(cookieA))
                            .header(HttpHeaderNames.SET_COOKIE, ServerCookieEncoder.STRICT.encode(cookieB))
                            .header(HttpHeaderNames.SET_COOKIE, ServerCookieEncoder.STRICT.encode(cookieC))
                            .content(MediaType.HTML_UTF_8, """
                                    <html>
                                    <body>
                                    <script>
                                        fetch('http://localhost:8080').then((response) => {})
                                    </script>
                                    </body>
                                    </html>
                                    """)
                            .build();
                })
                .port(8080, SessionProtocol.HTTP)
                .build()
                .start()
                .join();
    }
}
