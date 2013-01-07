package net.edralzar.jreadability.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import net.edralzar.jreadability.oauth.ReadabilityConst;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class ReadabilityDateAdapter extends TypeAdapter<Date> {

	@Override
	public void write(JsonWriter out, Date value) throws IOException {
		if (value == null) {
			out.nullValue();
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(value);
			cal.setTimeZone(TimeZone.getTimeZone("GMT"));
			out.value(new SimpleDateFormat(
					ReadabilityConst.DATETIME_INPUT_PATTERN).format(cal
					.getTime()));
		}
	}

	@Override
	public Date read(JsonReader in) throws IOException {
		if (in.peek() == JsonToken.NULL) {
			in.nextNull();
			return null;
		}
		TimeZone gmt = TimeZone.getTimeZone("GMT");

		String sDate = in.nextString();
		SimpleDateFormat sdf = new SimpleDateFormat(
				ReadabilityConst.DATETIME_INPUT_PATTERN);
		sdf.setTimeZone(gmt);
		Date parsedDate;
		try {
			parsedDate = sdf.parse(sDate);
		} catch (ParseException e) {
			throw new IOException(e);
		}
		Calendar cal = Calendar.getInstance(gmt);
		cal.setTime(parsedDate);
		cal.setTimeZone(TimeZone.getDefault());
		return cal.getTime();
	}

}
