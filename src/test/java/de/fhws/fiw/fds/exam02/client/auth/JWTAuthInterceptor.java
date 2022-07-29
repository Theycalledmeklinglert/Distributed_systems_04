package de.fhws.fiw.fds.exam02.client.auth;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang.StringUtils;
import java.io.IOException;

public class JWTAuthInterceptor implements Interceptor {
    private final String jsonToken;

        public JWTAuthInterceptor( final String jsonToken)
        {
            System.out.println(jsonToken);
            this.jsonToken = "Bearer " + jsonToken;
        }

        @Override public Response intercept(final Chain chain ) throws IOException
        {
            if ( StringUtils.isNotEmpty( this.jsonToken ) )
            {
                final Request request = chain.request( );
                final Request authenticatedRequest = request.newBuilder( )
                        .header( "Authorization", jsonToken ).build( );
                return chain.proceed( authenticatedRequest );
            }
            else
            {
                return chain.proceed( chain.request( ) );
            }
        }

    public String getJsonToken() {
        return jsonToken;
    }
}


