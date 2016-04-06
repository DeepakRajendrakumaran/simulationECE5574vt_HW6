package edu.vt.ece5574.gae;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import edu.vt.ece5574.sim.Configuration;


public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
        List<BlobKey> blobKeys = blobs.get("myFile");
        
        Configuration config = Configuration.getInstance();
      

        if (blobKeys == null || blobKeys.isEmpty()) {
            res.sendRedirect("/");
        } else {
            //res.sendRedirect("/serve?blob-key=" + blobKeys.get(0).getKeyString());
        	 config.load(blobKeys.get(0));
        	res.sendRedirect("/index.jsp");
        	
        }
    }
}