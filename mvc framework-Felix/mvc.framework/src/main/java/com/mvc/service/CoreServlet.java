package com.mvc.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.exception.XMLParserException;
import com.mvc.model.MVCConfig;
import com.mvc.util.XMLParser;

public class CoreServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1923531759315166142L;

	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		MVCConfigStrategy strategy=new MVCConfigStrategy();
		XMLParser parser=new XMLParser(strategy);
		String projectPath = parser.getClass().getResource("/").getPath();
		try {
			parser.parseXML(projectPath + "resources.xml");
			MVCConfig config=strategy.getConfig();
		} catch (XMLParserException e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	public static void main(String[] args) {
		MVCConfigStrategy strategy=new MVCConfigStrategy();
		XMLParser parser=new XMLParser(strategy);
		String projectPath = parser.getClass().getResource("/").getPath();
		try {
			parser.parseXML(projectPath + "resources.xml");
			MVCConfig config=strategy.getConfig();
			System.out.println(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
