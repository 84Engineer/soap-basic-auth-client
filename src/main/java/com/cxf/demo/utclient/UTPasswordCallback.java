package com.cxf.demo.utclient;

import org.apache.wss4j.common.ext.WSPasswordCallback;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;

/**
 * @author olysenko
 */
public class UTPasswordCallback implements CallbackHandler {

   @Override
   public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
      for (Callback callback : callbacks) {
         if (callback instanceof WSPasswordCallback) {
            WSPasswordCallback passwordCallback = (WSPasswordCallback) callback;
            if (passwordCallback.getIdentifier().equals("admin")) {
               passwordCallback.setPassword("admin");
               return;
            }
         }
      }
   }
}
