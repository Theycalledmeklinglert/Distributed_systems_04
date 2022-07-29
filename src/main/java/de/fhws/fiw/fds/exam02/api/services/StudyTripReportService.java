package de.fhws.fiw.fds.exam02.api.services;

import de.fhws.fiw.fds.exam02.api.states.study_trip_reports.GetStudyTripReportState;
import de.fhws.fiw.fds.exam02.models.StudyTripReportEntry;
import de.fhws.fiw.fds.exam03.utils.csvConverter.StudyTripReportEntryCsvMessageBodyWriter;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingBehaviorUsingOffsetSize;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;

import static de.fhws.fiw.fds.sutton.server.api.queries.PagingBehaviorUsingOffsetSize.*;

@Path("studyTripReport")
public class StudyTripReportService extends AbstractService {

    @GET
    @Produces( { MediaType.APPLICATION_JSON, StudyTripReportEntryCsvMessageBodyWriter.TEXT_CSV } )
    public Response getSingleStudyTrip(@QueryParam( "startDate" ) final LocalDate startDate,
                                       @DefaultValue( "" ) @QueryParam( "endDate" ) final LocalDate endDate, @DefaultValue( "0" ) @QueryParam( QUERY_PARAM_OFFSET ) final int offset,
                                       @DefaultValue( DEFAULT_PAGE_SIZE_STR ) @QueryParam( QUERY_PARAM_SIZE ) final int size )
    {

        final AbstractQuery<StudyTripReportEntry> query = new GetStudyTripReportState.ByAttributes(startDate, endDate);

        query.setPagingBehavior( new PagingBehaviorUsingOffsetSize<>( offset, size ) );

            return new GetStudyTripReportState.Builder( )
                    .setQuery( query )
                    .setUriInfo( this.uriInfo )
                    .setRequest( this.request )
                    .setHttpServletRequest( this.httpServletRequest )
                    .setContext( this.context )
                    .build( )
                    .execute( );

    }

}
