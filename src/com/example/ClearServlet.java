package com.example;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ClearServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query = "select from " + Data.class.getName();
        try {
            List<Data> datas = (List<Data>)pm.newQuery(query).execute();
            pm.deletePersistentAll(datas);
        } finally {
            pm.close();
        }

		resp.setContentType("text/plain");
		resp.getWriter().print("cleared");
	}
}
