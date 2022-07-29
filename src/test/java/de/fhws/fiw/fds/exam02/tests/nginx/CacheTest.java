package de.fhws.fiw.fds.exam02.tests.nginx;

import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.tests.models.Student;
import de.fhws.fiw.fds.exam02.tests.student.AbstractStudentTest;
import de.fhws.fiw.fds.exam02.tests.study_trip_students.AbstractStudyTripStudentsTest;
import de.fhws.fiw.fds.exam02.tests.study_trips.AbstractStudyTripTest;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMapUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class CacheTest extends AbstractStudyTripStudentsTest {

    /*
    @Test
    public void testMisscache() throws IOException
    {
        final RestApiResponse<Student> response = getCollectionRequestByUrl( HeaderMapUtils.withAcceptJson( ), defineBaseUrl( ) + "?showAll=false" );
        String cachingheader = response.getCachingHeader();
        assertEquals( cachingheader, "MISS" );
    }

     */


}
