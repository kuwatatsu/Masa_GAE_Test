package com.example;
import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class UpdateServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String id = req.getParameter("id");
		double x = Double.parseDouble(req.getParameter("x"));
		double y = Double.parseDouble(req.getParameter("y"));
		double z = Double.parseDouble(req.getParameter("z"));
		
		Data new_data = new Data(id, x, y, z);
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query = "select from " + Data.class.getName() + " where id == '" + id + "'";
		String query_all = "select from " + Data.class.getName();
    	StringBuffer sb = new StringBuffer();
        try {
            List<Data> old_datas = (List<Data>)pm.newQuery(query).execute();
            if (old_datas.isEmpty()) {
            	pm.makePersistent(new_data);
            } else {
            	Data old_data = old_datas.get(0);
            	old_data.setX(x);
            	old_data.setY(y);
            	old_data.setZ(z);
            	pm.deletePersistent(old_data);
            	pm.makePersistentAll(new_data);
            }
            List<Data> datas = (List<Data>)pm.newQuery(query_all).execute();
            for(Data l : datas) {
            	sb.append(l.getN());
            	sb.append(',');
            	sb.append(l.getId());
            	sb.append(',');
            	sb.append(l.getX());
            	sb.append(',');
            	sb.append(l.getY());
            	sb.append(',');
            	sb.append(l.getZ());
            	sb.append('\n');
            }
        } finally {
            pm.close();
        }

		resp.setContentType("text/csv");
		resp.getWriter().print(sb.toString());
	}
}
