package de.fhws.fiw.fds.exam02.api.states.study_trip_reports;

import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.database.spi.IStudyTripStudentDao;
import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.exam02.models.StudyTrip;
import de.fhws.fiw.fds.exam02.models.StudyTripReportEntry;
import de.fhws.fiw.fds.exam02.utils.study_trip.StudyTripDateUtils;
import de.fhws.fiw.fds.exam03.utils.BearerAuthHelper;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionState;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class GetStudyTripReportState extends AbstractGetCollectionState<StudyTripReportEntry>
{
	public GetStudyTripReportState(final AbstractGetCollectionState.AbstractGetCollectionStateBuilder builder )
	{
		super( builder );
	}

	@Override protected void authorizeRequest( )
	{
		BearerAuthHelper.accessControl(httpServletRequest, "admin");
	}

	@Override protected void defineTransitionLinks( )
	{
		// TODO: Welche Transition Links werden hier gebraucht?
	}

	@Override protected Response createResponse( )
	{
		return super.createResponse( );
	}

	@Override
	protected void defineHttpResponseBody() {
		this.responseBuilder.entity( new GenericEntity<Collection<StudyTripReportEntry>>( this.result.getResult( ) )
		{
		} );
	}

	public static class ByAttributes extends AbstractQuery<StudyTripReportEntry>
	{

		protected final LocalDate intervalStart;

		protected final LocalDate intervalEnd;

		public ByAttributes(
				final LocalDate intervalStart,
				final LocalDate intervalEnd)
		{
			this.intervalStart = intervalStart;
			this.intervalEnd = intervalEnd;
		}

		@Override
		protected CollectionModelResult<StudyTripReportEntry> doExecuteQuery( )
		{
			final Collection<StudyTrip> studyTripsFromDb =
					DaoFactory.getInstance( ).getStudyTripDao( ).readByPredicate( byAttributes( ) ).getResult( );

			final List<StudyTrip> sortedStudyTrips = new ArrayList<>( studyTripsFromDb );
			sortedStudyTrips.sort( StudyTrip.getComparator( ) );

			IStudyTripStudentDao storage = DaoFactory.getInstance().getStudyTripStudentDao();

			final List<StudyTripReportEntry> entrys = new ArrayList<>();

			for(StudyTrip trip : sortedStudyTrips)
			{
				Collection<Student> students = storage.readByPredicate(trip.getId(), new Predicate<Student>() {
					@Override
					public boolean test(Student student) {
						return true;
					}
				}).getResult();
				StudyTripReportEntry entry = new StudyTripReportEntry(trip.getCityName(), trip.getCountryName(), students.size(), (int) ChronoUnit.DAYS.between(trip.getStartDate(), trip.getEndDate()));
				entrys.add(entry);
			}

			return new CollectionModelResult<>( entrys );
		}

		protected Predicate<StudyTrip> byAttributes( )
		{
			return s -> matchDate( s );}

		private boolean matchDate( final StudyTrip studyTrip )
		{
			boolean isMatch = true;

			if ( this.intervalStart != null && this.intervalEnd != null )
			{
				isMatch =
						new StudyTripDateUtils(
								studyTrip,
								this.intervalStart,
								this.intervalEnd ).isStudyTripWithinInterval( );
			}

			return isMatch;
		}
	}


	public static class Builder extends AbstractGetCollectionStateBuilder
	{
		@Override public AbstractState build( )
		{
			return new GetStudyTripReportState( this );
		}
	}
}