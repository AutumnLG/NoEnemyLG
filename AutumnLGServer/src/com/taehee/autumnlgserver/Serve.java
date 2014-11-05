package com.taehee.autumnlgserver;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.FileInfo;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.utils.SystemProperty;

@SuppressWarnings("serial")
public class Serve extends HttpServlet {
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

//		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
//		
//
////		BlobKey blobKey = blobstoreService.createGsBlobKey("/gs/" + fileInfo.getFilename().getBucketName() + "/" + fileName.getObjectName());
//		BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
//
//		blobstoreService.serve(blobKey, res);
//		
//		
		
		String[] parts = req.getRequestURI().split("/");
        String gsObjectName = URLDecoder.decode(parts[parts.length - 1], "UTF-8");

        BlobKey blobKey = blobstoreService.createGsBlobKey(gsObjectName);

        blobstoreService.serve(blobKey, res);
	}
}


class ServeUrl {
    /**
     * Maximum content size allowed to respond with, in bytes.
     */
    public static final int MAX_FILE_SIZE = 31457280;

    /**
     * Google Cloud Storage (GCS) base URL. See Request URIs for more details:
     * https://developers.google.com/storage/docs/reference-uris
     */
    public static final String GCS_HOST = "https://storage.googleapis.com/";

    private boolean devMode;

    private BlobstoreService blobstore = 
            BlobstoreServiceFactory.getBlobstoreService();

    private ImagesService images = 
            ImagesServiceFactory.getImagesService();

    public ServeUrl() {
        this.devMode = SystemProperty.environment.value() == 
                SystemProperty.Environment.Value.Development;
    }

    /**
     * Creates a serving URL based on provided file info:
     * <ul>
     * <li>use Images API if content type starts with "image/"</li>
     * <li>construct a direct GCS URI link to serve from storage.googleapis.com
     * if file size is > 32Mb</li>
     * <li>otherwise, serve using ServeBlobServlet from the app</li>
     * </ul>
     * 
     * Reading blobs uploaded to a GCS bucked-based URLs is not currenly
     * supported in dev server, so in "dev" mode all serving URL will have a
     * value of <code>"/dev-does-not-support-gcs-serving-yet"</code>.
     * 
     * @param file Typically obtained from BlobstoreService.getFileInfos().
     * @return A permanent serving URL.
     */
    public String guessServingUrl(FileInfo file) {
        if (devMode) {
            return "/dev-does-not-support-gcs-serving-yet";
        }

        if (file.getContentType().startsWith("image/")) {
            return getImageServingUrl(file);
        } else if (file.getSize() > MAX_FILE_SIZE) {
            return getGcsServingUrl(file);
        } else {
            return getServletServingPath(file);
        }
    }

    /**
     * Creates a URL which is served by {@link ServeBlobServlet}. Note that App
     * Engine app response limit is 32Mb so files larger than 32Mb will throw
     * errors during serving.
     * 
     * @param file Typically obtained from BlobstoreService.getFileInfos().
     */
    public String getServletServingPath(FileInfo file) {
        try {
            return "/serve/"
                    + URLEncoder.encode(file.getGsObjectName(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a direct GCS-based serving URL in a form of
     * https://storage.googleapis.com/bucket/object
     * 
     * Note that the bucket has to have default ACL set to public-read.
     * Otherwise users won't be able to download the file contents.
     * 
     * @param file Typically obtained from BlobstoreService.getFileInfos().
     * @return URL that points to Google GCS, e.g
     *         bucket.storage.googleapis.com/object
     */
    public String getGcsServingUrl(FileInfo file) {
        String objectName = file.getGsObjectName();
        if (objectName == null) {
            throw new IllegalArgumentException("File was not uploaded to GCS");
        }
        // substring(4) strips "/gs/" prefix
        return GCS_HOST + objectName.substring(4);
    }

    /**
     * Uses Images API to create a serving URL.
     * 
     * @param file Typically obtained from BlobstoreService.getFileInfos().
     */
    public String getImageServingUrl(FileInfo file) {
        BlobKey blobKey = blobstore.createGsBlobKey(file.getGsObjectName());
        ServingUrlOptions opts = ServingUrlOptions.Builder.
                withBlobKey(blobKey).
                secureUrl(true);
        return images.getServingUrl(opts);
    }
}