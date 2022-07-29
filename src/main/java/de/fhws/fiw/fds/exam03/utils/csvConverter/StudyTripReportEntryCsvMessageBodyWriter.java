package de.fhws.fiw.fds.exam03.utils.csvConverter;

import de.fhws.fiw.fds.exam02.models.StudyTripReportEntry;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

@Provider
@Produces( StudyTripReportEntryCsvMessageBodyWriter.TEXT_CSV )
public class StudyTripReportEntryCsvMessageBodyWriter implements MessageBodyWriter<Collection<StudyTripReportEntry>>
{
	public static final String TEXT_CSV = "text/csv";

	@Override
	public boolean isWriteable( final Class<?> type, final Type genericType, final Annotation[] annotations,
								final MediaType mediaType )
	{
		return genericType.getTypeName( ).contains( "StudyTripReport" );
	}

	@Override public long getSize(final Collection<StudyTripReportEntry> entries, final Class<?> type, final Type genericType,
								  final Annotation[] annotations,
								  final MediaType mediaType )
	{
		return -1;
	}

	@Override public void writeTo( final Collection<StudyTripReportEntry> entries, final Class<?> type, final Type genericType,
								   final Annotation[] annotations,
								   final MediaType mediaType, final MultivaluedMap<String, Object> httpHeaders, final OutputStream entityStream )
			throws IOException, WebApplicationException
	{
		final StudyTripReportEntryCsvConverter converter = new StudyTripReportEntryCsvConverter(new OutputStreamWriter(entityStream));
		converter.write(entries);
	}

}
