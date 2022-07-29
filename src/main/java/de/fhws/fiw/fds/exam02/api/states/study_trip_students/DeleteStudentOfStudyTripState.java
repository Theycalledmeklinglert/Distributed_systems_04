package de.fhws.fiw.fds.exam02.api.states.study_trip_students;

import de.fhws.fiw.fds.exam02.api.hypermedia.rel_types.IStudyTripStudentRelTypes;
import de.fhws.fiw.fds.exam02.api.hypermedia.uris.IStudyTripStudentUri;
import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.exam02.models.StudyTrip;
import de.fhws.fiw.fds.exam03.utils.BearerAuthHelper;
import de.fhws.fiw.fds.exam03.utils.User;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.delete.AbstractDeleteRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;

import javax.ws.rs.NotAuthorizedException;

public class DeleteStudentOfStudyTripState extends AbstractDeleteRelationState<Student>
{
	public DeleteStudentOfStudyTripState( final Builder builder )
	{
		super( builder );
	}

	@Override protected void authorizeRequest( )
	{
		User user = BearerAuthHelper.accessControl(httpServletRequest, "admin", "lecturer");

		if(!user.getRole().equals("admin"))
		{
			StudyTrip checkAgainst = DaoFactory.getInstance().getStudyTripDao().readById(primaryId).getResult();
			if (!user.getId().equals(checkAgainst.getOrganizer())) throw new NotAuthorizedException("");
		}
	}

	@Override
	protected SingleModelResult<Student> loadModel( )
	{
		return DaoFactory.getInstance( ).getStudyTripStudentDao( ).readById( this.primaryId, this.modelIdToDelete );
	}

	@Override
	protected NoContentResult deleteModel( )
	{
		return DaoFactory.getInstance( ).getStudyTripStudentDao( ).deleteRelation( this.primaryId, this.modelIdToDelete );
	}

	@Override
	protected void defineTransitionLinks( )
	{
		addLink(
			IStudyTripStudentUri.REL_PATH,
			IStudyTripStudentRelTypes.GET_ALL_LINKED_STUDENTS,
			getAcceptRequestHeader( ),
			this.primaryId );
	}

	public static class Builder extends AbstractDeleteRelationStateBuilder
	{
		@Override
		public AbstractState build( )
		{
			return new DeleteStudentOfStudyTripState( this );
		}
	}
}