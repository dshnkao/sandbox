package com.denniskao.chrome;

import com.linecorp.armeria.common.*;
import com.linecorp.armeria.server.Server;
import io.netty.handler.codec.http.cookie.CookieHeaderNames;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import io.netty.handler.codec.http.cookie.ServerCookieEncoder;

public class ChromeCookieLimit {

    public static void main(String[] args) throws InterruptedException {
        Server.builder()
            .service("/", (ctx, req) -> {
                var cookieA = new DefaultCookie("CID", "a".repeat(10));
                cookieA.setDomain("localhost");
                cookieA.setMaxAge(100000);
                cookieA.setHttpOnly(true);
                cookieA.setSecure(true);
                cookieA.setPath("/");
                var cookieCB = new DefaultCookie("CB", "a".repeat(10));
                cookieA.setDomain("localhost");
                cookieA.setMaxAge(100000);
                cookieA.setHttpOnly(true);
                cookieA.setSecure(true);
                cookieA.setPath("/");
                var cookieB = new DefaultCookie("CAZ", "3wHgqSNt-QuSiAg_uUIaDV9mh6wgnZOUDHWi6ld7WU-kajxJdmiL3BL7Jsgy0h1lz4YmIthV0c_eweA5CVlbTSAas8W3byjtt68wuU-gIm28na1nnhVhg9gFSMVGXtdUWdfalE-GpZf77cyZodWU9RWA5BHUo9L4mkpOT4-RISFE279iIf48xjA-7lUZpNG9kDIJDGlEwlXpmgVjWBHvyyR9fiddEL0AUuewcLgylkwOziTUhlHtn2ua91Tx3PffXMivcOlhzRCypx0jGVurW29kcgg67QlnOmom-kI_ttxvyoezuMY6r63K-4nsl2jUKxjgwOoI8BTlfp_in1WaGS108FwnrFqVjghH9XEuEzDdtlT13LPRNEIldbJEJCW4JRVRVvAIrq_VnvNBH2Gcl4xSB8GBkNDilP6E8Pxvr1fBUw5Ad5JqOFagHxVZdLb7hLKfrrupde8GpMgl1blMWjKm7aW3ckWxybEiv1JsrH3ecQ0N_KZLZwpHhamgQ8RuwX0pC4N8QeZ-7gn0EBMssEcIYlRFE2Xl9-csFWWcMS6JWDX0Uc-MmaNJlQ_u-4qnzsjJDTst7KSiVjBp5kxTGfjqy6j0KYZE4jSndrfcHNm7hrXdruROouFdkWEuNXamUmesDTt8JrnKyXJwF5-Wp2kJh-XAyYsp6LojAC2qW4PS1KrENHgq-MIQikkG4VdGaFo9jms1fVl8idEm6w36bcwoc5IAgEDkdTWXOYEI7ZZvj4XGztbvkqYBRcTZkGTfgvDqvtUU42_yxgxE3u-ey3bmk1Fia5MnGGN21IrgkWj79NdfHJKxM0SPHIVxKysdOKns6hrsla5rykuf26FHliCABddLkKA-WCeRRLsm3OXAmlxmfRV3bIHwWD_YRm059QkcF4OgBY5n4zev7mGEpgNqgjEO6tA92pJebBw_QXZJNPEYq5HQeTfFfB27WhGDExJdfIkRKuZkdvR4uGADdz3-Ydo6DgCcbmeqmJ70osodnLPjqcWwDHcu3cE2yJqQ1T9s8OjaV3HY4jL2D1HugRHDSwAHeQAiRNQCPZ7QB3nTLL9R6Jjsz6uXaI6lxYhA-A-Wcs8uO1yQ6lYB-1pqHQEB2VQZ9yUS0x1-i4xvnBvGakpimkQceV2iPUBa-O4R3-EsLB6i4UkRcCKcPUwgLdDNFT2ItMDcCCXsSt78sjxSaOxaeHQvQWnIyMwjw38Qm60T_D0tMVhyBmpW0C1TPLSPnvcJJJSNPyeme63RXMyWfMk-yXFi_AGNAXZA6IjJAaItbCPQf9yzE9S7DSn3Fp-mjngKaBE8XoAkfnqkGbEzs4hZfFmH2nE7dHK08Ymy9x941Ut18rzQ2bsYvTau0erFgDGWRXugiqjJ630P_6Hr4Dfpqmmm2KrL7yRvUZoc4-2TpxgNvMXfMgeRGfSb44ss3fm5OdPnrLi_u2oNJ04DdtgXc7RBdxbQEIZrE5HFqUSOwQZrejBRTgxNn4zlDfJF4Cfwx1Xpp0WTKiP3ROy6yvNxPyUQnJMidyiosXD6QEYtQn7RuZnzooXgIy0klUJkGtk7TalqcfT_1fL272U6GOciwBgakq2MCGJLwsP0z8HaZmCcy4H7w_ZPahqbOd_bYOiZ0iI10eX7hqvl2bLcrnQNiG7k0ABPC_8QiMlwdBMKerTUWSoSVnQR7e3I5FTQMqhiTkmrMOJ91usTIfs0YlpA3PseQQJiKYt5WYQHX7Ani286ubi7gqo1VBcor4JDSZU4TW9haySQGqPvSvcZbihJ1Pa2zR_rfGTY9fgF0gGuXh6w1i6ZxPvyoosyZwM3SJDpn61AjerkZD4kpxzrM2izIDeInYzP9dDd0YS1GMyEAMpXYOqWYIsYK1eg05FlnvJQOKlAAIyfuAtNTf6jA9izLIocfflkyCCChshndetNPJXmlkKqchKU0Bu4lS2uB9BkJZ_zjXsrjtPgjGpKAxphXlRS5QbDT8fAGqdLjkvg4WJz_ZlLo-vsDdYFY_05jBuVAvG_UIIj5j_q8CvdtI19LH5wP7C4AfExiVz5Agtca0T5Y63ZPoxHVITUMW87iz4Yo9DwUyJItV0ujzChpNFpC0A-ZQMUQKgGA3z9NQeRT_Cc0jzpJPgcYTXbdMmTNAm6nTrfVikTkCI_EoJaHNESKjIgDwjpZ3IrKzzoDipgqm--sfRSOG7Et0BnCgKwmH46s0ye_HjONaLsMSiJ2kQFkMQhGTXQEBiPoI2ERj3oYJLpHGIgiG7VglIdx03WIHG2J_z1mt51Y3L_r7RAfHKfSNJSpS_1i_RuHRSEqp7zkgLKiKrTCQO0ORJGZfYxHUiaRkm1yU3bys9GuirRPoaUKIijtyO_2H0euj3biZxdIPKpWLGXZHwe8nY5IG1HPGsQgC28-U8dUQt0Ktxh_Rdt6uKx7YB3fNoTm4rGP54jKiZjuBGehrfEKOFvA3Mt4xKxwiOAfBJ_UXSK-0Ky1pqCMIO7Q-gmYdlEwUkjjIOjjpQv6PRytYICngOmwgl6iefOIiaRogzWgKNng4hI-GeOjtZaoTSN34_La95BXRcqGqBUxGrZatid6oSMq3evVS6iPiA5Vw5nW_zcOXVFPDbCoY1WFFKjNkWO6EQTHMk3C_axm4zhAMxTGyq6AehoRrUUdTqG1VbXOXP0itU4Ny26R4U0wUvVe-huEjQ-e3QcxIVfcjZvGzXBbq8tJcN_EBfxMT2aVFZ-S-zurGz7y0C3Kh5skEVYhX7ANMg7QMe-AmEihOo3S4z4zFswrMXVlUDgJho9AVFWBjiDD-4dA-dZybRP5SxAAssU_qAdSt8OCLx0MBH4xi6rjzygL-I66BYpWYame_MFnAtVIbAHkLPIGERJVDecU1SHFQiLZ1xAazmvueWKe1Q4Yk4hZACviJAxxmkrkGpNOQ3nocnhUAvarIUPP6KyhsY_v0n2m8cckSAYq7MJM4jlBbquRkgskdtgjtxbSYjkofqwC7pFIrt_-oUJahiw2dx0xywgYAfpdQBKr5bIzXIHxuMw_tzOq4FWikBNJEjkuxB8d5phQu52JeIsY2v6M082EfUjhhbrnlvw0FVKpj1lhNLpSJ1sdSUqBBnkl4dwhEBgLSajiX1TcWHd08uWZt6n5aqqJqAKxXEzoJ_oyAX_etSUcvcbaTX67aAshWGaoP-y5Y9hE0V1LQHOravrOJ6WpsNnkipgZpHE4E8J-Mkby7FHudvS-MO2ygIcX3Wgv7A-XVa1pFv8OHnqKjQISv7DeElA4h7yFzlow7S2PaxhsNWm5F2auuCbnhHFMPF9jW9gnGuSu_uN7pyHRYsQ8doAPGtdLKePMZNaJujjDoSCdZKdPO8Ld0oZn1jRO3N_ClLUVYt6_YUZd6HtnsBYC3Ll0DJKHZpw1CkCcYAlQ9krkli9-mi5crn7XV05RVlGr2E1ftqgjra1EKv2LZbHYIXHRzaHhLYcdomTxOvUiiVdQ0T7eC0FVxOA9wTlefZhp0lhoFVT_XLeTJxq_Yk46la0YUfYrCCiggIw33GT-BdbKHf4BWO5WHmfGDSru1j34IcMg0vEaw_-hoiyyTwsrtycoXh9bG7BXoVXnhsOi4CYm075s8LyxHr2q8DZZ8yZSZ7fkrAHMxfJv5bMIJCaH9ARVAK9h04Cca7RuvXa53-fbaptPTgjqth0Hnu-o-zh0iE_mIdsvu0Id4l40Win7du7IkIwWKcmAap3ht1iKC99AxQy8CBWXm4aGoFPY-blJUeUe5emDIDR7bjs-GlxYgQVcUuphqzuz4HCk5uKIt5GqaZcUQHFvzZF_Ef1VhuCya75XpkGzHajqVrNv7BW3ngYjpZ714qFJc2tURYY41yG03UuH1W-mqVMC7GtldOZiNifh2A8s0fdg9trTNdL8tbBOVqkfQlKdWw48uJ7wiS-c5kQHn_r9a6EX7gFRtQVsJv9T_S4FD1vf7nf_y1-WeM0bIGCrdAgtut6ERBgzapylLxeiqkRcaeYIPlIep5_CEuT36qFi2AKM6j7Xv8N4btgPXgvKjaUmfbxQ3BugbGh7CT3DW2uhPLePkYRwK7V_qE4rCRENAYxIekw7nc_70pR8PtzQ1gDDYzWrRI00a0nkXSsTALPageZ4NrmLSuNpxQQjBmBaxnbx3fvVRXZvCOYdd2io5cKwUVr553Vi1OMD6baaipbuVPCMqYaao4AXsm7Xw30MIRlqOGRtj5KTpZJR-zXfjP8jr1hUWPjqNPVjgRC6mkQcKblP9vtvBuCM0wQ-hq7gY-efPmGGgWp16ydZADfrskZj8q0cp9kSDz3WhZfhxFVdf_TS7X_10wE-jVzTrCJG1bTIE1jyiD7b2ilJSU-QGcGJejCTjbN-34JGL50Cjy96856BhOBEZIs8R0cUHSS4AX0_u2k1iHRMNjEzrep3pgHRnhkhYI8mhUX0qWTk4lmDOVne7LYk1eqOjb5hpVgUY1YLwGZrRv9lDveR8LBvgsCJobS8AwFkioRRazvmuMse4aQ6mSrmf0uYYXOO66Kvm7-j8_b3m_zVN9IE34JBcLX2EXOoIaQRmhj1PFbxC3yWmFdre85i4hFtnpXD2jbB3w0PYvGQv2GBU1LwcBG54whnx2kGyhoOZH-sPw1uLm7lxIPVawueaxKLJM099RQhnUyyZUqtR5FzcNzv0FHySAp7W_lcLSgxKL3GGGDIN9C--WktIQvew2r45bQDAbxc6lW6-MrGtlyD4RwhBBGHbqEE4leEE8SBoUglDEykrnngHU");
                cookieB.setDomain("localhost");
                cookieB.setMaxAge(100000);
                cookieB.setHttpOnly(true);
                cookieB.setSecure(true);
                cookieB.setPath("/");
                var cookieC = new DefaultCookie("CPA", "c".repeat(10));
                cookieC.setDomain("localhost");
                cookieC.setMaxAge(100000);
                cookieC.setSameSite(CookieHeaderNames.SameSite.None);
                cookieC.setPath("/");
                return HttpResponse.builder()
                    .ok()
                    .header(HttpHeaderNames.SET_COOKIE, ServerCookieEncoder.STRICT.encode(cookieA))
                    .header(HttpHeaderNames.SET_COOKIE, ServerCookieEncoder.STRICT.encode(cookieCB))
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
