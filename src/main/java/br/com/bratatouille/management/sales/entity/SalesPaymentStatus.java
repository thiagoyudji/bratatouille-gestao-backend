package br.com.bratatouille.management.sales.entity;

public enum SalesPaymentStatus {
    PENDING,
    APPROVED,
    DECLINED,
    CANCELED;

    public static SalesPaymentStatus fromProviderStatus(String providerStatus) {
        if (providerStatus == null || providerStatus.isBlank()) {
            return PENDING;
        }

        String normalized = providerStatus.trim().toUpperCase();

        if (normalized.contains("APPROV") || normalized.contains("PAID") || normalized.contains("CAPTURE") || normalized.contains("SETTLED")) {
            return APPROVED;
        }

        if (normalized.contains("CANCEL")) {
            return CANCELED;
        }

        if (normalized.contains("DECLIN") || normalized.contains("DENIED") || normalized.contains("FAIL") || normalized.contains("REJECT")) {
            return DECLINED;
        }

        if (normalized.contains("PEND") || normalized.contains("PROCESS")) {
            return PENDING;
        }

        return PENDING;
    }
}
