package com.cxf.demo.utclient;

import com.bharath.ws.soap.PaymentProcessor;
import com.bharath.ws.soap.PaymentProcessorImplService;
import com.bharath.ws.soap.PaymentProcessorRequest;
import com.bharath.ws.soap.PaymentProcessorResponse;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author olysenko
 */
public class PaymentWSClient {

   public static void main(String[] args) throws MalformedURLException {
      PaymentProcessorImplService service = new PaymentProcessorImplService(
            new URL("http://localhost:8080/javaFirstWs/paymentProcessor?wsdl"));

      PaymentProcessor port = service.getPaymentProcessorImplPort();

      Client client = ClientProxy.getClient(port);
      Endpoint endpoint = client.getEndpoint();

      Map<String, Object> props = new HashMap<>();
      props.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
      props.put(WSHandlerConstants.USER, "admin");
      props.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
      props.put(WSHandlerConstants.PW_CALLBACK_CLASS, UTPasswordCallback.class.getName());

      WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(props);

      endpoint.getOutInterceptors().add(wssOut);

      PaymentProcessorResponse response =
            port.processPayment(new PaymentProcessorRequest());

      System.out.println(response.isResult());

   }

}
