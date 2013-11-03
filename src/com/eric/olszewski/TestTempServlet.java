package com.eric.olszewski;

import java.io.IOException;

import javax.servlet.ServletException;

import junit.framework.TestCase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import com.mockobjects.servlet.MockHttpServletRequest;
import com.mockobjects.servlet.MockHttpServletResponse;

public class TestTempServlet extends TestCase {

	public void test_no_temperature() throws ServletException, IOException  {
		TestingLabConverterServlet s = new TestingLabConverterServlet();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		String nothing = null;
		request.setupAddParameter("farenheitTemperature", nothing);
		response.setExpectedContentType("text/html");
		s.doGet(request, response);
		response.verify();
		Document doc = Jsoup.parse(response.getOutputStreamContents());
		String temperature = doc.select("h2").first().text();
		assertEquals("Need to enter a temperature!",
				temperature);
	}
	
	public void test_invalid_temperature() throws ServletException, IOException {
		TestingLabConverterServlet s = new TestingLabConverterServlet();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		request.setupAddParameter("farenheitTemperature", "forty");
		response.setExpectedContentType("text/html");
		s.doGet(request, response);
		response.verify();
		Document doc = Jsoup.parse(response.getOutputStreamContents());
		String temperature = doc.select("h2").first().text();
		assertEquals("Got a NumberFormatException on forty",
				temperature);
	}
	
	public void test_invalid_temperature_format() throws ServletException, IOException {
		TestingLabConverterServlet s = new TestingLabConverterServlet();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		request.setupAddParameter("farenheitTemperature", "9.73E2");
		response.setExpectedContentType("text/html");
		s.doGet(request, response);
		response.verify();
		Document doc = Jsoup.parse(response.getOutputStreamContents());
		String temperature = doc.select("h2").first().text();
		assertEquals("Got a NumberFormatException on 9.73E2",
				temperature);
	}
	
	public void test_case_insensitive_parameter() throws ServletException, IOException {
		TestingLabConverterServlet s = new TestingLabConverterServlet();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		request.setupAddParameter("FARENHEITEMPERATURE", "212");
		response.setExpectedContentType("text/html");
		s.doGet(request, response);
		response.verify();
		Document doc = Jsoup.parse(response.getOutputStreamContents());
		String temperature = doc.select("h2").first().text();
		assertEquals("212 Farenheit = 100.00 Celsius",
				temperature);
	}

	public void test_two_decimal_format_100C() throws ServletException, IOException {
		TestingLabConverterServlet s = new TestingLabConverterServlet();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		request.setupAddParameter("farenheitTemperature", "212");
		response.setExpectedContentType("text/html");
		s.doGet(request, response);
		response.verify();
		Document doc = Jsoup.parse(response.getOutputStreamContents());
		String temperature = doc.select("h2").first().text();
		assertEquals("212 Farenheit = 100.00 Celsius",
				temperature);
	}

	public void test_two_decimal_format() throws ServletException, IOException {
		TestingLabConverterServlet s = new TestingLabConverterServlet();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		request.setupAddParameter("farenheitTemperature", "211.5");
		response.setExpectedContentType("text/html");
		s.doGet(request, response);
		response.verify();
		Document doc = Jsoup.parse(response.getOutputStreamContents());
		String temperature = doc.select("h2").first().text();
		assertEquals("211.5 Farenheit = 99.72 Celsius",
				temperature);
	}
	
	public void test_one_decimal_format() throws ServletException, IOException {
		TestingLabConverterServlet s = new TestingLabConverterServlet();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		request.setupAddParameter("farenheitTemperature", "-213.3");
		response.setExpectedContentType("text/html");
		s.doGet(request, response);
		response.verify();
		Document doc = Jsoup.parse(response.getOutputStreamContents());
		String temperature = doc.select("h2").first().text();
		assertEquals("-213.3 Farenheit = -136.3 Celsius",
				temperature);
	}

	public void test_onedecimal_format_200C() throws ServletException, IOException {
		TestingLabConverterServlet s = new TestingLabConverterServlet();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		request.setupAddParameter("farenheitTemperature", "392");
		response.setExpectedContentType("text/html");
		s.doGet(request, response);
		response.verify();
		Document doc = Jsoup.parse(response.getOutputStreamContents());
		String temperature = doc.select("h2").first().text();
		assertEquals("392 Farenheit = 200.0 Celsius",
				temperature);
	}

}
