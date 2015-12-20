/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.sheajohnh.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.sheajohnh.JokeMaker;

/** An endpoint class we are exposing */
@Api(
  name = "myApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.myapplication.sheajohnh.com",
    ownerName = "backend.myapplication.sheajohnh.com",
    packagePath=""
  )
)
public class MyEndpoint {

    /** A simple endpoint method that returns one joke */
    @ApiMethod(name = "getJoke")
    public MyBean getJoke() {
        MyBean response = new MyBean();

        JokeMaker jokeMaker = new JokeMaker();
        String newJoke = jokeMaker.getJoke();
        response.setData(newJoke);

        return response;
    }

}
