package de.fhws.fiw.fds.exam02.api.states.study_trips;

import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.models.StudyTrip;
import de.fhws.fiw.fds.exam03.utils.BearerAuthHelper;
import de.fhws.fiw.fds.exam03.utils.User;
import de.fhws.fiw.fds.exam03.utils.Users;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.post.AbstractPostState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;

public class PostStudyTripState extends AbstractPostState<StudyTrip>
{
	public PostStudyTripState( final AbstractPostStateBuilder<StudyTrip> builder )
	{
		super( builder );
	}

	@Override protected void authorizeRequest( )
	{
		User user = BearerAuthHelper.accessControl(httpServletRequest, "admin", "lecturer");
		this.modelToStore.setOrganizer(user.getId());
	}

	@Override protected NoContentResult saveModel( )
	{
		return DaoFactory.getInstance( ).getStudyTripDao( ).create( this.modelToStore );
	}

	@Override protected void defineTransitionLinks( )
	{

	}

	public static class Builder extends AbstractPostStateBuilder<StudyTrip>
	{
		@Override public AbstractState build( )
		{
			return new PostStudyTripState( this );
		}
	}
}