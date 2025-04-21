package com.veganSitavi.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.veganSitavi.service.ItemService;
import com.veganSitavi.service.PaypalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/payment")
public class PaypalController {

    private static final Logger log = LoggerFactory.getLogger(PaypalController.class);
    
    private final ItemService itemService;
    private final PaypalService paypalService;

    public PaypalController(ItemService itemService, PaypalService paypalService) {
        this.itemService = itemService;
        this.paypalService = paypalService;
    }

    @GetMapping("/facturar")
    public RedirectView createPayment(@RequestParam("total") double total) {
        if (total > 0) {
            try {
                String urlPaypalCancel = "http://localhost/payment/cancel";
                String urlPaypalSuccess = "http://localhost/payment/success";
                Payment payment = paypalService.createPayment(
                        total,
                        "USD",
                        "paypal",
                        "sale",
                        "Facturación en TechShop",
                        urlPaypalCancel,
                        urlPaypalSuccess);
                for (Links links : payment.getLinks()) {
                    if (links.getRel().equals("approval_url")) {
                        return new RedirectView(links.getHref());
                    }
                }
            } catch (PayPalRESTException e) {
                log.error("Error en pago: ", e);
            }
        }
        return new RedirectView("/payment/error");
    }

    @GetMapping("/success")
    public String paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                itemService.facturar();
                return "/paypal/pagoSuccess";
            }
        } catch (PayPalRESTException e) {
            log.error("Error en pago: ", e);
        }
        return "/paypal/pagoSuccess";
    }

    @GetMapping("/cancel")
    public String paymentCancel() {
        return "/paypal/pagoCancel";
    }

    @GetMapping("/error")
    public String paymentError() {
        return "/paypal/pagoError";
    }
}