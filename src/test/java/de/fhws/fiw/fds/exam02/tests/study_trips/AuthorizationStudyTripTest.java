package de.fhws.fiw.fds.exam02.tests.study_trips;

import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.tests.models.StudyTrip;
import de.fhws.fiw.fds.exam02.tests.student.AbstractStudentTest;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMapUtils;
import org.junit.Test;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class AuthorizationStudyTripTest extends AbstractStudyTripTest {

    public final static String UPDATE_SINGLE_STUDY_TRIP = "updateStudyTrip";

    public final static String DELETE_SINGLE_STUDY_TRIP = "deleteStudyTrip";

    @Test
    public void testGet_Authorized_200( ) throws IOException
    {
        RestApiResponse<StudyTrip> response = getSingleRequestById( HeaderMapUtils.withAcceptJson( ), 1, "Admin", "secret" );

        assertEquals( 200, response.getLastStatusCode( ) );

        StudyTrip studyTrip = response.getResponseSingleData( );

        assertNotNull( studyTrip );

        response = getSingleRequestById( HeaderMapUtils.withAcceptJson( ), 1, "Lecturer", "secret" );

        assertEquals( 200, response.getLastStatusCode( ) );

        studyTrip = response.getResponseSingleData( );

        assertNotNull( studyTrip );
    }

    @Test
    public void testGet_Unauthorized_401( ) throws IOException
    {
        RestApiResponse<StudyTrip> response = null;
        try {

            response = getSingleRequestById(HeaderMapUtils.withAcceptJson(), 1, "random", "user");
        }
        catch(WebApplicationException e){

        assertEquals( "HTTP 401 Unauthorized", e.getMessage() );
        assertNull( response );
    }

    }

        @Test
        public void testPost_Authorized_200( ) throws IOException
        {
            final RestApiResponse<StudyTrip> response = postRequest( HeaderMapUtils.withContentTypeJson( ), defineNewResource( ), "Admin", "secret"  );
            final String locationHeader = response.getLocationHeader( );

            assertEquals( 201, response.getLastStatusCode( ) );
            assertNotNull( locationHeader );
            assertTrue( locationHeader.startsWith( BASE_URL + "studytrips" ) );
        }

    @Test
    public void testPost_Unauthorized_401( ) throws IOException
    {
        final RestApiResponse<StudyTrip> response = postRequest( HeaderMapUtils.withContentTypeJson( ), defineNewResource( ), "sdfdsf", "secret"  );
        final String locationHeader = response.getLocationHeader( );

        assertEquals( 401, response.getLastStatusCode( ) );
        assertNull( locationHeader );
    }

        private StudyTrip defineNewResource( )
        {
            return new StudyTrip(
                    "TestTrip",
                    LocalDate.of( 2022, 2, 15 ),
                    LocalDate.of( 2022, 3, 1 ),
                    "FHWS",
                    "Bangalore",
                    "India",
                    "Admin");
        }

    @Test
    public void testPutAuthorized_204( ) throws IOException
    {
        final StudyTrip studyTrip = defineUpdateStudyTrip( );

        final RestApiResponse<StudyTrip> response = putRequest( HeaderMapUtils.withContentTypeJson( ), studyTrip, "Admin", "secret"  );

        assertEquals( 204, response.getLastStatusCode( ) );
    }

    @Test
    public void testPutUnauthorized_401( ) throws IOException
    {
        final StudyTrip studyTrip = defineUpdateStudyTrip( );

        final RestApiResponse<StudyTrip> response = putRequest( HeaderMapUtils.withContentTypeJson( ), studyTrip, "random", "secret"  );

        assertEquals( 401, response.getLastStatusCode( ) );
    }

    private StudyTrip defineUpdateStudyTrip( )
    {
        StudyTrip studyTrip = new StudyTrip(
                "Updated TestTrip",
                LocalDate.of( 2022, 2, 15 ),
                LocalDate.of( 2022, 3, 1 ),
                "FHWS",
                "Bangalore",
                "India",
                "Admin");
        studyTrip.setId(1);
        return studyTrip;
    }


    @Test
    public void test_Authorized_Post_And_Authorized_Delete_204_and_404( ) throws IOException
    {
        //post new resource
        final RestApiResponse<StudyTrip> responseFromPostRequest = postRequest( HeaderMapUtils.withContentTypeJson( ), defineNewResource( ), "Admin", "secret" );

        System.out.println(responseFromPostRequest.getLastStatusCode());

        final String locationHeader = responseFromPostRequest.getLocationHeader( );

        //get new resource
        final RestApiResponse<StudyTrip> responseFromFirstGetRequest = getSingleRequestByUrl( HeaderMapUtils.withAcceptJson( ), locationHeader, "Admin", "secret" );

        final StudyTrip tripToDelete = responseFromFirstGetRequest.getResponseSingleData( );

        assertNotNull( tripToDelete );

        //delete new resource
        final RestApiResponse<StudyTrip> responseFromDeleteRequest = deleteRequest( HeaderMapUtils.empty( ), tripToDelete, "Admin", "secret" );

        assertEquals( 204, responseFromDeleteRequest.getLastStatusCode( ) );
    }


    @Test
    public void test_Authorized_Post_And_Unauthorized_Delete_401( ) throws IOException
    {
        //post new resource
        final RestApiResponse<StudyTrip> responseFromPostRequest = postRequest( HeaderMapUtils.withContentTypeJson( ), defineNewResource( ), "Admin", "secret" );

        System.out.println(responseFromPostRequest.getLastStatusCode());

        final String locationHeader = responseFromPostRequest.getLocationHeader( );

        //get new resource
        final RestApiResponse<StudyTrip> responseFromFirstGetRequest = getSingleRequestByUrl( HeaderMapUtils.withAcceptJson( ), locationHeader, "Lecturer", "secret" );

        final StudyTrip tripToDelete = responseFromFirstGetRequest.getResponseSingleData( );

        assertNotNull( tripToDelete );

        //delete new resource
        final RestApiResponse<StudyTrip> responseFromDeleteRequest = deleteRequest( HeaderMapUtils.empty( ), tripToDelete, "Lecturer", "secret" );

        assertEquals( 401, responseFromDeleteRequest.getLastStatusCode( ) );

    }







  

}
