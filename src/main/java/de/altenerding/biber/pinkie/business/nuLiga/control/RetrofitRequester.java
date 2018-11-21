package de.altenerding.biber.pinkie.business.nuLiga.control;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.base.CaseFormat;
import de.altenerding.biber.pinkie.business.config.ConfigService;
import de.altenerding.biber.pinkie.business.config.entity.Config;
import de.altenerding.biber.pinkie.business.config.entity.ConfigProperty;
import de.altenerding.biber.pinkie.business.config.entity.Configuration;
import de.altenerding.biber.pinkie.business.nuLiga.entity.TokenResult;
import de.altenerding.biber.pinkie.business.systemproperty.SystemProperty;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.io.IOException;

@Singleton
@Startup
public class RetrofitRequester {

    private static final String GRANT_TYPE = "client_credentials";
    private static final String GRANT_TYPE_REFRESH = "refresh_token";
    private static final String SCOPE = "nuPortalRS_club";
    public static final String BASE_URL = "https://hbde-portal.liga.nu/rs/";
    private TokenResult tokenResult;
    private Retrofit retrofit;

    @Inject
    private Logger logger;
    @Inject
    private ConfigService configService;
    @Inject
    @SystemProperty(name = "nuLigaRestClientId")
    private String clientId;
    @Inject
    @SystemProperty(name = "nuLigaRestClientSecret")
    private String clientSecret;
    @Inject
    @Config(property = ConfigProperty.NU_LIGA_API_TOKEN)
    private String initialNuLigaToken;
    @Inject
    @Config(property = ConfigProperty.NU_LIGA_API_RESFRESH_TOKEN)
    private String initialNuLigeRefreshToken;

    @PostConstruct
    public void init() {
        logger.info("Initializing retrofit client");
        initRetrofit();
        tokenResult = new TokenResult(initialNuLigaToken, initialNuLigeRefreshToken);
    }

    public <T> T executeSyncronousCall(Call<T> call) {
        try {
            logger.info("Calling URL \'{}\'", call.request().url().toString());
            Response<T> response = call.execute();

            if (response.isSuccessful()) {
                //Successful call
                return response.body();
            } else if (response.code() == 401) {
                //Token probably invalid, so try refresh of token
                logger.error("Token invalid. Refresh token!");
                refreshToken();
                response = call.clone().execute();

                if (response.isSuccessful()) {
                    // now call is successful after refreshing token
                    return response.body();
                } else if (response.code() == 401) {
                    //Still authorization error. Do not try again
                    logger.error("Still error after token refresh: \'{}\'", response.errorBody().string());
                    return null;
                } else {
                    //Some other error while refreshing the token
                    logger.error("HTTP Error Code {} when refreshing the token with message \'{}\'", response.code(), response.errorBody().string());
                    return null;
                }
            } else {
                //Some other error during the execution of the call
                logger.error("HTTP Error Code {} with message \'{}\'", response.code(), response.errorBody().string());
                return null;
            }
        } catch (IOException e) {
           logger.error("Error while executing retrofit call", e);
            return null;
        }
    }

    private void initRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
            if (tokenResult == null || StringUtils.isEmpty(tokenResult.getAccessToken())) {
                createToken();
            }

            Request newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + tokenResult.getAccessToken())
                    .addHeader("Accept", "application/json")
                    .build();
            return chain.proceed(newRequest);
        }).build();
        //JacksonConverterFactory adding  TaskDeserializer to SimpleModule
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.setDeserializerModifier(new BeanDeserializerModifier() {
            @Override
            public JsonDeserializer<Enum> modifyEnumDeserializer(DeserializationConfig config,
                                                                 final JavaType type,
                                                                 BeanDescription beanDesc,
                                                                 final JsonDeserializer<?> deserializer) {
                return new JsonDeserializer<Enum>() {
                    @Override
                    public Enum deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
                        Class<? extends Enum> rawClass = (Class<Enum<?>>) type.getRawClass();
                        String name = CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, jp.getValueAsString());
                        return Enum.valueOf(rawClass, name);
                    }
                };
            }
        });
        mapper.registerModule(module);

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .build();
    }

    private void refreshToken() throws IOException {
        logger.info("Refreshing token!");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        TokenApi tokenApi = retrofit.create(TokenApi.class);

        Call<TokenResult> tokenCall = tokenApi.refreshToken(GRANT_TYPE_REFRESH, clientId, clientSecret, SCOPE, tokenResult.getRefreshToken());
        logger.info("Calling URL \'{}\'", tokenCall.request().url().toString());
        retrofit2.Response<TokenResult> response = tokenCall.execute();

        if (response.isSuccessful()) {
            tokenResult = response.body();
            logger.info("Token successfully refreshed");
            Configuration configuration = new Configuration(ConfigProperty.NU_LIGA_API_TOKEN, tokenResult.getAccessToken());
            configService.updateConfig(configuration);
        } else if (response.code() == 400) {
            logger.warn("Trying to create new token because of HTTP Status Code 400");
            logger.warn(response.errorBody().string());
            createToken();
        } else {
            throw new IOException(response.errorBody().string());
        }
    }

    public void createToken() throws IOException {
        logger.info("Creating new token!");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        TokenApi tokenApi = retrofit.create(TokenApi.class);
        Call<TokenResult> tokenCall = tokenApi.getToken(GRANT_TYPE, clientId, clientSecret, SCOPE);
        logger.info("Calling URL \'{}\'", tokenCall.request().url().toString());
        retrofit2.Response<TokenResult> response = tokenCall.execute();

        if (response.isSuccessful()) {
            tokenResult = response.body();
            logger.info("Token successfully created");
            Configuration configuration = new Configuration(ConfigProperty.NU_LIGA_API_TOKEN, tokenResult.getAccessToken());
            configService.updateConfig(configuration);
        } else {
            throw new IOException(response.errorBody().string());
        }
    }

    @Produces
    public Retrofit getRetrofit() {
        return retrofit;
    }
}
