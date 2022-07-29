package de.fhws.fiw.fds.exam02.api.states.students;

import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.exam02.models.StudyTrip;
import de.fhws.fiw.fds.exam03.utils.BearerAuthHelper;
import de.fhws.fiw.fds.exam03.utils.User;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.post.AbstractPostState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;

import javax.ws.rs.NotAuthorizedException;

public class PostStudentState extends AbstractPostState<Student>
{
	public PostStudentState( final Builder builder )
	{
		super( builder );
	}

	@Override protected void authorizeRequest( )
	{
		BearerAuthHelper.accessControl(httpServletRequest, "admin");
	}

	@Override protected NoContentResult saveModel( )
	{
		return DaoFactory.getInstance( ).getStudentDao( ).create( this.modelToStore );
	}

	@Override protected void defineTransitionLinks( )
	{

	}

	public static class Builder extends AbstractPostStateBuilder<Student>
	{
		@Override public AbstractState build( )
		{
			return new PostStudentState( this );
		}
	}
}