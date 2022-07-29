package de.fhws.fiw.fds.exam02.tests.student;

import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.tests.models.Student;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMap;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMapUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class AuthorizationStudentTest extends AbstractStudentTest {

    public final static String UPDATE_STUDENT = "updateStudent";

    public final static String DELETE_STUDENT = "deleteStudent";

    @Test
    public void testGet_Authorized_200( ) throws IOException
    {
        RestApiResponse<Student> response = getSingleRequestById( HeaderMapUtils.withAcceptJson( ), 1, "Admin", "secret" );

        assertEquals( 200, response.getLastStatusCode( ) );

        Student student = response.getResponseSingleData( );

        assertNotNull( student );

        response = getSingleRequestById( HeaderMapUtils.withAcceptJson( ), 1, "Lecturer", "secret" );

        assertEquals( 200, response.getLastStatusCode( ) );

        student = response.getResponseSingleData( );

        assertNotNull( student );
    }

    @Test
    public void testGet_Unauthorized_401( ) throws IOException
    {
        final RestApiResponse<Student> response = getSingleRequestById( HeaderMapUtils.withAcceptJson( ), 1, "random", "user" );

        assertEquals( 401, response.getLastStatusCode( ) );

        final Student student = response.getResponseSingleData( );

        assertNull( student );

    }

        @Test
        public void testPost_Authorized_200( ) throws IOException
        {
            final RestApiResponse<Student> response = postRequest( HeaderMapUtils.withContentTypeJson( ), defineNewResource( ), "Admin", "secret"  );
            final String locationHeader = response.getLocationHeader( );

            assertEquals( 201, response.getLastStatusCode( ) );
            assertNotNull( locationHeader );
            assertTrue( locationHeader.startsWith( BASE_URL + "students" ) );
        }

    @Test
    public void testPost_Unauthorized_401( ) throws IOException
    {
        final RestApiResponse<Student> response = postRequest( HeaderMapUtils.withContentTypeJson( ), defineNewResource( ), "Lecturer", "secret"  );
        final String locationHeader = response.getLocationHeader( );

        assertEquals( 401, response.getLastStatusCode( ) );
        assertNull( locationHeader );
    }

        private Student defineNewResource( )
        {
            return new Student(
                    "Patrick",
                    "Müller",
                    "patrick.mueller@fhws.de",
                    "BIN",
                    5,
                    1234 );
        }

    @Test
    public void testPutAuthorized_204( ) throws IOException
    {
        final Student student = defineStudent( );

        final RestApiResponse<Student> response = putRequest( HeaderMapUtils.withContentTypeJson( ), student, "Admin", "secret"  );

        assertEquals( 204, response.getLastStatusCode( ) );
    }

    @Test
    public void testPutUnauthorized_401( ) throws IOException
    {
        final Student student = defineStudent( );

        final RestApiResponse<Student> response = putRequest( HeaderMapUtils.withContentTypeJson( ), student, "Lecturer", "secret"  );

        assertEquals( 401, response.getLastStatusCode( ) );
    }

    private Student defineStudent( )
    {
        final Student student = new Student(
                "Max",
                "Mustermann",
                "max.mustermann@fhws.de",
                "BIN",
                2,
                1111 );

        student.setId( 1 );

        return student;
    }

    @Test
    public void test_Authorized_Post_And_Authorized_Delete_204_and_404( ) throws IOException
    {
        //post new resource
        final RestApiResponse<Student> responseFromPostRequest = postRequest( HeaderMapUtils.withContentTypeJson( ), defineNewResource( ), "Admin", "secret" );

        System.out.println(responseFromPostRequest.getLastStatusCode());

        final String locationHeader = responseFromPostRequest.getLocationHeader( );

        //get new resource
        final RestApiResponse<Student> responseFromFirstGetRequest = getSingleRequestByUrl( HeaderMapUtils.withAcceptJson( ), locationHeader, "Admin", "secret" );

        final Student studentToDelete = responseFromFirstGetRequest.getResponseSingleData( );

        assertNotNull( studentToDelete );

        //delete new resource
        final RestApiResponse<Student> responseFromDeleteRequest = deleteRequest( HeaderMapUtils.empty( ), studentToDelete, "Admin", "secret" );

        assertEquals( 204, responseFromDeleteRequest.getLastStatusCode( ) );

        //try to get deleted resource
        final RestApiResponse<Student> responseFromSecondGetRequest = getSingleRequestByUrl( HeaderMapUtils.withAcceptJson( ), locationHeader, "Admin", "secret" );

        assertEquals( 404, responseFromSecondGetRequest.getLastStatusCode( ) );

        final Student studentFromSecondGetRequest = responseFromSecondGetRequest.getResponseSingleData( );

        assertNull( studentFromSecondGetRequest );
    }


    @Test
    public void test_Authorized_Post_And_Unauthorized_Delete_403( ) throws IOException
    {
        //post new resource
        final RestApiResponse<Student> responseFromPostRequest = postRequest( HeaderMapUtils.withContentTypeJson( ), defineNewResource( ), "Admin", "secret" );

        System.out.println(responseFromPostRequest.getLastStatusCode());

        final String locationHeader = responseFromPostRequest.getLocationHeader( );

        //get new resource
        final RestApiResponse<Student> responseFromFirstGetRequest = getSingleRequestByUrl( HeaderMapUtils.withAcceptJson( ), locationHeader, "Lecturer", "secret" );

        final Student studentToDelete = responseFromFirstGetRequest.getResponseSingleData( );

        assertNotNull( studentToDelete );

        //delete new resource
        final RestApiResponse<Student> responseFromDeleteRequest = deleteRequest( HeaderMapUtils.empty( ), studentToDelete, "Lecturer", "secret" );

        assertEquals( 403, responseFromDeleteRequest.getLastStatusCode( ) );

        //try to get deleted resource
        final RestApiResponse<Student> responseFromSecondGetRequest = getSingleRequestByUrl( HeaderMapUtils.withAcceptJson( ), locationHeader, "Admin", "secret" );

        assertEquals( 200, responseFromSecondGetRequest.getLastStatusCode( ) );

        final Student studentFromSecondGetRequest = responseFromSecondGetRequest.getResponseSingleData( );

        assertNull( studentFromSecondGetRequest );
    }









}
