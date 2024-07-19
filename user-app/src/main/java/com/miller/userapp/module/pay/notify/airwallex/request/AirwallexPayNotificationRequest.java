package com.miller.userapp.module.pay.notify.airwallex.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class AirwallexPayNotificationRequest {
    @JSONField(name = "sourceId")
    private String sourceId;
    @JSONField(name = "accountId")
    private String accountId;
    @JSONField(name = "account_id")
    private String account_id;
    @JSONField(name = "createdAt")
    private String createdAt;
    @JSONField(name = "created_at")
    private String created_at;
    private DataObject data;
    private String name;
    private String id;
    private String version;
    @Data
    public static class DataObject{
        private PaymentConsent object;
        @Data
        public static class PaymentConsent{
            private String baseCurrency;
            private int amount;
            private Metadata metadata;
            private String merchantOrderId;
            private String createdAt;
            private String descriptor;
            private String paymentConsentId;
            private String updatedAt;
            private int capturedAmount;
            private int baseAmount;
            private LatestPaymentAttempt latestPaymentAttempt;
            private String currency;
            private String id;
            private String customerId;
            private String requestId;
            private Order order;
            private String status;
            @Data
            public static class LatestPaymentAttempt{
                private String providerTransactionId;
                private int amount;
                private String paymentIntentId;
                private String merchantOrderId;
                private String providerOriginalResponseCode;
                private String createdAt;
                private String paymentConsentId;
                private int refundedAmount;
                private String updatedAt;
                private AuthenticationData authenticationData;
                private String authorizationCode;
                private int capturedAmount;
                private String currency;
                private String settleVia;
                private String id;
                private PaymentMethodOptions paymentMethodOptions;
                private PaymentMethod paymentMethod;
                private String status;
                @Data
                public static class AuthenticationData{
                    private String cvcCode;
                    private FraudData fraudData;
                    private String cvcResult;
                    private DsData dsData;
                    private String avsResult;
                    @Data
                    public static class FraudData {
                        private String score;
                        private List<Object> riskFactors;
                        private String action;
                    }
                    @Data
                    public static class DsData{
                        private int retryCountForAuthDecline;
                    }

                }
                @Data
                public static class PaymentMethodOptions{
                    private Card card;

                }
                @Data
                public static class PaymentMethod{
                    private String updatedAt;
                    private String createdAt;
                    private String id;
                    private String customerId;
                    private String type;
                    private Card card;
                    private String status;

                }

            }
            @Data
            public static class Order{
                private String type;
                private List<ProductsItem> products;
                @Data
                public static class ProductsItem {
                    private String name;
                    private String type;
                }
            }
            @Data
            public static class Metadata{
                @JSONField(name="cardNoMd5")
                private String cardNoMd5;

            }
        }

    }
}
