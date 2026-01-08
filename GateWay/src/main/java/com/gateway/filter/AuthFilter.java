package com.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import org.springframework.stereotype.Component;

@Component
public class AuthFilter
        extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    @jakarta.annotation.PostConstruct
    public void init() {
        System.out.println("AuthFilter Bean has been created and loaded!");
    }

    @Autowired
    private Validator validator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            System.out.println("Checking path: " + exchange.getRequest().getURI().getPath());
            // ၁။ လမ်းကြောင်းက လုံခြုံရေး လိုအပ်သလား စစ်ဆေးခြင်း
            if (validator.predicate.test(exchange.getRequest())) {

                // ၂။ Authorization Header ပါ၊ မပါ စစ်ဆေးခြင်း
                // headers.containsKey() သို့မဟုတ် getFirst() == null ကို သုံးပါ
                if (!exchange.getRequest().getHeaders().containsKey("Authorization")) {
                    throw new RuntimeException("Missing Authorization Header");
                }

                String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
                String token = null;

                // ၃။ Bearer Token စစ်ဆေးခြင်း (startsWith ဖြစ်ရပါမည်၊ ! မဟုတ်ပါ)
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    token = authHeader.substring(7); // "Bearer " (၇ လုံး) ကို ဖြတ်ထုတ်ခြင်း
                } else {
                    throw new RuntimeException("Invalid Authorization Header Format");
                }

                // ၄။ Token ကို Validate လုပ်ခြင်း
                try {
                    jwtUtil.validateToken(token);// သို့မဟုတ် validateToken(token)
                } catch (Exception e) {
                    throw new RuntimeException("Unauthorized access to application");
                }
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // လိုအပ်ရင် configuration properties တွေ ဒီမှာ ထည့်နိုင်ပါတယ်
    }
}
