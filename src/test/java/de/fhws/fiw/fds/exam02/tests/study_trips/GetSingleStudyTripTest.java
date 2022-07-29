package de.fhws.fiw.fds.exam02.tests.study_trips;

import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.tests.models.Student;
import de.fhws.fiw.fds.exam02.tests.models.StudyTrip;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMap;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMapUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GetSingleStudyTripTest extends AbstractStudyTripTest {
    public final static String UPDATE_SINGLE_STUDY_TRIP = "updateStudyTrip";

    public final static String DELETE_SINGLE_STUDY_TRIP = "deleteStudyTrip";

    @Test
    public void test_200( ) throws IOException
    {
        final RestApiResponse<StudyTrip> response = getSingleRequestById( HeaderMapUtils.withAcceptJson( ), 2, userName, password );

        assertEquals( 200, response.getLastStatusCode( ) );

        final StudyTrip studyTrip = response.getResponseSingleData( );

        assertNotNull( studyTrip );

        assertEquals( 2, studyTrip.getId( ) );
        assertEquals( "University of Mumbai", studyTrip.getCompanyName( ) );
        assertEquals( "India", studyTrip.getCountryName( ) );
        assertEquals( "2000-02-10", studyTrip.getEndDate( ) );
        assertEquals( "2000-01-10", studyTrip.getStartDate( ) );
        assertEquals( "Indienreise 2000", studyTrip.getName( ) );
        assertEquals( "Admin", studyTrip.getOrganizer( ) );
        assertEquals("http://localhost/exam03/api/studytrips/2", studyTrip.getStudents());      // TODO: Check
        assertEquals("http://localhost/exam03/api/studytrips/2/students", studyTrip.getSelfLink());     // TODO: check
    }



    @Test
    public void test_hypermedia( ) throws IOException
    {
        final RestApiResponse<StudyTrip> response = getSingleRequestById( HeaderMapUtils.withAcceptJson( ), 1, userName, password );

        checkLinkHeaders( response );
    }

    private void checkLinkHeaders( final RestApiResponse<StudyTrip> response )
    {
        assertLinkHeaderEquals( response, UPDATE_SINGLE_STUDY_TRIP, BASE_URL + "studytrips/{id}" );
        assertLinkHeaderEquals( response, DELETE_SINGLE_STUDY_TRIP, BASE_URL + "studytrips/{id}" );
        assertLinkHeaderEquals( response, SELF, BASE_URL + "studytrip/1" );
    }

    @Test
    public void test_correct_media_type( ) throws IOException
    {
        final RestApiResponse<StudyTrip> response = getSingleRequestById( HeaderMapUtils.withAcceptJson( ), 1, userName, password  );

        assertEquals( 200, response.getLastStatusCode( ) );
    }

    @Test
    public void test_incorrect_media_type( ) throws IOException
    {
        final RestApiResponse<StudyTrip> response = getSingleRequestById( HeaderMapUtils.withAcceptXml( ), 1, userName, password  );

        assertEquals( 406, response.getLastStatusCode( ) );
    }

    @Test
    public void test_conditional_get_304( ) throws IOException
    {
        //user A loads a student
        final RestApiResponse<StudyTrip> responseFromFirstGetRequest = getSingleRequestById( HeaderMapUtils.withAcceptJson( ), 1, userName, password  );

        assertHeaderExists( responseFromFirstGetRequest, ETAG );

        final String etag = responseFromFirstGetRequest.getEtagHeader( );

        //user A revalidates
        final HeaderMap headersForSecondGetRequest = HeaderMapUtils.withAcceptJson( );
        headersForSecondGetRequest.addHeader( IF_NONE_MATCH, etag );

        final RestApiResponse<StudyTrip> responseFromSecondGetRequest = getSingleRequestById( headersForSecondGetRequest, 1, userName, password  );

        assertEquals( 304, responseFromSecondGetRequest.getLastStatusCode( ) );
    }

    @Test
    public void test_conditional_get_200( ) throws IOException
    {
        //user A loads a student
        final RestApiResponse<StudyTrip> responseFromFirstGetRequest = getSingleRequestById( HeaderMapUtils.withAcceptJson( ), 1, userName, password  );
        final StudyTrip studyTrip = responseFromFirstGetRequest.getResponseSingleData( );

        assertHeaderExists( responseFromFirstGetRequest, ETAG );

        final String initialEtag = responseFromFirstGetRequest.getEtagHeader( );

        //user B updates this resource
        studyTrip.setCityName( "Koenigheim" );

        final HeaderMap headersForPutRequestForUserB = HeaderMapUtils.withContentTypeJson( );
        headersForPutRequestForUserB.addHeader( IF_MATCH, initialEtag );

        final RestApiResponse<StudyTrip> responseFromPutRequest = putRequest( headersForPutRequestForUserB, studyTrip, userName, password  );

        assertEquals( 204, responseFromPutRequest.getLastStatusCode( ) );

        final String newEtag = responseFromPutRequest.getEtagHeader( );

        //user A revalidates using old etag
        final HeaderMap headersForSecondGetRequest = HeaderMapUtils.withAcceptJson( );
        headersForSecondGetRequest.addHeader( IF_NONE_MATCH, initialEtag );

        final RestApiResponse<StudyTrip> responseFromSecondGetRequest = getSingleRequestById( headersForSecondGetRequest, 1, userName, password  );

        assertEquals( 200, responseFromSecondGetRequest.getLastStatusCode( ) );
        assertNotNull( responseFromSecondGetRequest.getResponseSingleData( ) );
    }



}
