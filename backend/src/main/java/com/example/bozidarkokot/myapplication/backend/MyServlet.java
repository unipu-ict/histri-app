/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.example.bozidarkokot.myapplication.backend;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionScopes;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.AnnotateImageResponse;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.ImageSource;
import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.servlet.http.*;

public class MyServlet extends HttpServlet {
    MyServlet landmarkDetection;
    List<EntityAnnotation> entityList;

    /**
     * Be sure to specify the name of your application. If the application name is {@code null} or
     * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
     */
    private static final String APPLICATION_NAME = "HistriApp/1.0";

    private static final int MAX_RESULTS = 4;

    // [START run_application]
    /**
     * Annotates an image using the Vision API.
     */

    /**
     * Connects to the Vision API using Application Default Credentials.
     */
    public static Vision getVisionService() throws IOException, GeneralSecurityException {
        GoogleCredential credential =
                GoogleCredential.getApplicationDefault().createScoped(VisionScopes.all());
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        return new Vision.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
    // [END authenticate]

    // [START detect_gcs_object]
    private  Vision vision = null;

    /**
     * Constructs a {@link DetectLandmark} which connects to the Vision API.
     */
    public  MyServlet(){

    }
    public MyServlet(Vision vision) {
        this.vision = vision;
    }

    /**
     * Gets up to {@code maxResults} landmarks for an image stored at {@code uri}.
     */
    public List<EntityAnnotation> identifyLandmark(String uri, int maxResults) throws IOException {
        AnnotateImageRequest request =
                new AnnotateImageRequest()
                        .setImage(new Image().setSource(
                                new ImageSource().setGcsImageUri(uri)))
                        .setFeatures(ImmutableList.of(
                                new Feature()
                                        .setType("LANDMARK_DETECTION")
                                        .setMaxResults(maxResults)));
        Vision.Images.Annotate annotate =
                vision.images()
                        .annotate(new BatchAnnotateImagesRequest().setRequests(ImmutableList.of(request)));
        // Due to a bug: requests to Vision API containing large images fail when GZipped.
        annotate.setDisableGZipContent(true);

        BatchAnnotateImagesResponse batchResponse = annotate.execute();
        assert batchResponse.getResponses().size() == 1;
        AnnotateImageResponse response = batchResponse.getResponses().get(0);
        if (response.getLandmarkAnnotations() == null) {
            throw new IOException(
                    response.getError() != null
                            ? response.getError().getMessage()
                            : "Unknown error getting image annotations");
        }
        return response.getLandmarkAnnotations();
    }


    // [END detect_gcs_object]


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            landmarkDetection = new MyServlet(getVisionService());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        //androidPermissions.readWritePermission();
        //FetchQuestions.fetchDiscounts(this);
        // FetchQuestions.fetchRanking(this);
        try {
            entityList =  landmarkDetection.identifyLandmark("gs://histriapppula/demo-image.jpg",MAX_RESULTS);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            landmarkDetection = new MyServlet(getVisionService());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        //androidPermissions.readWritePermission();
        //FetchQuestions.fetchDiscounts(this);
        // FetchQuestions.fetchRanking(this);
        try {
            entityList =   landmarkDetection.identifyLandmark("gs://histriapppula/demo-image.jpg",MAX_RESULTS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
